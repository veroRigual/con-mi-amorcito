package gui;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import static util.FilesManagement.SetearTextos;

public class Login {
    static Login window;
    @FXML Label Para_acceder_a_esta_parte_de_la_aplicacion_necesita_credenciales;
    @FXML Label correo_electronico;
    @FXML Button Aceptar;

    @FXML AnchorPane anchorLogin;

    public void showManagementPane(){
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("ManagementPane.fxml"));
            // fxml.setLayoutX(100);
            // fxml.setLayoutY(15);
            // securityFactorPane.getInstance().getAnchorSFP().getChildren().add(fxml);
            exitBtn();
            Menu.getWindow().getContentArea().getChildren().removeAll();
            Menu.getWindow().getContentArea().getChildren().setAll(fxml);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void authenticate(){
        showManagementPane();
    }

    public void exitBtn(){
        // securityFactorPane.getInstance().getAnchorSFP().getChildren().remove(anchorLogin);
    }
    public static Login getInstance() {
        return window;
    }

    public void initialize() {
        window = this;
        SetearTextos(this.anchorLogin.getChildren(),Menu.getIdioma_actual());

    }

}

