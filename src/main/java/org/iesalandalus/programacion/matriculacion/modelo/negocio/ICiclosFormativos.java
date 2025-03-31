package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;

import javax.naming.OperationNotSupportedException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interfaz que define los métodos necesarios para gestionar la persistencia y operaciones
 * relacionadas con los ciclos formativos en el sistema. Esta interfaz debe ser implementada por
 * cualquier clase que maneje los datos de los ciclos formativos, ya sea en memoria o en una base de datos.
 * Proporciona métodos para comenzar y terminar la gestión de los datos, obtener la lista de ciclos formativos,
 * insertar, buscar y borrar ciclos formativos, así como obtener el tamaño de la colección de ciclos formativos.
 */
public interface ICiclosFormativos {

    public void comenzar();

    public void terminar();

    public ArrayList<CicloFormativo> get() throws SQLException;

    public int getTamano() throws SQLException;

    public void insertar(CicloFormativo cicloFormativo) throws OperationNotSupportedException, SQLException;

    public CicloFormativo buscar(CicloFormativo cicloFormativo) throws SQLException;

    public void borrar(CicloFormativo cicloFormativo) throws OperationNotSupportedException, SQLException;

}
