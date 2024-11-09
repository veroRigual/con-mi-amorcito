import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import exception.ErrorFieldException;
import gui.LoadingPane;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.Formula;
import util.FilesManagement;
import logic.DamSystem;

public class App extends Application{
    public static void main(String[] args) throws Exception {
        launch(args);

    }

    @Override
    public void start(Stage stage) throws Exception {
        Stage loadingStage = new Stage();
        Scene loadingScene = new Scene(FXMLLoader.load(getClass().getResource("gui/loadingPane.fxml")));
        loadingStage.setScene(loadingScene);
        loadingStage.setResizable(false);
        loadingStage.setTitle("DamGuard");
        loadingStage.show();


        new Thread(() -> {
            // Init.init();
            //prueba esto
            FilesManagement.CargandoFormulasEng(DamSystem.getInstance().getFormulasFileEng(), DamSystem.getInstance().getFormListEng());
              FilesManagement.CargandoFormulas(DamSystem.getInstance().getFormulasFile(), DamSystem.getInstance().getFormList());
              ArrayList<Formula> list = DamSystem.getInstance().getFormList();
            ArrayList<Formula> listEng = DamSystem.getInstance().getFormListEng();
              LoadingPane.getInstance().load();
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
        }).start();
    }

    private void showMainUI(Stage stage) throws IOException, ErrorFieldException {
        Parent root = FXMLLoader.load(getClass().getResource("gui/main.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("DamGuard");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }
}