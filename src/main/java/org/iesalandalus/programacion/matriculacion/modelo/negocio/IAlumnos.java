package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;

import javax.naming.OperationNotSupportedException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interfaz que define los métodos necesarios para gestionar la persistencia y operaciones
 * relacionadas con los alumnos en el sistema. Esta interfaz debe ser implementada por
 * cualquier clase que maneje los datos de los alumnos, ya sea en memoria o en una base de datos.
 * Proporciona métodos para comenzar y terminar la gestión de los datos, obtener la lista de alumnos,
 * insertar, buscar y borrar alumnos, así como obtener el tamaño de la colección de alumnos.
 */
public interface IAlumnos {

    public void comenzar();

    public void terminar();

    public ArrayList<Alumno> get() throws SQLException;

    public int getTamano() throws SQLException;

    public void insertar(Alumno alumno) throws OperationNotSupportedException, SQLException;

    public Alumno buscar(Alumno alumno) throws SQLException;

    public void borrar(Alumno alumno) throws OperationNotSupportedException, SQLException;
}
