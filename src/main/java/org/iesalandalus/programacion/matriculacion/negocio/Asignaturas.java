package org.iesalandalus.programacion.matriculacion.negocio;

import org.iesalandalus.programacion.matriculacion.dominio.Asignatura;
import javax.naming.OperationNotSupportedException;

public class Asignaturas {
    //1-Atributos
    private int capacidad;
    private int tamano;
    private Asignatura [] coleccionAsignaturas;

    //1.1-Constructor con lista de capacidad e inicializa atributos
    public Asignaturas(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        }
        this.capacidad = capacidad;
        this.tamano = 0;
        this.coleccionAsignaturas= new Asignatura[capacidad];
    }

    //Métodos
    //1.2-Get que devuelve copia profunda
    public Asignatura[] get() {
        return copiaProfundaAsignaturas();
    }
    //Copia profunda de la colección
    private Asignatura[] copiaProfundaAsignaturas() {
        Asignatura[] copiaAsignaturas = new Asignatura[tamano];
        for (int i = 0; i < tamano; i++) {
            copiaAsignaturas[i] = new Asignatura(coleccionAsignaturas[i]);
        }
        return copiaAsignaturas;
    }
    public int getTamano() {
        return tamano;
    }
    public int getCapacidad() {
        return capacidad;
    }

    //1.3-Insertar asignaturas al final de la colección mientras no esté repetido o sea nulo
    public void insertar(Asignatura asignatura) throws OperationNotSupportedException {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No se puede insertar una asignatura nula.");
        }
        if (capacidadSuperada(tamano)) {
            throw new OperationNotSupportedException("ERROR: No se aceptan más asignaturas.");
        }
        if (buscarIndice(asignatura) != -1) {
            throw new OperationNotSupportedException("ERROR: Ya existe una asignatura con ese código.");
        }
        // Validar que las horas no excedan el límite del ciclo formativo
        int horasTotales = asignatura.getHorasAnuales();
        for (int i = 0; i < tamano; i++) {
            if (coleccionAsignaturas[i].getCicloFormativo().equals(asignatura.getCicloFormativo())) {
                horasTotales += coleccionAsignaturas[i].getHorasAnuales();
            }
        }

        if (horasTotales > asignatura.getCicloFormativo().getHoras()) {
            throw new IllegalArgumentException("ERROR: El número de horas de la asignatura excede del total de horas del ciclo formativo.");
        }

        coleccionAsignaturas[tamano] = asignatura;
        tamano++;
    }

    //Buscar el índice de una asignatura
    private int buscarIndice(Asignatura asignatura) {
        for (int i = 0; i < tamano; i++) {
            if (coleccionAsignaturas[i].equals(asignatura)) {
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

    //1.4-Buscar una asignatura y devolverla si está en la colección, en caso contrario será null
    public Asignatura buscar(Asignatura asignatura) {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No se puede buscar una asignatura nula.");
        }
        int indice = buscarIndice(asignatura);
        if (indice != -1) {
            return new Asignatura(get()[indice]); // Devuelve una copia del alumno encontrado
        }
        return null;
    }

    //1.5-Si una asignatura se encuentra en la colección, se borra
    public void borrar(Asignatura asignatura) throws OperationNotSupportedException {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No se puede borrar una asignatura nula.");
        }
        int indice = buscarIndice(asignatura);
        if (indice != -1) {
            desplazarUnaPosicionHaciaIzquierda(indice);
            tamano--;
        } else {
            throw new OperationNotSupportedException("ERROR: No existe ninguna asignatura como la indicada.");
        }
    }
    //Tras borrar, se desplazan los elementos hacia la izquierda
    private void desplazarUnaPosicionHaciaIzquierda(int indice) {
        for (int i = indice; i < tamano - 1; i++) {
            coleccionAsignaturas[i] = coleccionAsignaturas[i + 1];
        }
        coleccionAsignaturas[tamano - 1] = null;
    }
}
