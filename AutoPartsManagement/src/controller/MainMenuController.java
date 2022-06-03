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
import java.util.ResourceBundle;

/**
 * Controller for the main scene.
 */
public class MainMenuController implements Initializable {
    private Stage stage;
    private Parent scene;
    @FXML
    private TextField partSearchBox;
    @FXML
    private TextField productSearchBox;
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
    private TableView<Product> productTableView;
    @FXML
    private TableColumn<Product, Integer> productIdCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, Integer> productInventoryCol;
    @FXML
    private TableColumn<Product, Double> productPriceCol;
    @FXML
    private Label exceptionLabelDelete;
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
    private static Part passedPart;
    private static Product passedProduct;

    /**
     * Will return the product selected so the modify page can get it's values.
     * @return passedProduct
     */
    public static Product getPassedProduct() {
        return passedProduct;
    }
    /**
     * Will set the product selected so the modify page can get it's values.
     * @return passedProduct
     */
    public static void setPassedProduct(Product passedProduct) {
        MainMenuController.passedProduct = passedProduct;
    }
    /**
     * Will return the part selected so the modify page can get it's values.
     * @return passedPart
     */
    public static Part getPassedPart() {
        return passedPart;
    }
    /**
     * Will set the product selected so the modify page can get it's values.
     * @return passedProduct
     */
    public static void setPassedPart(Part passedPart) {
        MainMenuController.passedPart = passedPart;
    }

    /**
     * Moves to the add part form.
     * @param event on click of add button in part section.
     * @throws IOException
     */
    @FXML
    public void onActionAddPart(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPartMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * Moves to the add product scene.
     * @param event on click of add button in product section.
     * @throws IOException
     */
    @FXML
    public void onActionAddProduct(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProductMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Will delete the part from all parts list.
     * @param event on click of delete in part section.
     */
    @FXML
    public void onActionDeletePart(ActionEvent event) {
        boolean isCleared;
        Part deletedPart = partTableView.getSelectionModel().getSelectedItem();
        isCleared = Inventory.deletePart(deletedPart);
        if (isCleared) {
            filteredParts.remove(deletedPart);
        }
    }

    /**
     * Will delete the product from all products list.
     * @param event on click of delete in product section.
     */
    @FXML
    public void onActionDeleteProduct(ActionEvent event) {
        boolean isCleared;
        if (productTableView.getSelectionModel().getSelectedItem().getAllAssociatedParts().isEmpty()) {
            isCleared = Inventory.deleteProduct(productTableView.getSelectionModel().getSelectedItem());
            if (isCleared) {
                filteredProducts.remove(productTableView.getSelectionModel().getSelectedItem());
            }
        } else {
            exceptionLabelDelete.setOpacity(1.0);
        }
    }

    /**
     * Will exit the program.
     * @param event on click of exit button.
     */
    @FXML
    public void onActionExit(ActionEvent event) {
    System.exit(0);
    }

    /**
     * Will move to the modify part screen and set the passed part as the highlighted part.
     * @param event on click of modify button in part section.
     * @throws IOException
     */
    @FXML
    public void onActionModifyPart(ActionEvent event) throws IOException{
        setPassedPart(partTableView.getSelectionModel().getSelectedItem());

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ModifyPartMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Will move to the modify product screen and set the passed product as the highlighted product.
     * @param event on click of modify button in part section.
     * @throws IOException
     */
    @FXML
    public void onActionModifyProduct(ActionEvent event) throws IOException{
        setPassedProduct(productTableView.getSelectionModel().getSelectedItem());

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ModifyProductMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Will search parts list by id or name.
     * @param event on press of a new key in parts search bar.
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
            filteredParts.clear();
            filteredParts.setAll(Inventory.lookupPart(partSearchBox.getText()));
            partTableView.setItems(filteredParts);
        }

    }

    /**
     * Will search products list by id or name.
     * @param event on press of a new key in products search bar.
     */
    @FXML
    public void onInputSearchProducts(KeyEvent event) {
        try {
            Integer.parseInt(productSearchBox.getText());
            filteredProducts.clear();
            filteredProducts.add(Inventory.lookupProduct(Integer.parseInt(productSearchBox.getText())));
            productTableView.setItems(filteredProducts);
        }
        catch (NumberFormatException e) {
            filteredProducts.clear();
            filteredProducts.setAll(Inventory.lookupProduct(productSearchBox.getText()));
            productTableView.setItems(filteredProducts);
        }
    }

    /**
     * Set up the list views when the form is displayed.
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

        productTableView.setItems(Inventory.getAllProducts());

        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }


}
