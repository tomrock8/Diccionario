//DNI 15418068 GONZALEZ COBO, ANGEL

public class Bilingue {

    public static Palabra[] creaPalabras() {
        Palabra[] creadas = new Palabra[8];
        // P E F
        creadas[0] = new Palabra("matter", 3);
        creadas[0].agregaAcepcion("assunto", 'P');
        creadas[0].agregaAcepcion("materia", 'E');
        creadas[1] = new Palabra("subway", 3);
        creadas[1].agregaAcepcion("metrô", 'P');
        creadas[1].agregaAcepcion("metro", 'E');
        creadas[1].agregaAcepcion("métro", 'F');
        creadas[2] = new Palabra("if", 3);
        creadas[2].agregaAcepcion("se", 'P');
        creadas[2].agregaAcepcion("si", 'E');
        creadas[2].agregaAcepcion("si", 'F');
        creadas[3] = new Palabra("basket", 3);
        creadas[3].agregaAcepcion("cesto", 'P');
        creadas[4] = new Palabra("cord", 3);
        creadas[4].agregaAcepcion("fio", 'P');
        creadas[5] = new Palabra("cord", 3);
        creadas[5].agregaAcepcion("corda", 'P');
        creadas[5].agregaAcepcion("cuerda", 'E');
        creadas[6] = new Palabra("cord", 3);
        creadas[6].agregaAcepcion("cabo", 'P');
        creadas[6].agregaAcepcion("cuerda", 'E');
        creadas[6].agregaAcepcion("corde", 'F');
        creadas[7] = new Palabra("matter", 3);
        creadas[7].agregaAcepcion("questão", 'P');
        creadas[7].agregaAcepcion("asunto", 'E');
        creadas[7].agregaAcepcion("affaire", 'F');
        return creadas;
    }

    public static void main(String[] args) {
        DiccA diccio = new DiccA();
        if (args.length >= 1) {
            diccio.leeDiccA(args[0]);
            System.out.println("============ DICCIONARIO LEIDO ===========");
            diccio.muestraDiccA(1);
            Palabra[] agregadas = creaPalabras();
            boolean flag = false;
            for (int i = 0; i < agregadas.length; i++) {
                flag = diccio.insertaPalabra(agregadas[i]);
                System.out.println(agregadas[i].getOrigen() + " insertada? -> " + flag);
            }
            System.out.println("============ DICCIONARIO MODIFICADO ===========");
            diccio.muestraDiccA(1);
        } else
            System.out.println("Forma uso: java p05 p05.dic");

    }
}

/*
 * Porcentaje(diccio.traduce1(args[1], args[2].charAt(0)));
 * 
 * public static void Porcentaje(String texto) { String[] palabras =
 * texto.split(" "); int nPalabras = 0; for (String s : palabras) { if
 * (!s.equalsIgnoreCase("-")) nPalabras++;
 * 
 * }
 * 
 * double porcentaje = (nPalabras * 100) / palabras.length;
 * System.out.println(porcentaje + "%"); }
 */
