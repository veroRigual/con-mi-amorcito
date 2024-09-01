package gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import util.FilesManagement;

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
