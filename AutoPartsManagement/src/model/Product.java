package model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Class to create and manipulate the product objects.
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor for the Product class.
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param max
     * @param min
     */
    public Product(int id, String name, double price, int stock, int max, int min) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.max = max;
        this.min = min;
    }

    /**
     * @return the id
     */
    public int getId() { return id; }

    /**
     * @param id to set
     */
    public void setId(int id) { this.id = id; }

    /**
     * @return the name
     */
    public String getName() {return name;}

    /**
     * @param name to set
     */
    public void setName(String name) {this.name = name;}

    /**
     * @return the price
     */
    public double getPrice() {return price;}

    /**
     * @param price to set
     */
    public void setPrice(double price) {this.price = price;}

    /**
     * @return the stock
     */
    public int getStock() {return stock;}

    /**
     * @param stock to set
     */
    public void setStock(int stock) {this.stock = stock;}

    /**
     * @return the min
     */
    public int getMin() {return min;}

    /**
     * @param min to set
     */
    public void setMin(int min) {this.min = min;}

    /**
     * @return the max
     */
    public int getMax() {return max;}

    /**
     * @param max to set
     */
    public void setMax(int max) {this.max = max;}

    /**
     * @param part to add to associated parts
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Will delete a part from the associated parts list.
     * @param selectedAssociatedPart the part to be deleted
     * @return whether the part isCleared
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        Boolean isCleared = false;
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Are you sure you want to delete this part?");

        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getDialogPane().getButtonTypes().add(cancelButtonType);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            isCleared = true;
            getAllAssociatedParts().remove(selectedAssociatedPart);
        }
        return isCleared;
    }

    /**
     * @return the list of associatedParts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

}
