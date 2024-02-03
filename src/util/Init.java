package util;

import java.util.ArrayList;

import dto.ValueDTO;
import logic.Variable;
import logic.*;

/**
 * Init
 */
public class Init {

    public static void init(){

        Variable v1 = new Variable("variable 1", "a", "prueba1");
        Variable v2 = new Variable("variable 2", "b", "prueba2");
        ArrayList<Variable> list = new ArrayList<Variable>();
        list.add(v2);
        list.add(v1);
        DamSystem.getInstance().addFormula("prueba1", "a+b", list);

        Variable v11 = new Variable("variable 11", "a", "prueba1");
        Variable v12 = new Variable("variable 21", "b", "prueba2");
        Variable v13 = new Variable("variable 31", "c", "prueba3");
        Variable v14 = new Variable("variable 41", "d", "prueba4");
        ArrayList<Variable> list3 = new ArrayList<Variable>();
        list3.add(v12);
        list3.add(v11);
        list3.add(v13);
        list3.add(v14);
        DamSystem.getInstance().addFormula("prueba2", "a+b-c^d", list3);

        // Formula f = new Formula();
        // ValueDTO va1 = new ValueDTO(4, "a", "p");
        // ValueDTO va2 = new ValueDTO(2, "b", "p");
        // ArrayList<ValueDTO> listV = new ArrayList<ValueDTO>();
        // listV.add(va1);
        // listV.add(va2);
        // System.out.println(DamSystem.getInstance().securityFactor("prueba2", listV));
    }
}