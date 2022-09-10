package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**This class is the model for a Product. */
public class Product {

    /**List of parts associated with a product. */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /**Product ID. */
    private int id;
    /**Product name. */
    private String name;
    /**Product price. */
    private double price;
    /**Product stock. */
    private int stock;
    /**Product minimum inventory. */
    private int min;
    /**Product maximum inventory. */
    private int max;

    /**Product constructor. */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;

    }
    /**Getter for product ID. */
    public int getId() { return id; }

    /**Setter for product ID. */
    public void setId(int id) {
        this.id = id;
    }

    /**Getter for product name. */
    public String getName() {
        return name;
    }

    /**Setter for product name. */
    public void setName(String name) {
        this.name = name;
    }

    /**Getter for product price. */
    public double getPrice() {
        return price;
    }

    /**Setter for product price. */
    public void setPrice(double price) {
        this.price = price;
    }

    /**Getter for product stock. */
    public int getStock() {
        return stock;
    }

    /**Setter for product stock. */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**Getter for minimum inventory. */
    public int getMin() {
        return min;
    }

    /**Setter for minimum inventory. */
    public void setMin(int min) {
        this.min = min;
    }

    /**Getter for maximum inventory. */
    public int getMax() {
        return max;
    }

    /**Setter for maximum inventory. */
    public void setMax(int max) {
        this.max = max;
    }

    /**Adds part to product's associated part list. */
    public void addAssociatedPart(Part part)
    {
        associatedParts.add(part);
    }

    /**Getter for product's associated part list. */
    public ObservableList<Part> getAllAssociatedParts()
    {
        return associatedParts;
    }

    /**Deletes part from product's associated part list. */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart)
    {
        return associatedParts.remove(selectedAssociatedPart);
    }
}
