package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;
import static model.Inventory.getAllFilteredParts;
import static model.Inventory.getAllFilteredProducts;

/**This class is the controller for the Main screen. */
public class MainScreenController implements Initializable {

    /**This method filters the parts by name. */
    public static ObservableList<Part> filterPartByName(String partName)
    {
        if(!(getAllFilteredParts().isEmpty()))
            getAllFilteredParts().clear();

        for(Part part: Inventory.getAllParts())
        {
            if(part.getName().contains(partName))
                getAllFilteredParts().add((part));
        }

        return getAllFilteredParts();

    }

    /**This method filters the parts by ID. */
    public static ObservableList<Part> filterPartById(int id)
    {
        if(!(getAllFilteredParts().isEmpty()))
            getAllFilteredParts().clear();

        for(Part part: Inventory.getAllParts())
        {
            if(part.getId() == id)
                getAllFilteredParts().add((part));
        }

        return getAllFilteredParts();

    }
    /**This method filters the products by name. */
    public ObservableList<Product> filterProductByName(String productName)

    {
        if(!(getAllFilteredProducts().isEmpty()))
            getAllFilteredProducts().clear();

        for(Product product: Inventory.getAllProducts())
        {
            if(product.getName().contains(productName))
                getAllFilteredProducts().add((product));
        }

        return getAllFilteredProducts();

    }

    /**This method filters the products by ID. */
    public ObservableList<Product> filterProductById(int id)
    {
        if(!(getAllFilteredProducts().isEmpty()))
            getAllFilteredProducts().clear();

        for(Product product: Inventory.getAllProducts())
        {
            if(product.getId() == id)
                getAllFilteredProducts().add((product));
        }

        return getAllFilteredProducts();

    }

