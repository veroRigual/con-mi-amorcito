package gui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.html.HTMLDocument.Iterator;

import Properties.Propiedad;
import dto.ValueDTO;
import exception.ErrorFieldException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
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

import static util.FilesManagement.SetearTextos;

public class ManagementPane {
    static ManagementPane helpSFP;

    public boolean value = true;
    private int pos;

    @FXML
    AnchorPane anchorSFP;

    @FXML
    TableView formulaTable;
    @FXML
    TableColumn Expresion;
    @FXML
    TableColumn Nombre;

    @FXML
    TableView<Variable> variableTable;
    @FXML
    TableColumn variableColumn;

    @FXML
    TextField funtionNameText;
    @FXML
    TextField functionLabel;
    @FXML
    TextField variableNameText;
    @FXML
    TextArea descriptionText;

    @FXML
    Button Aceptar;
    @FXML
    Button Cancelar;
    @FXML
    Button Eliminar;
    @FXML
    Button Modificar;
    @FXML
    Button Agregar;

    @FXML
    Spinner<Double> downLimitSpinner;
    @FXML
    Spinner<Double> upLimitSpinner;
    DoubleStringConverter doubleStringConverter = new DoubleStringConverter() {
        private final DecimalFormat df = new DecimalFormat("#,####");

        @Override
        public String toString(Double value) {
            // If the specified value is null, return a zero-length String
            if (value == null) {
                return "";
            }

            return df.format(value);
        }

        @Override
        public Double fromString(String value) {
            try {
                // If the specified value is null or zero-length, return null
                if (value == null)
                    return null;

                value = value.trim();

                if (value.length() < 1)
                    return null;

                // Validate all characters are digits
                for (char c : value.toCharArray()) {
                    if (!Character.isDigit(c) && c != ',') {
                        throw new ErrorFieldException("Valor insertado para la variable no permitido.");

                    }
                }

                // Validate there is only one '.'
                if (value.indexOf(',') != value.lastIndexOf(',')) {
                    throw new ErrorFieldException("Valor insertado para la variable no permitido.");

                }

                // Perform the requested parsing
                return df.parse(value).doubleValue();

            } catch (Exception ex) {
                return 0.0;
            }
        }
    };

    public static ManagementPane getInstance() {
        return helpSFP;
    }

    public void initialize() {
        helpSFP = this;
        loadTable();
        loadVariableTable();
        loadSpinner();
        upLimitSpinner.setEditable(true);
        downLimitSpinner.setEditable(true);
        FilesManagement.SetearTextos(this.anchorSFP.getChildren(), Menu.getIdioma_actual());
        if(Menu.getIdioma_actual().equalsIgnoreCase("ESPAÑOL")){
            ManagementPane.getInstance().Nombre.setText("Nombre");
            ManagementPane.getInstance().Expresion.setText("Expresión");
            ManagementPane.getInstance().variableTable.setPlaceholder(new Label("Tabla sin contenido"));
        }
        else {
            ManagementPane.getInstance().Nombre.setText("Name");
            ManagementPane.getInstance().Expresion.setText("Expression");
            ManagementPane.getInstance().variableTable.setPlaceholder(new Label("No content in table"));
            ManagementPane.getInstance().updateEng();}
    }


    public void exitBtn() {
        securityFactorPane.getInstance().getAnchorSFP().getChildren().remove(anchorSFP);
        securityFactorPane.getInstance().loadComboBoxes();
    }

