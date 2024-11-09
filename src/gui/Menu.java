package gui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.Properties;
import java.util.Locale;
import java.util.ResourceBundle;
import Properties.Propiedad;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

import static util.FilesManagement.*;

public class Menu {
private static String idioma_actual ="ESPAÑOL";
    static Menu window;
    @FXML Pane panel_opciones;

    @FXML StackPane contentArea;
    @FXML Pane sfButton;
    @FXML Pane helpButton;
    @FXML Pane ManagementButton;
    @FXML AnchorPane mainAnchorPane;
    @FXML Pane LanguageButton;
    @FXML Pane languageOptionsPane;

    @FXML Label FACTOR_DE_SEGURIDAD;
    @FXML Label GESTIONAR;
    @FXML Label AYUDA;
    @FXML Label SALIR;
    @FXML Label changingLanguageButton;
    @FXML AnchorPane EspLanguage;
    @FXML AnchorPane EngLanguage;


    public void initialize(){
        window = this;
        showHelpPane();
        EspLanguage.onMouseClickedProperty().set((MouseEvent event)->{
           idioma_actual= "ESPAÑOL";
            HacerCambio(idioma_actual);
        });
        EngLanguage.onMouseClickedProperty().set((MouseEvent event)->{
            idioma_actual= "ENGLISH";
            HacerCambio(idioma_actual);
        });
        //showSecurityFactorPane();
    }

    public static String getIdioma_actual() {
        return idioma_actual;
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

    public void showLangPane(){
        languageOptionsPane.setVisible(true);

}
    public void hideLangPane(){
        languageOptionsPane.setVisible(false);
    }
    public  void HacerCambio(String idioma){
        hideLangPane();
      if(AboutPane.getInstance()!= null){
      SetearTextos(AboutPane.getInstance().anchorSFP.getChildren(), idioma);
          SetearTextosIndependiente(this.FACTOR_DE_SEGURIDAD,idioma);
          SetearTextosIndependiente(this.GESTIONAR,idioma);
          SetearTextosIndependiente(this.AYUDA,idioma);
          SetearTextosIndependiente(this.SALIR,idioma);}
        SetearTextosIndependiente(this.changingLanguageButton,idioma);
       if(Login.getInstance()!= null) {
            SetearTextos(Login.getInstance().anchorLogin.getChildren(), idioma);}
        if(ManagementPane.getInstance()!= null) {
        SetearTextos(ManagementPane.getInstance().getSonsManagementPane(), idioma);

        if(idioma.equalsIgnoreCase("ESPAÑOL")){
        ManagementPane.getInstance().Nombre.setText("Nombre");
        ManagementPane.getInstance().Expresion.setText("Expresión");
        ManagementPane.getInstance().variableTable.setPlaceholder(new Label("Tabla sin contenido"));

        ManagementPane.getInstance().update();
        }
        else {
            ManagementPane.getInstance().Nombre.setText("Name");
            ManagementPane.getInstance().Expresion.setText("Expression");
            ManagementPane.getInstance().variableTable.setPlaceholder(new Label("No content in table"));
            ManagementPane.getInstance().updateEng();}
        }

      if(securityFactorPane.getInstance()!= null) {
           SetearTextos (securityFactorPane.getInstance().getAnchorSFP().getChildren(), idioma);
          if(idioma.equalsIgnoreCase("ESPAÑOL")){
             securityFactorPane.getInstance().valueColumn.setText("Valor");
              securityFactorPane.getInstance().valueTable.setPlaceholder(new Label("Tabla sin contenido"));
              securityFactorPane.getInstance().seleccionarr.setPromptText("Seleccionar");
              securityFactorPane.getInstance().seleccionar.setPromptText("Seleccionar");
              securityFactorPane.getInstance().formulaBox.setPromptText("Seleccionar");
              securityFactorPane.getInstance().update();
              securityFactorPane.getInstance().loadComboBoxes();
          }
          else {
              securityFactorPane.getInstance().valueColumn.setText("Value");
              securityFactorPane.getInstance().valueTable.setPlaceholder(new Label("No content in table"));
              securityFactorPane.getInstance().seleccionar.setPromptText("Select");
              securityFactorPane.getInstance().seleccionarr.setPromptText("Select");
              securityFactorPane.getInstance().formulaBox.setPromptText("Select");
              securityFactorPane.getInstance().updateEng();
          securityFactorPane.getInstance().loadComboBoxes();}
      }

       if(AnalysisPane.getInstance()!= null) {
            SetearTextos (AnalysisPane.getInstance().getSonsAnalysisPane(), idioma);
           if(Menu.getIdioma_actual().equalsIgnoreCase("ESPAÑOL"))
               AnalysisPane.getInstance().comboVariable.setPromptText("Seleccionar");
           else AnalysisPane.getInstance().comboVariable.setPromptText("Select");
       }
    }}

    //REVISAR
    //cargar idioma
    //String idiomaSeleccionado ="es";
    //ResourceBundle bundle = ResourceBundle.getBundle("E",new Locale(idiomaSeleccionado));

     /* public void cargarIdioma(String idioma){
            Properties propiedades = new Propiedades(idioma);
          labelAyudaSelected.setText(propiedades.getProperty("AYUDA"));
        }
*/

