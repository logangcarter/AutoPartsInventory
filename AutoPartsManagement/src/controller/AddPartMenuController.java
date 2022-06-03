package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import java.io.IOException;

/**
 * Controller for the add part form.
 */
public class AddPartMenuController {

    private Stage stage;
    private Parent scene;
    @FXML
    private RadioButton inHouseRBtn;
    @FXML
    private RadioButton outsourcedRBtn;
    @FXML
    private Text partSource;
    @FXML
    private TextField nameText;
    @FXML
    private TextField invText;
    @FXML
    private TextField priceText;
    @FXML
    private TextField maxText;
    @FXML
    private TextField partSourceText;
    @FXML
    private TextField minText;
    @FXML
    private Label exceptionLabelInv;
    @FXML
    private Label exceptionLabelPrice;
    @FXML
    private Label exceptionLabelMax;
    @FXML
    private Label exceptionLabelMin;
    @FXML
    private Label exceptionLabelMachineId;
    @FXML
    private Label exceptionMinLessMax;
    @FXML
    private Label exceptionInvBetweenMinMax;

    /**
     * Saves a part to all parts list.
     * @param event on click of Save button
     * @throws IOException
     */
    @FXML
    public void onActionSave(ActionEvent event) throws IOException{
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
        if (inHouseRBtn.isSelected()) {
            try {
                Integer.parseInt(partSourceText.getText());
                exceptionLabelMachineId.setOpacity(0);
            } catch (NumberFormatException e) {
                success = false;
                exceptionLabelMachineId.setOpacity(1.0);
            }
        }
        if (outsourcedRBtn.isSelected()){
            exceptionLabelMachineId.setOpacity(0);
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
                    if (inHouseRBtn.isSelected()) {
                        int partSource = Integer.parseInt(partSourceText.getText());
                        Inventory.addPart(new InHouse(id, name, price, stock, min, max, partSource));
                    } else if (outsourcedRBtn.isSelected()) {
                        String partSource = partSourceText.getText();
                        Inventory.addPart(new Outsourced(id, name, price, stock, min, max, partSource));
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
     * Will take you back to main screen without saving.
     * @param event on click of cancel button
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
     * Will change part type to in house.
     * @param event on click of in house radio button.
     */
    @FXML
    public void onActionInHouse(ActionEvent event) {
        partSource.setText("Machine ID");
    }

    /**
     * Will change part type to outsourced.
     * @param event on click of in house radio button.
     */
    @FXML
    public void onActionOutsourced(ActionEvent event) {
        partSource.setText("Company Name");
    }


}
