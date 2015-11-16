import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//DNI 15418068 GONZALEZ COBO, ANGEL

public class DiccA {

    private int nlenguas;
    private char[] lenguas;
    private Palabra[] dicc;
    private Palabra[] diccord;

    public DiccA() {
        nlenguas = -1;
        lenguas = null;
        dicc = null;
        diccord = null;
    }

    /**
     * Lee el diccionario e incorporando informacion.
     * 
     * @param f
     *            Nombre del fichero
     */
    public void leeDiccA(String f) {
        int numeroDeLineas = numeroDeLineas(f);

        if (numeroDeLineas % 10 != 0)
            numeroDeLineas += 10 - (numeroDeLineas % 10);

        dicc = new Palabra[numeroDeLineas];
        diccord = new Palabra[numeroDeLineas];

        try {
            File file = new File(f);
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));

                String linea = br.readLine();
                while (linea != null) {
                    if (nlenguas == -1) {
                        // Primera linea con la cantidad de idiomas
                        try {
                            // Eliminacion de los espacios
                            nlenguas = Integer.parseInt(linea.replace(" ", ""));
                        } catch (NumberFormatException ex) {
                            System.out.println("ERROR ==> NUMERO DE LENGUAS VACIO.");
                        }
                    } else {
                        if (lenguas == null) {
                            String[] numeroDeIdiomas = linea.split(" ");
                            lenguas = new char[numeroDeIdiomas.length];
                            for (int i = 0; i < lenguas.length; i++) {
                                lenguas[i] = numeroDeIdiomas[i].charAt(0);
                            }
                        } else {
                            String[] palabros = separaLinea(linea);
                            String ingles = palabros[0];

                            if (!ingles.equalsIgnoreCase("")) {
                                Palabra p = new Palabra(ingles, nlenguas);
                                for (int i = 0; i < lenguas.length; i++) {
                                    String acho = "";
                                    try {
                                        acho = palabros[i + 1];
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                        acho = "";
                                    }
                                    char pijo = lenguas[i];
                                    p.agregaAcepcion(acho, pijo);
                                }
                                insertaPalabra(p);
                            }
                        }
                    }
                    linea = br.readLine();
                }
                br.close();
                actualizaDiccord(diccord);
            }
        } catch (IOException e) {
            System.out.println("ERROR ==> IOEXCEPTION");
            // e.printStackTrace();
        }
    }

    /**
     * Inserta una palabra en la ultima posicion en dicc y ordenado en diccord.
     * 
     * @param p
     *            Palabra a agregar
     * @return true en caso de agregar palabra/acepcion
     */
    public boolean insertaPalabra(Palabra p) {
        int posicion = posicionDePalabra(p.getOrigen());
        boolean insertado = false;
        boolean insertOrd = false;

        if (posicion != -1) {
            dicc[posicion] = p;
            insertado = true;
            insertOrd = true;
        } else {
            if (!hayHueco(dicc))
                aumentaDiccionario(dicc);
            for (int i = 0; i < dicc.length; i++) {
                if (dicc[i] == null && !insertado) {
                    dicc[i] = p;
                    insertado = true;
                }
            }
        }

        if (!hayHueco(diccord))
            aumentaDiccionario(diccord);
        for (int i = 0; i < diccord.length; i++) {
            if (diccord[i] == null && !insertOrd) {
                diccord[i] = p;
                insertOrd = true;
            }
        }

        actualizaDiccord(diccord);
        return insertado;
    }

    /**
     * TODO
     * 
     * @param diccionario
     * @return
     */
    private Palabra[] aumentaDiccionario(Palabra[] diccionario) {
        Palabra[] resultado = new Palabra[diccionario.length + 10];

        for (int i = 0; i < diccionario.length; i++)
            resultado[i] = diccionario[i];

        return resultado;
    }

    /**
     * TODO
     * 
     * @param diccionario
     * @return
     */
    private boolean hayHueco(Palabra[] diccionario) {
        boolean resultado = false;
        for (int i = 0; i < diccionario.length; i++) {
            if (diccionario[i] == null)
                resultado = true;
        }

        return resultado;
    }

    /**
     * Divide las distintas palabras que contiene la linea.
     * 
     * @param linea
     *            String con todas las palabras
     * @return String[] con las palabras
     */
    private String[] separaLinea(String linea) {
        String[] palabros = null;

        if (linea.contains(" * ")) {
            linea = linea.replace(" * ", "*");
            palabros = linea.split("[ ]*\\*[ ]*");
        }

        return palabros;
    }

    /**
     * Actualiza el diccionario ordenado. Metodo de burbuja.
     * 
     * @param diccord
     *            Diccionario a ordenar
     */
    private void actualizaDiccord(Palabra[] diccord) {
        Palabra aux;

        for (int i = 0; i < diccord.length; i++) {
            for (int j = 1; j < (diccord.length - i); j++) {
                if (diccord[j] != null) {
                    if (diccord[j - 1].getOrigen().compareToIgnoreCase(diccord[j].getOrigen()) > 0) {
                        aux = diccord[j - 1];
                        diccord[j - 1] = diccord[j];
                        diccord[j] = aux;
                    }
                }
            }
        }
    }

    /**
     * Cuenta el numero de lineas en el archivo. Abro archivo y cuento de manera
     * secuencial las lineas que tiene el archivo.
     * 
     * @param f
     *            String con el nombre del archivo
     * @return int con el numero de lineas
     */
    private int numeroDeLineas(String f) {
        int cantidad = 0;

        try {
            File file = new File(f);
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String linea = br.readLine();

                while (linea != null) {
                    cantidad++;
                    linea = br.readLine();
                }
                br.close();
            }
        } catch (IOException e) {
            System.out.println("ERROR ==> IOEXCEPTION");
        }

        return cantidad;
    }

    /**
     * Busca en el diccionario la palabra de manera secuencial.
     * 
     * @param s
     *            Palabra a buscar.
     * @return int con la posicion en el diccionario. -1 en caso que no lo
     *         encuentre.
     */
    private int posicionDePalabra(String s) {
        for (int i = 0; i < dicc.length; i++) {
            if (dicc[i] != null) {
                if (dicc[i].getOrigen() != null) {
                    if (dicc[i].getOrigen().equalsIgnoreCase(s))
                        return i;
                }
            }
        }
        return -1;
    }

    /**
     * 
     * @param s
     *            String con la palabra a buscar en el diccionario no ordenado.
     * @return Numero de comparaciones que hace el algoritmo.
     */
    public int busqueda(String s) {
        int comparaciones = 0;
        for (int i = 0; i < dicc.length; i++) {
            if (dicc[i] != null) {
                if (dicc[i].getOrigen() != null) {
                    if (dicc[i].getOrigen().equalsIgnoreCase(s))
                        return comparaciones;
                    else
                        comparaciones++;
                }
            }
        }
        return -1;
    }

    /**
     * Muestra el diccionario. Dependiendo del valor de "i" se mostrara de
     * diferentes maneras.
     * 
     * @param i
     *            Valor para mostrar el diccionario. Si es 0 muestra el
     *            diccionario original. Si es 1 muestra el diccionario ordenado.
     */
    public void muestraDiccA(int i) {
        Palabra[] aMostrar = null;
        switch (i) {
        case 0:
            aMostrar = dicc;
            break;
        case 1:
            aMostrar = diccord;
            break;
        }

        for (int x = 0; x < dicc.length; x++) {
            if (aMostrar[x] != null) {
                aMostrar[x].escribeInfo();
            }
        }

    }

    /**
     * Muestra las "j" primeras lineas el diccionario. Dependiendo del valor de
     * "i" se mostrara de diferentes maneras.
     * 
     * @param i
     *            Valor para mostrar el diccionario. Si es 0 muestra el
     *            diccionario original. Si es 1 muestra el diccionario ordenado.
     * @param j
     *            Numero de lineas a mostrar del diccionario
     * 
     */
    public void muestraDiccA(int i, int j) {
        Palabra[] aMostrar = null;

        switch (i) {
        case 0:
            aMostrar = dicc;
            break;
        case 1:
            aMostrar = diccord;
            break;
        }

        if (j > aMostrar.length)
            j = aMostrar.length;

        for (int x = 0; x < j; x++) {
            if (aMostrar[x] != null) {
                aMostrar[x].escribeInfo();
            }
        }
    }

    /**
     * TODO
     * 
     * @param s
     *            String con la palabra a borrar
     * @return true si realiza la accion de borrado
     */
    public boolean borraPalabra(String s) {
        boolean accion = false;

        for (int i = 0; i < dicc.length; i++) {
            if (!accion) {
                if (dicc[i] != null) {
                    if (dicc[i].getOrigen().equalsIgnoreCase(s)) {
                        accion = true;
                        for (int j = i; j < dicc.length - 1; j++) {
                            dicc[j] = dicc[j + 1];
                        }
                        dicc[dicc.length - 1] = null;
                    }
                }
            }
        }

        for (int i = 0; i < diccord.length; i++) {
            if (!accion) {
                if (diccord[i] != null) {
                    if (diccord[i].getOrigen().equalsIgnoreCase(s)) {
                        accion = true;
                        for (int j = i; j < diccord.length - 1; j++) {
                            diccord[j] = diccord[j + 1];
                        }
                        diccord[diccord.length - 1] = null;
                    }
                }
            }
        }
        return accion;
    }

    /**
     * 
     * @param i
     *            Valor para mostrar el diccionario. Si es 0 muestra el
     *            diccionario original. Si es 1 muestra el diccionario ordenado.
     * @param j
     *            Numero de lineas a mostrar del diccionario
     * @param l
     *            Lengua a buscar en el diccionario
     */
    public void muestraDiccA(int i, int j, char l) {
        Palabra[] aMostrar = null;

        switch (i) {
        case 0:
            aMostrar = dicc;
            break;
        case 1:
            aMostrar = diccord;
            break;
        }

        if (j > aMostrar.length)
            j = aMostrar.length;

        for (int x = 0; x < aMostrar.length; x++) {
            if (aMostrar[x] != null) {
                if (aMostrar[x].getTraducciones(l) != null) {
                    System.out.println(aMostrar[x].getOrigen() + ":" + aMostrar[x].getTraducciones(l));
                } else {
                    System.out.println(aMostrar[x].getOrigen() + ":");
                }
            }
        }
    }

    public int busquedaOptima(String s) {
        // TODO

        int pos = -1, pi, pf, ultimo, m;
        boolean encontrado;
        // se realiza sobre el diccOrd
        // busqueda dicotomica sobre el vector ordenada
        ultimo = 0;
        while (ultimo < dicc.length && dicc[ultimo] != null) {
            ultimo++;
        }
        ultimo--; // la ultima posicion distinta de null.
        encontrado = false;
        pi = 0;
        pf = ultimo; // ultimo elemento que no sea null.
        while (!encontrado && pi <= pf) {
            m = (pi + pf) / 2;
            if (diccord[m].getOrigen().equalsIgnoreCase(s)) {
                encontrado = true;
            } else {
                if (s.compareToIgnoreCase(diccord[m].getOrigen()) > 0) {
                    pi = m + 1;
                } else {
                    pf = m - 1;
                }
            }
        }
        return pos;

    }

    // TODO
    public String traduce1(String s, char l) {
        String traduccion = null;
        boolean encontrado = false;

        for (int i = 0; i < dicc.length && encontrado == false && dicc[i] != null; i++) {
            if (dicc[i] != null && dicc[i].getOrigen().equalsIgnoreCase(s)) {
                encontrado = true;
                traduccion = dicc[i].getTraduccion(l);
            }
        }
        return traduccion;
    }

    // TODO
    public String traduce2(String s, char l) {
        String traducciones = null;
        int i = 0;
        while (i < dicc.length && dicc[i] != null && dicc[i].getOrigen().equalsIgnoreCase(s) == false) {
            i++;
        }
        // si lo he encontrado en la posicion i.
        if (i < dicc.length && dicc[i] != null) {
            traducciones = dicc[i].getTraducciones(l);
        }
        return traducciones;
    }

}
