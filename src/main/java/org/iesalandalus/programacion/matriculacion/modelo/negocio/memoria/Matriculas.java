package org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IMatriculas;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;

/**
 * Clase que gestiona una colección de matrículas.
 * Permite insertar, buscar, borrar y obtener información sobre las matrículas,
 * así como filtrarlas por alumno, curso académico o ciclo formativo.
 */
public class Matriculas implements IMatriculas {

    private ArrayList<Matricula> coleccionMatriculas;

    /**
     * Constructor de la clase Matriculas.
     * Inicializa la colección con la capacidad.
     */
    public Matriculas() {
        this.coleccionMatriculas= new ArrayList<>();
    }

    /**
     * Obtiene una copia profunda de la colección de matrículas.
     *
     * @return Un arreglo con una copia de las matrículas.
     * @throws OperationNotSupportedException Si no se puede copiar la colección.
     */
    public ArrayList<Matricula> get() throws OperationNotSupportedException {
        return copiaProfundaMatriculas();
    }

    /**
     * Realiza una copia profunda de las matrículas almacenadas en la colección.
     *
     * @return Un arreglo con las matrículas copiadas.
     * @throws OperationNotSupportedException Si no se puede copiar la colección.
     */
    private ArrayList<Matricula> copiaProfundaMatriculas() throws OperationNotSupportedException {
        ArrayList<Matricula> copiaMatriculas= new ArrayList<>();
        for (Matricula m : coleccionMatriculas) {
            copiaMatriculas.add((new Matricula(m)));
        }
        return copiaMatriculas;
    }

    @Override
    public void comenzar() {
    }

    @Override
    public void terminar() {
    }

    /**
     * Obtiene el tamaño actual de la colección (número de matrículas almacenadas).
     *
     * @return El tamaño actual de la colección.
     */
    public int getTamano() {
        return this.coleccionMatriculas.size();
    }

    /**
     * Inserta una matrícula en la colección.
     *
     * @param matricula La matrícula a insertar.
     * @throws OperationNotSupportedException Si no se pueden insertar más matrículas
     *         o si ya existe una matrícula con el mismo identificador.
     * @throws NullPointerException Si la matrícula es nula.
     */
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

    /**
     * Busca una matrícula en la colección.
     *
     * @param matricula La matrícula a buscar.
     * @return La matrícula encontrada o null si no se encuentra.
     * @throws OperationNotSupportedException Si no se puede buscar la matrícula.
     * @throws NullPointerException Si la matrícula es nula.
     */
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

    /**
     * Borra una matrícula de la colección.
     *
     * @param matricula La matrícula a borrar.
     * @throws OperationNotSupportedException Si no existe la matrícula a borrar.
     * @throws NullPointerException Si la matrícula es nula.
     */
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

    /**
     * Obtiene las matrículas asociadas a un alumno específico.
     *
     * @param alumno El alumno del cual obtener las matrículas.
     * @return Un array con las matrículas del alumno.
     * @throws OperationNotSupportedException Si no se pueden obtener las matrículas.
     */
    public ArrayList<Matricula> get(Alumno alumno) throws OperationNotSupportedException {
        ArrayList<Matricula> aux = new ArrayList<>();
        for (Matricula matricula : coleccionMatriculas) {
            if (matricula !=null && matricula.getAlumno().equals(alumno)){
                aux.add(new Matricula(matricula));
            }
        }
        return aux;
    }

    /**
     * Obtiene las matrículas asociadas a un curso académico específico.
     *
     * @param cursoAcademico El curso académico del cual obtener las matrículas.
     * @return Un array con las matrículas del curso académico.
     * @throws OperationNotSupportedException Si no se pueden obtener las matrículas.
     */
    public ArrayList<Matricula> get(String cursoAcademico) throws OperationNotSupportedException {
        ArrayList<Matricula> aux = new ArrayList<>();
        for (Matricula matricula : coleccionMatriculas) {
            if (matricula !=null && matricula.getCursoAcademico().equals(cursoAcademico)){
                aux.add(new Matricula(matricula));
            }
        }
        return aux;
    }

    /**
     * Obtiene las matrículas asociadas a un ciclo formativo específico.
     *
     * @param cicloFormativo El ciclo formativo del cual obtener las matrículas.
     * @return Un arreglo con las matrículas del ciclo formativo.
     * @throws OperationNotSupportedException Si no se pueden obtener las matrículas.
     */
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
