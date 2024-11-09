package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;

import javax.xml.parsers.ParserConfigurationException;

import Properties.Propiedad;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import logic.DamSystem;
import logic.Model;
import logic.Variable;
import util.*;

import static util.FilesManagement.SetearTextos;

public class securityFactorPane {
    static securityFactorPane window;


    private String variableAnalysis;


    public void setVariableAnalysis(String variableAnalysis) {
        this.variableAnalysis = variableAnalysis;
    }

    @FXML
    Pane anchorSFP;

    // @FXML Spinner timeSpinner;
    // @FXML Spinner highSpinner;
    @FXML
    Spinner speedSpinner;
    // @FXML Spinner poundSpinner;
    // @FXML Spinner cohesionSpinner;
    // @FXML Spinner angleSpinner;
    // @FXML Spinner permeabilitySpinner;
    // @FXML Spinner volumeSpinner;
    // @FXML Spinner crownSpinner;

    public Spinner getSpeedSpinner() {
        return speedSpinner;
    }

    @FXML
    ComboBox formulaBox;
    @FXML
    ComboBox<Object> seleccionarr;
    @FXML
    ComboBox<Object> seleccionar;



    @FXML
    TableColumn Variablee;
    @FXML
    TableColumn valueColumn;
    @FXML
    TableView<ValueDTO> valueTable;

    @FXML
    Label altura;
    @FXML
    Label corona;

    @FXML
    Label Estado;
    @FXML
    Label resultado_del_estado;

    @FXML
    Label modelLabel;
    @FXML
    Label variableLabel;
    @FXML
    Label pesoo;
    @FXML
    Label cohesionn;
    @FXML
    Label angulo;
    @FXML
    Label no_hay_datos_en_el_sistema_para_utilizar_la_opcion_seleccionada;

    public Label getVariableLabel() {
        return variableLabel;
    }

    @FXML
    Button managementBtn;
    @FXML
    Button Calcular;
    @FXML
    Button saveBtn;
    @FXML
    Button analisis_de_sensibilidad;
    @FXML
    Button Limpiar_grafico;

    public Button getLimpiar_grafico() {
        return Limpiar_grafico;
    }

    @FXML
    LineChart<?, ?> sFchart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    private ObservableList<ValueDTO> list;


    public ObservableList<ValueDTO> getList() {
        return list;
    }

    public void initialize() {
        window = this;
        loadComboBoxes();
        SetearTextos(this.anchorSFP.getChildren(), Menu.getIdioma_actual());
        if (Menu.getIdioma_actual().equalsIgnoreCase("ESPAÑOL")) {
            securityFactorPane.getInstance().valueColumn.setText("Valor");
            securityFactorPane.getInstance().valueTable.setPlaceholder(new Label("Tabla sin contenido"));
            securityFactorPane.getInstance().seleccionarr.setPromptText("Seleccionar");
            securityFactorPane.getInstance().seleccionar.setPromptText("Seleccionar");
            securityFactorPane.getInstance().formulaBox.setPromptText("Seleccionar");
        } else {
            securityFactorPane.getInstance().valueColumn.setText("Value");
            securityFactorPane.getInstance().valueTable.setPlaceholder(new Label("No content in table"));
            securityFactorPane.getInstance().formulaBox.setPromptText("Select");
            securityFactorPane.getInstance().seleccionarr.setPromptText("Select");
            securityFactorPane.getInstance().seleccionar.setPromptText("Select");
        }
        //xAxis = new CategoryAxis();
        /*sFchart.getYAxis().setLabel("FS");
        sFchart.getXAxis().setLabel("Dominio de la variable");*/
        // yAxis.setLabel("FS");
        // xAxis.setLabel("Dominio de la variable");
    }

    public static securityFactorPane getInstance() {
        return window;
    }

    public Pane getAnchorSFP() {
        return anchorSFP;
    }

    public TableView<ValueDTO> getTable() {
        return valueTable;
    }

    public void loadComboBoxes() {
        //  loadFormulaBox();
        loadPhenomenonBox();
    }

