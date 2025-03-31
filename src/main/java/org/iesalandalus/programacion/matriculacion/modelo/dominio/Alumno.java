package org.iesalandalus.programacion.matriculacion.modelo.dominio; //1-paquete

import java.time.LocalDate;
import java.util.Objects;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase que representa un alumno con atributos como nombre, DNI, correo,
 * teléfono, fecha de nacimiento y un identificador único (NIA).
 *
 * Permite validar los datos y realizar operaciones comunes sobre un alumno.
 */
public class Alumno {
    //Constantes (valores fijos para validaciones o formatos
    private static final String ER_TELEFONO = "[976][0-9]{8}"; //5-Teléfono fijo o móvil
    private static final String ER_CORREO = "\\w+[\\.\\w]*@\\w+[\\.\\w]*\\.\\w{2,5}\\b\\s?";
    private static final String ER_DNI = "([0-9]{8})([A-Za-z])"; //5-Correo con formato
    public static final String FORMATO_FECHA = "dd/MM/yyyy"; //2-Formato fecha dd/MM/YYYY
    private static final String ER_NIA = "[a-zéáíóú]{4}[0-9]{3}"; //5-4 del nombre en minúscula y 3 últimos del DNI
    private static final int MIN_EDAD_ALUMNADO = 16; //6-Edad mínima 16 años

    //Atributos (datos alumno)
    private String nombre;
    private String telefono;
    private String correo;
    private String dni;
    private LocalDate fechaNacimiento;
    private String nia;

    //Constructor con parámetros (crea un alummno con todos sus datos)
    public Alumno(String nombre, String dni, String correo, String telefono, LocalDate fechaNacimiento){
        //Asignación a través de setters para validar los datos
        setDni(dni);
        setNombre(nombre);
        setCorreo(correo);
        setTelefono(telefono);
        setFechaNacimiento(fechaNacimiento);
    }

    //Constructor copia con parámetros (crea un alummno copiando otro)
    public Alumno(Alumno alumno) {
        if(alumno == null) {
            throw new NullPointerException("ERROR: No es posible copiar un alumno nulo.");
        }
        //Asignación de valores a través de getters (que devuelven el valor actual de cada uno) copiando los datos validados
        setDni(alumno.getDni());
        setNombre(alumno.getNombre());
        setCorreo(alumno.getCorreo());
        setTelefono(alumno.getTelefono());
        setFechaNacimiento(alumno.getFechaNacimiento());
    }

    //Métodos de acceso y modificación (getters y setters)

        //Nia
    public String getNia() {
        return nia;
    }
    // Primer setNia (calcula automáticamente el NIA basado en el nombre y el DNI)
    private void setNia() {
        if (nombre != null && dni != null) {
            String nombreNormalizado = nombre.toLowerCase().replaceAll(" ", "");
            if (nombreNormalizado.length() < 4) {
                throw new IllegalArgumentException("ERROR: El nombre debe tener al menos 4 caracteres para generar el NIA.");
            }
            String primeraParte = nombre.toLowerCase().replaceAll(" ", "").substring(0, 4); // Toma las 4 primeras letras del nombre
            String ultimaParte = dni.substring(5,8); // Toma los últimos 3 dígitos del DNI
            nia = primeraParte + ultimaParte; // Combina ambas partes para formar el NIA
            if (!nia.matches(ER_NIA)) {
                throw new IllegalArgumentException("ERROR: El NIA generado no tiene un formato válido."); // Error si el NIA no cumple el formato
            }
            setNia(nia);
        } else {
            throw new NullPointerException("ERROR: No se puede generar el NIA sin nombre y DNI."); // Error si faltan nombre o DNI
        }
            }
    // Segundo setNia (permite asignar manualmente un NIA validado)
    private void setNia(String nia) {
        if (nia == null) {
            throw new NullPointerException("ERROR: El NIA no puede ser nulo."); // Error si el NIA es nulo
        }
        if (nia.isBlank()) {
            throw new IllegalArgumentException("ERROR: El NIA no puede ser nulo."); // Error si el NIA es nulo
        }
        if (nia.isEmpty()) {
            throw new IllegalArgumentException("ERROR: El NIA no puede estar vacío."); // Error si el nombre está vacío
        }
        if (!nia.matches(ER_NIA)) {
            throw new IllegalArgumentException("ERROR: El NIA no tiene un formato válido."); // Error si el NIA no cumple el formato
        }
        this.nia = nia; // Asigna el NIA tras validarlo
    }

