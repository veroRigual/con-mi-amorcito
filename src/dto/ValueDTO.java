package dto;

public class ValueDTO {

    private double value;
    private String nomenclature;
    private String name;

    public ValueDTO(double value, String nomenclature, String name) {
        this.value = value;
        this.nomenclature = nomenclature;
        this.name = name;
    }
    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        this.value = value;
    }
    public String getNomenclature() {
        return nomenclature;
    }
    public void setNomenclature(String nomenclature) {
        this.nomenclature = nomenclature;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
