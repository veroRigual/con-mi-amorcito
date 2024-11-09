package dto;

import exception.ErrorFieldException;

public class ValueDTO {

    private double value;
    private String nomenclature;
    private String name;
    private double downLimit;
    private double upLimit;
    private Double dwnlimit;
    private Double uplimit;


    public ValueDTO(String nomenclature, String name,double downLimit, double upLimit,Double dwnlimit,Double uplimit) throws ErrorFieldException {
        setDownLimit(downLimit);
        setUpLimit(upLimit);
        setNomenclature(nomenclature);
        setName(name);
        setValue(downLimit);
        setDwnlimit(dwnlimit);
        setuplimit(uplimit);
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
    /*public void setValue(double value) throws ErrorFieldException {
        if(value >= downLimit && value <= upLimit){
            try{
            this.value = value;
            } catch (NumberFormatException e) {
                throw new ErrorFieldException("Valor insertado para la variable un valor no permitido.");
            }
        }else{
            this.value = downLimit;
        throw new ErrorFieldException("Valor insertado para la variable no se encuentra entre el rango de valores permidos.\n"
        +":Rango: "+ String.valueOf(downLimit)+" - "+String.valueOf(upLimit) );
        }
    }*/

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
    public double getDownLimit() {
        return downLimit;
    }
    public void setDownLimit(double downLimit) {
        this.downLimit = downLimit;
    }

    public Double getDwnLimit() {
        return dwnlimit;
    }
    public void setDwnlimit(Double dwnlimit) {
        this.dwnlimit = dwnlimit;
    }
    public double getUpLimit() {
        return upLimit;
    }
    public void setUpLimit(double upLimit) {
        this.upLimit = upLimit;
    }

    public Double getupLimit() {
        return uplimit;
    }
    public void setuplimit(Double uplimit) {
        this.uplimit = uplimit;
    }
}
