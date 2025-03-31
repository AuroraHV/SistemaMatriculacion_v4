package org.iesalandalus.programacion.matriculacion.modelo.dominio;

/**
 * Clase que representa un Grado D, que es una especialización de Grado.
 * Un Grado D tiene una modalidad específica y puede durar entre 2 y 3 años.
 */
public class GradoD extends Grado {

    private Modalidad modalidad;

    public GradoD(String nombre, int numAnios, Modalidad modalidad) {
        super(nombre);
        setNumAnios(numAnios);
        setModalidad(modalidad);
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        if (modalidad == null) {
            throw new NullPointerException("ERROR: La modalidad de un grado D no puede ser nula.");
        }
        this.modalidad = modalidad;
    }

    @Override
    public void setNumAnios(int numAnios) {
        if (numAnios < 2 || numAnios > 3) {
            throw new IllegalArgumentException("ERROR: El número de años de un grado D debe ser 2 o 3.");
        }
        this.numAnios = numAnios;
    }

    @Override
    public String toString() {
        return "D: " + super.toString() + " , modalidad: " + modalidad;
    }
}
