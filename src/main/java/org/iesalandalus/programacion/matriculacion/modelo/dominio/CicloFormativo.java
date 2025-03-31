package org.iesalandalus.programacion.matriculacion.modelo.dominio;

import java.util.Objects;

/**
 * Clase que representa un Ciclo Formativo con su información básica como código,
 * familia profesional, grado, nombre y horas.
 */
public class CicloFormativo {
    //Constantes
    public static final int MAXIMO_NUMERO_HORAS = 2000;
    //Atributos
    private int codigo;
    private String familiaProfesional;
    private Grado grado;
    private String nombre;
    private int horas;

    //Constructor con parámetros
    public CicloFormativo(int codigo, String familiaProfesional, Grado grado, String nombre, int horas) {
        setCodigo(codigo);
        setFamiliaProfesional(familiaProfesional);
        setGrado(grado);
        setNombre(nombre);
        setHoras(horas);
    }

    //Constructor copia
    public CicloFormativo(CicloFormativo cicloFormativo) {
        if (cicloFormativo == null) {
            throw new NullPointerException("ERROR: No es posible copiar un ciclo formativo nulo.");
        }
        setCodigo(cicloFormativo.getCodigo());
        setFamiliaProfesional(cicloFormativo.getFamiliaProfesional());
        setGrado(cicloFormativo.getGrado());
        setNombre(cicloFormativo.getNombre());
        setHoras(cicloFormativo.getHoras());
    }
    //Métodos de acceso y modificación
    public int getCodigo() {
        return codigo;
    }
    private void setCodigo(int codigo) {
        if (codigo < 1000 || codigo > 9999) {
            throw new IllegalArgumentException("ERROR: El código del ciclo formativo debe ser un número de cuatro dígitos.");
        }
        this.codigo = codigo;
    }

    public String getFamiliaProfesional() {
        return familiaProfesional;
    }
    public void setFamiliaProfesional(String familiaProfesional) {
        if (familiaProfesional == null) {
            throw new NullPointerException("ERROR: La familia profesional de un ciclo formativo no puede ser nula.");
        }
        if (familiaProfesional.isBlank()) {
            throw new IllegalArgumentException("ERROR: La familia profesional no puede estar vacía.");
        }
        if (familiaProfesional.isEmpty()) {
            throw new IllegalArgumentException("ERROR: La familia profesional no puede estar vacía.");
        }
        this.familiaProfesional = familiaProfesional;
    }

    public Grado getGrado() {
        return grado;
    }
    public void setGrado(Grado grado) {
        if (grado == null) {
            throw new NullPointerException("ERROR: El grado de un ciclo formativo no puede ser nulo.");
        }
        this.grado = grado;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        if (nombre == null) {
            throw new NullPointerException("ERROR: El nombre de un ciclo formativo no puede ser nulo.");
        }
        if (nombre.isBlank()) {
            throw new IllegalArgumentException("ERROR: El nombre de un ciclo formativo no puede estar vacío.");
        }
        if (nombre.isEmpty()) {
            throw new IllegalArgumentException("ERROR: El nombre de un ciclo formativo no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public int getHoras() {
        return horas;
    }
    public void setHoras(int horas) {
        if (horas <= 0 || horas > MAXIMO_NUMERO_HORAS) {
            throw new IllegalArgumentException("ERROR: El número de horas de un ciclo formativo no puede ser menor o igual a 0 ni mayor a 2000.");
        }
        this.horas = horas;
    }

    //Métodos equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CicloFormativo that)) return false;
        return codigo == that.codigo;
    }
    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    //Imprimir
    public String imprimir(){
        return String.format("Código ciclo formativo=%d, nombre ciclo formativo=%s",this.getCodigo(),this.getNombre());
    }

    //ToString
    @Override
    public String toString() {
        return String.format("Código ciclo formativo=%d, familia profesional=%s, grado=%s, nombre ciclo formativo=%s, horas=%s",this.getCodigo(),this.getFamiliaProfesional(),this.getGrado(),this.getNombre(),this.getHoras());
    }
}