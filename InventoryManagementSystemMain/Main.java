package InventoryManagementSystemMain;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;

/**This program implements an inventory management system application. */
public class Main extends Application {
    @Override
    public void init(){
        System.out.println("Starting.");
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();
    }

    @Override
    public void stop(){
        System.out.println("Terminated");
    }

/**This method adds part and product data then launches the application.
 Javadoc files location: C:\Users\Luke\Documents\Computer Science\WGU\C482 Software I\javadoc */
    public static void main(String[] args) {

        //Create part objects for testing
        InHouse ih1 = new InHouse(1, "Fuse", 1.50, 500, 100, 1000, 47);
        Outsourced os1 = new Outsourced(2,"Sensor", 4.75, 25, 10, 100, "TEK");
        Product pro1 = new Product(3,"Computer", 799.99, 47, 0, 200);
        Product pro2 = new Product(4,"Phone", 599.99, 99, 0, 200);

        Inventory.addPart(ih1);
        Inventory.addPart(os1);
        Inventory.addProduct(pro1);
        Inventory.addProduct(pro2);
        pro1.addAssociatedPart(ih1);
        pro2.addAssociatedPart(os1);

        launch(args);
    }
}