    public void loadSpinner() {
        SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE, 0, 0.1);
        SpinnerValueFactory<Double> valueFactory1 = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE, 0, 0.1);
        //valueFactory.setConverter(doubleStringConverter);
        //valueFactory1.setConverter(doubleStringConverter);

        TextFormatter<Object> downTextFormatter = new TextFormatter<>(c -> {

            if (c.getText().matches("[^0-9.,]+") && !c.getText().isEmpty())
                return null;

            SpinnerValueFactory.DoubleSpinnerValueFactory factory = (SpinnerValueFactory.DoubleSpinnerValueFactory) downLimitSpinner
                    .getValueFactory();
            c.setText(c.getText().replace(",", "."));

            try {
                Double newVal = Double.parseDouble(c.getControlNewText());
                return (newVal >= factory.getMin() && factory.getMax() >= newVal) ? c : null;
            } catch (Exception ex) {
                c.setText("0.0");
                return c;
            }
        });

        TextFormatter<Object> upTextFormatter = new TextFormatter<>(c -> {

            if (c.getText().matches("[^0-9.,]+") && !c.getText().isEmpty())
                return null;
            c.setText(c.getText().replace(",", "."));
            SpinnerValueFactory.DoubleSpinnerValueFactory factory = (SpinnerValueFactory.DoubleSpinnerValueFactory) upLimitSpinner
                    .getValueFactory();
            try {
                Double newVal = Double.parseDouble(c.getControlNewText());
                return (newVal >= factory.getMin() && factory.getMax() >= newVal) ? c : null;
            } catch (Exception ex) {
                c.setText("0.0");
                return c;
            }
        });

        //upLimitSpinner.getEditor().setTextFormatter(downTextFormatter);
        //downLimitSpinner.getEditor().setTextFormatter(upTextFormatter);

        upLimitSpinner.setValueFactory(valueFactory);
        downLimitSpinner.setValueFactory(valueFactory1);

    }

    public void loadDownLimitSpinner() {
        double value = downLimitSpinner.getValue();
        double minValue = 0;
        double maxValue = upLimitSpinner.getValue();
        SpinnerValueFactory<Double> valueFactory1 = new SpinnerValueFactory.DoubleSpinnerValueFactory(minValue,
                maxValue, value, 0.1);
        //valueFactory1.setConverter(doubleStringConverter);
        downLimitSpinner.setValueFactory(valueFactory1);
    }

    public void loadUpLimitSpinner() {
        double value = upLimitSpinner.getValue();
        double minValue = downLimitSpinner.getValue();
        double maxValue = Double.MAX_VALUE;
        SpinnerValueFactory<Double> valueFactory1 = new SpinnerValueFactory.DoubleSpinnerValueFactory(minValue,
                maxValue, value, 0.1);
        // valueFactory1.setConverter(doubleStringConverter);
        upLimitSpinner.setValueFactory(valueFactory1);
    }

    public void loadTable() {
        Nombre.setCellValueFactory(new PropertyValueFactory<Formula, String>("name"));
        Expresion.setCellValueFactory(new PropertyValueFactory<Formula, String>("function"));
        if(Menu.getIdioma_actual() .equalsIgnoreCase("ESPAÑOL")){

        update();}
        else  { updateEng();
            }
    }

    public void update() {
        ArrayList<Formula> list = DamSystem.getInstance().getFormList();
        ObservableList<Formula> aux = FXCollections.observableArrayList(list);
        formulaTable.setItems(aux);
        formulaTable.refresh();
    }
//prueba

    public void updateEng() {
        ArrayList<Formula> listEng = DamSystem.getInstance().getFormListEng();
        ObservableList<Formula> aux = FXCollections.observableArrayList(listEng);
        formulaTable.setItems(aux);
        formulaTable.refresh();
    }

    public void loadVariableTable() {
        variableColumn.setCellValueFactory(new PropertyValueFactory<Variable, String>("nomenclature"));
       updateVariableTable();
    }
