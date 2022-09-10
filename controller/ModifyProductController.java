package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import javax.swing.*;
import static controller.MainScreenController.*;
import static controller.MainScreenController.filterPartById;
import static model.Inventory.getAllFilteredParts;

/**This class is the controller for the Modify Product screen. */
public class ModifyProductController implements Initializable {
    private Product productToModify = null;
    private ObservableList<Part> modifiedAssociatedParts = FXCollections.observableArrayList();

    /**This method is used to identify the product selected to be modified. */
    public void sendProduct(Product product)
    {
        this.productToModify = product;
        this.modifiedAssociatedParts = product.getAllAssociatedParts();

        modifyProductIdTxt.setText(String.valueOf((productToModify.getId())));
        modifyProductNameTxt.setText(productToModify.getName());
        modifyProductInvTxt.setText(String.valueOf(productToModify.getStock()));
        modifyProductPriceTxt.setText(String.valueOf(productToModify.getPrice()));
        modifyProductMaxTxt.setText(String.valueOf(productToModify.getMax()));
        modifyProductMinTxt.setText(String.valueOf(productToModify.getMin()));

        //Setup remove parts table
        modifyProductRemovePartTableView.setItems(modifiedAssociatedParts);
        modProdRemovePartIDCol.setCellValueFactory(new PropertyValueFactory<>("id")); // will call getId method
        modProdRemovePartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modProdRemovePartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modProdRemovePartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        //Setup add parts table
        modifyProductAddPartTableView.setItems(Inventory.getAllParts());
        modProdAddPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id")); // will call getId method
        modProdAddPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modProdAddPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modProdAddPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


    };

    @FXML
    private TableColumn<Part, Integer> modProdAddPartIDCol;

    @FXML
    private TableColumn<?, Integer> modProdAddPartInvCol;

    @FXML
    private TableColumn<Part, String> modProdAddPartNameCol;

    @FXML
    private TableColumn<Part, Double> modProdAddPartPriceCol;

    @FXML
    private TableColumn<Part, Integer> modProdRemovePartIDCol;

    @FXML
    private TableColumn<Part, Integer> modProdRemovePartInvCol;

    @FXML
    private TableColumn<Part, String> modProdRemovePartNameCol;

    @FXML
    private TableColumn<Part, Double> modProdRemovePartPriceCol;

    @FXML
    private TableView<Part> modifyProductAddPartTableView;

    @FXML
    private TextField modifyProductInvTxt;

    @FXML
    private TextField modifyProductMaxTxt;

    @FXML
    private TextField modifyProductMinTxt;

    @FXML
    private TextField modifyProductNameTxt;

    @FXML
    private TextField modifyProductPriceTxt;

    @FXML
    private TableView<Part> modifyProductRemovePartTableView;

    @FXML
    private TextField modifyProductSearchTxt;

    @FXML
    private TextField modifyProductIdTxt;

    @FXML
    void onActionDisplayMainScreen(ActionEvent event) throws IOException {

        Stage stage = (Stage)((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**This method saves the modified product and returns the user to the Main Screen. */
    @FXML
    void onActionModifyProductSave(ActionEvent event) throws IOException{

    try {
        String name = modifyProductNameTxt.getText();
        int stock = Integer.parseInt(modifyProductInvTxt.getText());
        double price = Double.parseDouble(modifyProductPriceTxt.getText());
        int max = Integer.parseInt(modifyProductMaxTxt.getText());
        int min = Integer.parseInt(modifyProductMinTxt.getText());

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


        Product modifiedProduct = new Product(productToModify.getId(), name, price, stock, min, max);

        for (Part part : modifiedAssociatedParts) {
            modifiedProduct.addAssociatedPart(part);
        }

        Inventory.updateProduct(modifiedProduct);
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

    /**This method removes the selected part from being associated with the product. */
    @FXML
    void onActionModifiedProductRemovePart(ActionEvent event){
        Part partToDelete = modifyProductRemovePartTableView.getSelectionModel().getSelectedItem();

        if (partToDelete == null){
            showAlert(1);
        }
        else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Part");
            alert.setHeaderText("Remove");
            alert.setContentText("Are you sure you want to remove the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK)
                modifiedAssociatedParts.remove(partToDelete);
        }
    }

    /**This method adds the selected part to be associated with the product. */
    @FXML
    void onActionModifyProductAddPart(ActionEvent event){
        modifiedAssociatedParts.add(modifyProductAddPartTableView.getSelectionModel().getSelectedItem());
    }

    /**This method filters/searches for a part in the Add table. */
    @FXML
    void onActionModProdSearchPartToAdd(ActionEvent event) throws IOException{

         try {
            if (!(filterPartByName(modifyProductSearchTxt.getText()).isEmpty())) {
                modifyProductAddPartTableView.setItems(getAllFilteredParts());
            } else if (!(filterPartById(Integer.parseInt(modifyProductSearchTxt.getText())).isEmpty())) {
                modifyProductAddPartTableView.setItems(getAllFilteredParts());
            } else {
                modifyProductAddPartTableView.setItems(Inventory.getAllParts());
                showAlert(3);
            }
        }
        catch(Exception e){
            showAlert(3);
            modifyProductAddPartTableView.setItems(Inventory.getAllParts());
            return;
        }

    }
}
