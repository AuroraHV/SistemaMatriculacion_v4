package org.iesalandalus.programacion.matriculacion.negocio;

import org.iesalandalus.programacion.matriculacion.dominio.CicloFormativo;
import javax.naming.OperationNotSupportedException;

public class CiclosFormativos {
    //1-Atributos
    private int capacidad;
    private int tamano;
    private CicloFormativo[] coleccionCiclosFormativos;

    //1.1-Constructor con lista de capacidad e inicializa atributos
    public CiclosFormativos (int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        }
        this.capacidad = capacidad;
        this.tamano = 0;
        this.coleccionCiclosFormativos= new CicloFormativo[capacidad];
    }

    //Métodos
    //1.2-Get que devuelve copia profunda
    public CicloFormativo[] get() {
        return copiaProfundaCiclosFormativos();
    }
    //Copia profunda de la colección
    private CicloFormativo[] copiaProfundaCiclosFormativos() {
        CicloFormativo[] copiaCiclosFormativos = new CicloFormativo[tamano];
        for (int i = 0; i < tamano; i++) {
            copiaCiclosFormativos[i] = new CicloFormativo(coleccionCiclosFormativos[i]);
        }
        return copiaCiclosFormativos;
    }
    public int getTamano() {
        return tamano;
    }
    public int getCapacidad() {
        return capacidad;
    }

    //1.3-Insertar alumnos al final de la colección mientras no esté repetido o sea nulo
    public void insertar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (cicloFormativo == null) {
            throw new NullPointerException("ERROR: No se puede insertar un ciclo formativo nulo.");
        }
        if (capacidadSuperada(tamano)) {
            throw new OperationNotSupportedException("ERROR: No se aceptan más ciclos formativos.");
        }
        if (buscarIndice(cicloFormativo) != -1) {
            throw new OperationNotSupportedException("ERROR: Ya existe un ciclo formativo con ese código.");
        }
        coleccionCiclosFormativos[tamano] = cicloFormativo;
        tamano++;
    }

    //Buscar el índice de un ciclo
    private int buscarIndice(CicloFormativo cicloFormativo) {
        for (int i = 0; i < tamano; i++) {
            if (coleccionCiclosFormativos[i].equals(cicloFormativo)) {
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

    //1.4-Buscar un ciclo y devolverlo si está en la colección, en caso contrario será null
    public CicloFormativo buscar(CicloFormativo cicloFormativo) {
        if (cicloFormativo == null) {
            throw new NullPointerException("ERROR: No se puede buscar un ciclo formativo nulo.");
        }
        int indice = buscarIndice(cicloFormativo);
        if (indice != -1) {
            return new CicloFormativo(get()[indice]); // Devuelve una copia del alumno encontrado
        }
        return null;
    }

    //1.5-Si un ciclo se encuentra en la colección, se borra
    public void borrar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (cicloFormativo == null) {
            throw new NullPointerException("ERROR: No se puede borrar un ciclo formativo nulo.");
        }
        int indice = buscarIndice(cicloFormativo);
        if (indice != -1) {
            desplazarUnaPosicionHaciaIzquierda(indice);
            tamano--;
        } else {
            throw new OperationNotSupportedException("ERROR: No existe ningún ciclo formativo como el indicado.");
        }
    }
    //Tras borrar, se desplazan los elementos hacia la izquierda
    private void desplazarUnaPosicionHaciaIzquierda(int indice) {
        for (int i = indice; i < tamano - 1; i++) {
            coleccionCiclosFormativos[i] = coleccionCiclosFormativos[i + 1];
        }
        coleccionCiclosFormativos[tamano - 1] = null;
    }
}
