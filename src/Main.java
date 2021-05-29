import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utils.DBConnector;
import utils.NotificationHandler;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        Locale locale = Locale.getDefault();
        String resourcePath = "resources/languages/change_language";
        ResourceBundle resources = ResourceBundle.getBundle(resourcePath,locale);

        Parent root = FXMLLoader.load(getClass().getResource("view_controller/Login.fxml"), resources);
        Scene scene= new Scene(root);

        primaryStage.setTitle(resources.getString("appTitle"));
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST,
            this::confirmClose);
        primaryStage.show();
    }

    private void confirmClose(WindowEvent event) {
        if(!NotificationHandler.confirmPopup(event, "Changes will not be saved.")) {
            event.consume();
        }
    }


    public static void main(String[] args) {
        try {
            DBConnector.startConnection();
            launch(args);
        } catch (ClassNotFoundException | SQLException e) {
            NotificationHandler.warningPopup("Database Connector", e.getMessage());
            e.printStackTrace();
        }
        DBConnector.closeConnection();
    }

}
