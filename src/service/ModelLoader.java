package service;

import org.dmg.pmml.PMML;
import org.jpmml.model.PMMLUtil;
import org.xml.sax.SAXException;

import jakarta.xml.bind.JAXBException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

public class ModelLoader {

    private static ModelLoader model;
    private PMML model1;
    private PMML model2;

    private ModelLoader() {

        try {
            File file1 = new File(new File("").getAbsolutePath()+ "/src/models/Modelo_simplificado_RNA_tiempo_%1.model");
            File file2 = new File(new File("").getAbsolutePath()+ "/src/models/Modelo_general_RNA_tiempo_%2.model");
            InputStream pmmlInputStream1 = new FileInputStream(file1);
            InputStream pmmlInputStream2 = new FileInputStream(file2);
            model1 = PMMLUtil.unmarshal(pmmlInputStream1);
            model2 = PMMLUtil.unmarshal(pmmlInputStream2);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
        }catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static ModelLoader getModel(){
        if(model == null)
            model = new ModelLoader();
        return model;
    }

    public PMML getPmmlModel1() {
        return model1;
    }

    public PMML getPmmlModel2() {
        return model2;
    }

}
