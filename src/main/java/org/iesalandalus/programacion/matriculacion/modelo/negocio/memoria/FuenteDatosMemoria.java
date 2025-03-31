package org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria;


import org.iesalandalus.programacion.matriculacion.modelo.negocio.*;

/**
 * Implementación de la interfaz que gestiona los datos en memoria.
 * Esta clase proporciona métodos para la creación de instancias de las diferentes entidades
 * del sistema (alumnos, ciclos formativos, asignaturas y matrículas) en estructuras de datos
 * en memoria, sin utilizar una base de datos persistente.
 */
public class FuenteDatosMemoria implements IFuenteDatos {

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
