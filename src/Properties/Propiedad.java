package Properties;

 import java.io.IOException;
 import java.util.List;
 import java.util.Properties;


 import javafx.scene.Node;
 import javafx.scene.control.Label;
 import java.util.ResourceBundle;

public class Propiedad extends Properties {

        public Propiedad (String idioma) {
          if (idioma.equals("ENGLISH")) { //si es inglés procede a leer las propiedades del archivo English.properties
            getProperties("English.properties");
        } else {
                getProperties("Espanol.properties");
            }
    }

    //leer las propiedades
    private void  getProperties(String idioma){
        try {
            this.load(getClass().getResourceAsStream(idioma));
        } catch (IOException ex){

        }
    }
//    public  static String getString(String key){
//        return getProperty(key);
//            }


}

