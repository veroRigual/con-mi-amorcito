package gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class Login {

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
}
