package org.iesalandalus.programacion.matriculacion.negocio;

import org.iesalandalus.programacion.matriculacion.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.dominio.Matricula;
import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;


public class Matriculas{
    //1-Atributos
    private ArrayList<Matricula> coleccionMatriculas;

    //1.1-Constructor con lista de capacidad e inicializa atributos
    public Matriculas() {
        this.coleccionMatriculas= new ArrayList<>();
    }

    //Métodos
    //1.2-Get que devuelve copia profunda
    public ArrayList<Matricula> get() throws OperationNotSupportedException {
        return copiaProfundaMatriculas();
    }
    //Copia profunda de la colección
    private ArrayList<Matricula> copiaProfundaMatriculas() throws OperationNotSupportedException {
        ArrayList<Matricula> copiaMatriculas= new ArrayList<>();
        for (Matricula m : coleccionMatriculas) {
            copiaMatriculas.add((new Matricula(m)));
        }
        return copiaMatriculas;
    }
    public int getTamano() {
        return this.coleccionMatriculas.size();
    }

    //1.3-Insertar matrícula al final de la colección mientras no esté repetido o sea nulo
    public void insertar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null) {
            throw new NullPointerException("ERROR: No se puede insertar una matrícula nula.");
        }
        int indice = this.coleccionMatriculas.indexOf(matricula);
        if (indice == -1) {
            this.coleccionMatriculas.add(new Matricula(matricula));
        } else {
            throw new OperationNotSupportedException("ERROR: Ya existe una matrícula con ese identificador.");
        }
    }

    //1.4-Buscar una matrícula y devolverla si está en la colección, en caso contrario será null
    public Matricula buscar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null) {
            throw new NullPointerException("ERROR: No se puede buscar una matrícula nula.");
        }
        int indice = this.coleccionMatriculas.indexOf(matricula);
        if (indice == -1) {
            return null;
        } else {
            return new Matricula(this.coleccionMatriculas.get(indice));
        }
    }

    //1.5-Si un alumno se encuentra en la colección, se borra
    public void borrar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null) {
            throw new NullPointerException("ERROR: No se puede borrar una matrícula nula.");
        }
        int indice = this.coleccionMatriculas.indexOf(matricula);
        if (indice == -1) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna matrícula como la indicada.");
        }
        this.coleccionMatriculas.remove(indice);
    }

    //2-Devuelve colección de matriculas de un alumno
    public ArrayList<Matricula> get(Alumno alumno) throws OperationNotSupportedException {
        ArrayList<Matricula> aux = new ArrayList<>();
        for (Matricula matricula : coleccionMatriculas) {
            if (matricula !=null && matricula.getAlumno().equals(alumno)){
                aux.add(new Matricula(matricula));
            }
        }
        return aux;
    }

    //2-Devuelve colección de matriculas de curso académico
    public ArrayList<Matricula> get(String cursoAcademico) throws OperationNotSupportedException {
        ArrayList<Matricula> aux = new ArrayList<>();
        for (Matricula matricula : coleccionMatriculas) {
            if (matricula !=null && matricula.getCursoAcademico().equals(cursoAcademico)){
                aux.add(new Matricula(matricula));
            }
        }

        return aux;
    }
    //2-Devuelve colección de matriculas de ciclo formativo
    public ArrayList<Matricula> get(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        ArrayList<Matricula> aux = new ArrayList<>();
        for(Matricula matricula: coleccionMatriculas){
            if (matricula != null){
                for (Asignatura asignatura : matricula.getColeccionAsignaturas()) {
                    if (asignatura != null && asignatura.getCicloFormativo().equals(cicloFormativo)) {
                        aux.add(new Matricula(matricula));
                        break;
                    }
                }
            }
        }
        return aux;
    }
}
