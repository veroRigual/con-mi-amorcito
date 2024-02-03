package logic;

import java.util.ArrayList;
import java.util.HashMap;

import org.lsmp.djep.djep.DJep;
import org.nfunk.jep.JEP;

import dto.ValueDTO;

public class Formula {

    private String name;
    private String function;
	private JEP parsed;
	private ArrayList<Variable> variables = new ArrayList<Variable>();
    
    public Formula(String name, String function, ArrayList<Variable> variables) {
        this.name = name;
        this.function = function;
        this.variables = variables;
        parsed = new JEP();
        parsed.addStandardFunctions();
        parsed.addStandardConstants();
        parsed.addComplex();
        parsed.setAllowUndeclared(true);
        parsed.setAllowAssignment(true);
        parsed.setImplicitMul(true);

    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getFunction() {
        return function;
    }
    public void setFunction(String function) {
        this.function = function;
    }
    public JEP getParsed() {
        return parsed;
    }
    public void setParsed(JEP parsed) {
        this.parsed = parsed;
    }
    public ArrayList<Variable> getVariables() {
        return variables;
    }
    public void setVariables(ArrayList<Variable> variables) {
        this.variables = variables;
    }

    public double evaluate(ArrayList<ValueDTO> list){
        double value;
        String auxFuntion = function;
        for(ValueDTO v: list){
           auxFuntion = auxFuntion.replace(v.getNomenclature(), String.valueOf(v.getValue()));
        }
        auxFuntion = auxFuntion.replace(",", ".");
        parsed.parseExpression(auxFuntion);
        value = parsed.getValue();
        return value;
    }

    public String toString(){
        return name;
    }

}
