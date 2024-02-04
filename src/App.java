import java.util.ArrayList;

import dto.ValueDTO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import util.Init;

public class App extends Application{
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("gui/main.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("DamGuard");
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
        Init.init();
    }
}
