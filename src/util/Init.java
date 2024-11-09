package util;

import java.util.ArrayList;

import exception.ErrorFieldException;
import logic.Variable;
import logic.*;

/**
 * Init
 */
public class Init {

    public static void init() throws ErrorFieldException{

        //Variable v1 = new Variable("altura de terraplén", "H", "prueba1",0,100);
        Variable v11 = new Variable("Altura del terraplén", "H", "Altura desde el centro de la corona hasta el límite inferior del terraplén, medida en metros.",15,40,15.0,40.0);
        //Variable v2 = new Variable("peso", "B", "prueba2",0,100);
        Variable v12 = new Variable("Peso específico húmedo del suelo", "B", "Obtenido a partir del ensayo Proctor Estándar o de las relaciones volumétricas y gravimétricas de los suelos, es el peso de suelo por volumen unitario.",16.6,19.4,16.6,19.4);
        //Variable v3 = new Variable("cohesión", "C", "prueba2",0,100);
            Variable v13 = new Variable("Cohesión", "C", "Es la propiedad básica de los suelos finos que ofrece resistencia a los cambios de forma, típica de suelos finos. Es uno de los parámetros que determina la resistencia al corte de los suelos",13.8,67.5,13.8,67.5);
        //Variable v4 = new Variable("ángulo", "L", "prueba2",0,100);
        Variable v14 = new Variable("Ángulo de fricción interna del suelo", "L", "Es la propiedad del suelo que relaciona el máximo ángulo posible para la pendiente de un conjunto de suelo, típico de suelos granulares. Es uno de los parámetros que determina la resistencia al corte de los suelos.",12.6,29.5,12.6,29.5);
        
        //Variable v5 = new Variable("permeabilidad", "k", "prueba2",0,100);
        // Variable v6 = new Variable("contenido volumétrico", "V", "prueba2",0,100);
        // Variable v7 = new Variable("compresibilidad", "m", "prueba2",0,100);
        // Variable v8 = new Variable("velocidad", "V", "prueba2",0,100);
        Variable v18 = new Variable("Velocidad de desembalse", "V", "Velocidad a la que desciende el agua contenida en la presa, tanto por el posible efecto de la sequía como por el vertimiento en las obras de toma.",0.1,0.3,0.1,0.3);
        // Variable v9 = new Variable("tiempo", "t", "prueba2",0,100);
        Variable v19 = new Variable("Tiempo", "t", "",0,100,0.0,100.0);
        // ArrayList<Variable> list = new ArrayList<Variable>();
        ArrayList<Variable> list1 = new ArrayList<Variable>();
        // list.add(v1);
        // list.add(v2);
        // list.add(v3);
        // list.add(v4);
        // list.add(v5);
        // list.add(v6);
        // list.add(v7);
        // list.add(v8);
        // list.add(v9);
        list1.add(v19);
        list1.add(v18);
        list1.add(v14);
        list1.add(v12);
        list1.add(v11);
        list1.add(v13);
        // DamSystem.getInstance().addFormula("formula 1 general",
        //  "-0.1026*H+1.1722*B+0.0438*C+0.0801*L+230140.9759*k-8.9166*V-5243.141*m-1.4488*V-0.0298*t+0.0013*H^2-0.0334+B^2-0,0002*C^2-0.0007*L^2-2.49*10^10*k^2+8.2026*V^2+17474898.0412*m^2+2.1201*V^2+0.0002*t^2-5.3889",list);

        // Variable v11 = new Variable("altura", "X", "prueba1",19,25);
        // Variable v12 = new Variable("ancho de corona", "Y", "prueba2",23,99);
        // Variable v13 = new Variable("variable 31", "c", "prueba3",0,10);
        // Variable v14 = new Variable("tiempo", "T", "prueba4",0,100);
        // ArrayList<Variable> list3 = new ArrayList<Variable>();
        // list3.add(v12);
        // list3.add(v11);
        // list3.add(v13);
        // list3.add(v14);
        // DamSystem.getInstance().addFormula("formula 2 general",
        // "-0.0632*H+0.6522*B+0,0401*C+0,0753*L+233388,5945k*k-8,724*V-3838,685*m-3,8072*V-0,0091*t+0,0008*H^2-0,0187+B^2-0,0002*C^2-0.0004*L^2-2,52*10^10*k^2+8,3991*V^2+12084415,4354*m^2+3,8545*V^2+0,0000204*t^2-1,5104",
        //  list);

         DamSystem.getInstance().addFormula("fórmula en dias simplificada",
         "-0,0976*H+3,528*B+0,0402*C+0,0382*L-1,9453*V-0,03*t+0,0012*(H^2)-0,0984*(B^2)-0,0001*(C^2)+0,0003*(L^2)+3,1424(V^2)+0,0002*(t^2)-28,8314",
          list1);

          DamSystem.getInstance().addFormula("fórmula en porciento simplificada",
        "-0,0639*H+2,9574*B+0,0346*C+0,0358*L-3,8876*V-0,0091*t+0,0008*(H^2)-0,0827*(B^2)-0,000099*(C^2)+0,0005*(L^2)+4,121*(V^2)+0,0000204*(t^2)-24,115",
         list1);
    }
}