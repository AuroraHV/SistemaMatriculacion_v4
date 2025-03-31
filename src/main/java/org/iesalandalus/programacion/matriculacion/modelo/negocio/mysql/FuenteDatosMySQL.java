package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAlumnos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAsignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.ICiclosFormativos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IMatriculas;

/**
 * Clase que implementa la interfaz IFuenteDatos y proporciona la implementación
 * concreta de los métodos para crear las distintas colecciones de datos
 * en el contexto de una fuente de datos MySQL.
 * Esta clase es responsable de proporcionar instancias específicas para
 * gestionar las entidades de alumnos, ciclos formativos, asignaturas y matrículas
 * dentro de un sistema de base de datos MySQL.
 */
public class FuenteDatosMySQL implements IFuenteDatos {

    @Override
    public IAlumnos crearAlumnos() {
        return new Alumnos();
    }

    @Override
    public ICiclosFormativos crearCiclosFormativos() {
        return new CiclosFormativos();
    }

    @Override
    public IAsignaturas crearAsignaturas() {
        return new Asignaturas();
    }

    @Override
    public IMatriculas crearMatriculas() {
        return new Matriculas();
    }

}