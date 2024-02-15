import java.io.IOException;
import exception.ErrorFieldException;
import gui.LoadingPane;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.Init;

public class App extends Application{
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Parent root = FXMLLoader.load(getClass().getResource("gui/main.fxml"));
        // Scene scene = new Scene(root);
        // stage.setTitle("DamGuard");
        // stage.setScene(scene);
        // stage.setMaximized(true);
        // stage.initStyle(StageStyle.UNDECORATED);
        // stage.show();
        // Init.init();

        Stage loadingStage = new Stage();
        Scene loadingScene = new Scene(FXMLLoader.load(getClass().getResource("gui/loadingPane.fxml")));
        loadingStage.setScene(loadingScene);
        loadingStage.initStyle(StageStyle.UNDECORATED);
        loadingStage.show();
        new Thread(() -> {
            try {
                Init.init();
                LoadingPane.getInstance().load();
                // Simular la carga de datos
                // Thread.sleep(1000); // Simulando una espera de 3 segundos

                // Cerrar la ventana de espera y mostrar la interfaz principal
                Platform.runLater(() -> {
                    loadingStage.close();
                    try {
                        showMainUI(stage);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (ErrorFieldException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                });
            // } catch (InterruptedException e) {
            //     e.printStackTrace();
            } catch (ErrorFieldException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }).start();
    }

    

    private void showMainUI(Stage stage) throws IOException, ErrorFieldException {
        // Aquí puedes cargar la interfaz principal de tu aplicación
        // Por ejemplo, puedes crear tu Scene y configurarla en el primaryStage
        // primaryStage.setScene(...);

        // Mostrar la interfaz principal
        Parent root = FXMLLoader.load(getClass().getResource("gui/main.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("DamGuard");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        // primaryStage.show();
    }
}