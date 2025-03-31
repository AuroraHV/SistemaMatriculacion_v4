package org.iesalandalus.programacion.matriculacion.modelo.dominio;

import java.util.Objects;

/**
 * Representa una asignatura dentro del sistema de matriculación.
 * Cada asignatura tiene un código único, nombre, número de horas anuales,
 * curso al que pertenece, número de horas de desdoble, especialidad del profesorado
 * y un ciclo formativo asociado.
 */
public class Asignatura {
    //Constantes
    public static final int MAX_NUM_HORAS_ANUALES = 300;
    public static final int MAX_NUM_HORAS_DESDOBLES = 6;
    private static final String ER_CODIGO = "[0-9]{4}";
    //Atributos
    private String codigo;
    private String nombre;
    private int horasAnuales;
    private Curso curso;
    private int horasDesdoble;
    private EspecialidadProfesorado especialidadProfesorado;
    private CicloFormativo cicloFormativo;

    //Constructor con parámetros
    public Asignatura(String codigo, String nombre, int horasAnuales, Curso curso, int horasDesdoble, EspecialidadProfesorado especialidadProfesorado, CicloFormativo cicloFormativo) {
        setCodigo(codigo);
        setNombre(nombre);
        setHorasAnuales(horasAnuales);
        setCurso(curso);
        setHorasDesdoble(horasDesdoble);
        setEspecialidadProfesorado(especialidadProfesorado);
        setCicloFormativo(cicloFormativo);
    }

    //Constructor copia
    public Asignatura(Asignatura asignatura) {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No es posible copiar una asignatura nula.");
        }
        setCodigo(asignatura.getCodigo());
        setNombre(asignatura.getNombre());
        setHorasAnuales(asignatura.getHorasAnuales());
        setCurso(asignatura.getCurso());
        setHorasDesdoble(asignatura.getHorasDesdoble());
        setEspecialidadProfesorado(asignatura.getEspecialidadProfesorado());
        setCicloFormativo(asignatura.getCicloFormativo());
    }

    //Métodos de acceso y modificación
    public CicloFormativo getCicloFormativo() {
        return new CicloFormativo (cicloFormativo);
    }
    public void setCicloFormativo(CicloFormativo cicloFormativo) {
        if (cicloFormativo == null) {
            throw new NullPointerException("ERROR: El ciclo formativo de una asignatura no puede ser nulo.");
        }
        this.cicloFormativo = cicloFormativo;
    }

    public String getCodigo() {
        return codigo;
    }
    private void setCodigo(String codigo) {
        if (codigo == null) {
            throw new NullPointerException("ERROR: El código de una asignatura no puede ser nulo.");
        }
        if (codigo.isBlank()) {
            throw new IllegalArgumentException("ERROR: El código de una asignatura no puede estar vacío.");
        }
        if (codigo.isEmpty()) {
            throw new IllegalArgumentException("ERROR: El código de una asignatura no puede estar vacío.");
        }
        if (!codigo.matches(ER_CODIGO)) {
            throw new IllegalArgumentException("ERROR: El código de la asignatura no tiene un formato válido.");
        }
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        if (nombre == null) {
            throw new NullPointerException("ERROR: El nombre de una asignatura no puede ser nulo.");
        }
        if (nombre.isBlank()) {
            throw new IllegalArgumentException("ERROR: El nombre de una asignatura no puede estar vacío.");
        }
        if (nombre.isEmpty()) {
            throw new IllegalArgumentException("ERROR: El nombre de la asignatura no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public int getHorasAnuales() {
        return horasAnuales;
    }

    public void setHorasAnuales(int horasAnuales) {
        if (horasAnuales <= 0 || horasAnuales > MAX_NUM_HORAS_ANUALES) {
            throw new IllegalArgumentException("ERROR: El número de horas de una asignatura no puede ser menor o igual a 0 ni mayor a 300.");
        }
        this.horasAnuales = horasAnuales;
    }

    public Curso getCurso() {
        return curso;
    }
    public void setCurso(Curso curso) {
        if (curso == null) {
            throw new NullPointerException("ERROR: El curso de una asignatura no puede ser nulo.");
        }
        this.curso = curso;
    }

    public int getHorasDesdoble() {
        return horasDesdoble;
    }
    public void setHorasDesdoble(int horasDesdoble) {
        if (horasDesdoble < 0 || horasDesdoble > MAX_NUM_HORAS_DESDOBLES) {
            throw new IllegalArgumentException("ERROR: El número de horas de desdoble de una asignatura no puede ser menor a 0 ni mayor a 6.");
        }
        this.horasDesdoble = horasDesdoble;
    }

    public EspecialidadProfesorado getEspecialidadProfesorado() {
        return especialidadProfesorado;
    }
    public void setEspecialidadProfesorado(EspecialidadProfesorado especialidadProfesorado) {
        if (especialidadProfesorado == null) {
            throw new NullPointerException("ERROR: La especialidad del profesorado de una asignatura no puede ser nula.");
        }
        this.especialidadProfesorado = especialidadProfesorado;
    }

    //Métodos equals y hashCode
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Asignatura that = (Asignatura) obj;
        return Objects.equals(codigo, that.codigo);
    }
    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    //Imprimir
    public String imprimir(){
        return String.format("Código asignatura=%s, nombre asignatura=%s, ciclo formativo=%s", this.getCodigo(), this.getNombre(), this.getCicloFormativo().imprimir());
    }
    //ToString
    @Override
    public String toString() {
        return String.format("Código=%s, nombre=%s, horas anuales=%d, curso=%s, horas desdoble=%d, ciclo formativo=%s, especialidad profesorado=%s", this.getCodigo(), this.getNombre(),this.getHorasAnuales(),
                this.getCurso(),this.getHorasDesdoble(), this.getCicloFormativo().imprimir(),this.getEspecialidadProfesorado());
    }
}

