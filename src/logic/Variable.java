package logic;

import exception.ErrorFieldException;

public class Variable {

    private String name;
    private String nomenclature;
    private String description;
    public double downLimit =0;
    public double upLimit =0;
    public Double dwnlimit =0.0;
    public Double uplimit=0.0;

    public Variable(String name, String nomenclature, String description,double downLimit, double upLimit,Double dwnlimit,Double uplimit) throws ErrorFieldException {
        this.name = name;
        this.nomenclature = nomenclature;
        this.description = description;
        setUpLimit(upLimit);
        setDownLimit(downLimit);
        setDwnlimit(dwnlimit);
        setuplimit(uplimit);
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
        if(downLimit <= upLimit)
        this.downLimit = downLimit;
        else
         throw new ErrorFieldException("El valor del limite inferior debe ser menor que el valor del limite superior");
    }
    public void setDwnlimit(Double dwnlimit) throws ErrorFieldException {
        if(dwnlimit <= upLimit)
            this.dwnlimit = dwnlimit;
        else
            throw new ErrorFieldException("El valor del limite inferior debe ser menor que el valor del limite superior");
    }
    public double getUpLimit() {
        return upLimit;

    }
    public void setUpLimit(double upLimit) throws ErrorFieldException {
         if(upLimit >= downLimit)
        this.upLimit = upLimit;
        else
        throw new ErrorFieldException("El valor del limite superior debe ser mayor al valor del limite inferior");
    }

    public Double getDwnLimit() {
        return  dwnlimit;
    }
    public Double getupLimit() {
        return uplimit;
    }

    public void setuplimit(Double uplimit) throws ErrorFieldException {
        if(uplimit >= dwnlimit)
            this.uplimit = uplimit;
        else
            throw new ErrorFieldException("El valor del limite superior debe ser mayor al valor del limite inferior");
    }
}
