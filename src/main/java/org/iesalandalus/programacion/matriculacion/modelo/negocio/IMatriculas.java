package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;

import javax.naming.OperationNotSupportedException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interfaz que define los métodos necesarios para gestionar las matrículas en el sistema.
 * Las clases que implementen esta interfaz proporcionarán la lógica de acceso y manipulación
 * de los datos relacionados con las matrículas, como su inserción, eliminación, búsqueda y recuperación.
 * Los métodos permiten gestionar matrículas de manera general, así como obtener matrículas específicas
 * basadas en un alumno, un ciclo formativo o un curso académico determinado.
 */
public interface IMatriculas {

    public void comenzar();

    public void terminar();

    public int getTamano() throws SQLException;

    public ArrayList<Matricula> get() throws OperationNotSupportedException, SQLException;

    public void insertar(Matricula matricula) throws OperationNotSupportedException, SQLException;

    public Matricula buscar(Matricula matricula) throws OperationNotSupportedException, SQLException;

    public void borrar(Matricula matricula) throws OperationNotSupportedException, SQLException;

    public ArrayList<Matricula> get(Alumno alumno) throws OperationNotSupportedException, SQLException;

    public ArrayList<Matricula> get(String cursoAcademico) throws OperationNotSupportedException, SQLException;

    public ArrayList<Matricula> get(CicloFormativo cicloFormativo) throws OperationNotSupportedException, SQLException;
}
