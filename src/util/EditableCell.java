package util;

import dto.ValueDTO;
import gui.securityFactorPane;
import javafx.scene.control.Alert;
import javafx.util.StringConverter;

public  class EditableCell<Double> extends javafx.scene.control.cell.TextFieldTableCell<ValueDTO, Double> {
    private final StringConverter<Double> converter;

    public EditableCell(StringConverter<Double> converter) {
        super(converter);
        this.converter = converter;
    }

    @Override
    public void updateItem(Double item, boolean empty) {
        try{
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
        } else {
            setText(converter.toString(item));
        }
    } catch(Exception ne){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("información");
            alert.setHeaderText("Valor insertado no válido");
            alert.setContentText("Error: "+ne.getMessage());
            alert.showAndWait();
        }
    }
}
