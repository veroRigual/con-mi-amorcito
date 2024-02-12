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
import logic.Model;
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
    @FXML ComboBox<Phenomenon> phenomenonBox;
    @FXML ComboBox<TypeModel> modelBox;

    @FXML TableColumn variableColumn;
    @FXML TableColumn valueColumn;
    @FXML TableView<ValueDTO> valueTable;

    @FXML Label infoLabel;
    @FXML Label infoLabel1;
    @FXML Label statusLabel;
    @FXML Label highValueLabel;
    @FXML Label crownValueLabel;
    @FXML Label modelLabel;

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
        if(phenomenonBox.getSelectionModel().getSelectedIndex() == 0){
            if(modelBox.getSelectionModel().getSelectedIndex()==0){
        ObservableList<logic.Formula> list = FXCollections.observableArrayList(DamSystem.getInstance().getFormList());
        formulaBox.setItems(list);
        modelLabel.setText("Ecuación:");
                modelLabel.setVisible(true);
        // managementBtn.setVisible(true);
        }else{
            ObservableList<Model> list = FXCollections.observableArrayList(DamSystem.getInstance().getDesemModelsList());
            formulaBox.setItems(list);
            modelLabel.setText("Modelo:");
                modelLabel.setVisible(true);
        }
    }else{
        ObservableList<Model> list = FXCollections.observableArrayList(DamSystem.getInstance().getPreciModelsList());
        formulaBox.setItems(list);
        modelLabel.setText("Modelo:");
            modelLabel.setVisible(true);
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
        if(formulaBox.getSelectionModel().getSelectedItem() != null){
            if(phenomenonBox.getSelectionModel().getSelectedItem().equals(Phenomenon.Desembalse)
            && modelBox.getSelectionModel().getSelectedItem().equals(TypeModel.Regresión)){
                loadRegression();
            }else{
                try {
                    loadBayes();
                } catch (ErrorFieldException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
}
    }

    private void loadBayes() throws ErrorFieldException{
        Model aux = (Model) formulaBox.getSelectionModel().getSelectedItem();
        ArrayList<String> vList = aux.getVariables();
        ArrayList<ValueDTO> vaList = new ArrayList<ValueDTO>();
        variableColumn.setCellValueFactory(new PropertyValueFactory<ValueDTO, String>("name"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<ValueDTO, Double>("value"));
        valueColumn.setEditable(true);
        valueColumn.setCellFactory(param -> new EditableCell<>(new DoubleStringConverter()));
        int i = 0;
        for(String l: vList){
            vaList.add(new ValueDTO("NO", vList.get(i), Double.MIN_VALUE, Double.MAX_VALUE));
            i++;
        }
        list = FXCollections.observableArrayList(vaList);
        update();
    }

    private void loadRegression(){
        logic.Formula aux = (logic.Formula)formulaBox.getSelectionModel().getSelectedItem();
        ArrayList<Variable> vList = aux.getVariables();
        ArrayList<ValueDTO> vaList = new ArrayList<ValueDTO>();
        variableColumn.setCellValueFactory(new PropertyValueFactory<ValueDTO, String>("name"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<ValueDTO, Double>("value"));
        valueColumn.setEditable(true);
        valueColumn.setCellFactory(param -> new EditableCell<>(new DoubleStringConverter()));
        for(Variable l: vList){
            try {
                vaList.add(new ValueDTO(l.getNomenclature(), l.getName(), l.getDownLimit(), l.getUpLimit()));
                list = FXCollections.observableArrayList(vaList);

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
        double var = -1;
        if(formulaBox.getSelectionModel().getSelectedItem() instanceof logic.Formula){
        try {
            var = DamSystem.getInstance().securityFactor(nameFormula, list);
        } catch (ActionNotPermitted e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }else{
        try{
        Model aux = (Model)formulaBox.getSelectionModel().getSelectedItem();
        var = aux.evaluate(list);
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("información");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    if(var != -1){
        infoLabel.setText("Factor de seguridad: "+ String.format("%.3f", var));
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
        saveBtn.setDisable(false);
    }
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
        if(aux.getName().equalsIgnoreCase("altura")
        || aux.getName().equals("Altura de terraplén"))
        highValueLabel.setText(String.valueOf(aux.getValue()));
        if(aux.getName().equalsIgnoreCase("ancho de corona"))
        crownValueLabel.setText(String.valueOf(aux.getValue()));
    }
}