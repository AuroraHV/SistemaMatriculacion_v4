package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;

import javax.naming.OperationNotSupportedException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interfaz que define los métodos necesarios para gestionar la persistencia y operaciones
 * relacionadas con las asignaturas en el sistema. Esta interfaz debe ser implementada por
 * cualquier clase que maneje los datos de las asignaturas, ya sea en memoria o en una base de datos.
 * Proporciona métodos para comenzar y terminar la gestión de los datos, obtener la lista de asignaturas,
 * insertar, buscar y borrar asignaturas, así como obtener el tamaño de la colección de asignaturas.
 */
public interface IAsignaturas {

    public void comenzar();

    public void terminar();

    public ArrayList<Asignatura> get() throws SQLException;

    public int getTamano() throws SQLException;

    public void insertar(Asignatura asignatura) throws OperationNotSupportedException, SQLException;

    public Asignatura buscar(Asignatura asignatura) throws SQLException;

    public void borrar(Asignatura asignatura) throws OperationNotSupportedException, SQLException;
}
