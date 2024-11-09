package logic;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.fasterxml.jackson.annotation.JacksonInject.Value;

import dto.ValueDTO;
import exception.ActionNotPermitted;
import exception.ErrorFieldException;
import jakarta.xml.bind.JAXBException;
import service.ModelLoader;

public class DamSystem {

    private static DamSystem instance;
    private ArrayList<Formula> formList;
    private ArrayList<Formula> formListEng;
    private ArrayList<Result> resultsList;
    private ArrayList<Model> modelsList;
    private ArrayList<Model> modelsListEng;
    private File formulasFile;
    private File formulasFileEng;
    private File variableFile;
    private File resultsFile;

    private DamSystem() {
        this.formList = new ArrayList<Formula>();
        this.formListEng = new ArrayList<Formula>();
        resultsList = new ArrayList<Result>();
        modelsList = ModelLoader.loadModels();
        resultsFile = new File("./resultsFile.dvs");
        formulasFile = new File("./formulasFile.csv");
        formulasFileEng = new File("./formulasFileEng.csv");
    }

    public static DamSystem getInstance(){
        if(instance == null)
            instance = new DamSystem();
        return instance;
    }

    public ArrayList<Formula> getFormList() {
        return formList;
    }

    //prueba
    public ArrayList<Formula> getFormListEng() {
        return formListEng;
    }

    public ArrayList<Model> getModelsList() {
        return modelsList;
    }

    public ArrayList<Model> getDesemModelsList() {
        ArrayList<Model> list = new ArrayList<Model>();
        for(Model l: modelsList){
            if(l.getPhenomenon().equalsIgnoreCase("desembalse"))
                list.add(l);
        }
        return list;
    }
    //prueba
    public ArrayList<Model> getModelsListEng() {
        return modelsListEng;
    }

    public ArrayList<Model> getDesemModelsListEng() {
        ArrayList<Model> listEng = new ArrayList<Model>();
        for(Model l: modelsList){
            if(l.getPhenomenon().equalsIgnoreCase("desembalse"))
                listEng.add(l);
        }
        return listEng;
    }


    public ArrayList<Model> getPreciModelsList() {
        ArrayList<Model> list = new ArrayList<Model>();
        for(Model l: modelsList){
            if(l.getPhenomenon().equalsIgnoreCase("presipitacion"))
                list.add(l);
        }
        return list;
    }

    public ArrayList<Result> getResultsList() {
        return resultsList;
    }

    public void addFormula(String name, String function, ArrayList<Variable> list){
        Formula formula = new Formula(name, function, list);
        formList.add(formula);
    }

    //pruebapara la lista de formulas en inlg'es
    public void addFormulaEng(String name, String function, ArrayList<Variable> listEng){
        Formula formula = new Formula(name, function,listEng);
        formListEng.add(formula);
    }

    public void modifyFormula(int pos, String name, String function, ArrayList<Variable> list){
        formList.get(pos).setName(name);
        formList.get(pos).setFunction(function);
        formList.get(pos).setVariables(list);
    }

    public void deleteFormula(int index){
        formList.remove(index);
    }

    //prueba
    public void modifyFormulaEng(int pos, String name, String function, ArrayList<Variable> listEng){
        formListEng.get(pos).setName(name);
        formListEng.get(pos).setFunction(function);
        formListEng.get(pos).setVariables(listEng);
    }

