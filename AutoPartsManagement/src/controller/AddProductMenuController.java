package controller;

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
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 * Controller for the add product form.
 */
public class AddProductMenuController implements Initializable {
    private Stage stage;
    private Parent scene;
    @FXML
    private TextField nameText;
    @FXML
    private TextField invText;
    @FXML
    private TextField priceText;
    @FXML
    private TextField maxText;
    @FXML
    private TextField minText;
    @FXML
    private TextField partSearchBox;
    @FXML
    private TableView<Part> partTableView;
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInventoryCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TableView<Part> associatedPartTableView;
    @FXML
    private TableColumn<Part, Integer> associatedPartIdCol;
    @FXML
    private TableColumn<Part, String> associatedPartNameCol;
    @FXML
    private TableColumn<Part, Integer> associatedPartInventoryCol;
    @FXML
    private TableColumn<Part,Double> associatedPartPriceCol;
    @FXML
    private Label exceptionLabelInv;
    @FXML
    private Label exceptionLabelPrice;
    @FXML
    private Label exceptionLabelMax;
    @FXML
    private Label exceptionLabelMin;
    @FXML
    private Label exceptionMinLessMax;
    @FXML
    private Label exceptionInvBetweenMinMax;
    private ObservableList<Part> allAssociatedParts = FXCollections.observableArrayList();
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();

    /**
     * Will add new product to all products list.
     * @param event on click of add button
     */
    @FXML
    public void onActionAdd(ActionEvent event) {
        allAssociatedParts.add(partTableView.getSelectionModel().getSelectedItem());
    }

    /**
     * Will search for parts by id or name in parts search box.
     * @param event on press of a new key in search box.
     */
    @FXML
    public void onInputSearchParts(KeyEvent event) {
        try {
            Integer.parseInt(partSearchBox.getText());
            filteredParts.clear();
            filteredParts.add(Inventory.lookupPart(Integer.parseInt(partSearchBox.getText())));
            partTableView.setItems(filteredParts);
        }
        catch (NumberFormatException e) {
            partTableView.setItems(Inventory.lookupPart(partSearchBox.getText()));
        }

    }

    /**
     * Removes the associated part from all associated parts list.
     * @param event on click of remove associated part button.
     */
    @FXML
    public void onActionRemoveAssociatedPart(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Are you sure you want to delete this associated part?");

        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getDialogPane().getButtonTypes().add(cancelButtonType);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            allAssociatedParts.remove(associatedPartTableView.getSelectionModel().getSelectedItem());
        }
    }

    /**
     * Saves a product to all products list.
     * @param event on click of save button.
     * @throws IOException
     */
    @FXML
    public void onActionSave(ActionEvent event) throws IOException {
        boolean success = true;
        exceptionInvBetweenMinMax.setOpacity(0);
        exceptionMinLessMax.setOpacity(0);

        try {
            Integer.parseInt(invText.getText());
            exceptionLabelInv.setOpacity(0);
        } catch (NumberFormatException e) {
            success = false;
            exceptionLabelInv.setOpacity(1.0);
        }
        try {
            Double.parseDouble(priceText.getText());
            exceptionLabelPrice.setOpacity(0);
        } catch (NumberFormatException e) {
            success = false;
            exceptionLabelPrice.setOpacity(1.0);
        }
        try {
            Integer.parseInt(maxText.getText());
            exceptionLabelMax.setOpacity(0);
        } catch (NumberFormatException e) {
            success = false;
            exceptionLabelMax.setOpacity(1.0);
        }
        try {
            Integer.parseInt(minText.getText());
            exceptionLabelMin.setOpacity(0);
        } catch (NumberFormatException e) {
            success = false;
            exceptionLabelMin.setOpacity(1.0);
        }
        if (success) {
            int id = Inventory.uniqueIdGenerator();
            String name = nameText.getText();
            int stock = Integer.parseInt(invText.getText());
            double price = Double.parseDouble(priceText.getText());
            int max = Integer.parseInt(maxText.getText());
            int min = Integer.parseInt(minText.getText());
            if ( min <= max) {
                if (stock <= max && stock >= min) {
                    Inventory.addProduct(new Product(id, name, price, stock, max, min));
                    Product addedProduct = Inventory.lookupProduct(id);
                    int index = -1;
                    for (Part part : allAssociatedParts) {
                        index++;
                        addedProduct.addAssociatedPart(allAssociatedParts.get(index));
                    }

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
                else {
                    exceptionInvBetweenMinMax.setOpacity(1.0);
                }
            }
            else {
                exceptionMinLessMax.setOpacity(1.0);
            }
        }



    }

    /**
     * Will return to the main screen.
     * @param event on click of cancel button.
     * @throws IOException
     */
    @FXML
    public void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Set up the list views when the scene is opened.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partTableView.setItems(Inventory.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartTableView.setItems(allAssociatedParts);

        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}