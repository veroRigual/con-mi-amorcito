package gui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.text.html.HTMLDocument.Iterator;

import dto.ValueDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

public class ManagementPane {
    ManagementPane helpSFP;

    public boolean value = true;

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

    public void initialize(){
        helpSFP = this;
        loadTable();
        loadVariableTable();
    }

    public void exitBtn(){
        securityFactorPane.getInstance().getAnchorSFP().getChildren().remove(anchorSFP);
        securityFactorPane.getInstance().loadComboBoxes();
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
        ObservableList<Variable> list = FXCollections.observableList(funtionCheck(functionLabel.getText()));
        variableTable.setItems(list);
      //  checkFilds();
    }

    public LinkedList<Variable> funtionCheck(String funtion){
        LinkedList<Variable> list = new LinkedList<Variable>();
        for(int i = 0; i < funtion.length(); i++){
            char aux = funtion.charAt(i);
            if(Character.isAlphabetic(aux))
                if(listCheck(aux))
                    list.add(new Variable("", aux+"", ""));
        }
        return list;
    }

    public boolean listCheck(char character){
        boolean value = true;
        if(!variableTable.getItems().isEmpty()){
        ListIterator<Variable> it = variableTable.getItems().listIterator();
        while (it.hasNext() && value) {
            String aux = it.next().getNomenclature();
            if(aux.equals(character+""))
                value = false;
        }
    }
        return value;
    }

    public void insertData(){
        int auxIndex = variableTable.getSelectionModel().getSelectedIndex();
        ((Variable)variableTable.getItems().get(auxIndex)).setDescription(descriptionText.getText());
        ((Variable)variableTable.getItems().get(auxIndex)).setName(variableNameText.getText());
        checkFilds();
    }

    public void ModifyBtn(){
        if(value)
            value = false;
    }

    public void InsertBtn(){
        if(!value)
            value = false;

        funtionNameText.setDisable(false);
        functionLabel.setDisable(false);
        variableTable.setDisable(false);
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
            update();

            funtionNameText.setText("");
            functionLabel.setText("");
            // variableNameText.setText("");
            
        }else{

        }
    }

    public void cancelBtn(){
        funtionNameText.setDisable(true);
        functionLabel.setDisable(true);
        variableNameText.setDisable(true);
        variableTable.setDisable(true);
        cancelBtn.setDisable(true);
    }

    public void deleteBtn(){
        int index = formulaTable.getSelectionModel().getSelectedIndex();
        DamSystem.getInstance().deleteFormula(index);
        update();
    }

    public void selectVariable(MouseEvent e){
        activateFields();
        variableNameText.setText("");
        descriptionText.setText("");
        Variable aux = variableTable.getSelectionModel().getSelectedItem();
        descriptionText.setText(aux.getDescription());
        variableNameText.setText(aux.getName());
        checkFilds();
    }
}
