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
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import static controller.MainScreenController.showAlert;

/**This class is the controller for the Add Part screen. RUNTIME ERROR: A runtime error occurred when a letter was entered into the "stock" text field.
 This was corrected by implementing try/catch to verify all the data entered into the text fields is valid.*/
public class AddPartController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb){
    }

    @FXML
    private RadioButton addPartInHouseBtn;

    @FXML
    private TextField addPartInvTxt;

    @FXML
    private TextField addPartManufacturerTxt;

    @FXML
    private TextField addPartMaxTxt;

    @FXML
    private TextField addPartMinTxt;

    @FXML
    private TextField addPartNameTxt;

    @FXML
    private RadioButton addPartOutsourcedBtn;

    @FXML
    private TextField addPartPriceTxt;

    @FXML
    private ToggleGroup partTypeTG;

    @FXML
    private Label partTypeLabel;

/**This method returns the user to the main screen*/
    @FXML
    void onActionDisplayMainScreen(ActionEvent event) throws IOException {

        Stage stage = (Stage)((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(scene));
        stage.show();

    }

/**When the In-house button is clicked, this method changes the prompt label to Machine ID. */
    @FXML
    void onActionInHouseButton(ActionEvent event){
        partTypeLabel.setText("Machine ID");
    }

    /**When the Outsourced button is clicked, this method changes the prompt label to Company Name. */
    @FXML
    void onActionOutsourcedButton(ActionEvent event){
        partTypeLabel.setText("Company Name");
    }

/**This method stores the part. */
    @FXML
    void onActionAddPartSave(ActionEvent event) throws IOException {
    try {
        int id = Inventory.getNextId();
        Inventory.incrementNextId();
        String name = addPartNameTxt.getText();
        int stock = Integer.parseInt(addPartInvTxt.getText());
        double price = Double.parseDouble(addPartPriceTxt.getText());
        int max = Integer.parseInt(addPartMaxTxt.getText());
        int min = Integer.parseInt(addPartMinTxt.getText());
        int machineId;
        String companyName;

        if (min >= max){
            showAlert(5);
            return;
        }

        if (stock > max){
            showAlert(6);
            return;
        }

        if (stock < min){
            showAlert(7);
            return;
        }


        if (addPartInHouseBtn.isSelected()) {
                machineId = Integer.parseInt(addPartManufacturerTxt.getText());
                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
            }

        if (addPartOutsourcedBtn.isSelected()) {
                companyName = addPartManufacturerTxt.getText();
                Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
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
