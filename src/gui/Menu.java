package gui;

import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import util.FilesManagement;
import logic.DamSystem;
 
public class Menu {

    static Menu window;

    @FXML StackPane contentArea;
    @FXML Pane sfButton;
    @FXML Pane helpButton;
    @FXML Pane ManagementButton;
    @FXML AnchorPane mainAnchorPane;

    @FXML Label labelSFSelected;
    @FXML Label labelSF;
    @FXML Label labelAyudaSelected;
    @FXML Label labelAyuda;

    public void initialize(){
        window = this;
        showSecurityFactorPane();
    }

    public static Menu getWindow(){
        return window;
    }

    public StackPane getContentArea() {
        return contentArea;
    }

   public void showSecurityFactorPane(){
    Parent fxml;
    try {
        fxml = FXMLLoader.load(getClass().getResource("securityFactorPane.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    sfButton.getStyleClass().clear();
    sfButton.getStyleClass().add("buttonsBarSelected");
    helpButton.getStyleClass().clear();
    helpButton.getStyleClass().add("buttonsBar");
    ManagementButton.getStyleClass().clear();
    ManagementButton.getStyleClass().add("buttonsBar");
    // labelSFSelected.setVisible(true);
    // labelSF.setVisible(false);
    // labelAyudaSelected.setVisible(false);
    // labelAyuda.setVisible(true);
    }

    public void showHelpPane(){
    Parent fxml;
    try {
        fxml = FXMLLoader.load(getClass().getResource("AboutPane.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    helpButton.getStyleClass().clear();
    helpButton.getStyleClass().add("buttonsBarSelected");
    sfButton.getStyleClass().clear();
    sfButton.getStyleClass().add("buttonsBar");
    ManagementButton.getStyleClass().clear();
    ManagementButton.getStyleClass().add("buttonsBar");

    // labelSFSelected.setVisible(false);
    // labelSF.setVisible(true);
    // labelAyudaSelected.setVisible(true);
    // labelAyuda.setVisible(false);
   }

   public void showManagementPane(){
    Parent fxml;
    try {
        fxml = FXMLLoader.load(getClass().getResource("Login.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    ManagementButton.getStyleClass().clear();
    ManagementButton.getStyleClass().add("buttonsBarSelected");
    helpButton.getStyleClass().clear();
    helpButton.getStyleClass().add("buttonsBar");
    sfButton.getStyleClass().clear();
    sfButton.getStyleClass().add("buttonsBar");
    }

   public void exitWindow(){
        Stage stage = (Stage) mainAnchorPane.getScene().getWindow();
        stage.close();
        //FilesManagement.WriteFormulaToFile(DamSystem.getInstance().getFormulasFile());
    }
}
