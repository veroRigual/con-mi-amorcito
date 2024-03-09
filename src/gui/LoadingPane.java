package gui;

import org.apache.commons.math3.geometry.Point;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;

public class LoadingPane {

    static LoadingPane window;

    @FXML private ProgressBar loading;

    double point = 0;

    public void initialize(){
        window = this;
        
    }

    public static LoadingPane getInstance(){
        return window;
    }

    public void load(){
        while(point < 100){
            point = point + 0.00001;
            loading.setProgress(point);
        }
    }
}
