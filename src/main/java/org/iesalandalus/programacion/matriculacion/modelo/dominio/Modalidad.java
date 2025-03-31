package org.iesalandalus.programacion.matriculacion.modelo.dominio;

/**
 * Enumeración que representa las diferentes modalidades de un grado.
 * Existen dos modalidades: presencial y semipresencial.
 */
public enum Modalidad {

    PRESENCIAL ("Presencial"),
    SEMIPRESENCIAL ("Semipresencial");

    private String cadenaAMostrar;

    //Constructor
    private Modalidad(String cadenaAMostrar) {
        this.cadenaAMostrar = cadenaAMostrar;
    }

    //Método imprimir
    public String imprimir() {
        int digito = 0;
        if(cadenaAMostrar == PRESENCIAL.cadenaAMostrar) {
            digito = 0;
        } else if(cadenaAMostrar == SEMIPRESENCIAL.cadenaAMostrar) {
            digito = 1;
        } else {
            digito = 2;
        }
       return digito + ".-" + cadenaAMostrar;
    }

    //Representación en texto de los valores
    @Override
    public String toString() {
        return cadenaAMostrar;
    }

}


