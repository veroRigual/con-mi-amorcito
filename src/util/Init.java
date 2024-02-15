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

        Variable v1 = new Variable("altura", "a", "prueba1",0,99);
        Variable v2 = new Variable("ancho de corona", "b", "prueba2",0,99);
        ArrayList<Variable> list = new ArrayList<Variable>();
        list.add(v2);
        list.add(v1);
        DamSystem.getInstance().addFormula("formula 1", "a+b", list);

        Variable v11 = new Variable("altura", "a", "prueba1",19,25);
        Variable v12 = new Variable("ancho de corona", "b", "prueba2",23,99);
        // Variable v13 = new Variable("variable 31", "c", "prueba3");
        Variable v14 = new Variable("tiempo", "c", "prueba4",0,100);
        ArrayList<Variable> list3 = new ArrayList<Variable>();
        list3.add(v12);
        list3.add(v11);
        // list3.add(v13);
        list3.add(v14);
        DamSystem.getInstance().addFormula("formula 2", "a+b-c", list3);

        // Formula f = new Formula();
        // ValueDTO va1 = new ValueDTO(4, "a", "p");
        // ValueDTO va2 = new ValueDTO(2, "b", "p");
        // ArrayList<ValueDTO> listV = new ArrayList<ValueDTO>();
        // listV.add(va1);
        // listV.add(va2);
        // System.out.println(DamSystem.getInstance().securityFactor("prueba2", listV));
    }
}