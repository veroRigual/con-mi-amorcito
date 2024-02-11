package logic;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import dto.ValueDTO;
import exception.ActionNotPermitted;

public class DamSystem {

    private static DamSystem instance;
    private ArrayList<Formula> formList;
    private ArrayList<Result> resultsList;
    private File formulasFile;
    private File variableFile;

    private DamSystem() {
        this.formList = new ArrayList<Formula>();
        resultsList = new ArrayList<Result>();
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

    public void modifyFormula(int pos, String name, String function, ArrayList<Variable> list){
        formList.get(pos).setName(name);
        formList.get(pos).setFunction(function);
        formList.get(pos).setVariables(list);
    }

    public void deleteFormula(int index){
        formList.remove(index);
    }

    /**
     * Busca en la lista de formula la primera que coincida con el nombre que se le pasa por parámetro
     * @param name nombre la formula
     * @return objeto de tipo Formula de encontrase en el caso contrario null
     */
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

    /**
     * Calcula el valor del factor de seguridad para la formula seleccionada y el conjunto de valores que se le da a sus variables
     * @param name nombre de la formula
     * @param list lista que contine los valores de las variables que contiene la formula
     * @return el valor del factor de seguridad
     */
    public double securityFactor(String name, ArrayList<ValueDTO> list) throws ActionNotPermitted{
        double value = 0;
        if(!list.isEmpty()){
        Formula auxF = findForm(name);
        if(auxF != null)
            value = auxF.evaluate(list);
        else
            throw new ActionNotPermitted("La formula solicitada no se encuentra en el sistema");
        }else
            throw new ActionNotPermitted("Para calcular el Factor de Seguridad debe los valores de las variables los datos a las variables");
        return value;
    }
}
