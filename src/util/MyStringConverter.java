package util;

import javafx.util.StringConverter;

public class MyStringConverter<T> extends StringConverter<T> {
    private final Class<T> clazz;

    public MyStringConverter(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String toString(T object) {
        return object == null ? "" : object.toString();
    }

    @Override
    public T fromString(String string) {
        if (String.class.isAssignableFrom(clazz)) {
            return (T) string;
        } else if (Integer.class.isAssignableFrom(clazz)) {
            return (T) Integer.valueOf(string);
        } else if (Boolean.class.isAssignableFrom(clazz)) {
            return (T) Boolean.valueOf(string);
        } else {
            throw new IllegalArgumentException("Tipo no soportado");
        }
    }
}
