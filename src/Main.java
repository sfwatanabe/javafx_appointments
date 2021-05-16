import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DBConnector;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // TODO Remember to delete these locale testing lines before submission.
//        Locale locale = new Locale("fr", "FR");
//        Locale.setDefault(locale);
        Locale locale = Locale.getDefault();
        String resourcePath = "resources/languages/change_language";
        ResourceBundle resources = ResourceBundle.getBundle(resourcePath,locale);

        Parent root = FXMLLoader.load(getClass().getResource("view_controller/Login.fxml"), resources);
        Scene scene= new Scene(root);

        primaryStage.setTitle(resources.getString("appTitle"));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        DBConnector.startConnection();
        launch(args);
        DBConnector.closeConnection();
    }

}
