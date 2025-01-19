package org.iesalandalus.programacion.matriculacion.dominio;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;

//1-Clase matrícula
public class Matricula {
    //Constantes
    public static final int MAXIMO_MESES_ANTERIOR_ANULACION = 6;
    public static final int MAXIMO_DIAS_ANTERIOR_MATRICULA = 15;
    public static final int MAXIMO_NUMERO_HORAS_MATRICULA = 1000;
    public static final int MAXIMO_NUMERO_ASIGNATURAS_POR_MATRICULA = 10;
    public static final String ER_CURSO_ACADEMICO = "\\d{2}-\\d{2}";
    public static final String FORMATO_FECHA = "dd/MM/yyyy";
    //Atributos
    private int idMatricula;
    private String cursoAcademico;
    private LocalDate fechaMatriculacion;
    private LocalDate fechaAnulacion;
    private Alumno alumno;
    private Asignatura[] coleccionAsignaturas;

    //4-Constructor con parámetros
    public Matricula (int idMatricula, String cursoAcademico, LocalDate fechaMatriculacion, Alumno alumno, Asignatura[] coleccionAsignaturas) throws OperationNotSupportedException {
        setIdMatricula(idMatricula);
        setCursoAcademico(cursoAcademico);
        setFechaMatriculacion(fechaMatriculacion);
        setAlumno(alumno);
        setColeccionAsignaturas(coleccionAsignaturas);
    }