    public void loadFormulaBox() {

        if (seleccionarr.getSelectionModel().getSelectedIndex() == 0) {
            if (seleccionar.getSelectionModel().getSelectedIndex() == 0) {
                ObservableList<logic.Formula> list;
                if (Menu.getIdioma_actual().equalsIgnoreCase("ESPAÑOL")) {
                    list = FXCollections.observableArrayList(DamSystem.getInstance().getFormList());
                } else {
                    list = FXCollections.observableArrayList(DamSystem.getInstance().getFormListEng());
                }

                if (!list.isEmpty()) {
                    valueTable.setDisable(false);
                    formulaBox.setDisable(false);
                    Calcular.setDisable(true);
                    analisis_de_sensibilidad.setDisable(true);
                    // Limpiar_grafico.setDisable(true);
                    saveBtn.setDisable(true);

                    formulaBox.setItems(list);
                    modelLabel.setText("Fórmula:");
                    modelLabel.setVisible(true);
                    // managementBtn.setVisible(true);
                    no_hay_datos_en_el_sistema_para_utilizar_la_opcion_seleccionada.setVisible(false);
                } else {
                    no_hay_datos_en_el_sistema_para_utilizar_la_opcion_seleccionada.setVisible(true);
                    valueTable.setDisable(true);
                    formulaBox.setDisable(true);
                }
            } else {

                formulaBox.setDisable(false);
                Calcular.setDisable(true);
                analisis_de_sensibilidad.setDisable(true);
                // Limpiar_grafico.setDisable(true);
                saveBtn.setDisable(true);

                ObservableList<Model> list = FXCollections.observableArrayList(DamSystem.getInstance().getDesemModelsList());
                formulaBox.setItems(list);
                modelLabel.setText("Modelo:");
                modelLabel.setVisible(true);
                valueTable.setDisable(false);
            }
        } else {

            formulaBox.setDisable(false);
            Calcular.setDisable(true);
            analisis_de_sensibilidad.setDisable(true);
            // Limpiar_grafico.setDisable(true);
            saveBtn.setDisable(true);

            ObservableList<Model> list = FXCollections.observableArrayList(DamSystem.getInstance().getPreciModelsList());
            formulaBox.setItems(list);
            modelLabel.setText("Modelo:");
            modelLabel.setVisible(true);
            valueTable.setDisable(false);
        }
    }

    public void loadModelBox() {
        Calcular.setDisable(true);
        analisis_de_sensibilidad.setDisable(true);

        // Limpiar_grafico.setDisable(true);
        saveBtn.setDisable(true);
        seleccionar.setDisable(false);
        if (Menu.getIdioma_actual().equalsIgnoreCase("ESPAÑOL")) {
            ObservableList<Object> list = null;
            if (seleccionarr.getSelectionModel().getSelectedIndex() == 0) {
                list = FXCollections.observableArrayList(TypeModelEs.values());
                seleccionar.setItems(list);
                // managementBtn.setVisible(true);
            } else {
                list = FXCollections.observableArrayList(TypeModelEs.RNA);
                seleccionar.setItems(list);
                // managementBtn.setVisible(false);
            }
        }
        else

        {if (Menu.getIdioma_actual().equalsIgnoreCase("ENGLISH")) {
            ObservableList<Object> list = null;
            if (seleccionarr.getSelectionModel().getSelectedIndex() == 0) {
                list = FXCollections.observableArrayList(TypeModelEng.values());
                seleccionar.setItems(list);
                // managementBtn.setVisible(true);
            } else {
                list = FXCollections.observableArrayList(TypeModelEng.ANN);
                seleccionar.setItems(list);
                // managementBtn.setVisible(false);
            }
        }
        }
    }



    public void loadPhenomenonBox(){
        if (Menu.getIdioma_actual().equalsIgnoreCase("ESPAÑOL")) {
        ObservableList<Object> list = FXCollections.observableArrayList(Phenomenon.values());
        seleccionarr.setItems(list);
    }
        else{
            ObservableList<Object> list = FXCollections.observableArrayList(PhenomenonEng.values());
            seleccionarr.setItems(list);
        }
    }

