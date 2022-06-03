import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;
import java.io.IOException;
/**
 * @author Logan Carter
 *
 * JAVADOC is zipped up with the project file.
 */

/**
 * This is the main class for the application.
 * FUTURE ENHANCEMENT - Use a database a back end to manage the data.
 */
public class Main extends Application {

    /**
     * This launches the UI for the application.
     * @param primaryStage is the main FX stage of the application.
     * @throws IOException which is required for load.
     */
    @Override
    public void start(Stage primaryStage)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        primaryStage.setTitle("Main Screen");
        primaryStage.setScene(new Scene(root, 643, 400));
        primaryStage.show();
    }

    /**
     * This class is the entry point for the application.
     * @param args not used.
     */
    public static void main(String[] args) {
        InHouse part1 = new InHouse(Inventory.uniqueIdGenerator(),"Gear",19.50,3, 1, 10, 1);
        InHouse part2 = new InHouse(Inventory.uniqueIdGenerator(),"Screw",5.00,15, 1, 99, 2);
        Outsourced part3 = new Outsourced(Inventory.uniqueIdGenerator(), "Spark Plug", 449.99, 2, 1, 3, "Motors Inc.");
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);

        Product product1 = new Product(Inventory.uniqueIdGenerator(), "Motor", 339.99, 2, 6,1);
        Product product2 = new Product(Inventory.uniqueIdGenerator(), "Engine", 699.89, 4, 10,2);
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        product1.addAssociatedPart(part1);
        product2.addAssociatedPart(part2);
        product2.addAssociatedPart(part3);



        launch(args);
    }

}
