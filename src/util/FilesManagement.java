package util;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

import Properties.Propiedad;
import com.sun.javafx.scene.control.skin.TableColumnHeader;
import gui.Menu;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import logic.DamSystem;
import logic.Formula;
import logic.Variable;

public class FilesManagement {

    public static void WriteFormulaToFile (File formulasFile){
String space = ";";
ArrayList<Formula> formulas= DamSystem.getInstance().getFormList();
try {
    FileWriter fileWriter= new FileWriter(formulasFile);
    StringBuilder line = new StringBuilder();
    line.append("Name");
    line.append(space);
    line.append("Equation");
    line.append(space);
    line.append("Variables");
    line.append("\n");

    for(int i=0;i< formulas.size();i++){
        Formula f= formulas.get(i);
        line.append(f.getName());
            line.append(space);
            line.append(f.getFunction());
            line.append(space);
                        for (Variable variable : formulas.get(i).getVariables()) {
                line.append(variable.getName());
                line.append(space);
                line.append(variable.getNomenclature());
                line.append(space);
                line.append(variable.getDescription());
                line.append(space);
                line.append(variable.getDownLimit());
                 line.append(space);
                 line.append(variable.getUpLimit());
                 line.append(space);           
                 fileWriter.write(line.toString());
                line.delete(0, line.length());
            }
            line.append("\n");
    }
    fileWriter.close();

} catch (Exception e) {
  System.out.println("No se pudo guardar el fichero");
  System.out.println(e.getMessage());
}

    }

    //probar cargar y usar el fichero con fórmulas en inglés

   public static void WriteFormulaToFileEng (File formulasFileEng){
        String space = ";";
        ArrayList<Formula> formulas= DamSystem.getInstance().getFormListEng();
        try {
            FileWriter fileWriter= new FileWriter(formulasFileEng);
            StringBuilder line = new StringBuilder();
            line.append("Name");
            line.append(space);
            line.append("Equation");
            line.append(space);
            line.append("Variables");
            line.append("\n");

            for(int i=0;i< formulas.size();i++){
                Formula f= formulas.get(i);
                line.append(f.getName());
                line.append(space);
                line.append(f.getFunction());
                line.append(space);
                for (Variable variable : formulas.get(i).getVariables()) {
                    line.append(variable.getName());
                    line.append(space);
                    line.append(variable.getNomenclature());
                    line.append(space);
                    line.append(variable.getDescription());
                    line.append(space);
                    line.append(variable.getDownLimit());
                    line.append(space);
                    line.append(variable.getUpLimit());
                    line.append(space);
                    fileWriter.write(line.toString());
                    line.delete(0, line.length());
                }
                line.append("\n");
            }
            fileWriter.close();

        } catch (Exception e) {
            System.out.println("Couldn't save the file");
            System.out.println(e.getMessage());
        }

    }


    public static void WriteOneFormulaToFile (File formulasFile,String name,String function,ArrayList<Variable>variables){
        String space = ";";
        
        try {
            FileWriter fileWriter= new FileWriter(formulasFile);
            StringBuilder line = new StringBuilder();
            line.append("Name");
            line.append(space);
            line.append("Equation");
            line.append(space);
            line.append("Variables");
            line.append("\n");
        
           
                Formula f= new Formula(name, function, variables);
                line.append(f.getName());
                    line.append(space);
                    line.append(f.getFunction());
                    line.append(space);
                    for (Variable variable : f.getVariables()) {
                        line.append(variable.getName());
                        line.append(space);
                        line.append(variable.getNomenclature());
                        line.append(space);
                        line.append(variable.getDescription());
                        line.append(space);
                        line.append(variable.getDownLimit());
                         line.append(space);
                         line.append(variable.getUpLimit());
                         line.append(space);           
                         fileWriter.write(line.toString());
                        line.delete(0, line.length());
                    }
                    line.append("\n");
            
            fileWriter.close();
        
        } catch (Exception e) {
          System.out.println("No se pudo guardar el fichero");
        }
        
            }
   
