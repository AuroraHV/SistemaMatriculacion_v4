package org.iesalandalus.programacion.matriculacion.negocio;

import org.iesalandalus.programacion.matriculacion.dominio.Alumno;
import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;

public class Alumnos {
    //1-Atributos
    private ArrayList<Alumno> coleccionAlumnos;

    //1.1-Constructor con lista de capacidad e inicializa atributos
    public Alumnos() {
        this.coleccionAlumnos= new ArrayList<>();
    }

    //Métodos
    //1.2-Get que devuelve copia profunda
    public ArrayList<Alumno> get() {
        return copiaProfundaAlumnos();
    }
    //Copia profunda de la colección
    private ArrayList<Alumno> copiaProfundaAlumnos() {
        ArrayList<Alumno> copiaAlumnos = new ArrayList<>();
        //Alumno[] copiaAlumnos = new Alumno[this.coleccionAlumnos.size()];
        for (Alumno a : coleccionAlumnos) {
            copiaAlumnos.add (new Alumno(a));
        }
        return copiaAlumnos;
    }
    public int getTamano() {
        return this.coleccionAlumnos.size();
    }

    //1.3-Insertar alumnos al final de la colección mientras no esté repetido o sea nulo
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

    //1.4-Buscar un alumno y devolverlo si está en la colección, en caso contrario será null
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

    //1.5-Si un alumno se encuentra en la colección, se borra
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