    //5-Constructor copia
    public Matricula(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null) {
            throw new NullPointerException("ERROR: No es posible copiar una matrícula nula.");
        }
        setIdMatricula(matricula.getIdMatricula());
        setCursoAcademico(matricula.getCursoAcademico());
        setFechaMatriculacion(matricula.getFechaMatriculacion());
        setFechaAnulacion(matricula.getFechaAnulacion());
        setAlumno(matricula.getAlumno());
        setColeccionAsignaturas(matricula.getColeccionAsignaturas());
    }

    //2-Métodos de acceso y modificación
    public int getIdMatricula() {
        return idMatricula;
    }
    public void setIdMatricula(int idMatricula) {
        if (idMatricula <= 0) {
            throw new IllegalArgumentException("ERROR: El identificador de una matrícula no puede ser menor o igual a 0.");
        }
        this.idMatricula = idMatricula;
    }

    public String getCursoAcademico() {
        return cursoAcademico;
    }
    public void setCursoAcademico(String cursoAcademico) {
        if (cursoAcademico == null) {
            throw new NullPointerException("ERROR: El curso académico de una matrícula no puede ser nulo.");
        }
        if (cursoAcademico.isEmpty()) {
            throw new IllegalArgumentException("ERROR: El curso académico de una matrícula no puede estar vacío.");
        }
        if (cursoAcademico.isBlank()) {
            throw new IllegalArgumentException("ERROR: El curso académico de una matrícula no puede estar vacío.");
        }
        if (!cursoAcademico.matches(ER_CURSO_ACADEMICO)) {
            throw new IllegalArgumentException("ERROR: El formato del curso académico no es correcto.");
        }

        this.cursoAcademico = cursoAcademico;
    }

    public LocalDate getFechaMatriculacion() {
        return fechaMatriculacion;
    }
    public void setFechaMatriculacion(LocalDate fechaMatriculacion) {
        if (fechaMatriculacion == null) {
            throw new NullPointerException("ERROR: La fecha de matriculación de una mátricula no puede ser nula.");
        }
        if (fechaMatriculacion.isBefore(LocalDate.now().minusDays(MAXIMO_DIAS_ANTERIOR_MATRICULA))) {
            throw new IllegalArgumentException("ERROR: La fecha de matriculación no puede ser anterior a 15 días.");
        }
        if (fechaMatriculacion.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("ERROR: La fecha de matriculación no puede ser posterior a hoy.");
        }
        this.fechaMatriculacion = fechaMatriculacion;
    }

    public LocalDate getFechaAnulacion() {
        return fechaAnulacion;
    }
    public void setFechaAnulacion(LocalDate fechaAnulacion) {
        if (fechaAnulacion != null) {
            if (fechaAnulacion.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("ERROR: La fecha de anulación de una matrícula no puede ser posterior a hoy.");
            }
            if (fechaAnulacion.isBefore(fechaMatriculacion)) {
                throw new IllegalArgumentException("ERROR: La fecha de anulación no puede ser anterior a la fecha de matriculación.");
            }
            if (fechaAnulacion.isBefore(fechaMatriculacion.minusMonths(MAXIMO_MESES_ANTERIOR_ANULACION))) {
                throw new IllegalArgumentException("ERROR: La fecha de anulación no puede ser anterior a " + MAXIMO_MESES_ANTERIOR_ANULACION + " meses.");
            }
        }
        this.fechaAnulacion = fechaAnulacion;
    }

    public Alumno getAlumno() {
        return new Alumno (alumno);
    }
    public void setAlumno(Alumno alumno) {
        if (alumno == null) {
            throw new NullPointerException("ERROR: El alumno de una matrícula no puede ser nulo.");
        }
        this.alumno = alumno;
    }

    public Asignatura[] getColeccionAsignaturas() {
        return Arrays.copyOf(coleccionAsignaturas, coleccionAsignaturas.length);
    }
    public void setColeccionAsignaturas(Asignatura[] coleccionAsignaturas) throws OperationNotSupportedException {
        if (coleccionAsignaturas == null) {
            throw new NullPointerException("ERROR: La lista de asignaturas de una matrícula no puede ser nula.");
        }
        if (coleccionAsignaturas.length > MAXIMO_NUMERO_ASIGNATURAS_POR_MATRICULA) {
            throw new OperationNotSupportedException("ERROR: No se pueden superar las 10 asignaturas por matrícula.");
        }
        if (superaMaximoNumeroHorasMatricula(coleccionAsignaturas)) {
            throw new OperationNotSupportedException("ERROR: No se puede realizar la matrícula ya que supera el máximo de horas permitidas (" + MAXIMO_NUMERO_HORAS_MATRICULA + " horas).");
        }
        this.coleccionAsignaturas = Arrays.copyOf(coleccionAsignaturas, coleccionAsignaturas.length);
    }

    //3-Máximo horas de asignaturas de una matricula
    private boolean superaMaximoNumeroHorasMatricula(Asignatura[] asignaturasMatricula) {
        int totalHoras = 0;
        for (Asignatura asignatura : asignaturasMatricula) {
            if (asignatura != null) {
                totalHoras = totalHoras + asignatura.getHorasAnuales() + asignatura.getHorasDesdoble();
                if (totalHoras > MAXIMO_NUMERO_HORAS_MATRICULA) {
                    return true;
                }
            }
        }
        return false;
    }

    //5-Métodos equals y hashCode
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Matricula matricula = (Matricula) obj;
        return idMatricula == matricula.idMatricula;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMatricula);
    }
    //7-Cadena asignaturas de la matricula
    private String asignaturasMatricula(){
        StringBuilder resultado= new StringBuilder();
        for(Asignatura asignatura : coleccionAsignaturas)
        {
            if (asignatura!=null)
                resultado.append(asignatura.imprimir());
        }
        return resultado.toString();
    }

    //8-Imprimir
    public String imprimir(){
        return String.format("idMatricula=%d, curso académico=%s, fecha matriculación=%s, alumno={%s}",
                this.getIdMatricula(), this.getCursoAcademico(),
                this.getFechaMatriculacion().format(DateTimeFormatter.ofPattern(Matricula.FORMATO_FECHA)),
                this.getAlumno().imprimir());
    }

    //7-ToString
    @Override
    public String toString() {
        if (fechaAnulacion == null) {
            return "idMatricula=" + idMatricula +
                    ", curso académico=" + cursoAcademico +
                    ", fecha matriculación=" + fechaMatriculacion.format(DateTimeFormatter.ofPattern(FORMATO_FECHA)) +
                    ", alumno=" + alumno.imprimir() +
                    ", Asignaturas={ " + asignaturasMatricula() + "}";
        } else {
            return "idMatricula=" + idMatricula +
                    ", curso académico=" + cursoAcademico +
                    ", fecha matriculación=" + fechaMatriculacion.format(DateTimeFormatter.ofPattern(FORMATO_FECHA)) +
                    ", fecha anulación=" + fechaAnulacion.format(DateTimeFormatter.ofPattern(FORMATO_FECHA)) +
                    ", alumno=" + alumno.imprimir() +
                    ", Asignaturas={ " + asignaturasMatricula() + "}";
        }
    }
}
