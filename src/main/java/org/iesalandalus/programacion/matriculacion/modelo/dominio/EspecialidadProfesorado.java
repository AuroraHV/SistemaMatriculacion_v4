package org.iesalandalus.programacion.matriculacion.modelo.dominio;

/**
 * Representa las especialidades disponibles para el profesorado.
 * Las especialidades disponibles son:
 *     INFORMATICA: Especialidad en Informática.
 *     SISTEMAS: Especialidad en Sistemas.
 *     FOL: Formación y Orientación Laboral (FOL).
 * Cada especialidad tiene una representación en formato de cadena asociada.
 */
public enum EspecialidadProfesorado {
    //Enumerado con valores
    INFORMATICA("Informática"),
    SISTEMAS("Sistemas"),
    FOL("FOL");

    //Atributo
    private String cadenaAMostrar;

    //Constructor
    private EspecialidadProfesorado(String cadenaAMostrar) {
        this.cadenaAMostrar = cadenaAMostrar;
    }

    //Método imprimir
    public String imprimir() {
        if (this == INFORMATICA) {
            return "0.-" + this.cadenaAMostrar;
        } else if (this == SISTEMAS) {
            return "1.-" + this.cadenaAMostrar;
        } else if (this == FOL) {
            return "2.-" + this.cadenaAMostrar;
        } else {
            throw new IllegalArgumentException("Especialidad no reconocida.");
        }
    }

    //Representación en texto de los valores
    @Override
    public String toString() {
        return this.cadenaAMostrar;
    }

}
