package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * This class holds methods for manipulating parts and dealing
 * with the observable lists.
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int uniqueId = 0;

    /**
     *
     * @param newPart to add to all parts
     */
    public static void addPart(Part newPart) { allParts.add(newPart); }

    /**
     *
     * @param newProduct to add to all products
     */
    public static void addProduct(Product newProduct) { allProducts.add(newProduct); }

    /**
     *
     * @return allParts the observable list that holds all parts
     */
    public static ObservableList<Part> getAllParts() { return allParts; }

    /**
     * @return allProducts the observable list that hold all products
     */
    public static ObservableList<Product> getAllProducts() { return allProducts; }

    /**
     * Updates the part in observable parts list with new values.
     * @param index the place of the part in the array
     * @param selectedPart the part to update
     */
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }

    /**
     * Updates the product in observable products list with new values
     * @param index the place of the part in the array
     * @param selectedProduct the product to update
     */
    public static void updateProduct(int index, Product selectedProduct){
        allProducts.set(index, selectedProduct);
    }

    /**
     * Creates a unique id.
     * @return the uniqueId
     */
    public static int uniqueIdGenerator() {
        uniqueId++;
        return uniqueId;
    }

    /**
     * Will delete a part from the observable parts list.
     * @param selectedPart the part selected
     * @return isCleared true if the part was deleted, false if it was cancelled.
     */
    public static boolean deletePart(Part selectedPart) {
        Boolean isCleared = false;
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Are you sure you want to delete this part?");

        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getDialogPane().getButtonTypes().add(cancelButtonType);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            isCleared = true;
            allParts.remove(selectedPart);
        }
        return isCleared;

    }

    /**
     * Will delete a product from the observable products list.
     * @param selectedProduct
     * @return isCleared true if the product was deleted, false if it was cancelled.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        Boolean isCleared = false;
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Are you sure you want to delete this product?");

        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getDialogPane().getButtonTypes().add(cancelButtonType);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            isCleared = true;
            allProducts.remove(selectedProduct);
        }
        return isCleared;
    }

    /**
     * Will search all parts in the observable list for the one that matches the
     * inputted unique id.
     * @param partId the id that is being searched for
     * @return the resulting part
     */
     public static Part lookupPart(int partId) {
        for (Part part : getAllParts() ) {
            if(part.getId() == partId) {
                return part;
            }
        }
        return null;
    }
    /**
     * Will search all products in the observable list for the one that matches the
     * inputted unique id.
     * @param productId the id that is being searched for
     * @return the resulting product
     */
    public static Product lookupProduct(int productId) {
        for (Product product : getAllProducts() ) {
            if(product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * Will search all products in the observable list for the one that contains
     * the inputted string
     * @param productName the name that is being searched for
     * @return the resulting product
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> searchedProducts = FXCollections.observableArrayList();
        for (Product product : getAllProducts()) {
            if (product.getName().contains(productName)) {
                searchedProducts.add(product);
            }
        }
        return searchedProducts;
    }
    /**
     * Will search all parts in the observable list for parts that contains
     * the inputted string.
     * @param partName the name that is being searched for
     * @return the resulting list of parts
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> searchedParts = FXCollections.observableArrayList();
        for (Part part : getAllParts()) {
            if (part.getName().contains(partName)) {
                searchedParts.add(part);
            }
        }
        return searchedParts;
    }


}
