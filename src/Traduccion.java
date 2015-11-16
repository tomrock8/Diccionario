
//DNI 15418068 GONZALEZ COBO, ANGEL

import java.util.ArrayList;

public class Traduccion {

    private char idioma; // Idioma de la traduccion
    private ArrayList<String> significados; // Los diferentes sisnonimos para la
                                            // palabra en ese idioma

    /**
     * @param idioma
     *            Idioma de la traduccion;
     */
    public Traduccion(char idioma) {
        this.idioma = idioma;
        significados = new ArrayList<String>();
    }

    /**
     * @return char del idioma.
     */
    public char getIdioma() {
        return idioma;
    }

    /**
     * @return String con los significados.
     */
    public ArrayList<String> getSignificados() {
        return significados;
    }

    /**
     * Devuelve las acepciones de la palabra separado por "/".
     * 
     * @return String con las acepciones
     */
    public String getAcepciones() {
        String resultado = "";
        for (String s : significados) {
            resultado += s + "/";
        }
        return resultado.substring(0, resultado.length() - 1);
    }

    /**
     * Agrega a la lista las acepciones que no posea.
     * 
     * @param acepcion
     *            Lista de palabras a agregar
     * @return true en caso de accion
     */
    public boolean agregaAcepcion(ArrayList<String> acepcion) {
        boolean resultado = false;
        if (significados.size() < 5) {
            for (int i = 0; i < acepcion.size(); i++) {
                int pos = significados.indexOf(acepcion.get(i));
                if (pos == -1) {
                    significados.add(acepcion.get(i));
                    resultado = true;
                }
            }
        }
        return resultado;
    }

    /**
     * Agrega la acepcion a la lista si no la posee.
     * 
     * @param acepcion
     *            Palabra a agregar
     * @return true en caso de accion
     */
    public boolean agregaAcepcion(String acepcion) {
        boolean resultado = false;
        if (significados.size() < 5) {
            int pos = significados.indexOf(acepcion);
            if (pos == -1) {
                significados.add(acepcion);
                resultado = true;
            }
        }
        return resultado;
    }
}
