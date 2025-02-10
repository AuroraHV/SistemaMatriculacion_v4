package org.iesalandalus.programacion.matriculacion.negocio;

import org.iesalandalus.programacion.matriculacion.dominio.CicloFormativo;
import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;

public class CiclosFormativos {
    //1-Atributos
    private ArrayList<CicloFormativo> coleccionCiclosFormativos;

    //1.1-Constructor con lista de capacidad e inicializa atributos
    public CiclosFormativos () {
        this.coleccionCiclosFormativos= new ArrayList<>();
    }

    //Métodos
    //1.2-Get que devuelve copia profunda
    public ArrayList<CicloFormativo> get() {
        return copiaProfundaCiclosFormativos();
    }
    //Copia profunda de la colección
    private ArrayList<CicloFormativo> copiaProfundaCiclosFormativos() {
        ArrayList<CicloFormativo> copiaCiclosFormativos = new ArrayList<>();
        for (CicloFormativo c : coleccionCiclosFormativos) {
            copiaCiclosFormativos.add(new CicloFormativo(c));
        }
        return copiaCiclosFormativos;
    }
    public int getTamano() {
        return this.coleccionCiclosFormativos.size();
    }

    //1.3-Insertar alumnos al final de la colección mientras no esté repetido o sea nulo
    public void insertar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (cicloFormativo == null) {
            throw new NullPointerException("ERROR: No se puede insertar un ciclo formativo nulo.");
        }
        int indice = this.coleccionCiclosFormativos.indexOf(cicloFormativo);
        if (indice == -1) {
            this.coleccionCiclosFormativos.add(new CicloFormativo(cicloFormativo));
        } else {
            throw new OperationNotSupportedException("ERROR: Ya existe un ciclo formativo con ese código.");
        }
    }

    //1.4-Buscar un ciclo y devolverlo si está en la colección, en caso contrario será null
    public CicloFormativo buscar(CicloFormativo cicloFormativo) {
        if (cicloFormativo == null) {
            throw new NullPointerException("ERROR: No se puede buscar un ciclo formativo nulo.");
        }
        int indice = this.coleccionCiclosFormativos.indexOf(cicloFormativo);
        if (indice == -1) {
            return null;
        } else {
            return new CicloFormativo(this.coleccionCiclosFormativos.get(indice));
        }
    }

    //1.5-Si un ciclo se encuentra en la colección, se borra
    public void borrar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (cicloFormativo == null) {
            throw new NullPointerException("ERROR: No se puede borrar un ciclo formativo nulo.");
        }
        int indice = this.coleccionCiclosFormativos.indexOf(cicloFormativo);
        if (indice == -1) {
            throw new OperationNotSupportedException("ERROR: No existe ningún ciclo formativo como el indicado.");
        } else {
            this.coleccionCiclosFormativos.remove(indice);
        }
    }
}
