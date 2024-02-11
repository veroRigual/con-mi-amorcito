package logic;

import exception.ErrorFieldException;

public class Variable {

    private String name;
    private String nomenclature;
    private String description;
    private double downLimit;
    private double upLimit;

    public Variable(String name, String nomenclature, String description,double downLimit, double upLimit) throws ErrorFieldException {
        this.name = name;
        this.nomenclature = nomenclature;
        this.description = description;
        setDownLimit(downLimit);
        setUpLimit(upLimit);
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
    public double getDownLimit() {
        return downLimit;
    }
    public void setDownLimit(double downLimit) throws ErrorFieldException {
       // if(downLimit < upLimit)
        this.downLimit = downLimit;
        // else
        // throw new ErrorFieldException("El valor del limite inferior debe ser menor que el valor del limite superior");
    }
    public double getUpLimit() {
        return upLimit;

    }
    public void setUpLimit(double upLimit) throws ErrorFieldException {
        // if(upLimit > downLimit)
        this.upLimit = upLimit;
        // else
        // throw new ErrorFieldException("El valor del limite superior debe ser mayor al valor del limite inferior");
    }
}