        //Nombre
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        if (nombre == null) {
            throw new NullPointerException("ERROR: El nombre de un alumno no puede ser nulo."); // Error si el nombre es nulo
        }
        if (nombre.isEmpty()) {
            throw new IllegalArgumentException("ERROR: El nombre de un alumno no puede estar vacío."); // Error si el nombre está vacío
        }
        if (nombre.isBlank()) {
            throw new IllegalArgumentException("ERROR: El nombre de un alumno no puede estar vacío."); // Error si el nombre está vacío
        }
        this.nombre = formateaNombre(nombre); // Normaliza el formato del nombre
        setNia();
    }
    // Normaliza el formato del nombre
    private String formateaNombre(String nombre){
        if (nombre == null) {
            throw new NullPointerException("ERROR: El nombre de un alumno no puede ser nulo.");
        }
        if (nombre.isBlank()) {
            throw new IllegalArgumentException("ERROR: El nombre no puede estar en blanco.");
        }
        if (nombre.isEmpty()) {
            throw new IllegalArgumentException("ERROR: El nombre no puede estar vacío.");
        }
        String[] palabras = nombre.trim().toLowerCase().split("\\s+"); // Divide el nombre en palabras
        StringBuilder nombreFormateado = new StringBuilder();
        for (String palabra : palabras) {
            nombreFormateado.append(Character.toUpperCase(palabra.charAt(0))) // Capitaliza la primera letra
                    .append(palabra.substring(1)) // Minúsculas para el resto
                    .append(" ");
        }
        return nombreFormateado.toString().trim(); // Devuelve el nombre formateado
    }

        //Teléfono
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        if (telefono == null)
            throw new NullPointerException("ERROR: El teléfono de un alumno no puede ser nulo.");
        if (telefono.isBlank()) {
            throw new IllegalArgumentException("ERROR: El teléfono del alumno no tiene un formato válido.");
        }
        if (telefono.isEmpty()) {
            throw new IllegalArgumentException("ERROR: El teléfono del alumno no puede estar vacío.");
        }
        if(!telefono.matches(ER_TELEFONO))
            throw new IllegalArgumentException("ERROR: El teléfono del alumno no tiene un formato válido.");
        this.telefono = telefono;
    }

        //Correo
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        if(correo==null)
            throw new NullPointerException("ERROR: El correo de un alumno no puede ser nulo.");

        if (correo.isEmpty())
            throw new IllegalArgumentException("ERROR: El correo del alumno no tiene un formato válido.");

        if (correo.isBlank())
            throw new IllegalArgumentException("ERROR: El correo del alumno no tiene un formato válido.");

        if(!correo.matches(ER_CORREO))
            throw new IllegalArgumentException("ERROR: El correo del alumno no tiene un formato válido.");
        this.correo = correo;
    }
        //DNI
    public String getDni() {
        return dni;
    }
    private void setDni(String dni) {
        if(dni==null)
            throw new NullPointerException("ERROR: El dni de un alumno no puede ser nulo.");
        if(dni.isBlank())
            throw new IllegalArgumentException("ERROR: El dni del alumno no tiene un formato válido.");
        if(dni.isEmpty())
            throw new IllegalArgumentException("ERROR: El dni del alumno no tiene un formato válido.");
        dni = dni.toUpperCase();
        if (!dni.matches(ER_DNI))
            throw new IllegalArgumentException("ERROR: El dni del alumno no tiene un formato válido.");
        if (!comprobarLetraDni(dni)) {
            throw new IllegalArgumentException("ERROR: La letra del dni del alumno no es correcta.");
        }
        this.dni = dni;
         // Recalcula automáticamente el NIA tras cambiar el DNI
    }
    //Comprobar letra DNI
    private boolean comprobarLetraDni(String dni) {
        if(dni==null)
            throw new NullPointerException("ERROR: El DNI no puede ser nulo.");
        if(dni.isBlank())
            throw new IllegalArgumentException("ERROR: El DNI no puede estar en blanco.");
        if(dni.isEmpty())
            throw new IllegalArgumentException("ERROR: El DNI no puede estra vacío.");
        if (!dni.matches(ER_DNI))
            throw new IllegalArgumentException("ERROR: El DNI no tiene un formato válido.");
        // Crear el patrón para validar el formato del DNI
        Pattern patron = Pattern.compile(ER_DNI);
        Matcher comparador = patron.matcher(dni);
        // Validar que el DNI coincida con el patrón
        if (!comparador.matches()) {
            throw new IllegalArgumentException("ERROR: El formato del DNI no es válido.");
        }
        try {
            // Extraer el número del DNI
            int numeroDni = Integer.parseInt(comparador.group(1)); // Grupo 1: Los primeros 8 dígitos
            char letra = comparador.group(2).charAt(0); // Grupo 2: La letra
            final String LETRAS_TABLA= "TRWAGMYFPDXBNJZSQVHLCKE"; // Tabla de letras para el DNI
            char letraCorrecta = LETRAS_TABLA.charAt(numeroDni % 23); //Calcular la letra del DNI para comparación
            return letra == letraCorrecta;
        }catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("ERROR: Los datos del DNI son inconsistentes o incompletos.", e);
        }
    }

        //Fecha de nacimiento (validar mayor de 16 años)
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    private void setFechaNacimiento(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            throw new NullPointerException("ERROR: La fecha de nacimiento de un alumno no puede ser nula."); // Error si la fecha es nula
        }
        LocalDate fechaActual = LocalDate.now(); // Obtiene la fecha actual
        // Validar que la fecha no sea posterior a hoy
        if (fechaNacimiento.isAfter(fechaActual)) {
            throw new IllegalArgumentException("ERROR: La fecha de nacimiento no puede ser posterior a hoy."); // Error si la fecha es futura
        }
        // Calcular la edad en base al año de nacimiento y el año actual
        int edad = fechaActual.getYear() - fechaNacimiento.getYear();
        // Si aun no ha cumplido ese año, restar 1 a la edad
        if (fechaNacimiento.plusYears(edad).isAfter(fechaActual)) {
            edad--; // Ajustar la edad si aún no ha cumplido años
        }
        // Validar que cumple con la edad mínima
        if (edad < MIN_EDAD_ALUMNADO) {
            throw new IllegalArgumentException("ERROR: La edad del alumno debe ser mayor o igual a " + MIN_EDAD_ALUMNADO + " años."); // Error si no cumple la edad mínima
        }
        this.fechaNacimiento = fechaNacimiento;
    }

    //Iniciales nombre
    public String getIniciales() {
        StringBuilder iniciales = new StringBuilder();
        for (String palabra : nombre.trim().split("\\s+")) {
            iniciales.append(palabra.charAt(0)); // Toma la primera letra de cada palabra
        }
        return iniciales.toString().toUpperCase(); // Devuelve las iniciales en mayúsculas
    }

    //Equals para comparar si dos alumnos son iguales, si tienen el mismo DNI
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Alumno alumno)) return false;
        return Objects.equals(dni, alumno.dni);
    }
    //HashCode para optimizar la búsqueda y organización, en base al dni
    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }


    public String imprimir() {
        return "Alumno=" +
                "nombre=" + getNombre() + " " + "(" + getIniciales() + ")" +
                ", DNI=" + getDni() +
                ", correo=" + getCorreo() +
                ", teléfono=" + getTelefono() +
                ", fechaNacimiento=" + fechaNacimiento.format(DateTimeFormatter.ofPattern(FORMATO_FECHA)) +
                ", NIA=" + nia;
    }

    //Representación en texto para mostrar los atributos especificados en un sout (que devuelvan la cadena)
    @Override
    public String toString() {
        return "Número de Identificación del Alumnado " +
                "(NIA)=" + getNia() + " nombre=" + getNombre() + " (" + getIniciales() + ")" +
                ", DNI=" + getDni() + ", correo=" + getCorreo() + ", teléfono=" + getTelefono() +
                ", fecha nacimiento=" + fechaNacimiento.format(DateTimeFormatter.ofPattern(FORMATO_FECHA));
    }
}






