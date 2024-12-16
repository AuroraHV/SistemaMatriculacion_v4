package org.iesalandalus.programacion.matriculacion.negocio;

import org.iesalandalus.programacion.matriculacion.dominio.Alumno;
import javax.naming.OperationNotSupportedException;

public class Alumnos {
    //1-Atributos
    private int capacidad;
    private int tamano;
    private Alumno [] coleccionAlumnos;

    //1.1-Constructor con lista de capacidad e inicializa atributos
    public Alumnos(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        }
        this.capacidad = capacidad;
        this.tamano = 0;
        this.coleccionAlumnos= new Alumno[capacidad];
    }

    //Métodos
    //1.2-Get que devuelve copia profunda
    public Alumno[] get() {
        return copiaProfundaAlumnos();
    }
    //Copia profunda de la colección
    private Alumno[] copiaProfundaAlumnos() {
        Alumno[] copiaAlumnos = new Alumno[tamano];
        for (int i = 0; i < tamano; i++) {
            copiaAlumnos[i] = new Alumno(coleccionAlumnos[i]);
        }
        return copiaAlumnos;
    }
    public int getTamano() {
        return tamano;
    }
    public int getCapacidad() {
        return capacidad;
    }

    //1.3-Insertar alumnos al final de la colección mientras no esté repetido o sea nulo
    public void insertar(Alumno alumno) throws OperationNotSupportedException {
        if (alumno == null) {
            throw new NullPointerException("ERROR: No se puede insertar un alumno nulo.");
        }
        if (capacidadSuperada(tamano)) {
            throw new OperationNotSupportedException("ERROR: No se aceptan más alumnos.");
        }
        if (buscarIndice(alumno) != -1) {
            throw new OperationNotSupportedException("ERROR: Ya existe un alumno con ese dni.");
        }
        coleccionAlumnos[tamano] = alumno;
        tamano++;
    }

    //Buscar el índice de un alumno
    private int buscarIndice(Alumno alumno) {
        for (int i = 0; i < tamano; i++) {
            if (coleccionAlumnos[i].equals(alumno)) {
                return i;
            }
        }
        return -1; // Si no se encuentra, retornamos -1
    }
    //Verificar si el tamaño está superado
    private boolean tamanoSuperado(int indice) {
        return indice >= getTamano();
    }
    //Verificar si la capacidad está superada
    private boolean capacidadSuperada(int indice) {
        return indice >= getCapacidad();
    }

    //1.4-Buscar un alumno y devolverlo si está en la colección, en caso contrario será null
    public Alumno buscar(Alumno alumno) {
        if (alumno == null) {
            throw new NullPointerException("ERROR: No se puede buscar un alumno nulo.");
        }
        int indice = buscarIndice(alumno);
        if (indice != -1) {
            return new Alumno(get()[indice]); // Devuelve una copia del alumno encontrado
        }
        return null;
    }

    //1.5-Si un alumno se encuentra en la colección, se borra
    public void borrar(Alumno alumno) throws OperationNotSupportedException {
        if (alumno == null) {
            throw new NullPointerException("ERROR: No se puede borrar un alumno nulo.");
        }
        int indice = buscarIndice(alumno);
        if (indice != -1) {
            desplazarUnaPosicionHaciaIzquierda(indice);
            tamano--;
        } else {
            throw new OperationNotSupportedException("ERROR: No existe ningún alumno como el indicado.");
        }
    }
    //Tras borrar, se desplazan los elementos hacia la izquierda
    private void desplazarUnaPosicionHaciaIzquierda(int indice) {
        for (int i = indice; i < tamano - 1; i++) {
            coleccionAlumnos[i] = coleccionAlumnos[i + 1];
        }
        coleccionAlumnos[tamano - 1] = null;
    }
}




