package service;

import logic.Model;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

public class ModelLoader {

    public static ArrayList<Model> loadModels(){
        LinkedList<Model> list = new LinkedList<Model>();

        File folder = new File(new File("").getAbsolutePath()+ "/src/models");
        File[] files = folder.listFiles();

        if (files != null) {
            for (File f : files) {
                if (f.isFile() && f.getName().toLowerCase().endsWith(".pmml")) {
                    Model aux = new Model(f,f.getName());
                    list.add(aux);
                }
            }
        }

        return list.isEmpty() ? new ArrayList<>(): new ArrayList<>(list);
    }

}
