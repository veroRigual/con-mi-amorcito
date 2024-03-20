package util;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
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
           
            ArrayList<Variable> variables = new ArrayList<Variable> ();
            try {
                
                Scanner scanner = new Scanner(formulasFile);
                scanner.nextLine(); // Saltar la primera línea que contiene los encabezados
                Formula f = null;
                while (scanner.hasNextLine()) {
                    String linea = scanner.nextLine();
                    String[] valores = linea.split(";"); /*Separar los valores de la línea utilizando el separador ";"*/	
                    for(int i=2;i<valores.length;i++) {
                     String name = valores[i].trim();
                     String nomenclature = valores[i+=1].trim();
                     String description = valores[i+=1].trim();
                     double downLimit = Double.parseDouble(valores[i+=1]);                    
                     double upLimit = Double.parseDouble(valores[i+=1]);            
                     
                      /*Create a new Variable object and add it to the Formula*/
                      Variable variable = new Variable(name,nomenclature,description,downLimit,upLimit);
                      variables.add(variable);
                                        
                                                }
                                                 f = new Formula(valores [0], valores[1], variables);
                                                 formList.add(f);
                                                
                                            }scanner.close();
                                        }catch (Exception e) {
                                                System.out.println("No se pudo cargar el archivo");
                                            }
                                           
                                        }
                                    }