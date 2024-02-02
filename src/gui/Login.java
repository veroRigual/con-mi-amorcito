package gui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Login {

    public void showManagementPane(){
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("ManagementPane.fxml"));
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
}
