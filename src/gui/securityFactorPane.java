package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import dto.ValueDTO;
import exception.ActionNotPermitted;
import exception.ErrorFieldException;
import jakarta.xml.bind.JAXBException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.converter.DoubleStringConverter;
import logic.DamSystem;
import logic.Formula;
import logic.Model;
import logic.Variable;
import util.EditableCell;
import util.Phenomenon;
import util.TypeModel;

public class securityFactorPane {
    static securityFactorPane window;

    private String variableAnalysis;
    

    public void setVariableAnalysis(String variableAnalysis) {
        this.variableAnalysis = variableAnalysis;
    }

    @FXML Pane anchorSFP;

    // @FXML Spinner timeSpinner;
    // @FXML Spinner highSpinner;
    @FXML Spinner speedSpinner;
    // @FXML Spinner poundSpinner;
    // @FXML Spinner cohesionSpinner;
    // @FXML Spinner angleSpinner;
    // @FXML Spinner permeabilitySpinner;
    // @FXML Spinner volumeSpinner;
    // @FXML Spinner crownSpinner;

    public Spinner getSpeedSpinner() {
        return speedSpinner;
    }

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
    @FXML Label variableLabel;
    @FXML Label weightValue;
    @FXML Label cohesionLabel;
    @FXML Label angleLabel;
    @FXML Label noDataLabel;

    public Label getVariableLabel() {
        return variableLabel;
    }

    @FXML Button managementBtn;
    @FXML Button calculateBtn;
    @FXML Button saveBtn;
    @FXML Button anlysisBtn;
    @FXML  Button cleanBtn;

    public Button getCleanBtn() {
        return cleanBtn;
    }

    @FXML LineChart<?,?> sFchart;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;

    private ObservableList<ValueDTO> list;



    public ObservableList<ValueDTO> getList() {
        return list;
    }

    public void initialize(){
        window = this;
        loadComboBoxes();
        //xAxis = new CategoryAxis();
        sFchart.getYAxis().setLabel("FS");
        sFchart.getXAxis().setLabel("Dominio de la variable");
        // yAxis.setLabel("FS");
        // xAxis.setLabel("Dominio de la variable");
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
        
        if(phenomenonBox.getSelectionModel().getSelectedIndex() == 0){
            if(modelBox.getSelectionModel().getSelectedIndex()==0){
        ObservableList<logic.Formula> list = FXCollections.observableArrayList(DamSystem.getInstance().getFormList());
        if(!list.isEmpty()){
            valueTable.setDisable(false);
            formulaBox.setDisable(false);
            calculateBtn.setDisable(true);
            anlysisBtn.setDisable(true);
            // cleanBtn.setDisable(true);
            saveBtn.setDisable(true);

        formulaBox.setItems(list);
        modelLabel.setText("Ecuación:");
                modelLabel.setVisible(true);
        // managementBtn.setVisible(true);
        noDataLabel.setVisible(false);
        }else{
            noDataLabel.setVisible(true);
            valueTable.setDisable(true);
            formulaBox.setDisable(true);
        }
        }else{

            formulaBox.setDisable(false);
            calculateBtn.setDisable(true);
            anlysisBtn.setDisable(true);
            // cleanBtn.setDisable(true);
            saveBtn.setDisable(true);

            ObservableList<Model> list = FXCollections.observableArrayList(DamSystem.getInstance().getDesemModelsList());
            formulaBox.setItems(list);
            modelLabel.setText("Modelo:");
                modelLabel.setVisible(true);
                valueTable.setDisable(false);
        }
    }else{

        formulaBox.setDisable(false);
        calculateBtn.setDisable(true);
        anlysisBtn.setDisable(true);
        // cleanBtn.setDisable(true);
        saveBtn.setDisable(true);

        ObservableList<Model> list = FXCollections.observableArrayList(DamSystem.getInstance().getPreciModelsList());
        formulaBox.setItems(list);
        modelLabel.setText("Modelo:");
            modelLabel.setVisible(true);
            valueTable.setDisable(false);
    }
    }

