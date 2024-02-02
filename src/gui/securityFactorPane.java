package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import util.Formula;
import util.Phenomenon;;

public class securityFactorPane {
    securityFactorPane anchorSFP;

    @FXML Spinner timeSpinner;
    @FXML Spinner highSpinner;
    @FXML Spinner speedSpinner;
    @FXML Spinner poundSpinner;
    @FXML Spinner cohesionSpinner;
    @FXML Spinner angleSpinner;
    @FXML Spinner permeabilitySpinner;
    @FXML Spinner volumeSpinner;
    @FXML Spinner crownSpinner;

    @FXML ComboBox formulaBox;
    @FXML ComboBox phenomenonBox;


    public void initialize(){
        anchorSFP = this;
        // loadSpinners();
        loadComboBoxes();
    }

    public void loadSpinners(){
        loadAngleSpinner();
        loadCohesionSpinner();
        loadCrownSpinner();
        loadDayTimeSpinner();
        loadHighSpinner();
        loadPermeabilitySpinner();
        loadPoundSpinner();
        loadSpeedSpinner();
        // loadVolumeSpinner();
    }

    public void loadHighSpinner(){
        SpinnerValueFactory.IntegerSpinnerValueFactory highValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(15, 40, 1);
        highSpinner.setValueFactory(highValueFactory);
    }

    public void loadSpeedSpinner(){
        SpinnerValueFactory.DoubleSpinnerValueFactory speedValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.10, 0.30, 0.05);
        speedSpinner.setValueFactory(speedValueFactory);
    }

    public void loadPoundSpinner(){
        SpinnerValueFactory.DoubleSpinnerValueFactory poundValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(16.6, 19.4, 0.1);
        poundSpinner.setValueFactory(poundValueFactory);
    }

    public void loadCohesionSpinner(){
        SpinnerValueFactory.DoubleSpinnerValueFactory cohesionValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(13.8, 67.5, 0.1);
        cohesionSpinner.setValueFactory(cohesionValueFactory);
    }

    public void loadAngleSpinner(){
        SpinnerValueFactory.DoubleSpinnerValueFactory angleValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(12.6, 29.5, 0.1);
        angleSpinner.setValueFactory(angleValueFactory);
    }

    public void loadPermeabilitySpinner(){ //revisar
        SpinnerValueFactory.DoubleSpinnerValueFactory permeabilityvalueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(12.6, 29.5, 0.1);
        permeabilitySpinner.setValueFactory(permeabilityvalueFactory);
    }

    public void loadVolumeSpinner(){
        SpinnerValueFactory.DoubleSpinnerValueFactory volumevalueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.553, 0.378, 0.1);
        volumeSpinner.setValueFactory(volumevalueFactory);
    }

    public void loadCrownSpinner(){
        SpinnerValueFactory.DoubleSpinnerValueFactory crownValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(4.0, 6.0, 0.5);
        crownSpinner.setValueFactory(crownValueFactory);
    }

    public void loadDayTimeSpinner(){
        SpinnerValueFactory.IntegerSpinnerValueFactory timeValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 350, 1);
        timeSpinner.setValueFactory(timeValueFactory);
    }

    public void loadPercentTimeSpinner(){
        SpinnerValueFactory.IntegerSpinnerValueFactory percentValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 10);
        timeSpinner.setValueFactory(percentValueFactory);
    }

    public void loadComboBoxes(){
        loadFormulaBox();
        loadPhenomenonBox();
    }

    public void loadFormulaBox(){
        ObservableList<Formula> list = FXCollections.observableArrayList(Formula.values());
        formulaBox.setItems(list);
    }

    public void loadPhenomenonBox(){
        ObservableList<Phenomenon> list = FXCollections.observableArrayList(Phenomenon.values());
        phenomenonBox.setItems(list);
    }
}
