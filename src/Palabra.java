//DNI 15418068 GONZALEZ COBO, ANGEL

public class Palabra {

    private String origen; // Palabra de origen en ingles
    private Traduccion[] trad; // Lista de traducciones de los idiomas

    /**
     * @param p
     *            palabra origen.
     * @param nl
     *            numero total de lenguas a traducir.
     */
    public Palabra(String p, int nl) {
        origen = p;
        trad = (nl > 0) ? new Traduccion[nl] : new Traduccion[3];
    }

    /**
     * Almacena la traduccion en la primera posicion libre si no tiene ya
     * traduccion. Si tiene traduccion agrega la acepcion si no la posee.
     * 
     * @param t
     *            Traduccion que se almacena.
     * @param l
     * 
     * @return int con la posicion que ha interaccionado.
     */
    public int setTrad(Traduccion t, char l) {
        int pos = -1;
        // No existe la traduccion de la palabra
        if (getTraduccion(l) == null) {
            for (int i = 0; i < trad.length; i++) {
                if (Character.toUpperCase(trad[i].getIdioma()) == Character.toUpperCase(l)) {
                    trad[i].agregaAcepcion(t.getSignificados());
                    pos = i;
                }
            }
        } else {
            // Agrega la acepcion
            for (int i = 0; i < trad.length; i++) {
                if (pos == -1) {
                    if (Character.toUpperCase(trad[i].getIdioma()) == Character.toUpperCase(l)) {
                        trad[i].agregaAcepcion(t.getSignificados());
                        pos = i;
                    }
                }
            }
        }

        return pos;
    }

    /**
     * @return String con la cadena de origen.
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * @param l
     *            Lengua a buscar.
     * @return String con la primera aceptacion de las traducciones. Por defecto
     *         devuelve null.
     */
    public String getTraduccion(char l) {
        for (Traduccion t : trad) {
            if (Character.toUpperCase(t.getIdioma()) == Character.toUpperCase(l)) {
                return t.getSignificados().get(0);
            }
        }
        return null;
    }

    /**
     * @param l
     *            Lengua a buscar.
     * @return String con todas las aceptaciones separado por "/".
     */
    public String getTraducciones(char l) {
        for (Traduccion t : trad) {
            if (Character.toUpperCase(t.getIdioma()) == Character.toUpperCase(l)) {
                return t.getAcepciones();
            }
        }
        return null;
    }

    /**
     * @param s
     *            Acepcion a agregar.
     * @param l
     *            Lengua de la acepcion.
     * @return true si realiza la operacion.
     */
    public boolean agregaAcepcion(String s, char l) {
        Traduccion t = null;
        int posicion = 0;
        boolean accion = false;

        for (int i = 0; i < trad.length; i++) {
            if (trad[i] != null) {
                if (Character.toUpperCase(trad[i].getIdioma()) == Character.toUpperCase(l)) {
                    if (!trad[i].getAcepciones().contains(s)) {
                        accion = trad[i].agregaAcepcion(s);
                    }
                }
                posicion++;
            }
        }

        if (t == null)
            t = new Traduccion(l);

        if (!accion && posicion < trad.length) {
            accion = t.agregaAcepcion(s);
            trad[posicion] = t;
        }

        return accion;
    }

    /**
     * Muestra las traducciones asociadas a la cadena. Los diferentes idiomas
     * van separados con ":"
     */
    public void escribeInfo() {
        String resultado = origen + ":";

        for (Traduccion t : trad) {
            if (t != null)
                resultado += t.getAcepciones() + ":";
        }

        System.out.println(resultado.substring(0, resultado.length() - 1));
    }
    
    public String toString(){
        return origen;
    }
}
