package util;

import dto.ValueDTO;
import gui.securityFactorPane;
import javafx.util.StringConverter;

public  class EditableCell<T> extends javafx.scene.control.cell.TextFieldTableCell<ValueDTO, T> {
    private final StringConverter<T> converter;

    public EditableCell(StringConverter<T> converter) {
        super(converter);
        this.converter = converter;
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
        } else {
            setText(converter.toString(item));
        }
    }
}