    public static void CargandoFormulas(File formulasFile,ArrayList<Formula>formList) {

            try {

                Scanner scanner = new Scanner(formulasFile);
                scanner.nextLine(); // Saltar la primera línea que contiene los encabezados
                while (scanner.hasNextLine()) {
                    ArrayList<Variable> variables = new ArrayList<Variable> ();
                    String linea = scanner.nextLine();
                    String[] valores = linea.split(";"); /*Separar los valores de la línea utilizando el separador ";"*/
                    for(int i=2;i<valores.length;i++) {
                         String name = valores[i].trim();
                         String nomenclature = valores[i+=1].trim();
                         String description = valores[i+=1].trim();
                         double downLimit = Double.parseDouble(valores[i+=1]);
                         double upLimit = Double.parseDouble(valores[i+=1]);
                        Double dwnlimit= downLimit;
                        Double uplimit=upLimit;

                          /*Create a new Variable object and add it to the Formula*/
                          Variable variable = new Variable(name,nomenclature,description,downLimit,upLimit,dwnlimit,uplimit);
                          variables.add(variable);
                      }
                      Formula f = new Formula(valores [0], valores[1], variables);
                      formList.add(f);

                }scanner.close();

            }catch (Exception e) {
                 System.out.println("No se pudo cargar el archivo");
                 System.out.println(e.getMessage());
                 System.out.println(e.getCause());
            }
    }

    public static void CargandoFormulasEng(File formulasFileEng,ArrayList<Formula>formListEng) {

        try {

            Scanner scanner = new Scanner(formulasFileEng);
            scanner.nextLine(); // Saltar la primera línea que contiene los encabezados
            while (scanner.hasNextLine()) {
                ArrayList<Variable> variables = new ArrayList<Variable> ();
                String linea = scanner.nextLine();
                String[] valores = linea.split(";"); /*Separar los valores de la línea utilizando el separador ";"*/
                for(int i=2;i<valores.length;i++) {
                    String name = valores[i].trim();
                    String nomenclature = valores[i+=1].trim();
                    String description = valores[i+=1].trim();
                    double downLimit = Double.parseDouble(valores[i+=1]);
                    double upLimit = Double.parseDouble(valores[i+=1]);
                    Double dwnlimit= downLimit;
                    Double uplimit=upLimit;


                    /*Create a new Variable object and add it to the Formula*/
                    Variable variable = new Variable(name,nomenclature,description,downLimit,upLimit,dwnlimit,uplimit);
                    variables.add(variable);
                }
                Formula f = new Formula(valores [0], valores[1], variables);
                formListEng.add(f);

            }scanner.close();

        }catch (Exception e) {
            System.out.println("Couldn't load the file");
        }
    }
    TableColumn Nombre;
    public static void SetearTextos(List<Node> list, String idioma){
        Propiedad p = new Propiedad(idioma);
        Button button = new Button("");
       /* Nombre.setCellValueFactory(new PropertyValueFactory<Formula, String>("name"));
        if(idioma .equalsIgnoreCase("ESPAÑOL"))
            Nombre.setText("Nombre");
        else
            Nombre.setText("Name");
       TableColumn<?,?> tableColumn =new TableColumn<?,?>() ;*/

        for (Node l:list) {
            if(l instanceof Label )
            ((Label)l).setText(p.getProperty(l.getId()));
            if (l instanceof Button)
                ((Button)l).setText(p.getProperty(l.getId()));
            if(l instanceof Text )
                ((Text)l).setText(p.getProperty(l.getId()));


            /*if(l instanceof TableColumn){
                TableColumn<?,?> tableColumn =(TableColumn<?,?> ) l;
                ((TableColumn)l).setText(p.getProperty(l.getId()));

        } */}

    }

    public static void SetearTextosIndependiente(Label l,String idioma){
        Propiedad p = new Propiedad(idioma);
        if(l instanceof Label )
            ((Label)l).setText(p.getProperty(l.getId()));
       /* if (l instanceof Button)
            ((Button)l).setText(p.getProperty(l.getId()));*/

    }
}