package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.xml.sax.SAXException;

import dto.ValueDTO;
import exception.ActionNotPermitted;
import exception.ErrorFieldException;
import jakarta.xml.bind.JAXBException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import util.Phenomenon;

import static util.FilesManagement.SetearTextos;

public class AnalysisPane {

    private static AnalysisPane window;

    @FXML private AnchorPane analysisAnchor;
    @FXML  ComboBox<String> comboVariable;
    @FXML private Spinner<Double> passedSpinner;

    @FXML Button Aceptar;

    private ArrayList<ValueDTO> l;

    public static AnalysisPane getInstance() {
        return window;
    }

    public void initialize(){
        window = this;
        l = new ArrayList<>(securityFactorPane.getInstance().getTable().getItems());
        loadCombo();
        SetearTextos(this.analysisAnchor.getChildren(),Menu.getIdioma_actual());
        if(Menu.getIdioma_actual().equalsIgnoreCase("ESPAÑOL"))
            AnalysisPane.getInstance().comboVariable.setPromptText("Seleccionar");
        else AnalysisPane.getInstance().comboVariable.setPromptText("Select");
    }

    public void loadCombo(){
        ArrayList<String> list = new ArrayList<>();
        for (ValueDTO l : l) {
            // if(!l.getName().equalsIgnoreCase(""))
                list.add(l.getName());
        }
        ObservableList<String> l = FXCollections.observableArrayList(list);
        comboVariable.setItems(l);
    }

    public void selectCombo(){
        loadSpinner();
        Aceptar.setDisable(false);
        passedSpinner.setDisable(false);
    }

    public void loadSpinner(){
        SpinnerValueFactory<Double> valueFactory =new SpinnerValueFactory.DoubleSpinnerValueFactory(0.1,9999.0,0.1,0.1);
        passedSpinner.setValueFactory(valueFactory);

    }

    public void aceptBtn() throws ActionNotPermitted, ErrorFieldException, ParserConfigurationException, SAXException, JAXBException, IOException{
        String aux = comboVariable.getSelectionModel().getSelectedItem();
        securityFactorPane.getInstance().setVariableAnalysis(aux);
        securityFactorPane.getInstance().loadSpinnerSpeed(aux, passedSpinner.getValue());
        securityFactorPane.getInstance().getAnchorSFP().getChildren().remove(analysisAnchor);
        securityFactorPane.getInstance().getLimpiar_grafico().setDisable(false);
    }

    public void cancelBtn(){
        securityFactorPane.getInstance().getAnchorSFP().getChildren().remove(analysisAnchor);
    }

    public Pane getAnalysisAnchor() {
        return analysisAnchor;
    }

    public List<Node> getSonsAnalysisPane(){
        return analysisAnchor.getChildren();
    }
}