public void updateVariableTableModify(ArrayList<Variable> variables){
    ObservableList<Variable> list = FXCollections.observableList(variables);
    variableTable.setItems(list);
    if (list.isEmpty()) {
        variableNameText.setDisable(true);
        descriptionText.setDisable(true);
        downLimitSpinner.setDisable(true);
        upLimitSpinner.setDisable(true);
        variableNameText.setText("");
        descriptionText.setText("");
        variableTable.setDisable(true);
    } else {
        variableTable.setDisable(false);
    }
    //  checkFilds();
}

    public void updateVariableTable() {
        ObservableList<Variable> list;
        if (value)
        list = FXCollections.observableList(extractVariables(functionLabel.getText()));
        else {
            Formula formula = (Formula) formulaTable.getSelectionModel().getSelectedItem();

            if (formula == null) {
                return;
            }
            String variableName= variableNameText.getText();
            String description = descriptionText.getText();
            double downLimit=downLimitSpinner.getValue();
            double upLimit=upLimitSpinner.getValue();
            Double dwnlimit=(Double)downLimit;
            Double uplimit=(Double)upLimit;

            ArrayList<Variable>currentVariables=new ArrayList<>(formula.getVariables());
            ArrayList<Variable> newVariables=extractVariables(functionLabel.getText(),currentVariables,variableName,description,downLimit,upLimit,dwnlimit,uplimit);
            list=FXCollections.observableList(newVariables);
        }
        variableTable.setItems(list);

        if (list.isEmpty()) {
            variableNameText.setDisable(true);
            descriptionText.setDisable(true);
            downLimitSpinner.setDisable(true);
            upLimitSpinner.setDisable(true);
            variableNameText.setText("");
            descriptionText.setText("");
            variableTable.setDisable(true);
        } else {
            variableTable.setDisable(false);
        }
        //  checkFilds();
    }

    public static ArrayList<Variable> extractVariables(String equation,ArrayList<Variable>currentVariables,String variableName,String description,double downLimit,double upLimit,Double dwnlimit,Double uplimit) {
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
        for (String variableNamee : variables) {
            boolean found = false;
            for (Variable existingVar : currentVariables) {
                if (existingVar.getNomenclature().equals(variableNamee)) {
                    list.add(existingVar);
                    found = true;
                    break;
                }
            }
            if (!found) {
                try {
                    list.add(new Variable(variableName, variableNamee, description, downLimit, upLimit, dwnlimit, uplimit));
                } catch (ErrorFieldException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
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

        for (String l : variables)
            try {
                list.add(new Variable("", l, "", 0, 0,0.0,0.0));
            } catch (ErrorFieldException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        return list;
    }


    //prueba
    public static ArrayList<Variable> extractVariablesEng(String equation) {
        ArrayList<String> variables = new ArrayList<>();
        ArrayList<Variable> listEng = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\b([a-zA-Z_][a-zA-Z0-9_]*)\\b");
        Matcher matcher = pattern.matcher(equation);

        while (matcher.find()) {
            String variable = matcher.group(1);
            if (!variables.contains(variable)) {
                variables.add(variable);
            }
        }

        for (String l : variables)
            try {
                listEng.add(new Variable("", l, "", 0, 0,0.0,0.0));
            } catch (ErrorFieldException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        return listEng;
    }
    public void insertData() {
        int auxIndex = variableTable.getSelectionModel().getSelectedIndex();
        ((Variable) variableTable.getItems().get(auxIndex)).setDescription(descriptionText.getText());
        ((Variable) variableTable.getItems().get(auxIndex)).setName(variableNameText.getText());

        checkFilds();
    }

    public boolean insertDataSpinner() {
        int auxIndex = variableTable.getSelectionModel().getSelectedIndex();
        try {
            double min = downLimitSpinner.getValue();
            double max = upLimitSpinner.getValue();
            if (min <= max)
            {
                ((Variable) variableTable.getItems().get(auxIndex)).downLimit = min;
                ((Variable) variableTable.getItems().get(auxIndex)).upLimit = max;
                ((Variable) variableTable.getItems().get(auxIndex)).dwnlimit = min;
                ((Variable) variableTable.getItems().get(auxIndex)).uplimit = max;
                return true;
            }
            else
            {
                throw new ErrorFieldException("El valor del limite inferior debe ser menor que el valor del limite superior");
            }
        } catch (ErrorFieldException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("información");
            alert.setHeaderText("Valor insertado no válido");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        return false;
    }

    public void ModifyBtn() {
        Formula formula = (Formula) formulaTable.getSelectionModel().getSelectedItem();
        if (formula != null) {
           // List<Variable> currentVariables = new ArrayList<>(formula.getVariables());
            // if(value)
            value = false;
            funtionNameText.setText("");
            functionLabel.setText("");
            descriptionText.setText("");
            variableNameText.setText("");
            loadSpinner();

            funtionNameText.setDisable(false);
            functionLabel.setDisable(false);
            funtionNameText.setText(formula.getName());
            functionLabel.setText(formula.getFunction());
            pos = formulaTable.getSelectionModel().getSelectedIndex();
            variableTable.setItems(FXCollections.observableArrayList(formula.getVariables()));
           // variableTable.setItems(FXCollections.observableArrayList(currentVariables));
            variableTable.setDisable(false);
            Cancelar.setDisable(false);
        }
    }

    public void onKeyReleasedUp() {
        try {
            double maxValue = upLimitSpinner.getValue();
            double minValue = downLimitSpinner.getValue();
            if (maxValue < minValue) {
                SpinnerValueFactory<Double> valueFactory1 = new SpinnerValueFactory.DoubleSpinnerValueFactory(minValue,
                        minValue, minValue, 0.1);
                //valueFactory1.setConverter(doubleStringConverter);
                upLimitSpinner.setValueFactory(valueFactory1);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("información");
            alert.setHeaderText("Error");
            alert.setContentText("Solo puede insertar números");
            alert.showAndWait();
        }
    }

    public void onKeyReleasedDown() {
        try {
            double maxValue = upLimitSpinner.getValue();
            double minValue = downLimitSpinner.getValue();
            if (maxValue < minValue) {
                SpinnerValueFactory<Double> valueFactory1 = new SpinnerValueFactory.DoubleSpinnerValueFactory(maxValue,
                        maxValue, maxValue, 0.1);
                //valueFactory1.setConverter(doubleStringConverter);
                downLimitSpinner.setValueFactory(valueFactory1);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("información");
            alert.setHeaderText("Error");
            alert.setContentText("Solo puede insertar números");
            alert.showAndWait();
        }
    }

    public void InsertBtn() {
        //   if(!value)
        value = true;

        funtionNameText.setDisable(false);
        functionLabel.setDisable(false);
        // variableTable.setDisable(false);
        Cancelar.setDisable(false);
    }

    public void activateFields() {
        variableNameText.setDisable(false);
        descriptionText.setDisable(false);
    }

    public void checkFilds() {
        if (!funtionNameText.getText().equals("") &&
                !functionLabel.getText().equals("") && !variableNameText.getText().equals("")
                && !descriptionText.getText().equals(""))
            Aceptar.setDisable(false);
        else
            Aceptar.setDisable(true);
    }

    public boolean validateFunction(String function, Propiedad propiedad){
        boolean flag = true;
        if (function == null || function.trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR,propiedad.getProperty("error"),propiedad.getProperty("ex_header_msg"),propiedad.getProperty("functionEmpty"));
            flag= false;

        }
        String regex = "[0-9+\\-*().a-zA-Z_]+";
        if (!function.matches(regex)){
            showAlert(Alert.AlertType.ERROR,propiedad.getProperty("error"),propiedad.getProperty("invalid_characters"),propiedad.getProperty("ex_header_msg"));
            flag= false;
        }
        if (!areParenthesesBalanced(function)){
            showAlert(Alert.AlertType.ERROR,propiedad.getProperty("error"),propiedad.getProperty("unbalanced_parentheses"),propiedad.getProperty("ex_header_msg"));
            flag= false;}

        if (!validateOperators(function)){
            showAlert(Alert.AlertType.ERROR,propiedad.getProperty("error"),propiedad.getProperty("wrong_placed_operators"),propiedad.getProperty("ex_header_msg"));
            flag= false;}
         ArrayList<Variable> variables = extractVariables(function);
        if (!atLeastTwoVariables(function)){
            showAlert(Alert.AlertType.ERROR,propiedad.getProperty("error"),propiedad.getProperty("at_least_two_var"),propiedad.getProperty("ex_header_msg"));
            flag= false;}

        return flag;
    }
public boolean atLeastTwoVariables(String function){
    boolean flag =true;
    ArrayList<String> variables = new ArrayList<>();
    Pattern pattern = Pattern.compile("\\b([a-zA-Z_][a-zA-Z0-9_]*)\\b");
    Matcher matcher = pattern.matcher(function);

    while (matcher.find()) {
        String variable = matcher.group(1);
        if (!variables.contains(variable)) {
            variables.add(variable);
        }
    }
    if (variables.size()<2)
        flag = false;
    return flag;
}
    public boolean areParenthesesBalanced(String function){
        int balance = 0;
        boolean flag =true;
        for(char c : function.toCharArray()){
            if (c=='('){
                balance++;
            } else if (c==')'){
                balance--;
                if (balance <0){
                    return false;
                }
            }
        } return  balance ==0;
    }

    public  boolean validateOperators(String function){
        function= function.replaceAll("\\s+","");
        boolean flag= true;
        //no puede comenzar ni terminar con un operador
        if (function.startsWith("+")||function.startsWith("-")||function.startsWith("*")||function.startsWith("/")||function.endsWith("+")||function.endsWith("-")||function.endsWith("*")||function.endsWith("/")){
            flag=false;
        }
        //verificar que no haya operadores consecutivos
        String invalidPatterns = "\\+\\+|\\+\\-|\\+\\*|\\+/|\\-\\+|\\-\\-|\\-\\*|\\-/|\\*\\+|\\*\\-|\\*\\*|\\*/|/\\+|/\\-|/\\*|//";
        Pattern pattern = Pattern.compile(invalidPatterns);
        Matcher matcher = pattern.matcher(function);
        if (matcher.find()){
            flag=false;
        }
        return flag;
    }
 private Alert showAlertConfirmation(Alert.AlertType typeI,String type, String message, String header_msg){
     Alert alert = new Alert(typeI);
     alert.setTitle(type);
     alert.setHeaderText(header_msg);
     alert.setContentText(message);
return alert;
      }
    public void showAlert(Alert.AlertType typeI,String type, String message, String header_msg){
        Alert alert = new Alert(typeI);
        alert.setTitle(type);
        alert.setHeaderText(header_msg);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void AceptBtn() {
        if (value) {
            if(Menu.getIdioma_actual() .equalsIgnoreCase("ESPAÑOL")){
                Propiedad propiedad= new Propiedad("ESPAÑOL");
                if (!validateFunction(functionLabel.getText(), propiedad)){
                    return;
                }
                ArrayList<Variable> list = new ArrayList<Variable>(variableTable.getItems());

            DamSystem.getInstance().addFormula(funtionNameText.getText(), functionLabel.getText(), list);}

            else{
                Propiedad propiedad= new Propiedad("ENGLISH");
                if (!validateFunction(functionLabel.getText(), propiedad)){
                    return;
                }
                ArrayList<Variable> listEng = new ArrayList<Variable>(variableTable.getItems());
                 DamSystem.getInstance().addFormulaEng(funtionNameText.getText(), functionLabel.getText(), listEng);}

            if (insertDataSpinner())
            {
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
                Cancelar.setDisable(true);
                Cancelar.setDisable(true);
                variableTable.getItems().clear();
                variableTable.setDisable(true);
                Aceptar.setDisable(true);

                if(Menu.getIdioma_actual() .equalsIgnoreCase("ESPAÑOL")){
                    update();
                    FilesManagement.WriteFormulaToFile(DamSystem.getInstance().getFormulasFile());

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("información");
                    alert.setHeaderText("Aviso");
                    alert.setContentText("La fórmula se ha registrado exitosamente");
                    alert.showAndWait();}
                else{

                    updateEng();
                    FilesManagement.WriteFormulaToFileEng(DamSystem.getInstance().getFormulasFileEng());

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("information");
                    alert.setHeaderText("Warning");
                    alert.setContentText("The formula has been registered successfully");
                    alert.showAndWait();}


            }
            // update();
        } else {

            if(Menu.getIdioma_actual() .equalsIgnoreCase("ESPAÑOL")){
                Propiedad propiedad= new Propiedad("ESPAÑOL");



                if (!validateFunction(functionLabel.getText(), propiedad)){
                    return;
                }
                Formula formula = (Formula) formulaTable.getSelectionModel().getSelectedItem();

                if (formula == null) {
                    return;
                }
                String variableName= variableNameText.getText();
                String description = descriptionText.getText();
                double downLimit=downLimitSpinner.getValue();
                double upLimit=upLimitSpinner.getValue();
                Double dwnlimit=(Double)downLimit;
                Double uplimit=(Double)upLimit;

                ArrayList<Variable>currentVariables=new ArrayList<>(formula.getVariables());
                ArrayList<Variable> newVariables=extractVariables(functionLabel.getText(),currentVariables,variableName,description,downLimit,upLimit,dwnlimit,uplimit);
                //updateVariableTableModify(newVariables);
                DamSystem.getInstance().modifyFormula(pos, funtionNameText.getText(), functionLabel.getText(), newVariables);

                }
            else{
                Propiedad propiedad= new Propiedad("ENGLISH");
                if (!validateFunction(functionLabel.getText(), propiedad)){
                    return;
                }
                Formula formula = (Formula) formulaTable.getSelectionModel().getSelectedItem();

                if (formula == null) {
                    return;
                }
                String variableName= variableNameText.getText();
                String description = descriptionText.getText();
                double downLimit=downLimitSpinner.getValue();
                double upLimit=upLimitSpinner.getValue();
                Double dwnlimit=(Double)downLimit;
                Double uplimit=(Double)upLimit;

                ArrayList<Variable>currentVariables=new ArrayList<>(formula.getVariables());
                ArrayList<Variable> newVariables=extractVariables(functionLabel.getText(),currentVariables,variableName,description,downLimit,upLimit,dwnlimit,uplimit);
                DamSystem.getInstance().modifyFormulaEng(pos, funtionNameText.getText(), functionLabel.getText(), newVariables);}


            //update();
            if (insertDataSpinner())
            {
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
                Cancelar.setDisable(true);
                Cancelar.setDisable(true);
                variableTable.getItems().clear();
                variableTable.setDisable(true);
                Aceptar.setDisable(true);

                if(Menu.getIdioma_actual() .equalsIgnoreCase("ESPAÑOL")){
                    update();
                    FilesManagement.WriteFormulaToFile(DamSystem.getInstance().getFormulasFile());


                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("información");
                alert.setHeaderText("Aviso");
                alert.setContentText("La fórmula se ha modificado exitosamente");
                alert.showAndWait();     }

                else{
                    updateEng();
                    FilesManagement.WriteFormulaToFileEng(DamSystem.getInstance().getFormulasFileEng());

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("information");
                    alert.setHeaderText("Warning");
                    alert.setContentText("The formula has been modified successfully");
                    alert.showAndWait(); }

            }

        }
    }

    public void cancelBtn() {
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
        Cancelar.setDisable(true);
        Cancelar.setDisable(true);
    }

    public void deleteBtn() {
         Propiedad propiedad= new Propiedad(Menu.getIdioma_actual());
     Alert alert = showAlertConfirmation(Alert.AlertType.CONFIRMATION,propiedad.getProperty("information"),propiedad.getProperty("no_reverse_delete"),propiedad.getProperty("sure_to_delete"));

      ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
        if (result == ButtonType.OK) {
            int index = formulaTable.getSelectionModel().getSelectedIndex();
            if(Menu.getIdioma_actual() .equalsIgnoreCase("ESPAÑOL")){
                DamSystem.getInstance().deleteFormula(index);
                update();
                FilesManagement.WriteFormulaToFile(DamSystem.getInstance().getFormulasFile());}
            else{
                DamSystem.getInstance().deleteFormulaEng(index);
                updateEng();
                FilesManagement.WriteFormulaToFileEng(DamSystem.getInstance().getFormulasFileEng());}


            Eliminar.setDisable(true);
            Modificar.setDisable(true);
            showAlert(Alert.AlertType.INFORMATION,propiedad.getProperty("information"),propiedad.getProperty("formula_deleted"),propiedad.getProperty("warning"));
                    }

    }


    public void selectVariable(MouseEvent e) {
        Variable aux = variableTable.getSelectionModel().getSelectedItem();
        if (aux != null) {
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

    public void clicTable() {
        if (formulaTable.getSelectionModel().getSelectedItem() != null) {
            Eliminar.setDisable(false);
            Modificar.setDisable(false);
        } else {
            Eliminar.setDisable(true);
            Modificar.setDisable(true);
        }
    }

    public List<Node> getSonsManagementPane() {
        return anchorSFP.getChildren();
    }

   /* public Node getSonsManagementPaneL(){
        return anchorSFP.getChildren().get(2);
    }
System.out.println();*/
}
