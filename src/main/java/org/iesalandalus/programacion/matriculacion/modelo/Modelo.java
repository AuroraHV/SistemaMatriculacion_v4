package org.iesalandalus.programacion.matriculacion.modelo;

import org.iesalandalus.programacion.matriculacion.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.negocio.Alumnos;
import org.iesalandalus.programacion.matriculacion.negocio.Asignaturas;
import org.iesalandalus.programacion.matriculacion.negocio.CiclosFormativos;
import org.iesalandalus.programacion.matriculacion.negocio.Matriculas;

import javax.naming.OperationNotSupportedException;

public class Modelo {

    public static final int CAPACIDAD = 10;
    private Alumnos alumnos;
    private Matriculas matriculas;
    private Asignaturas asignaturas;
    private CiclosFormativos ciclosFormativos;

    public void comenzar() {
        this.alumnos = new Alumnos(CAPACIDAD);
        this.matriculas = new Matriculas(CAPACIDAD);
        this.asignaturas = new Asignaturas(CAPACIDAD);
        this.ciclosFormativos = new CiclosFormativos(CAPACIDAD);
    }

    public void terminar() {
        System.out.println("La aplicación ha finalizado correctamente.");
    }

    //Alumno
    public void insertar(Alumno alumno) throws OperationNotSupportedException {
        if (this.alumnos == null) {
            this.alumnos = new Alumnos(CAPACIDAD);
        }
        this.alumnos.insertar(alumno);
    }
    public Alumno buscar(Alumno alumno) {
        if (this.alumnos == null) {
            this.alumnos = new Alumnos(CAPACIDAD);
        }
        return this.alumnos.buscar(alumno);
    }
    public void borrar(Alumno alumno) throws OperationNotSupportedException {
        if (this.alumnos == null) {
            this.alumnos = new Alumnos(CAPACIDAD);
        }
        this.alumnos.borrar(alumno);
    }
    public Alumno[] getAlumnos() {
        if (this.alumnos == null) {
            this.alumnos = new Alumnos(CAPACIDAD);
        }
        return this.alumnos.get();
    }

    //Asignatura
    public void insertar(Asignatura asignatura) throws OperationNotSupportedException {
        if (this.asignaturas == null) {
            this.asignaturas = new Asignaturas(CAPACIDAD);
        }
        this.asignaturas.insertar(asignatura);
    }
    public Asignatura buscar(Asignatura asignatura) {
        if (this.asignaturas == null) {
            this.asignaturas = new Asignaturas(CAPACIDAD);
        }
        return this.asignaturas.buscar(asignatura);
    }
    public void borrar(Asignatura asignatura) throws OperationNotSupportedException {
        if (this.asignaturas == null) {
            this.asignaturas = new Asignaturas(CAPACIDAD);
        }
        this.asignaturas.borrar(asignatura);
    }
    public Asignatura[] getAsignaturas() {
        if (this.asignaturas == null) {
            this.asignaturas = new Asignaturas(CAPACIDAD);
        }
        return this.asignaturas.get();
    }

    //CicloFormativo
    public void insertar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (this.ciclosFormativos == null) {
            this.ciclosFormativos = new CiclosFormativos(CAPACIDAD);
        }
        this.ciclosFormativos.insertar(cicloFormativo);
    }
    public CicloFormativo buscar(CicloFormativo cicloFormativo) {
        if (this.ciclosFormativos == null) {
            this.ciclosFormativos = new CiclosFormativos(CAPACIDAD);
        }
        return this.ciclosFormativos.buscar(cicloFormativo);
    }
    public void borrar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (this.ciclosFormativos == null) {
            this.ciclosFormativos = new CiclosFormativos(CAPACIDAD);
        }
        this.ciclosFormativos.borrar(cicloFormativo);
    }
    public CicloFormativo[] getCiclosFormativos() {
        if (this.ciclosFormativos == null) {
            this.ciclosFormativos = new CiclosFormativos(CAPACIDAD);
        }
        return this.ciclosFormativos.get();
    }

    //Matrícula
    public void insertar(Matricula matricula) throws OperationNotSupportedException {
        if (this.matriculas == null) {
            this.matriculas = new Matriculas(CAPACIDAD);
        }
        this.matriculas.insertar(matricula);
    }
    public Matricula buscar(Matricula matricula) throws OperationNotSupportedException {
        if (this.matriculas == null) {
            this.matriculas = new Matriculas(CAPACIDAD);
        }
        return this.matriculas.buscar(matricula);
    }
    public void borrar(Matricula matricula) throws OperationNotSupportedException {
        if (this.matriculas == null) {
            this.matriculas = new Matriculas(CAPACIDAD);
        }
        this.matriculas.borrar(matricula);
    }
    public Matricula[] getMatriculas() throws OperationNotSupportedException {
        if (this.matriculas == null) {
            this.matriculas = new Matriculas(CAPACIDAD);
        }
        return this.matriculas.get();
    }

    public Matricula[] getMatriculas(Alumno alumno) throws OperationNotSupportedException {
        if (this.matriculas == null) {
            this.matriculas = new Matriculas(CAPACIDAD);
        }
        return this.matriculas.get(alumno);
    }

    public Matricula[] getMatriculas(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (this.matriculas == null) {
            this.matriculas = new Matriculas(CAPACIDAD);
        }
        return this.matriculas.get(cicloFormativo);
    }

    public Matricula[] getMatriculas(String cursoAcademico) throws OperationNotSupportedException {
        if (this.matriculas == null) {
            this.matriculas = new Matriculas(CAPACIDAD);
        }
        return this.matriculas.get(cursoAcademico);
    }
}
