package org.iesalandalus.programacion.matriculacion.negocio;

import org.iesalandalus.programacion.matriculacion.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.dominio.CicloFormativo;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;

public class Asignaturas {
    //1-Atributos
    private ArrayList<Asignatura> coleccionAsignaturas;

    //1.1-Constructor con lista de capacidad e inicializa atributos
    public Asignaturas() {
        this.coleccionAsignaturas= new ArrayList<>();
    }

    //Métodos
    //1.2-Get que devuelve copia profunda
    public ArrayList<Asignatura> get() {
        return copiaProfundaAsignaturas();
    }
    //Copia profunda de la colección
    private ArrayList<Asignatura> copiaProfundaAsignaturas() {
        ArrayList<Asignatura> copiaAsignaturas = new ArrayList<>();
        for (Asignatura a : coleccionAsignaturas) {
            copiaAsignaturas.add(new Asignatura(a));
        }
        return copiaAsignaturas;
    }
    public int getTamano() {
        return this.coleccionAsignaturas.size();
    }

    //1.3-Insertar asignaturas al final de la colección mientras no esté repetido o sea nulo
    public void insertar(Asignatura asignatura) throws OperationNotSupportedException {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No se puede insertar una asignatura nula.");
        }
        int indice = this.coleccionAsignaturas.indexOf(asignatura);
        if (indice == -1) {
            // Obtener el ciclo formativo de la asignatura a insertar
            CicloFormativo cicloFormativo = asignatura.getCicloFormativo();
            int horasTotales = asignatura.getHorasAnuales() + asignatura.getHorasDesdoble();

            // Sumar las horas de las asignaturas que pertenecen al mismo ciclo formativo
            for (Asignatura a : coleccionAsignaturas) {
                if (a.getCicloFormativo().equals(cicloFormativo)) {
                    horasTotales += a.getHorasAnuales() + a.getHorasDesdoble();
                }
            }

            // Validar si se supera el límite de horas del ciclo formativo
            if (horasTotales > cicloFormativo.getHoras()) {
                throw new IllegalArgumentException("ERROR: El número total de horas de asignaturas en el ciclo formativo supera el máximo permitido de "
                        + cicloFormativo.getHoras() + " horas.");
            }

            this.coleccionAsignaturas.add(asignatura);
        } else {
            throw new OperationNotSupportedException("ERROR: Ya existe una asignatura con ese código.");
        }

    }

    //1.4-Buscar una asignatura y devolverla si está en la colección, en caso contrario será null
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

    //1.5-Si una asignatura se encuentra en la colección, se borra
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
