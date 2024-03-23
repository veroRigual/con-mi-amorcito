package gui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.html.HTMLDocument.Iterator;

import dto.ValueDTO;
import exception.ErrorFieldException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import logic.DamSystem;
import logic.Formula;
import logic.Variable;
import util.EditableCell;
import util.FilesManagement;

public class ManagementPane {
    static ManagementPane helpSFP;

    public boolean value = true;
    private int pos;

    @FXML AnchorPane anchorSFP;

    @FXML TableView formulaTable;
    @FXML TableColumn expreColumn;
    @FXML TableColumn nameColumn;

    @FXML TableView<Variable> variableTable;
    @FXML TableColumn variableColumn;

    @FXML TextField funtionNameText;
    @FXML TextField functionLabel;
    @FXML TextField variableNameText;
    @FXML TextArea descriptionText;

    @FXML Button aceptBtn;
    @FXML Button cancelBtn;
    @FXML Button deleteBtn;
    @FXML Button modifyBtn;
    @FXML Button addBtn;

    @FXML Spinner<Double> downLimitSpinner;
    @FXML Spinner<Double> upLimitSpinner;
    
    public static ManagementPane getInstance(){
        return helpSFP;
    }

    public void initialize(){
        helpSFP = this;
        loadTable();
        loadVariableTable();
        loadSpinner();
    }

    public void exitBtn(){
        securityFactorPane.getInstance().getAnchorSFP().getChildren().remove(anchorSFP);
        securityFactorPane.getInstance().loadComboBoxes();
    }

    public void loadSpinner(){
        SpinnerValueFactory<Double> valueFactory =new SpinnerValueFactory.DoubleSpinnerValueFactory(0,Double.MAX_VALUE,0,0.1);
        SpinnerValueFactory<Double> valueFactory1 =new SpinnerValueFactory.DoubleSpinnerValueFactory(0,Double.MAX_VALUE,0.1,0.1);
        upLimitSpinner.setValueFactory(valueFactory);
        downLimitSpinner.setValueFactory(valueFactory1);
    }

    public void loadUpLimitSpinner(){
        double value = downLimitSpinner.getValue() + 0.1;
        SpinnerValueFactory<Double> valueFactory1 =new SpinnerValueFactory.DoubleSpinnerValueFactory(value,Double.MAX_VALUE,value,0.1);
        upLimitSpinner.setValueFactory(valueFactory1);
    }

    public void loadTable(){
        nameColumn.setCellValueFactory(new PropertyValueFactory<Formula, String>("name"));
        expreColumn.setCellValueFactory(new PropertyValueFactory<Formula, String>("function"));

        update();
    }

    public void update(){
        ObservableList<Formula> aux = FXCollections.observableArrayList(DamSystem.getInstance().getFormList());
        formulaTable.setItems(aux);
    }

    public void loadVariableTable(){
        variableColumn.setCellValueFactory(new PropertyValueFactory<Variable, String>("nomenclature"));
        updateVariableTable();
    }

    public void updateVariableTable(){
        ObservableList<Variable> list = FXCollections.observableList(extractVariables(functionLabel.getText()));
        variableTable.setItems(list);
        if(list.isEmpty()){
            variableNameText.setDisable(true);
            descriptionText.setDisable(true);
            downLimitSpinner.setDisable(true);
            upLimitSpinner.setDisable(true);
            variableNameText.setText("");
            descriptionText.setText("");
            variableTable.setDisable(true);
        }
        else{
            variableTable.setDisable(false);
        }
      //  checkFilds();
    }

