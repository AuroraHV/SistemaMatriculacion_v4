package org.iesalandalus.programacion.matriculacion.modelo;

import org.iesalandalus.programacion.matriculacion.dominio.*;
import org.iesalandalus.programacion.matriculacion.negocio.Alumnos;
import org.iesalandalus.programacion.matriculacion.negocio.Asignaturas;
import org.iesalandalus.programacion.matriculacion.negocio.CiclosFormativos;
import org.iesalandalus.programacion.matriculacion.negocio.Matriculas;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;

public class Modelo {

    private Alumnos alumnos;
    private Matriculas matriculas;
    private Asignaturas asignaturas;
    private CiclosFormativos ciclosFormativos;

    public void comenzar() {
        this.alumnos = new Alumnos();
        this.matriculas = new Matriculas();
        this.asignaturas = new Asignaturas();
        this.ciclosFormativos = new CiclosFormativos();
    }

    public void terminar() {
        System.out.println("La aplicación ha finalizado correctamente.");
    }

    //Alumno
    public void insertar(Alumno alumno) throws OperationNotSupportedException {
        if (this.alumnos == null) {
            this.alumnos = new Alumnos();
        }
        this.alumnos.insertar(alumno);
    }
    public Alumno buscar(Alumno alumno) {
        if (this.alumnos == null) {
            this.alumnos = new Alumnos();
        }
        return this.alumnos.buscar(alumno);
    }
    public void borrar(Alumno alumno) throws OperationNotSupportedException {
        if (this.alumnos == null) {
            this.alumnos = new Alumnos();
        }
        this.alumnos.borrar(alumno);
    }
    public ArrayList<Alumno> getAlumnos() {
        if (this.alumnos == null) {
            this.alumnos = new Alumnos();
        }
        return this.alumnos.get();
    }

    //Asignatura
    public void insertar(Asignatura asignatura) throws OperationNotSupportedException {
        if (this.asignaturas == null) {
            this.asignaturas = new Asignaturas();
        }
        this.asignaturas.insertar(asignatura);
    }
    public Asignatura buscar(Asignatura asignatura) {
        if (this.asignaturas == null) {
            this.asignaturas = new Asignaturas();
        }
        return this.asignaturas.buscar(asignatura);
    }
    public void borrar(Asignatura asignatura) throws OperationNotSupportedException {
        if (this.asignaturas == null) {
            this.asignaturas = new Asignaturas();
        }
        this.asignaturas.borrar(asignatura);
    }
    public ArrayList<Asignatura> getAsignaturas() {
        if (this.asignaturas == null) {
            this.asignaturas = new Asignaturas();
        }
        return this.asignaturas.get();
    }

    //CicloFormativo
    public void insertar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (this.ciclosFormativos == null) {
            this.ciclosFormativos = new CiclosFormativos();
        }
        this.ciclosFormativos.insertar(cicloFormativo);
    }
    public CicloFormativo buscar(CicloFormativo cicloFormativo) {
        if (this.ciclosFormativos == null) {
            this.ciclosFormativos = new CiclosFormativos();
        }
        return this.ciclosFormativos.buscar(cicloFormativo);
    }
    public void borrar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (this.ciclosFormativos == null) {
            this.ciclosFormativos = new CiclosFormativos();
        }
        this.ciclosFormativos.borrar(cicloFormativo);
    }
    public ArrayList<CicloFormativo> getCiclosFormativos() {
        if (this.ciclosFormativos == null) {
            this.ciclosFormativos = new CiclosFormativos();
        }
        return this.ciclosFormativos.get();
    }

    //Matrícula
    public void insertar(Matricula matricula) throws OperationNotSupportedException {
        if (this.matriculas == null) {
            this.matriculas = new Matriculas();
        }
        this.matriculas.insertar(matricula);
    }
    public Matricula buscar(Matricula matricula) throws OperationNotSupportedException {
        if (this.matriculas == null) {
            this.matriculas = new Matriculas();
        }
        return this.matriculas.buscar(matricula);
    }
    public void borrar(Matricula matricula) throws OperationNotSupportedException {
        if (this.matriculas == null) {
            this.matriculas = new Matriculas();
        }
        this.matriculas.borrar(matricula);
    }
    public ArrayList<Matricula> getMatriculas() throws OperationNotSupportedException {
        if (this.matriculas == null) {
            this.matriculas = new Matriculas();
        }
        return this.matriculas.get();
    }

    public ArrayList<Matricula> getMatriculas(Alumno alumno) throws OperationNotSupportedException {
        if (this.matriculas == null) {
            this.matriculas = new Matriculas();
        }
        return this.matriculas.get(alumno);
    }

    public ArrayList<Matricula> getMatriculas(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (this.matriculas == null) {
            this.matriculas = new Matriculas();
        }
        return this.matriculas.get(cicloFormativo);
    }

    public ArrayList<Matricula> getMatriculas(String cursoAcademico) throws OperationNotSupportedException {
        if (this.matriculas == null) {
            this.matriculas = new Matriculas();
        }
        return this.matriculas.get(cursoAcademico);
    }
}
