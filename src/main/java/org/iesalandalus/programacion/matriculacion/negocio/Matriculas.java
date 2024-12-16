package org.iesalandalus.programacion.matriculacion.negocio;

import org.iesalandalus.programacion.matriculacion.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.dominio.Matricula;
import javax.naming.OperationNotSupportedException;


public class Matriculas{
    //1-Atributos
    private int capacidad;
    private int tamano;
    private Matricula [] coleccionMatriculas;

    //1.1-Constructor con lista de capacidad e inicializa atributos
    public Matriculas(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        }
        this.capacidad = capacidad;
        this.tamano = 0;
        this.coleccionMatriculas= new Matricula[capacidad];
    }

    //Métodos
    //1.2-Get que devuelve copia profunda
    public Matricula[] get() throws OperationNotSupportedException {
        return copiaProfundaMatriculas();
    }
    //Copia profunda de la colección
    private Matricula[] copiaProfundaMatriculas() throws OperationNotSupportedException {
        Matricula[] copiaMatriculas = new Matricula[tamano];
        for (int i = 0; i < tamano; i++) {
            copiaMatriculas[i] = new Matricula(coleccionMatriculas[i]);
        }
        return copiaMatriculas;
    }
    public int getTamano() {
        return tamano;
    }
    public int getCapacidad() {
        return capacidad;
    }

    //1.3-Insertar matrícula al final de la colección mientras no esté repetido o sea nulo
    public void insertar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null) {
            throw new NullPointerException("ERROR: No se puede insertar una matrícula nula.");
        }
        if (capacidadSuperada(tamano)) {
            throw new OperationNotSupportedException("ERROR: No se aceptan más matrículas.");
        }
        if (buscarIndice(matricula) != -1) {
            throw new OperationNotSupportedException("ERROR: Ya existe una matrícula con ese identificador.");
        }
        coleccionMatriculas[tamano] = matricula;
        tamano++;
    }

    //Buscar el índice de una matrícula
    private int buscarIndice(Matricula matricula) {
        for (int i = 0; i < tamano; i++) {
            if (coleccionMatriculas[i].equals(matricula)) {
                return i;
            }
        }
        return -1; // Si no se encuentra, retornamos -1
    }
    //Verificar si el tamaño está superado
    private boolean tamanoSuperado(int indice) {
        return indice >= getTamano();
    }
    //Verificar si la capacidad está superada
    private boolean capacidadSuperada(int indice) {
        return indice >= getCapacidad();
    }

    //1.4-Buscar una matrícula y devolverla si está en la colección, en caso contrario será null
    public Matricula buscar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null) {
            throw new NullPointerException("ERROR: No se puede buscar una matrícula nula.");
        }
        int indice = buscarIndice(matricula);
        if (indice != -1) {
            return new Matricula(get()[indice]); // Devuelve una copia del alumno encontrado
        }
        return null;
    }

    //1.5-Si un alumno se encuentra en la colección, se borra
    public void borrar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null) {
            throw new NullPointerException("ERROR: No se puede borrar una matrícula nula.");
        }
        int indice = buscarIndice(matricula);
        if (indice != -1) {
            desplazarUnaPosicionHaciaIzquierda(indice);
            tamano--;
        } else {
            throw new OperationNotSupportedException("ERROR: No existe ninguna matrícula como la indicada.");
        }
    }
    //Tras borrar, se desplazan los elementos hacia la izquierda
    private void desplazarUnaPosicionHaciaIzquierda(int indice) {
        for (int i = indice; i < tamano - 1; i++) {
            coleccionMatriculas[i] = coleccionMatriculas[i + 1];
        }
        coleccionMatriculas[tamano - 1] = null;
    }

    //2-Devuelve colección de matriculas de un alumno
    public Matricula[] get(Alumno alumno) throws OperationNotSupportedException {
        if (alumno == null) {
            throw new NullPointerException("ERROR: El alumno no puede ser nulo.");
        }
        Matricula[] matriculasAlumno = new Matricula[tamano];
        int j = 0;
        for (int i = 0; i < tamano; i++) {
            if (coleccionMatriculas[i].getAlumno().equals(alumno)) {
                matriculasAlumno[j] = new Matricula(coleccionMatriculas[i]);
                j++;
            }
        }
        Matricula[] matriculaAlumno = new Matricula[j];
        for (int i = 0; i < j; i++) {
            matriculaAlumno[i] = matriculasAlumno[i];
        }
        return matriculaAlumno;
    }
    //2-Devuelve colección de matriculas de curso académico
    public Matricula[] get(String cursoAcademico) throws OperationNotSupportedException {
        if (cursoAcademico == null) {
            throw new NullPointerException("ERROR: El curso académico no puede ser nulo ni vacío.");
        }
        if (cursoAcademico.isBlank()) {
            throw new IllegalArgumentException("ERROR: El curso académico no puede ser nulo ni vacío.");
        }
        if (cursoAcademico.isEmpty()) {
            throw new IllegalArgumentException("ERROR: El curso académico no puede ser nulo ni vacío.");
        }
        Matricula[] matriculasCursoAcademico = new Matricula[tamano];
        int j = 0;
        for (int i = 0; i < tamano; i++) {
            if (coleccionMatriculas[i].getCursoAcademico().equals(cursoAcademico)) {
                matriculasCursoAcademico[j] = new Matricula(coleccionMatriculas[i]);
                j++;
            }
        }
        Matricula[] matriculaCursoAcademico = new Matricula[j];
        for (int i = 0; i < j; i++) {
            matriculaCursoAcademico[i] = matriculasCursoAcademico[i];
        }
        return matriculaCursoAcademico;
    }
    //2-Devuelve colección de matriculas de ciclo formativo
    public Matricula[] get(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (cicloFormativo == null) {
            throw new NullPointerException("ERROR: El ciclo formativo no puede ser nulo.");
        }
        Matricula[] matriculasCicloFormativo = new Matricula[tamano];
        int j = 0;
        for (int i = 0; i < tamano; i++) {
            for (Asignatura asignatura : coleccionMatriculas[i].getColeccionAsignaturas()) {
                if (asignatura != null && asignatura.getCicloFormativo().equals(cicloFormativo)) {
                    matriculasCicloFormativo[j] = new Matricula(coleccionMatriculas[i]);
                    j++;
                    break;
                }
            }
        }
        Matricula[] matriculaCicloFormativo = new Matricula[j];
        for (int i = 0; i < j; i++) {
            matriculaCicloFormativo[i] = matriculasCicloFormativo[i];
        }
        return matriculaCicloFormativo;
    }
}
