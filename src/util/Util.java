package util;

import java.util.LinkedList;

public class Util {

    public static String SignificantNumbers(double numero) {
        // Verificar si el número es cero
        if (numero == 0.0) {
            return "0.0";
        }

        // Calcular el factor de escala para ajustar el número al formato deseado
        double factorEscala = Math.pow(10, 3 - Math.floor(Math.log10(Math.abs(numero))) - 1);

        // Aplicar el factor de escala y formatear el número
        double numeroFormateado = Math.round(numero * factorEscala) / factorEscala;

        // Convertir el número formateado a cadena y quitar los ceros no significativos
        String resultado = Double.toString(numeroFormateado).replaceAll("\\.E?0*$", "");

        return resultado;
    }

    public static double denormalize(double yNormalized, double yMin, double yMax) {
        return yNormalized * (yMax - yMin) + yMin;
    }

}
