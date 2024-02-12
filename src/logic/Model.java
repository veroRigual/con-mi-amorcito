package logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.dmg.pmml.Array;
import org.dmg.pmml.DataField;
import org.dmg.pmml.DataType;
import org.dmg.pmml.OutputField;
import org.dmg.pmml.PMML;
import org.jpmml.evaluator.Evaluator;
import org.jpmml.evaluator.LoadingModelEvaluatorBuilder;
import org.jpmml.evaluator.ModelEvaluator;
import org.jpmml.evaluator.ModelEvaluatorBuilder;
import org.jpmml.evaluator.ModelEvaluatorFactory;
import org.jpmml.model.PMMLUtil;
import org.xml.sax.SAXException;

import dto.ValueDTO;
import jakarta.xml.bind.JAXBException;

public class Model {

    private PMML model;
    private String name;
    private String phenomenon;


    public Model(File file, String name) {
        try (InputStream pmmlInputStream1 = new FileInputStream(file)) {
            // InputStream pmmlInputStream2 = new FileInputStream(file2);
            model = PMMLUtil.unmarshal(pmmlInputStream1);
        } catch (ParserConfigurationException | SAXException | JAXBException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        setName(name);
        setPhenomenon(phenomenon);
    }

    public PMML getModel() {
        return model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name.substring(1,name.length()-5);
    }

    public void setPhenomenon(String phenomenon){
        this.phenomenon = phenomenon = name.charAt(0) == 'D' ? "desembalse": "presipitacion";
    }

    public ArrayList<String> getVariables(){
        ArrayList<String> list = new ArrayList<String>();

        for(DataField m: model.getDataDictionary().getDataFields()){
            if(!m.getName().equalsIgnoreCase("FS"))
            list.add(m.getName());
            
        }
        
        return list;
    }

    public String getPhenomenon() {
        return phenomenon;
    }

    public double evaluate(List<ValueDTO> list){
        double value = 0;
        Evaluator evaluator = new ModelEvaluatorBuilder(model).build();
        Map<String, Number> arguments = new LinkedHashMap<>();
        for(ValueDTO l: list)
        arguments.put(l.getName(), l.getValue());
        // arguments.put(list.get(0).getName(), 9.0);
        // arguments.put(list.get(1).getName(), 9.0);
        // arguments.put(list.get(2).getName(), 9.0);
        // arguments.put(list.get(3).getName(), 9.0);
        // arguments.put(list.get(4).getName(), 9.0);
        // arguments.put(list.get(5).getName(), 9);
        Map<String, ?> results = evaluator.evaluate(arguments);
        value =  (Double) results.get("FS");
        return value;
    }

    public String toString(){
        return name;
    }
}
