package logic;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import dto.ValueDTO;

public class DamSystem {

    private static DamSystem instance;
    private ArrayList<Formula> formList = new ArrayList<Formula>();
    private File formulasFile;
    private File resultsFile;

    private DamSystem() {
        this.formList = new ArrayList<Formula>();
    }

    public static DamSystem getInstance(){
        if(instance == null)
            instance = new DamSystem();
        return instance;
    }

    public ArrayList<Formula> getFormList() {
        return formList;
    }

    public void addFormula(String name, String function, ArrayList<Variable> list){
        Formula formula = new Formula(name, function, list);
        formList.add(formula);
    }

    private Formula findForm(String name){
        Formula aux =  null;
        boolean stop = true;
        Iterator<Formula> it = formList.iterator();
        while (it.hasNext() && stop) {
            aux = it.next();
            if(aux.getName().equals(name)){
                stop = false;
            }
        }
        return stop ? null : aux;
    }

    public double securityFactor(String name, ArrayList<ValueDTO> list){
        double value = 0;
        Formula auxF = findForm(name);
        value = auxF.evaluate(list);
        return value;
    }
}
