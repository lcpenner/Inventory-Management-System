package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import static controller.MainScreenController.showAlert;

/**This class is the controller for the Modify Part screen. */
public class ModifyPartController implements Initializable {

    private Part partToModify = null;

    /**This method is used to identify the part selected to be modified. */
    public void sendPart(Part part)
    {
        this.partToModify = part;

        if (partToModify instanceof InHouse) {
            modifyPartInHouseBtn.setSelected(true);
            modifyPartId.setText(String.valueOf((partToModify.getId())));
            modifyPartNameTxt.setText(partToModify.getName());
            modifyPartInvTxt.setText(String.valueOf(partToModify.getStock()));
            modifyPartPriceTxt.setText(String.valueOf(partToModify.getPrice()));
            modifyPartMaxTxt.setText(String.valueOf(partToModify.getMax()));
            modifyPartMinTxt.setText(String.valueOf(partToModify.getMin()));
            modifyPartTypeLabel.setText("Machine ID");
            modifyPartManufacturerTxt.setText(String.valueOf(((InHouse) partToModify).getMachineId()));
        }

        if (partToModify instanceof Outsourced){
            modifyPartOutsourcedBtn.setSelected(true);
            modifyPartId.setText(String.valueOf((partToModify.getId())));
            modifyPartNameTxt.setText(partToModify.getName());
            modifyPartInvTxt.setText(String.valueOf(partToModify.getStock()));
            modifyPartPriceTxt.setText(String.valueOf(partToModify.getPrice()));
            modifyPartMaxTxt.setText(String.valueOf(partToModify.getMax()));
            modifyPartMinTxt.setText(String.valueOf(partToModify.getMin()));
            modifyPartTypeLabel.setText("Company Name");
            modifyPartManufacturerTxt.setText(String.valueOf(((Outsourced) partToModify).getCompanyName()));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
    }

    @FXML
    private Label modifyPartTypeLabel;

    @FXML
    private RadioButton modifyPartInHouseBtn;

    @FXML
    private TextField modifyPartInvTxt;

    @FXML
    private TextField modifyPartManufacturerTxt;

    @FXML
    private TextField modifyPartId;

    @FXML
    private TextField modifyPartMaxTxt;

    @FXML
    private TextField modifyPartMinTxt;

    @FXML
    private TextField modifyPartNameTxt;

    @FXML
    private RadioButton modifyPartOutsourcedBtn;

    @FXML
    private TextField modifyPartPriceTxt;

    @FXML
    void onActionModifyPartInHouseBtn(ActionEvent event){
       modifyPartTypeLabel.setText("Machine ID");
       modifyPartManufacturerTxt.setText("");
    }

    @FXML
    void onActionModifyPartOutsourcedBtn(ActionEvent event){
        modifyPartTypeLabel.setText("Company Name");
        modifyPartManufacturerTxt.setText("");
    }

    /**This method takes the user back to the main screen. */
    @FXML
    void onActionDisplayMainScreen(ActionEvent event) throws IOException  {

        Stage stage = (Stage)((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method saves the modified part and returns the user to the Main Screen. */
    @FXML
    void onActionModifyPartSave(ActionEvent event) throws IOException {
    try {
        String name = modifyPartNameTxt.getText();
        int stock = Integer.parseInt(modifyPartInvTxt.getText());
        double price = Double.parseDouble(modifyPartPriceTxt.getText());
        int max = Integer.parseInt(modifyPartMaxTxt.getText());
        int min = Integer.parseInt(modifyPartMinTxt.getText());
        int machineId;
        String companyName;

        if (min >= max) {
            showAlert(5);
            return;
        }

        if (stock > max) {
            showAlert(6);
            return;
        }

        if (stock < min) {
            showAlert(7);
            return;
        }

        if (modifyPartInHouseBtn.isSelected()) {
            machineId = Integer.parseInt(modifyPartManufacturerTxt.getText());
            Inventory.updatePart(new InHouse(partToModify.getId(), name, price, stock, min, max, machineId));
        }

        if (modifyPartOutsourcedBtn.isSelected()) {
            companyName = modifyPartManufacturerTxt.getText();
            Inventory.updatePart(new Outsourced(partToModify.getId(), name, price, stock, min, max, companyName));
        }
    }

    catch(Exception e){
        showAlert(9);
        return;
    }

        //Return back to main screen
        Stage stage = (Stage)((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(scene));
        stage.show();

    }

}
