package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import dto.ValueDTO;
import exception.ActionNotPermitted;
import exception.ErrorFieldException;
import jakarta.xml.bind.JAXBException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import util.Phenomenon;

public class AnalysisPane {
    
    private static AnalysisPane window;

    @FXML private AnchorPane analysisAnchor;
    @FXML private ComboBox<String> comboVariable;
    @FXML private Spinner<Double> passedSpinner;

    private ArrayList<ValueDTO> l;

    public static AnalysisPane getWindow() {
        return window;
    }

    public void initialize(){
        l = new ArrayList<>(securityFactorPane.getInstance().getTable().getItems());
        loadCombo();
    }

    public void loadCombo(){
        ArrayList<String> list = new ArrayList<>();
        for (ValueDTO l : l) {
            if(!l.getName().equalsIgnoreCase("tiempo"))
                list.add(l.getName());
        }
        ObservableList<String> l = FXCollections.observableArrayList(list);
        comboVariable.setItems(l);
    }

    public void selectCombo(){
        loadSpinner();
    }

    public void loadSpinner(){
        SpinnerValueFactory<Double> valueFactory =new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0,9999.0,0.5);
        passedSpinner.setValueFactory(valueFactory);

    }

    public void aceptBtn() throws ActionNotPermitted, ErrorFieldException, ParserConfigurationException, SAXException, JAXBException, IOException{
        String aux = comboVariable.getSelectionModel().getSelectedItem();
        securityFactorPane.getInstance().getVariableLabel().setText("Variable analizada: " + aux);
        securityFactorPane.getInstance().setVariableAnalysis(aux);
        securityFactorPane.getInstance().loadSpinnerSpeed(aux, passedSpinner.getValue());
        securityFactorPane.getInstance().getAnchorSFP().getChildren().remove(analysisAnchor);
    }

    public void cancelBtn(){
        securityFactorPane.getInstance().getAnchorSFP().getChildren().remove(analysisAnchor);
    }
}
