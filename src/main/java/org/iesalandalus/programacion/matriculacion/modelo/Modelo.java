package org.iesalandalus.programacion.matriculacion.modelo;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.*;


import javax.naming.OperationNotSupportedException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase Modelo que gestiona las operaciones sobre los objetos de negocio:
 * alumnos, asignaturas, ciclos formativos y matrículas.
 * Permite insertar, buscar, borrar y obtener colecciones de estos objetos.
 */
public class Modelo {

    private IAlumnos alumnos;
    private IMatriculas matriculas;
    private IAsignaturas asignaturas;
    private ICiclosFormativos ciclosFormativos;
    private IFuenteDatos fuenteDatos;

    /**
     * Constructor de la clase Modelo.
     * @param factoriaFuenteDatos Instancia de la clase FactoriaFuenteDatos.
     */
    public Modelo(FactoriaFuenteDatos factoriaFuenteDatos) {
        setFuenteDatos(factoriaFuenteDatos.crear());
    }
    /**
     * Establece la fuente de datos.
     * @param fuenteDatos Instancia de la clase IFuenteDatos.
     */
    private void setFuenteDatos(IFuenteDatos fuenteDatos) {
        this.fuenteDatos = fuenteDatos;
    }

    /**
     * Inicializa las colecciones de alumnos, asignaturas, ciclos formativos y matrículas
     * con la capacidad definida.
     */
    public void comenzar() {
        this.alumnos = fuenteDatos.crearAlumnos();
        this.asignaturas = fuenteDatos.crearAsignaturas();
        this.ciclosFormativos = fuenteDatos.crearCiclosFormativos();
        this.matriculas = fuenteDatos.crearMatriculas();
    }

    /**
     * Finaliza la aplicación mostrando un mensaje por consola.
     */
    public void terminar() {
        this.alumnos.terminar();
        this.asignaturas.terminar();
        this.ciclosFormativos.terminar();
        this.matriculas.terminar();
        System.out.println("La aplicación ha finalizado correctamente.");
    }

    /**
     * Métodos para insertar, buscar, borrar y obtener colección de ALUMNOS.
     */
    public void insertar(Alumno alumno) throws OperationNotSupportedException, SQLException {
        this.alumnos.insertar(alumno);
    }
    public Alumno buscar(Alumno alumno) throws SQLException {
        return this.alumnos.buscar(alumno);
    }
    public void borrar(Alumno alumno) throws OperationNotSupportedException, SQLException {
        this.alumnos.borrar(alumno);
    }
    public ArrayList<Alumno> getAlumnos() throws SQLException {
        return this.alumnos.get();
    }

    /**
     * Métodos para insertar, buscar, borrar y obtener colección de ASIGNATURAS.
     */
    public void insertar(Asignatura asignatura) throws OperationNotSupportedException, SQLException {
        this.asignaturas.insertar(asignatura);
    }
    public Asignatura buscar(Asignatura asignatura) throws SQLException {
        return this.asignaturas.buscar(asignatura);
    }
    public void borrar(Asignatura asignatura) throws OperationNotSupportedException, SQLException {
        this.asignaturas.borrar(asignatura);
    }
    public ArrayList<Asignatura> getAsignaturas() throws SQLException {
        return this.asignaturas.get();
    }

    /**
     * Métodos para insertar, buscar, borrar y obtener colección de CICLOS FORMATIVOS.
     */
    public void insertar(CicloFormativo cicloFormativo) throws OperationNotSupportedException, SQLException {
        this.ciclosFormativos.insertar(cicloFormativo);
    }
    public CicloFormativo buscar(CicloFormativo cicloFormativo) throws SQLException {
        return this.ciclosFormativos.buscar(cicloFormativo);
    }
    public void borrar(CicloFormativo cicloFormativo) throws OperationNotSupportedException, SQLException {
        this.ciclosFormativos.borrar(cicloFormativo);
    }
    public ArrayList<CicloFormativo> getCiclosFormativos() throws SQLException {
        return this.ciclosFormativos.get();
    }

    /**
     * Métodos para insertar, buscar, borrar y obtener colección de MATRICULAS.
     */
    public void insertar(Matricula matricula) throws OperationNotSupportedException, SQLException {
        this.matriculas.insertar(matricula);
    }
    public Matricula buscar(Matricula matricula) throws OperationNotSupportedException, SQLException {
        return this.matriculas.buscar(matricula);
    }
    public void borrar(Matricula matricula) throws OperationNotSupportedException, SQLException {
        this.matriculas.borrar(matricula);
    }
    public ArrayList<Matricula> getMatriculas() throws OperationNotSupportedException, SQLException {
        return this.matriculas.get();
    }
    // Devuelve colección de matriculas de un alumno.
    public ArrayList<Matricula> getMatriculas(Alumno alumno) throws OperationNotSupportedException, SQLException {
        return this.matriculas.get(alumno);
    }
    // Devuelve colección de matriculas de un ciclo formativo.
    public ArrayList<Matricula> getMatriculas(CicloFormativo cicloFormativo) throws OperationNotSupportedException, SQLException {
        return this.matriculas.get(cicloFormativo);
    }
    // Devuelve colección de matriculas de un curso académico.
    public ArrayList<Matricula> getMatriculas(String cursoAcademico) throws OperationNotSupportedException, SQLException {
        return this.matriculas.get(cursoAcademico);
    }
}