    public static ArrayList<Variable> extractVariables(String equation) {
    ArrayList<String> variables = new ArrayList<>();
    ArrayList<Variable> list = new ArrayList<>();
    Pattern pattern = Pattern.compile("\\b([a-zA-Z_][a-zA-Z0-9_]*)\\b");
    Matcher matcher = pattern.matcher(equation);

    while (matcher.find()) {
    String variable = matcher.group(1);
    if (!variables.contains(variable)) {
        variables.add(variable);
    }
    }

    for(String l:variables)
        try {
            list.add(new Variable("", l, "",0,0));
        } catch (ErrorFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    return list;
    }

    public void insertData(){
        int auxIndex = variableTable.getSelectionModel().getSelectedIndex();
        ((Variable)variableTable.getItems().get(auxIndex)).setDescription(descriptionText.getText());
        ((Variable)variableTable.getItems().get(auxIndex)).setName(variableNameText.getText());

        checkFilds();
    }

    public void insertDataSpinner(){
        int auxIndex = variableTable.getSelectionModel().getSelectedIndex();
        try{
        ((Variable)variableTable.getItems().get(auxIndex)).setDownLimit(downLimitSpinner.getValue());
        ((Variable)variableTable.getItems().get(auxIndex)).setUpLimit(upLimitSpinner.getValue());
        }
        catch(ErrorFieldException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("información");
            alert.setHeaderText("Valor insertado no válido");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        
        checkFilds();
    }

    public void ModifyBtn(){
        Formula formula = (Formula) formulaTable.getSelectionModel().getSelectedItem();
        if(formula != null){
        if(value)
            value = false;
        funtionNameText.setDisable(false);
        functionLabel.setDisable(false);
        funtionNameText.setText(formula.getName());
        functionLabel.setText(formula.getFunction());
        pos = formulaTable.getSelectionModel().getSelectedIndex();
        variableTable.setItems(FXCollections.observableArrayList(formula.getVariables()));
        variableTable.setDisable(false);
        cancelBtn.setDisable(false);
        }
    }

    public void InsertBtn(){
        if(!value)
            value = false;

        funtionNameText.setDisable(false);
        functionLabel.setDisable(false);
        // variableTable.setDisable(false);
        cancelBtn.setDisable(false);
    }

    public void activateFields(){
        variableNameText.setDisable(false);
        descriptionText.setDisable(false);
    }

    public void checkFilds(){
        if(!funtionNameText.getText().equals("") &&
        !functionLabel.getText().equals("") && !variableNameText.getText().equals("")
        && !descriptionText.getText().equals(""))
            aceptBtn.setDisable(false);
        else
            aceptBtn.setDisable(true);
    }

    public void AceptBtn(){
        if(value){
            ArrayList<Variable> list = new ArrayList<Variable>(variableTable.getItems());
            DamSystem.getInstance().addFormula(funtionNameText.getText(), functionLabel.getText(), list);
            FilesManagement.WriteOneFormulaToFile(DamSystem.getInstance().getFormulasFile(),funtionNameText.getText(), functionLabel.getText(), list);
            insertDataSpinner();
           // update();
            
            funtionNameText.setText("");
            functionLabel.setText("");
            descriptionText.setText("");
            variableNameText.setText("");

            funtionNameText.setDisable(true);
            functionLabel.setDisable(true);
            variableNameText.setDisable(true);
            descriptionText.setDisable(true);
            downLimitSpinner.setDisable(true);
            upLimitSpinner.setDisable(true);
            cancelBtn.setDisable(true);
            cancelBtn.setDisable(true);
            variableTable.getItems().clear();
            variableTable.setDisable(true);
            aceptBtn.setDisable(true);
            update();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("información");
            alert.setHeaderText("Aviso");
            alert.setContentText("La fórmula se ha registrado exitosamente");
            alert.showAndWait();
        }else{
            ArrayList<Variable> list = new ArrayList<Variable>(variableTable.getItems());
            DamSystem.getInstance().modifyFormula(pos, funtionNameText.getText(), functionLabel.getText(), list);
            //update();
            FilesManagement.WriteOneFormulaToFile(DamSystem.getInstance().getFormulasFile(),funtionNameText.getText(), functionLabel.getText(), list);
            insertDataSpinner();
            funtionNameText.setText("");
            functionLabel.setText("");
            descriptionText.setText("");
            variableNameText.setText("");

            funtionNameText.setDisable(true);
            functionLabel.setDisable(true);
            variableNameText.setDisable(true);
            descriptionText.setDisable(true);
            downLimitSpinner.setDisable(true);
            upLimitSpinner.setDisable(true);
            cancelBtn.setDisable(true);
            cancelBtn.setDisable(true);
            variableTable.getItems().clear();
            variableTable.setDisable(true);
            aceptBtn.setDisable(true);
            update();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("información");
            alert.setHeaderText("Aviso");
            alert.setContentText("La formula se ha modificado exitosamente");
            alert.showAndWait();
        }
    }

    public void cancelBtn(){
        funtionNameText.setText("");
            functionLabel.setText("");
            descriptionText.setText("");
            variableNameText.setText("");

            funtionNameText.setDisable(true);
            functionLabel.setDisable(true);
            variableTable.setDisable(true);
            variableNameText.setDisable(true);
            descriptionText.setDisable(true);
            downLimitSpinner.setDisable(true);
            upLimitSpinner.setDisable(true);
            cancelBtn.setDisable(true);
            cancelBtn.setDisable(true);
    }

    public void deleteBtn(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("informacion");
        alert.setHeaderText("¿Seguro que decea eliminar el registro de la ecuación?");
        alert.setContentText("Esta acción no podrá ser revertida y se perdera la información, por favor precione confirma si deseas continuar.");
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
        if (result == ButtonType.OK){
        int index = formulaTable.getSelectionModel().getSelectedIndex();
        DamSystem.getInstance().deleteFormula(index);
        update();
        deleteBtn.setDisable(true);
        modifyBtn.setDisable(true);
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("información");
        alert.setHeaderText("Aviso");
        alert.setContentText("La fórmula ha sido eliminada exitosamente");
        alert.showAndWait();
        }
    }

    public void selectVariable(MouseEvent e){
        Variable aux = variableTable.getSelectionModel().getSelectedItem();
        if(aux != null){
            activateFields();
            variableNameText.setText("");
            descriptionText.setText("");
            descriptionText.setText(aux.getDescription());
            variableNameText.setText(aux.getName());
            downLimitSpinner.setDisable(false);
            upLimitSpinner.setDisable(false);
            downLimitSpinner.getValueFactory().setValue(aux.getDownLimit());
            upLimitSpinner.getValueFactory().setValue(aux.getUpLimit());
            checkFilds();
        }
    }

    public void clicTable(){
        if(formulaTable.getSelectionModel().getSelectedItem() != null){
            deleteBtn.setDisable(false);
            modifyBtn.setDisable(false);
        }else {
            deleteBtn.setDisable(true);
            modifyBtn.setDisable(true);
        }
    }

}