    public void loadModelBox(){
        calculateBtn.setDisable(true);
        anlysisBtn.setDisable(true);
        // cleanBtn.setDisable(true);
        saveBtn.setDisable(true);
        modelBox.setDisable(false);
        ObservableList<TypeModel> list= null;
        if(phenomenonBox.getSelectionModel().getSelectedIndex() == 0){
            list = FXCollections.observableArrayList(TypeModel.values());
            modelBox.setItems(list);
            // managementBtn.setVisible(true);
        }else{
            list = FXCollections.observableArrayList(TypeModel.RNA);
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
        anlysisBtn.setDisable(false);
        // cleanBtn.setDisable(false);
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
            vaList.add(new ValueDTO("NO", vList.get(i), 0, 200));
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

    // public void checkValue(KeyEvent e){
    //     String aux = e.getText();
    //     for (int i = 0; i < aux.length(); i++) {
    //         if(Character.isAlphabetic(i)){
    //         Alert alert = new Alert(Alert.AlertType.ERROR);
    //         alert.setTitle("información");
    //         alert.setHeaderText("Valor insertado no válido");
    //         // alert.setContentText(e.getMessage());
    //         alert.showAndWait();
    //         }
    //     }
    // }

    public void updateTable(TableColumn.CellEditEvent<ValueDTO, Double> event){
        
        try {
            TablePosition<ValueDTO, Double> pos = event.getTablePosition();
        int row = pos.getRow();
        ValueDTO rowData = event.getTableView().getItems().get(row);
            Double newValue = event.getNewValue();
            rowData.setValue(newValue);
        } catch (ErrorFieldException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("información");
            alert.setHeaderText("Valor insertado no válido");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            draw();
        } catch(Exception ne){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("información");
            alert.setHeaderText("Valor insertado no válido");
            alert.setContentText("Error: "+ne.getMessage());
            alert.showAndWait();
        }
        draw();
    }

    public void calculateButton(){
        Object formula = formulaBox.getSelectionModel().getSelectedItem();
        ArrayList<ValueDTO> list = new ArrayList<ValueDTO>(valueTable.getItems());
        double var = -1;
        if(formulaBox.getSelectionModel().getSelectedItem() instanceof logic.Formula){
        try {
            var = DamSystem.getInstance().securityFactor(formula, list);
        } catch (ActionNotPermitted e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }else{
        try{
        Model aux = (Model)formulaBox.getSelectionModel().getSelectedItem();
        var = aux.evaluate(list);
        }catch(Exception e){
            e.printStackTrace();
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
        //statusLabel.setVisible(true);
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

    public void draw(){
        ValueDTO aux = valueTable.getSelectionModel().getSelectedItem();
        if(aux.getName().contains("ltura")){
        highValueLabel.setText(String.valueOf(aux.getValue()));
        highValueLabel.setVisible(true);
        }
        if(aux.getName().contains("orona")){
        crownValueLabel.setText(String.valueOf(aux.getValue()));
        crownValueLabel.setVisible(true);
        }
        if(aux.getName().contains("gulo")){
        angleLabel.setText(String.valueOf(aux.getValue()));
        angleLabel.setVisible(true);
        }
        if(aux.getName().contains("ohes")){
        cohesionLabel.setText(String.valueOf(aux.getValue()));
        cohesionLabel.setVisible(true);
        }
        if(aux.getName().contains("eso")){
        weightValue.setText(String.valueOf(aux.getValue()));
        weightValue.setVisible(true);
        }
    }

    public void loadChart(LinkedList<Double> list, double value){
        XYChart.Series series = new XYChart.Series<>();
        // yAxis.setLowerBound(0);
        // yAxis.setUpperBound(5);
        
        //yAxis.setTickUnit(1);
        xAxis.setTickLength(10);
        double var = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            series.getData().add(new XYChart.Data<String,Double>(String.valueOf(var), list.get(i)));
            var += value;
        }
        series.setName(variableAnalysis);
        sFchart.getData().add(series);
    }

    public void bookBtn() throws ActionNotPermitted, ErrorFieldException, ParserConfigurationException, SAXException, JAXBException, IOException{
        TextInputDialog dialogo = new TextInputDialog();
        dialogo.setHeaderText("Ingrese un identificador para los datos:");
        dialogo.setContentText("Identificador:");
        Optional<String> text = dialogo.showAndWait();
            DamSystem.getInstance().saveResults();
    }


    public void graph(){
        Object o = formulaBox.getSelectionModel().getSelectedItem();
        LinkedList<Double> list = new LinkedList<Double>();
            // list = new LinkedList<Double>();
            try {
                list = DamSystem.getInstance().securityFactorList(o, new ArrayList<ValueDTO>(valueTable.getItems()));
                // DamSystem.getInstance().addResult(, list);
            } catch (ActionNotPermitted | ErrorFieldException | ParserConfigurationException | SAXException
                    | JAXBException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // loadChart(list);
    }

    public void clearChartData(){
        sFchart.getData().clear();
        cleanBtn.setDisable(true);
    }

    public void showAnalysisPane(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../gui/AnalysisPane.fxml"));
            root.setLayoutX(300);
            root.setLayoutY(200);
            anchorSFP.getChildren().add(root);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void loadSpinnerSpeed(String name, double value) throws ActionNotPermitted, ErrorFieldException, ParserConfigurationException, SAXException, JAXBException, IOException{
        LinkedList<Double> list = DamSystem.getInstance().securityFactorAnalysisList(formulaBox.getSelectionModel().getSelectedItem(), new ArrayList<>(valueTable.getItems()), value, name);
        loadChart(list, value);
}
}


