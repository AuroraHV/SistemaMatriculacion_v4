package org.iesalandalus.programacion.matriculacion.modelo.dominio;

/**
 * Clase abstracta que representa un grado educativo.
 * Un grado tiene un nombre, unas iniciales generadas automáticamente y un número de años de duración.
 * Esta clase define el comportamiento general de un grado, pero deja la implementación del número
 * de años a sus subclases.
 */
public abstract class Grado {

    protected String nombre;
    protected String iniciales;
    protected int numAnios;

    public Grado(String nombre){
        setNombre(nombre);
    }

    public String getNombre() {
        return nombre;
    }

   protected void setNombre(String nombre) {
       if (nombre == null) {
           throw new NullPointerException("ERROR: El nombre de un grado no puede ser nulo.");
       }
       if (nombre.isBlank()) {
           throw new IllegalArgumentException("ERROR: El nombre de un grado no puede estar vacío.");
       }
       if (nombre.isEmpty()) {
           throw new IllegalArgumentException("ERROR: El nombre de un grado no puede estar vacío.");
       }
       this.nombre = nombre;
       setIniciales();
   }

    private void setIniciales() {
        String[] palabras = nombre.split("[ ]+");
        String iniciales = "";
        for (String palabra : palabras) {
            iniciales += palabra.charAt(0);
        }
        this.iniciales = iniciales.toUpperCase();
    }

    public int getNumAnios() {
        return numAnios;
    }

    public abstract void setNumAnios(int numAnios);

    public String toString() {
        return "(" + iniciales + ") - " + nombre;
    }
}
