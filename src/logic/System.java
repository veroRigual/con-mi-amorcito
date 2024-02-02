package logic;

import java.util.ArrayList;
import java.util.Iterator;

public class System {

    private static System instance;
    private ArrayList<Dam> damList = new ArrayList<Dam>();
    private ArrayList<Formula> formList = new ArrayList<Formula>();

    private System() {
        this.damList = new ArrayList<Dam>();
        this.formList = new ArrayList<Formula>();
    }

    public static System getInstance(){
        if(instance == null)
            instance = new System();
        return instance;
    }

    private Dam findDam(String name){
        Dam aux =  null;
        boolean stop = true;
        Iterator<Dam> it = damList.iterator();
        while (it.hasNext() && stop) {
            aux = it.next();
            if(aux.getName().equals(name)){
                stop = false;
            }
        }
        return stop ? null : aux;
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

    public double securityFactor(String name, String nameForm){
        double value = 0;
        Dam aux = findDam(name);
        Formula auxF = findForm(nameForm);
        return value;
    }


    public double securityFactor(String formName, int daysAmount, double percent, double high, double speed, double pound, double cohesion, double angulo, double permeabilidad, double volumen, double compresibilidad, double anchoCorona){
        double value =0;
        return value;
    }
}
