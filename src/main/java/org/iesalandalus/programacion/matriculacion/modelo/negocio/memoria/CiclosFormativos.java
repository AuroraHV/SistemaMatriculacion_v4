package org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.ICiclosFormativos;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;

/**
 * Clase que gestiona una colección de ciclos formativos.
 * Permite insertar, buscar, borrar y obtener información sobre los ciclos formativos.
 * Los ciclos formativos se almacenan en un array de una capacidad.
 *
 */
public class CiclosFormativos implements ICiclosFormativos {

    private ArrayList<CicloFormativo> coleccionCiclosFormativos;

    /**
     * Constructor de la clase CiclosFormativos.
     * Inicializa la colección con la capacidad especificada.
     */
    public CiclosFormativos () {
        this.coleccionCiclosFormativos= new ArrayList<>();
    }

    @Override
    public void comenzar() {
    }

    @Override
    public void terminar() {
    }

    /**
     * Obtiene una copia profunda de la colección de ciclos formativos.
     *
     * @return Un array con una copia de los ciclos formativos.
     */
    public ArrayList<CicloFormativo> get() {
        return copiaProfundaCiclosFormativos();
    }

    /**
     * Realiza una copia profunda de los ciclos formativos almacenados en la colección.
     *
     * @return Un array con los ciclos formativos copiados.
     */
    private ArrayList<CicloFormativo> copiaProfundaCiclosFormativos() {
        ArrayList<CicloFormativo> copiaCiclosFormativos = new ArrayList<>();
        for (CicloFormativo c : coleccionCiclosFormativos) {
            copiaCiclosFormativos.add(new CicloFormativo(c));
        }
        return copiaCiclosFormativos;
    }

    /**
     * Obtiene el tamaño actual de la colección (número de ciclos formativos almacenados).
     *
     * @return El tamaño actual de la colección.
     */
    public int getTamano() {
        return this.coleccionCiclosFormativos.size();
    }

    /**
     * Inserta un ciclo formativo en la colección.
     *
     * @param cicloFormativo El ciclo formativo a insertar.
     * @throws OperationNotSupportedException Si no se pueden insertar más ciclos formativos
     *         o si ya existe un ciclo formativo con el mismo código.
     * @throws NullPointerException Si el ciclo formativo es nulo.
     */
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

    /**
     * Busca un ciclo formativo en la colección.
     *
     * @param cicloFormativo El ciclo formativo a buscar.
     * @return El ciclo formativo encontrado o null si no se encuentra.
     * @throws NullPointerException Si el ciclo formativo es nulo.
     */
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

    /**
     * Borra un ciclo formativo de la colección.
     *
     * @param cicloFormativo El ciclo formativo a borrar.
     * @throws OperationNotSupportedException Si no existe el ciclo formativo a borrar.
     * @throws NullPointerException Si el ciclo formativo es nulo.
     */
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
