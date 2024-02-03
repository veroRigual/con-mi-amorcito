package logic;

public class Variable<T> {

    private String name;
    private String nomenclature;
    private String description;

    public Variable(String name, String nomenclature, String description) {
        this.name = name;
        this.nomenclature = nomenclature;
        this.description = description;
    }
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
}
