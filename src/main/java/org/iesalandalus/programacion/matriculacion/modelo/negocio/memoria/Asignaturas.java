package org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAsignaturas;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;

/**
 * Clase que gestiona una colección de asignaturas.
 * Permite insertar, buscar, borrar y obtener información sobre las asignaturas.
 * Las asignaturas se almacenan en un array con una capacidad.
 */
public class Asignaturas implements IAsignaturas {
    //Array de asignaturas.
    private ArrayList<Asignatura> coleccionAsignaturas;

    /**
     * Constructor de la clase Asignaturas.
     * Inicializa la colección con la capacidad especificada.
     */
    public Asignaturas() {
        this.coleccionAsignaturas= new ArrayList<>();
        comenzar();
    }

    @Override
    public void comenzar() {
    }

    @Override
    public void terminar() {
    }

    /**
     * Obtiene una copia profunda de la colección de asignaturas.
     *
     * @return Un arreglo con una copia de las asignaturas.
     */
    public ArrayList<Asignatura> get() {
        return copiaProfundaAsignaturas();
    }

    /**
     * Realiza una copia profunda de las asignaturas almacenadas en la colección.
     *
     * @return Un arreglo con las asignaturas copiadas.
     */
    private ArrayList<Asignatura> copiaProfundaAsignaturas() {
        ArrayList<Asignatura> copiaAsignaturas = new ArrayList<>();
        for (Asignatura a : coleccionAsignaturas) {
            copiaAsignaturas.add(new Asignatura(a));
        }
        return copiaAsignaturas;
    }

    /**
     * Obtiene el tamaño actual de la colección (número de asignaturas almacenadas).
     *
     * @return El tamaño actual de la colección.
     */
    public int getTamano() {
        return this.coleccionAsignaturas.size();
    }

    /**
     * Inserta una asignatura en la colección.
     *
     * @param asignatura La asignatura a insertar.
     * @throws OperationNotSupportedException Si no se pueden insertar más asignaturas
     *         o si ya existe una asignatura con el mismo código.
     * @throws NullPointerException Si la asignatura es nula.
     */
    public void insertar(Asignatura asignatura) throws OperationNotSupportedException {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No se puede insertar una asignatura nula.");
        }
        int indice = this.coleccionAsignaturas.indexOf(asignatura);
        if (indice == -1) {
            // Obtener el ciclo formativo de la asignatura a insertar.
            CicloFormativo cicloFormativo = asignatura.getCicloFormativo();
            int horasTotales = asignatura.getHorasAnuales() + asignatura.getHorasDesdoble();
            // Sumar las horas de las asignaturas que pertenecen al mismo ciclo formativo.
            for (Asignatura a : coleccionAsignaturas) {
                if (a.getCicloFormativo().equals(cicloFormativo)) {
                    horasTotales += a.getHorasAnuales() + a.getHorasDesdoble();
                }
            }
            // Validar si se supera el límite de horas del ciclo formativo.
            if (horasTotales > cicloFormativo.getHoras()) {
                throw new IllegalArgumentException("ERROR: El número total de horas de asignaturas en el ciclo formativo supera el máximo permitido de "
                        + cicloFormativo.getHoras() + " horas.");
            }
            this.coleccionAsignaturas.add(asignatura);
        } else {
            throw new OperationNotSupportedException("ERROR: Ya existe una asignatura con ese código.");
        }
    }

    /**
     * Busca una asignatura en la colección.
     *
     * @param asignatura La asignatura a buscar.
     * @return La asignatura encontrada o null si no se encuentra.
     * @throws NullPointerException Si la asignatura es nula.
     */
    public Asignatura buscar(Asignatura asignatura) {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No se puede buscar una asignatura nula.");
        }
        int indice = this.coleccionAsignaturas.indexOf(asignatura);
        if (indice == -1) {
            return null;
        } else {
            return new Asignatura(this.coleccionAsignaturas.get(indice));
        }
    }

    /**
     * Borra una asignatura de la colección.
     *
     * @param asignatura La asignatura a borrar.
     * @throws OperationNotSupportedException Si no existe la asignatura a borrar.
     * @throws NullPointerException Si la asignatura es nula.
     */
    public void borrar(Asignatura asignatura) throws OperationNotSupportedException {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No se puede borrar una asignatura nula.");
        }
        int indice = this.coleccionAsignaturas.indexOf(asignatura);
        if (indice == -1) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna asignatura como la indicada.");
        }
        this.coleccionAsignaturas.remove(indice);
    }
}