    /**This method sets up the tables. */
    @Override
    public void initialize(URL url, ResourceBundle rb){


        //Setup parts table
        MainPartTableView.setItems(Inventory.getAllParts());


        mainPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id")); // will call getId method
        mainPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mainPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


        //Setup products table
        MainProductTableView.setItems(Inventory.getAllProducts());

        mainProdIDCol.setCellValueFactory(new PropertyValueFactory<>("id")); // will call getId method
        mainProdNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainProdInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mainProdPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));



    }

    @FXML
    private TableView<Part> MainPartTableView;

    @FXML
    private TableView<Product> MainProductTableView;

    @FXML
    private TableColumn<Part, Integer> mainPartIDCol;

    @FXML
    private TableColumn<Part, Integer> mainPartInvCol;

    @FXML
    private TableColumn<Part, String> mainPartNameCol;

    @FXML
    private TableColumn<Part, Double> mainPartPriceCol;

    @FXML
    private TableColumn<Product, Integer> mainProdIDCol;

    @FXML
    private TableColumn<Product, Integer> mainProdInvCol;

    @FXML
    private TableColumn<Product, String> mainProdNameCol;

    @FXML
    private TableColumn<Product, Double> mainProdPriceCol;

    @FXML
    private TextField searchPartTxt;

    @FXML
    private TextField searchProductTxt;

    /**This method will take the user to the Add Part Screen. */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("../view/AddPart.fxml"));
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**This method will take the user to the Add Product Screen. */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("../view/AddProduct.fxml"));
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**This method will delete the selected part. */
    @FXML
    void onActionDeletePart(ActionEvent event) {

        Part partToDelete = MainPartTableView.getSelectionModel().getSelectedItem();

        if (partToDelete == null){
            showAlert(1);
        }
        else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Part");
            alert.setHeaderText("Delete");
            alert.setContentText("Are you sure you want to delete the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK)
                Inventory.deletePart(partToDelete);
        }

    }

    /**This method will delete the selected product. */
    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        Product productToDelete = MainProductTableView.getSelectionModel().getSelectedItem();

        if (productToDelete == null){
            showAlert(2);
            return;
        }
        else if (!(productToDelete.getAllAssociatedParts().isEmpty()))
        {
            showAlert(8);
            return;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Product");
            alert.setHeaderText("Delete");
            alert.setContentText("Are you sure you want to delete the selected product?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK)
                Inventory.deleteProduct(productToDelete);
        }

    }

    /**This method will end the program. */
    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    /**This method will take the user to the Modify Part screen. */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {

        if(MainPartTableView.getSelectionModel().getSelectedItem() == null)
        {
            showAlert(1);
        }
        else {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ModifyPart.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            ModifyPartController MPartController = loader.getController();
            MPartController.sendPart(MainPartTableView.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setTitle("Inventory Management System");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**This method will take the user to the Modify Product screen. */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {

        if (MainProductTableView.getSelectionModel().getSelectedItem() == null){
            showAlert(2);
        }
        else {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ModifyProduct.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            ModifyProductController MProductController = loader.getController();
            MProductController.sendProduct(MainProductTableView.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setTitle("Inventory Management System");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**This method will search for a part in the table. */
    @FXML
    void onActionSearchPart(ActionEvent event) throws IOException{
    try {
        if (!(filterPartByName(searchPartTxt.getText()).isEmpty())) {
            MainPartTableView.setItems(getAllFilteredParts());
        } else if (!(filterPartById(Integer.parseInt(searchPartTxt.getText())).isEmpty())) {
            MainPartTableView.setItems(getAllFilteredParts());
        } else {
            MainPartTableView.setItems(Inventory.getAllParts());
            showAlert(3);
        }
    }
    catch(Exception e){
        showAlert(3);
        MainPartTableView.setItems(Inventory.getAllParts());
        return;
    }
    }

    /**This method will search for a product in the table. */
    @FXML
    void onActionSearchProduct(ActionEvent event) throws IOException{
    try {
        if (!(filterProductByName(searchProductTxt.getText()).isEmpty())) {
            MainProductTableView.setItems(getAllFilteredProducts());
        } else if (!(filterProductById(Integer.parseInt(searchProductTxt.getText())).isEmpty())) {
            MainProductTableView.setItems(getAllFilteredProducts());
        } else {
            MainProductTableView.setItems(Inventory.getAllProducts());
            showAlert(4);
        }
    }
    catch(Exception e){
        showAlert(4);
        MainProductTableView.setItems(Inventory.getAllProducts());
        return;
    }
    }

    /**This method will display different dialog boxes depending on the error. */
    public static void showAlert(int alertCase){

        Alert alertError = new Alert(Alert.AlertType.ERROR);

        switch(alertCase) {
            case 1:
                alertError.setTitle("Error");
                alertError.setHeaderText("Part not selected");
                alertError.setContentText("Please select part in table");
                alertError.showAndWait();
                break;
            case 2:
                alertError.setTitle("Error");
                alertError.setHeaderText("Product not selected");
                alertError.setContentText("Please select product in table");
                alertError.showAndWait();
                break;
            case 3:
                alertError.setTitle("Error");
                alertError.setHeaderText("Part not found");
                alertError.showAndWait();
                break;
            case 4:
                alertError.setTitle("Error");
                alertError.setHeaderText("Product not found");
                alertError.showAndWait();
                break;
            case 5:
                alertError.setTitle("Error");
                alertError.setHeaderText("Min should be less than Max");
                alertError.showAndWait();
                break;
            case 6:
                alertError.setTitle("Error");
                alertError.setHeaderText("Inv should not exceed Max");
                alertError.showAndWait();
                break;
            case 7:
                alertError.setTitle("Error");
                alertError.setHeaderText("Inv should not be below Min");
                alertError.showAndWait();
                break;
            case 8:
                alertError.setTitle("Error");
                alertError.setHeaderText("Cannot delete a product that has a part associated with it");
                alertError.showAndWait();
                break;
            case 9:
                alertError.setTitle("Error");
                alertError.setHeaderText("Please enter valid values into each text field");
                alertError.showAndWait();
                break;


        }
    }
}
