package org.iesalandalus.programacion.matriculacion.dominio;

public enum Curso {
    //1-Enumerado con valores
    PRIMERO("Primero"),
    SEGUNDO("Segundo");

    //2-Atributo
    private String cadenaAMostrar;

    //3-Constructor
    private Curso(String cadenaAMostrar) {
        this.cadenaAMostrar = cadenaAMostrar;
    }

    //4-Método imprimir
    public String imprimir() {
        if (this == PRIMERO) {
            return "0.-" + this.cadenaAMostrar;
        } else if (this == SEGUNDO) {
            return "1.-" + this.cadenaAMostrar;
        } else {
            throw new IllegalArgumentException("Curso no reconocido");
        }
    }

    //5-Representación en texto de los valores
    @Override
    public String toString() {
        return cadenaAMostrar;
    }
}

