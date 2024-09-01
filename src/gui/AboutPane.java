package gui;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import util.FilesManagement;

public class AboutPane {
    static AboutPane helpSFP;
    @FXML  AnchorPane anchorSFP;
   /* @FXML  Text texto_info_programa;
    @FXML  Text seg_texto_info_programa;
*/
    public static AboutPane getInstance() {
        return helpSFP;
    }

    public void initialize() {
        helpSFP = this;

    }

}
