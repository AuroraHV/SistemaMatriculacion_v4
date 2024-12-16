package org.iesalandalus.programacion.matriculacion.dominio;

public enum EspecialidadProfesorado {
    //1-Enumerado con valores
    INFORMATICA("Informática"),
    SISTEMAS("Sistemas"),
    FOL("FOL");

    //2-Atributo
    private String cadenaAMostrar;

    //3-Constructor
    private EspecialidadProfesorado(String cadenaAMostrar) {
        this.cadenaAMostrar = cadenaAMostrar;
    }

    //4-Método imprimir
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

    //5-Representación en texto de los valores
    @Override
    public String toString() {
        return this.cadenaAMostrar;
    }

}
