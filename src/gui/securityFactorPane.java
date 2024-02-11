package gui;

import java.io.IOException;
import java.util.ArrayList;


import dto.ValueDTO;
import exception.ActionNotPermitted;
import exception.ErrorFieldException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.converter.DoubleStringConverter;
import logic.DamSystem;
import logic.Variable;
import util.EditableCell;
import util.Phenomenon;
import util.TypeModel;

public class securityFactorPane {
    static securityFactorPane window;

    @FXML Pane anchorSFP;

   

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
    @FXML ComboBox modelBox;

    @FXML TableColumn variableColumn;
    @FXML TableColumn valueColumn;
    @FXML TableView<ValueDTO> valueTable;

    @FXML Label infoLabel;
    @FXML Label infoLabel1;
    @FXML Label statusLabel;
    @FXML Label highValueLabel;
    @FXML Label crownValueLabel;

    @FXML Button managementBtn;
    @FXML Button calculateBtn;
    @FXML Button saveBtn;

    @FXML LineChart sFChart;

    private ObservableList<ValueDTO> list;


    public ObservableList<ValueDTO> getList() {
        return list;
    }

    public void initialize(){
        window = this;
        // loadSpinners();
        loadComboBoxes();
        loadChart();
    }

    public static securityFactorPane getInstance(){
        return window;
    }

    public Pane getAnchorSFP() {
        return anchorSFP;
    }

    public TableView<ValueDTO> getTable(){
        return valueTable;
    }

    public void loadComboBoxes(){
      //  loadFormulaBox();
        loadPhenomenonBox();
    }

    public void loadFormulaBox(){
        formulaBox.setDisable(false);
        calculateBtn.setDisable(true);
        saveBtn.setDisable(true);
        if(modelBox.getSelectionModel().getSelectedIndex()==0){
        ObservableList<logic.Formula> list = FXCollections.observableArrayList(DamSystem.getInstance().getFormList());
        formulaBox.setItems(list);
        // managementBtn.setVisible(true);
        }else{
            // managementBtn.setVisible(false);
        }
    }

    public void loadModelBox(){
        calculateBtn.setDisable(true);
        saveBtn.setDisable(true);
        modelBox.setDisable(false);
        ObservableList<TypeModel> list= null;
        if(phenomenonBox.getSelectionModel().getSelectedIndex() == 0){
            list = FXCollections.observableArrayList(TypeModel.values());
            modelBox.setItems(list);
            // managementBtn.setVisible(true);
        }else{
            list = FXCollections.observableArrayList(TypeModel.Redes_Neuronales);
            modelBox.setItems(list);
            // managementBtn.setVisible(false);
        }
    }

    public void loadPhenomenonBox(){
        ObservableList<Phenomenon> list = FXCollections.observableArrayList(Phenomenon.values());
        phenomenonBox.setItems(list);
    }

    public <T> void loadValueTable(){
        calculateBtn.setDisable(false);
        saveBtn.setDisable(false);
        logic.Formula aux = (logic.Formula)formulaBox.getSelectionModel().getSelectedItem();
        ArrayList<Variable> vList = aux.getVariables();
        ArrayList<ValueDTO> vaList = new ArrayList<ValueDTO>();
        for(Variable l: vList){
            try {
                vaList.add(new ValueDTO(l.getNomenclature(), l.getName(), l.getDownLimit(), l.getUpLimit()));
                list = FXCollections.observableArrayList(vaList);
        variableColumn.setCellValueFactory(new PropertyValueFactory<ValueDTO, String>("name"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<ValueDTO, T>("value"));
        valueColumn.setEditable(true);
        valueColumn.setCellFactory(param -> new EditableCell<>(new DoubleStringConverter()));

        update();
            } catch (ErrorFieldException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("información");
        alert.setHeaderText("Valor insertado no válido");
        alert.setContentText(e.getMessage());
        }
    }
    }

    public void update(){
        valueTable.setItems(list);
    }

    public void updateTable(TableColumn.CellEditEvent<ValueDTO, Double> event){
        TablePosition<ValueDTO, Double> pos = event.getTablePosition();
        Double newValue = event.getNewValue();
        int row = pos.getRow();
        ValueDTO rowData = event.getTableView().getItems().get(row);
        try {
            rowData.setValue(newValue);
        } catch (ErrorFieldException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("información");
            alert.setHeaderText("Valor insertado no válido");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            draw();
        } catch (Exception ne){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("información");
            alert.setHeaderText("Valor insertado no válido");
            alert.setContentText("Error: "+ne.getMessage());
            alert.showAndWait();
        }
        draw();
    }

    public void calculateButton(){
        String nameFormula = formulaBox.getSelectionModel().getSelectedItem().toString();
        ArrayList<ValueDTO> list = new ArrayList<ValueDTO>(valueTable.getItems());
        double var = 0;
        try {
            var = DamSystem.getInstance().securityFactor(nameFormula, list);
        } catch (ActionNotPermitted e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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

    public void showLogin(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            root.setLayoutX(300);
            root.setLayoutY(200);
            anchorSFP.getChildren().add(root);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void loadChart(){
        XYChart.Series<String,Double> series = new XYChart.Series<>();

    }

    public void draw(){
        ValueDTO aux = valueTable.getSelectionModel().getSelectedItem();
        if(aux.getName().equals("altura"))
        highValueLabel.setText(String.valueOf(aux.getValue()));
        if(aux.getName().equals("ancho de corona"))
        crownValueLabel.setText(String.valueOf(aux.getValue()));
    }
}