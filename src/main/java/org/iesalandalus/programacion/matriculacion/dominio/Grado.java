package org.iesalandalus.programacion.matriculacion.dominio;

public enum Grado {

    //1-Enumerado con valores
    GDCFGB("Grado Básico"),
    GDCFGM("Grado Medio"),
    GDCFGS("Grado Superior");

    //2-Atributo
    private String cadenaAMostrar;

    //3-Constructor
    private Grado(String cadenaAMostrar) {
        this.cadenaAMostrar = cadenaAMostrar;
    }

    //4-Método imprimir
    public String imprimir() {
        if (this == GDCFGB) {
            return "0.-" + this.cadenaAMostrar;
        } else if (this == GDCFGM) {
            return "1.-" + this.cadenaAMostrar;
        } else if (this == GDCFGS) {
            return "2.-" + this.cadenaAMostrar;
        } else {
            throw new IllegalArgumentException("Grado no reconocido");
        }
    }

    //5-Representación en texto de los valores
    @Override
    public String toString() {
        return this.cadenaAMostrar;
    }

}
