package Properties;

 import java.io.IOException;
 import java.util.Properties;

public class Propiedades extends Properties {
    public Propiedades (String idioma) {
        if (idioma.equals("ESPANOL")) {//si es español procede a leer las propiedades del archivo Español.properties
            getProperties("Espanol.properties");
        } else if (idioma.equals("INGLES")) { //si es inglés procede a leer las propiedades del archivo English.properties
            getProperties("English.properties");
        } else{  //sino por default español
            getProperties("ESPANOL");
        }
    }

    //leer las propiedades
    private void  getProperties(String idioma){
        try {
            this.load(getClass().getResourceAsStream(idioma));
        } catch (IOException ex){

        }
    }
}
