package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**This class is the model for an inventory of all parts and products. FUTURE ENHANCEMENT: A future enhancement would be to store the parts and products
 in alphabetical order. Displaying the inventory in alphabetical order in the table views could improve the user experience and potentially allow for faster search algorithms. */
public class Inventory {

    /**List of all parts. */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**List of filtered parts. */
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();

    /**List of all products. */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**List of filtered products. */
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

    /**Declares and initializes nextId to be used for part and product IDs. */
    public static int nextId = 7;

    /**Adds part to part list. */
    public static void addPart(Part newPart)
    {
        allParts.add(newPart);
    }

    /**Adds product to product list. */
    public static void addProduct(Product newProduct)
    {
        allProducts.add(newProduct);
    }

    /**Returns a part based on a given ID. */
    public static Part lookupPart(int partId)
    {
        for (Part part: allParts) {
            if (part.getId() == partId)
                return part;
        }
        return null;
    }

    /**Returns a product based on a given ID. */
    public static Product lookupProduct(int productId)
    {
        for (Product product: allProducts) {
            if (product.getId() == productId)
                return product;
        }
        return null;

    }

    /**Returns a part based on a given part name. */
    public static Part lookupPart(String partName)
    {
        for (Part part: allParts) {
            if (part.getName() == partName)
                return part;
        }
        return null;
    }

    /**Returns a product based on a given product name. */
    public static Product lookupProduct(String productName)
    {
        for (Product product: allProducts) {
            if (product.getName() == productName)
                return product;
        }
        return null;
    }

    /**Updates a part's stored data. */
    public static void updatePart(Part modifiedPart)
    {
        for(int i = 0; i < allParts.size(); i++)
        {
            if(allParts.get(i).getId() == modifiedPart.getId()) {
                allParts.set(i, modifiedPart);
                break;
            }

        }

    }

    /**Updates a product's stored data. */
    public static void updateProduct(Product modifiedProduct)
    {
        for(int i = 0; i < allProducts.size(); i++)
        {
            if(allProducts.get(i).getId() == modifiedProduct.getId()) {
                allProducts.set(i, modifiedProduct);
                break;
            }

        }
    }

    /**Deletes a part from the part list. */
    public static boolean deletePart(Part selectedPart)
    {
        if (allParts.contains(selectedPart))
            return allParts.remove(selectedPart);
        else
            return false;
    }

    /**Deletes a product from the product list. */
    public static boolean deleteProduct(Product selectedProduct)
    {
        if (allProducts.contains(selectedProduct))
            return allProducts.remove(selectedProduct);
        else
            return false;
    }

    /**Getter for the full part list. */
    public static ObservableList<Part> getAllParts()
    {
        return allParts;
    }

    /**Getter for the filtered part list. */
    public static ObservableList<Part> getAllFilteredParts()
    {
        return filteredParts;
    }

    /**Getter for the full product list. */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    /**Getter for the filtered product list. */
    public static ObservableList<Product> getAllFilteredProducts(){
        return filteredProducts;
    }

    /**Getter for the nextId. */
    public static int getNextId() { return nextId;}

    /**Increments the nextId. */
    public static void incrementNextId(){nextId++;};


}
