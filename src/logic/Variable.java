package logic;

public class Variable<T> {
    private String name;
    private String nomenclature;
    private String description;
    private T value;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getNomenclature() {
        return nomenclature;
    }
    public void setNomenclature(String nomenclatura) {
        this.nomenclature = nomenclatura;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public T getValue() {
        return value;
    }
    public void setValue(T value) {
        this.value = value;
    }

}
