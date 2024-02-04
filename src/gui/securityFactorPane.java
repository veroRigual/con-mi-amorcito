package gui;

import java.beans.EventHandler;
import java.util.ArrayList;

import javax.swing.Action;

import dto.ValueDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.converter.DoubleStringConverter;
import logic.DamSystem;
import logic.Variable;
import util.EditableCell;
import util.MyStringConverter;
import util.Phenomenon;
import util.Util;

public class securityFactorPane {
    static securityFactorPane anchorSFP;

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

    @FXML TableColumn variableColumn;
    @FXML TableColumn valueColumn;
    @FXML TableView<ValueDTO> valueTable;

    @FXML Label infoLabel;
    @FXML Label infoLabel1;
    @FXML Label statusLabel;

    private ObservableList<ValueDTO> list;


    public ObservableList<ValueDTO> getList() {
        return list;
    }

    public void initialize(){
        anchorSFP = this;
        // loadSpinners();
        loadComboBoxes();
    }

    public static securityFactorPane getInstance(){
        return anchorSFP;
    }

    public TableView<ValueDTO> getTable(){
        return valueTable;
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
        ObservableList<logic.Formula> list = FXCollections.observableArrayList(DamSystem.getInstance().getFormList());
        formulaBox.setItems(list);
    }

    public <T> void loadPhenomenonBox(){
        ObservableList<Phenomenon> list = FXCollections.observableArrayList(Phenomenon.values());
        phenomenonBox.setItems(list);
    }

    public <T> void loadValueTable(){
        logic.Formula aux = (logic.Formula)formulaBox.getSelectionModel().getSelectedItem();
        ArrayList<Variable> vList = aux.getVariables();
        ArrayList<ValueDTO> vaList = new ArrayList<ValueDTO>();
        for(Variable l: vList){
            vaList.add(new ValueDTO(0, l.getNomenclature(), l.getName()));
        }
        list = FXCollections.observableArrayList(vaList);
        variableColumn.setCellValueFactory(new PropertyValueFactory<ValueDTO, String>("name"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<ValueDTO, T>("value"));
        valueColumn.setEditable(true);
        //valueColumn.setCellFactory(tc -> new EditableTableCell<ValueDTO, T>());
        valueColumn.setCellFactory(param -> new EditableCell<>(new DoubleStringConverter()));
        

        update();
    }

    public void update(){
        valueTable.setItems(list);
    }

    public void updateTable(TableColumn.CellEditEvent<ValueDTO, Double> event){
        TablePosition<ValueDTO, Double> pos = event.getTablePosition();
        Double newValue = event.getNewValue();
        int row = pos.getRow();
        ValueDTO rowData = event.getTableView().getItems().get(row);
        rowData.setValue(newValue);
    }

    public void calculateButton(){
        String nameFormula = formulaBox.getSelectionModel().getSelectedItem().toString();
        ArrayList<ValueDTO> list = new ArrayList<ValueDTO>(valueTable.getItems());
        double var = DamSystem.getInstance().securityFactor(nameFormula, list);
        infoLabel.setText("Factor de seguridad: "+var);
        if(var < 1){
            infoLabel1.setText("Talud en posible fallo");
            infoLabel1.getStyleClass().clear();
            infoLabel1.getStyleClass().add("case 1");
        }else if(var >= 1 && var < 1.5){
            infoLabel1.setText("Talud estable");
            infoLabel1.getStyleClass().clear();
            infoLabel1.getStyleClass().add("case 2");
        }
        else if(var >= 1.5 && var <= 2){
            infoLabel1.setText("Talud estable y seguro");
            infoLabel1.getStyleClass().clear();
            infoLabel1.getStyleClass().add("case 3");
        }else if(var >2){
            infoLabel1.setText("Talud estable y seguro pero antieconómico");
            infoLabel1.getStyleClass().clear();
            infoLabel1.getStyleClass().add("case 4");
        }
        infoLabel.setVisible(true);
        infoLabel1.setVisible(true);
        statusLabel.setVisible(true);
    }
}