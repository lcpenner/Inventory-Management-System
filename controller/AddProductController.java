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
import model.Inventory;
import model.Part;
import model.Product;
import static controller.MainScreenController.*;
import static model.Inventory.getAllFilteredParts;

/**This class is the controller for the Add Product screen. */
public class AddProductController implements Initializable {

    private ObservableList<Part> addAssociatedParts = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb){
        //Setup add parts table
        addProductAddPartTableView.setItems(Inventory.getAllParts());
        addProdAddPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id")); // will call getId method
        addProdAddPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProdAddPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProdAddPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Setup remove parts table
        addProductRemovePartTableView.setItems(addAssociatedParts);
        addProdRemovePartIDCol.setCellValueFactory(new PropertyValueFactory<>("id")); // will call getId method
        addProdRemovePartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProdRemovePartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProdRemovePartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @FXML
    private TableColumn<Part, Integer> addProdAddPartIDCol;

    @FXML
    private TableColumn<Part, Integer> addProdAddPartInvCol;

    @FXML
    private TableColumn<Part, String> addProdAddPartNameCol;

    @FXML
    private TableColumn<Part, Double> addProdAddPartPriceCol;

    @FXML
    private TableColumn<Part, Integer> addProdRemovePartIDCol;

    @FXML
    private TableColumn<Part, Integer> addProdRemovePartInvCol;

    @FXML
    private TableColumn<Part, String> addProdRemovePartNameCol;

    @FXML
    private TableColumn<Part, Double> addProdRemovePartPriceCol;

    @FXML
    private TableView<Part> addProductAddPartTableView;

    @FXML
    private TextField addProductInvTxt;

    @FXML
    private TextField addProductMaxTxt;

    @FXML
    private TextField addProductMinTxt;

    @FXML
    private TextField addProductNameTxt;

    @FXML
    private TextField addProductPriceTxt;

    @FXML
    private TableView<Part> addProductRemovePartTableView;

    @FXML
    private TextField addProductSearchTxt;

    /**This method stores the product. */
    @FXML
    void onActionAddProductSave(ActionEvent event) throws IOException{
        try {
            int id = Inventory.getNextId();
            Inventory.incrementNextId();
            String name = addProductNameTxt.getText();
            int stock = Integer.parseInt(addProductInvTxt.getText());
            double price = Double.parseDouble(addProductPriceTxt.getText());
            int max = Integer.parseInt(addProductMaxTxt.getText());
            int min = Integer.parseInt(addProductMinTxt.getText());

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

            Product newProduct = new Product(id, name, price, stock, min, max);

            for (Part part : addAssociatedParts) {
                newProduct.addAssociatedPart(part);
            }

            Inventory.addProduct(newProduct);
        }

        catch(Exception e){
            showAlert(9);
            return;
        }

        Stage stage = (Stage)((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**This method returns the user back to the main screen. */
    @FXML
    void onActionDisplayMainScreen(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**This method adds the selected part to be associated with the product. */
    @FXML
    void onActionAddPart(ActionEvent event){
        addAssociatedParts.add(addProductAddPartTableView.getSelectionModel().getSelectedItem());
    }

    /**This method removes the selected part from being associated with the product. */
    @FXML
    void onActionRemovePart(ActionEvent event){
        Part partToDelete = addProductRemovePartTableView.getSelectionModel().getSelectedItem();

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
                addAssociatedParts.remove(partToDelete);
        }

    }

    /**This method searches for the part. */
    @FXML
    void onActionSearchPartToAdd(ActionEvent event) throws IOException{

         try {
            if (!(filterPartByName(addProductSearchTxt.getText()).isEmpty())) {
                addProductAddPartTableView.setItems(getAllFilteredParts());
            } else if (!(filterPartById(Integer.parseInt(addProductSearchTxt.getText())).isEmpty())) {
                addProductAddPartTableView.setItems(getAllFilteredParts());
            } else {
                addProductAddPartTableView.setItems(Inventory.getAllParts());
                showAlert(3);
            }
        }
        catch(Exception e){
            showAlert(3);
            addProductAddPartTableView.setItems(Inventory.getAllParts());
            return;
        }

    }

}