    public void deleteFormulaEng(int index){
        formListEng.remove(index);
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
            throw new ActionNotPermitted("La fórmula solicitada no se encuentra en el sistema");
        }else
            throw new ActionNotPermitted("Para calcular el Factor de Seguridad debe los valores de las variables los datos a las variables");
        return value;
    }

    public double securityFactor(Object o, ArrayList<ValueDTO> list) throws ActionNotPermitted, ParserConfigurationException, SAXException, JAXBException, IOException{
        if(!list.isEmpty()){
            if(o != null){
            double value = o instanceof Model ? ((Model) o).evaluate(list) : ((Formula) o).evaluate(list);
            return value;
            }else
                throw new ActionNotPermitted("Debe escoger algun método para realizar el FS");
        }else
            throw new ActionNotPermitted("Para predecir el Factor de Seguridad debe los valores de las variables los datos a las variables");
    }

    public LinkedList<Double> securityFactorList(String name, ArrayList<ValueDTO> list) throws ActionNotPermitted, ErrorFieldException{
        LinkedList<Double> values = new LinkedList<Double>();
        if(!list.isEmpty()){
        Formula auxF = findForm(name);
        boolean found = true;
        double aux = 0;
        int i;
        for (i = 0; i < list.size() && found; i++) {
            if(list.get(i).getName().equalsIgnoreCase("Tiempo")){
                found = false;
                aux = list.get(i).getValue();
            }
        }
        if(auxF != null){
        while(aux > 0){
            list.get(i).setValue(aux);
            values.add(auxF.evaluate(list));
            --aux;
        }
        }
        else
            throw new ActionNotPermitted("La fórmula solicitada no se encuentra en el sistema");
        }else
            throw new ActionNotPermitted("Para calcular el Factor de Seguridad debe los valores de las variables los datos a las variables");
        return values;
    }

    public LinkedList<Double> securityFactorList(Object o, ArrayList<ValueDTO> list) throws ActionNotPermitted, ErrorFieldException, ParserConfigurationException, SAXException, JAXBException, IOException{
        LinkedList<Double> values = new LinkedList<Double>();
        ArrayList<ValueDTO> auxl = list;
        if(!list.isEmpty()){
        boolean found = true;
        double aux = 0;
        double aux1 = 0;
        int pos = 0;
        for (int i = 0; i < list.size() && found; i++) {
            if(list.get(i).getName().equalsIgnoreCase("Tiempo")){
                found = false;
                aux = list.get(i).getValue();
                aux1 = list.get(i).getValue();
                pos = i;
            }
        }
        while(aux > -1){
            auxl.get(pos).setValue(aux);
            values.add(o instanceof Model ? ((Model) o).evaluate(auxl):((Formula) o).evaluate(auxl));
            --aux;
        }
        list.get(pos).setValue(aux1);
        }else
            throw new ActionNotPermitted("Para calcular el Factor de Seguridad debe los valores de las variables los datos a las variables");
        return values;
    }

    public LinkedList<Double> securityFactorAnalysisList(Object o, ArrayList<ValueDTO> list, double step, String var) throws ActionNotPermitted, ErrorFieldException, ParserConfigurationException, SAXException, JAXBException, IOException{
        LinkedList<Double> values = new LinkedList<Double>();
        if(!list.isEmpty()){
            ArrayList<ValueDTO> auxList = list;
        boolean found = true;
        double aux = 0;
        double aux1 = 0;
        int pos = 0;
        for (int i = 0; i < list.size() && found; i++) {
            if(list.get(i).getName().equalsIgnoreCase(var)){
                found = false;
                aux = list.get(i).getDownLimit();
                aux1 = list.get(i).getValue();
                pos = i;
            }
        }
        values.add(aux);
        while(aux <= auxList.get(pos).getUpLimit()){
            auxList.get(pos).setValue(aux);
            values.add(o instanceof Model ? ((Model) o).evaluate(auxList):((Formula) o).evaluate(auxList));
            aux += step;
        }
        list.get(pos).setValue(aux1);
        }else
            throw new ActionNotPermitted("Para calcular el Factor de Seguridad debe darle valor a las variables");
        return values;
    }

    public void saveResults() throws ActionNotPermitted, ErrorFieldException, ParserConfigurationException, SAXException, JAXBException, IOException{
        RandomAccessFile raf = new RandomAccessFile(resultsFile, "rw");
        StringBuilder string = new StringBuilder();
        raf.seek(raf.length());
       String space = ";";
      //  string.append(text);
      //  string.append(space);
       // LinkedList<Double> listValues = securityFactorList(o, list);
        for (Result v : resultsList) {
            string.append(v.getDamName());
            string.append(space);
            for (int i = 0; i < v.getFsValue().size(); i++) {
                string.append(v.getFsValue().get(i));
                string.append(space);
            }
        }
        string.append("\n");
        raf.writeUTF(string.toString());
        raf.close();
    }

    public void addResult(String name, LinkedList<Double> list){
        Result aux = new Result(name, 0, list);
        resultsList.add(aux);
    }

    public File getFormulasFile() {
      return formulasFile;
    }
    public File getFormulasFileEng() {
        return formulasFileEng;
    }

}
