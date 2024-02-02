package logic;

import java.util.ArrayList;
import java.util.HashMap;
import org.nfunk.jep.JEP;

public class Formula {

    private String name;
    private String function;
	private JEP parsed;
	private ArrayList<Variable> variables = new ArrayList<Variable>();
    
    public Formula(String name, String function, JEP parsed, ArrayList<Variable> variables) {
        this.name = name;
        this.function = function;
        this.parsed = parsed;
        this.variables = variables;
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

}
