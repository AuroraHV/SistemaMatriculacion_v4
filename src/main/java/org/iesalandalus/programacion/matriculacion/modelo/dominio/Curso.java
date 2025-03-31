package org.iesalandalus.programacion.matriculacion.modelo.dominio;

/**
 * Representa los cursos disponibles para la matriculación de un alumno.
 * Los cursos disponibles son:
 *     PRIMERO: Representa el primer curso.
 *     SEGUNDO: Representa el segundo curso.
 * Cada curso tiene una descripción asociada que se muestra en formato de cadena.
 */
public enum Curso {
    //Enumerado con valores
    PRIMERO("Primero"),
    SEGUNDO("Segundo");

    //Atributo
    private String cadenaAMostrar;

    //Constructor
    private Curso(String cadenaAMostrar) {
        this.cadenaAMostrar = cadenaAMostrar;
    }

    //Método imprimir
    public String imprimir() {
        if (this == PRIMERO) {
            return "0.-" + this.cadenaAMostrar;
        } else if (this == SEGUNDO) {
            return "1.-" + this.cadenaAMostrar;
        } else {
            throw new IllegalArgumentException("Curso no reconocido");
        }
    }

    //Representación en texto de los valores
    @Override
    public String toString() {
        return cadenaAMostrar;
    }
}