    public <T> void loadValueTable(){
        Calcular.setDisable(false);
        analisis_de_sensibilidad.setDisable(false);
        // Limpiar_grafico.setDisable(false);
        if (Menu.getIdioma_actual().equalsIgnoreCase("ESPAÑOL")) {
        if(formulaBox.getSelectionModel().getSelectedItem() != null){
            if(seleccionarr.getSelectionModel().getSelectedItem().equals(Phenomenon.Desembalse)
            && seleccionar.getSelectionModel().getSelectedItem().equals(TypeModelEs.Regresión)){
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
        else{
            if(formulaBox.getSelectionModel().getSelectedItem() != null){
            if(seleccionarr.getSelectionModel().getSelectedItem().equals(PhenomenonEng.Drawdown)
                    && seleccionar.getSelectionModel().getSelectedItem().equals(TypeModelEng.Regression)){
                loadRegression();
            }else{
                try {
                    loadBayes();
                } catch (ErrorFieldException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }}
    }

    private void loadBayes() throws ErrorFieldException{
        Model aux = (Model) formulaBox.getSelectionModel().getSelectedItem();
        ArrayList<String> vList = aux.getVariables();
        ArrayList<ValueDTO> vaList = new ArrayList<ValueDTO>();
        Variablee.setCellValueFactory(new PropertyValueFactory<ValueDTO, String>("name"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<ValueDTO, Double>("value"));
        valueColumn.setEditable(true);
        valueColumn.setCellFactory(param -> new EditableCell<>(EditableCell.createDoubleStringConverter()));
        //valueColumn.setCellFactory(param -> new EditableCell<>(new DoubleStringConverter()));
        int i = 0;
        for(String l: vList){
            vaList.add(new ValueDTO("NO", vList.get(i), 0, 200,0.0,0.0));
            i++;
        }
        list = FXCollections.observableArrayList(vaList);
        if(Menu.getIdioma_actual() .equalsIgnoreCase("ESPAÑOL"))
        update();
        else updateEng();
    }

    private void loadRegression(){
        logic.Formula aux = (logic.Formula)formulaBox.getSelectionModel().getSelectedItem();
        ArrayList<Variable> vList = aux.getVariables();
        ArrayList<ValueDTO> vaList = new ArrayList<ValueDTO>();
        Variablee.setCellValueFactory(new PropertyValueFactory<ValueDTO, String>("name"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<ValueDTO, Double>("value"));
        valueColumn.setEditable(true);
        valueColumn.setCellFactory(param -> new EditableCell<>(EditableCell.createDoubleStringConverter()));
        //valueColumn.setCellFactory(param -> new EditableCell<>(new DoubleStringConverter()));
        for(Variable l: vList){
            try {
                vaList.add(new ValueDTO(l.getNomenclature(), l.getName(), l.getDownLimit(), l.getUpLimit(),l.getDwnLimit(),l.getupLimit()));

            } catch (ErrorFieldException e) {
               if(Menu.getIdioma_actual().equalsIgnoreCase("ESPAÑOL")) {
              Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("información");
        alert.setHeaderText("Valor insertado no válido");
        alert.setContentText(e.getMessage());
        alert.showAndWait();}

                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("information");
                    alert.setHeaderText("Inserted value not valid");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();}
                }


        }
        list = FXCollections.observableArrayList(vaList);
        if(Menu.getIdioma_actual() .equalsIgnoreCase("ESPAÑOL"))
            update();
        else updateEng();
    }



    public void update(){
        valueTable.setItems(list);
    }

    public void updateEng(){
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
            //if (newValue<rowData.getUpLimit()&& newValue>rowData.getDownLimit()){

            rowData.setValue(newValue);

        }catch(Exception ne){
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
    }  /*aqui revisar cambiar estos textos q carguen y no sean escritos manualmente*/
    if(var != -1){
        if(Menu.getIdioma_actual() .equalsIgnoreCase("ESPAÑOL")){

        Estado.setText("Factor de seguridad: "+ String.format("%.3f", var));
        if(var < 1){
            resultado_del_estado.setText("Talud en posible fallo");
            resultado_del_estado.getStyleClass().clear();
            resultado_del_estado.getStyleClass().add("case 1");
        }else if(var >= 1 && var < 1.5){
            resultado_del_estado.setText("Talud estable");
            resultado_del_estado.getStyleClass().clear();
            resultado_del_estado.getStyleClass().add("case 2");
        }
        else if(var >= 1.5 && var <= 2){
            resultado_del_estado.setText("Talud estable y seguro");
            resultado_del_estado.getStyleClass().clear();
            resultado_del_estado.getStyleClass().add("case 3");
        }else if(var >2){
            resultado_del_estado.setText("Talud estable y seguro pero antieconómico");
            resultado_del_estado.getStyleClass().clear();
            resultado_del_estado.getStyleClass().add("case 4");
        }}
        else {
            Propiedad propiedad= new Propiedad("ENGLISH");
            Estado.setText(propiedad.getProperty("Factor_de_seguridad")+": "+ String.format("%.3f", var));
            if(var < 1){
                resultado_del_estado.setText(propiedad.getProperty("possible_failure_slope"));
                resultado_del_estado.getStyleClass().clear();
                resultado_del_estado.getStyleClass().add("case 1");
            }else if(var >= 1 && var < 1.5){
                resultado_del_estado.setText(propiedad.getProperty("stable_slope"));
                resultado_del_estado.getStyleClass().clear();
                resultado_del_estado.getStyleClass().add("case 2");
            }
            else if(var >= 1.5 && var <= 2){
                resultado_del_estado.setText(propiedad.getProperty("safe_slope"));
                resultado_del_estado.getStyleClass().clear();
                resultado_del_estado.getStyleClass().add("case 3");
            }else if(var >2){
                resultado_del_estado.setText(propiedad.getProperty("uneconomical_slope"));
                resultado_del_estado.getStyleClass().clear();
                resultado_del_estado.getStyleClass().add("case 4");
            }
        }
        Estado.setVisible(true);
        resultado_del_estado.setVisible(true);
        //Estado.setVisible(true);
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
        altura.setText(String.valueOf(aux.getValue()));
        altura.setVisible(true);
        }
        if(aux.getName().contains("orona")){
        corona.setText(String.valueOf(aux.getValue()));
        corona.setVisible(true);
        }
        if(aux.getName().contains("gulo")){
        angulo.setText(String.valueOf(aux.getValue()));
        angulo.setVisible(true);
        }
        if(aux.getName().contains("ohes")){
        cohesionn.setText(String.valueOf(aux.getValue()));
        cohesionn.setVisible(true);
        }
        if(aux.getName().contains("eso")){
        pesoo.setText(String.valueOf(aux.getValue()));
        pesoo.setVisible(true);
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
                System.out.println(e.getMessage());
            }
            // loadChart(list);
    }

    public void clearChartData(){
        sFchart.getData().clear();
        Limpiar_grafico.setDisable(true);
    }

    public void showAnalysisPane(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("AnalysisPane.fxml"));
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


