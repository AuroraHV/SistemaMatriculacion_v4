package org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAlumnos;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;

/**
 * Clase que gestiona una colección de alumnos con una capacidad.
 * Permite realizar operaciones como insertar, buscar y borrar alumnos.
 * La capacidad máxima de la colección se define en el momento de su creación.
 */
public class Alumnos implements IAlumnos {
    
    //Array que almacena los alumnos de la colección.
    private ArrayList<Alumno> coleccionAlumnos;

    /**
     * Constructor que inicializa la colección.
     */
    public Alumnos() {
        this.coleccionAlumnos= new ArrayList<>();
        comenzar();
    }

    @Override
    public void comenzar() {
    }

    @Override
    public void terminar() {
    }

    /**
     * Devuelve una copia profunda de los alumnos almacenados en la colección.
     *
     * @return Un array con copias de los alumnos.
     */
    public ArrayList<Alumno> get() {
        return copiaProfundaAlumnos();
    }

    /**
     * Crea una copia profunda del array de alumnos.
     *
     * @return Un array con copias de los alumnos.
     */
    private ArrayList<Alumno> copiaProfundaAlumnos() {
        ArrayList<Alumno> copiaAlumnos = new ArrayList<>();
        //Alumno[] copiaAlumnos = new Alumno[this.coleccionAlumnos.size()];
        for (Alumno a : coleccionAlumnos) {
            copiaAlumnos.add (new Alumno(a));
        }
        return copiaAlumnos;
    }

    /**
     * Devuelve el tamaño actual de la colección.
     *
     * @return El número de alumnos almacenados en la colección.
     */
    public int getTamano() {
        return this.coleccionAlumnos.size();
    }

    /**
     * Inserta un alumno en la colección.
     *
     * @param alumno Alumno a insertar. No puede ser nulo.
     * @throws OperationNotSupportedException Si la colección está llena o si ya existe un alumno con el mismo DNI.
     * @throws NullPointerException Si el alumno es nulo.
     */
    public void insertar(Alumno alumno) throws OperationNotSupportedException {
        if (alumno == null) {
            throw new NullPointerException("ERROR: No se puede insertar un alumno nulo.");
        }
        int indice = this.coleccionAlumnos.indexOf(alumno);
        if (indice == -1) {
            this.coleccionAlumnos.add(new Alumno(alumno));
        } else {
            throw new OperationNotSupportedException("ERROR: Ya existe un alumno con ese dni.");
        }
    }

    /**
     * Busca un alumno en la colección.
     *
     * @param alumno Alumno a buscar. No puede ser nulo.
     * @return Una copia del alumno si se encuentra, o null si no se encuentra.
     * @throws NullPointerException Si el alumno es nulo.
     */
    public Alumno buscar(Alumno alumno) {
        if (alumno == null) {
            throw new NullPointerException("ERROR: No se puede buscar un alumno nulo.");
        }
        int indice = this.coleccionAlumnos.indexOf(alumno);
        if (indice == -1) {
            return null;
        } else {
            return new Alumno(this.coleccionAlumnos.get(indice));
        }
    }

    /**
     * Borra un alumno de la colección.
     *
     * @param alumno Alumno a borrar. No puede ser nulo.
     * @throws OperationNotSupportedException Si el alumno no existe en la colección.
     * @throws NullPointerException Si el alumno es nulo.
     */
    public void borrar(Alumno alumno) throws OperationNotSupportedException {
        if (alumno == null) {
            throw new NullPointerException("ERROR: No se puede borrar un alumno nulo.");
        }
        int indice = this.coleccionAlumnos.indexOf(alumno);
        if (indice == -1) {
            throw new OperationNotSupportedException("ERROR: No existe ningún alumno como el indicado.");
        }
        this.coleccionAlumnos.remove(indice);
    }
}




