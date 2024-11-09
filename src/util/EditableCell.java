package util;

import dto.ValueDTO;
import exception.ErrorFieldException;
import gui.ManagementPane;
import gui.securityFactorPane;
import javafx.scene.control.Alert;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

public  class EditableCell<Double> extends javafx.scene.control.cell.TextFieldTableCell<ValueDTO, java.lang.Double> {
    private final StringConverter<java.lang.Double> converter;
    private TextField textField;
    private boolean alertActive = false;


    public EditableCell(StringConverter<java.lang.Double> converter) {
        super(converter);
        this.converter = converter;
    }

    @Override
     public void startEdit() {
        super.startEdit();
        if (textField == null) {
            createTextField(); //crear el TextField manualmente
        }
        setGraphic(textField); //Mostrar el TextField en la celda
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        textField.setText(converter.toString(getItem())); //Mostrar el valor actual en el TexField
        textField.requestFocus();
        textField.selectAll();
    }

    @Override
            public void cancelEdit() {
        super.cancelEdit();
        setText(converter.toString(getItem()));
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    public void updateItem(java.lang.Double item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            if(isEditing()){
                textField.setText(converter.toString(item));
                setGraphic(textField);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
            else{
                setText(converter.toString(item));
                setGraphic(null);
                setContentDisplay(ContentDisplay.TEXT_ONLY);
            }
        }
    }
 private void createTextField(){
        textField= new TextField();

     textField.setOnAction(event -> commitOrShowError());
     textField.focusedProperty().addListener((obs,oldVal,newVal) -> {
         if (!newVal){
             commitOrShowError(); //Guardar cuando pierde el foco
         }
     });
 }

    private void commitOrShowError() {
       if (alertActive){
           return;
       }
        try{
            java.lang.Double newValue = converter.fromString(textField.getText());
            ValueDTO currentValue= getTableView().getItems().get(getIndex());


            //textField.setText(converter.toString(getItem()));
            if(newValue.doubleValue() <currentValue.getDwnLimit() || newValue>currentValue.getupLimit())
                throw new ErrorFieldException("Valor insertado para la variable no se encuentra entre el rango de valores permidos.\n" +":Rango: "+ String.valueOf(currentValue.getDownLimit())+" - "+String.valueOf(currentValue.getUpLimit()) );

           else{ commitEdit(converter.fromString(textField.getText())); //Guardar el nuevo valor si es v'alido
           setContentDisplay(ContentDisplay.TEXT_ONLY);}}
         catch (NumberFormatException | ErrorFieldException e){
            showErrorAlert(e);
            textField.requestFocus(); //Mantener el foco en el campo de texto si hay un error
            textField.selectAll(); //Seleccionar_todo el texto para facilitar la correcci'on
        }
    }
    public void showAlert(Alert.AlertType typeI,String type, String message, String header_msg){
        Alert alert = new Alert(typeI);
        alert.setTitle(type);
        alert.setHeaderText(header_msg);
        alert.setContentText(message);
        alert.showAndWait().ifPresent(response ->{
            alertActive = false;
            textField.requestFocus();
            textField.selectAll();
        });

    }
     private void showErrorAlert(Exception ex) {
         alertActive = true;
         showAlert(Alert.AlertType.ERROR,"información",ex.getMessage(),"Valor insertado no válido");
        /* Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setTitle("información");
         alert.setHeaderText("Valor insertado no válido");
         alert.setContentText(" Debe ingresar un valor numérico ");
         alert.showAndWait().ifPresent(response ->{
         alertActive = false;
         textField.requestFocus();
         textField.selectAll();
     });*/
     }
    public static StringConverter<java.lang.Double> createDoubleStringConverter(){
        return new StringConverter<java.lang.Double>() {

            @Override
            public String toString(java.lang.Double value) {
                return value==null ? "" : value.toString();
            }

            @Override
            public java.lang.Double fromString(String text) {
               try{
                return  java.lang.Double.parseDouble(text);}
               catch (NumberFormatException e){
                   throw new NumberFormatException("El valor no puede contener letras");
               }
            }

        };

    }
}
