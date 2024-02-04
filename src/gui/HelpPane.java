package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelpPane {

    HelpPane window;
    @FXML private Label nameLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label dataInfoLabel;

    public void initialize(){
        window = this;
    }

    public HelpPane getInstance(){
        return window;
    }

}
