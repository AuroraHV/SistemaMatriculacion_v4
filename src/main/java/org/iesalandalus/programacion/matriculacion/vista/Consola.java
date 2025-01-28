package org.iesalandalus.programacion.matriculacion.vista;

import org.iesalandalus.programacion.utilidades.Entrada;
import org.iesalandalus.programacion.matriculacion.dominio.*;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

//1-Clase Consola
public class Consola {

    //2-Constructor de utilidades
    private Consola() {
    }

    //3-Muestra menú de opciones
    public static void mostrarMenu() {
        System.out.println("Opciones del menú:");
        for (Opcion opcion : Opcion.values()) {
            System.out.println(opcion);
        }
    }

    //4-Elige opción
    public static Opcion elegirOpcion() {
        int opcion;
        do {
            System.out.print("Introduce el número de la opción: ");
            opcion = Entrada.entero();
        } while (opcion < 0 || opcion >= Opcion.values().length);
        return Opcion.values()[opcion];
    }

    //ALUMNO
    public static Alumno leerAlumno() {
        final Alumno ALUMNO_GUIA = new Alumno("Carlos", "12345678Z", "carlos.perez@gmail.com", "612345678",
                LocalDate.parse("15/03/1998", DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        String nombre = null;
        String dni = null;
        String correo = null;
        String telefono = null;
        LocalDate fechaNacimiento;

        // Define el formato personalizado para la fecha
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Validación del nombre
        boolean valido = false;
        do {
            System.out.print("Introduce el nombre del alumno: ");
            try {
                nombre = Entrada.cadena();
                if (nombre == null || nombre.isBlank()) {
                    throw new IllegalArgumentException("ERROR: El nombre no puede ser nulo o vacío.");
                }
                Alumno alumnoNombre = new Alumno(nombre,ALUMNO_GUIA.getDni(), ALUMNO_GUIA.getCorreo(), ALUMNO_GUIA.getTelefono(), ALUMNO_GUIA.getFechaNacimiento());
                valido = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!valido);

        // Validación del DNI
        valido = false;
        do {
            System.out.print("Introduce el DNI del alumno: ");
            try {
                dni = Entrada.cadena();
                if (dni == null || dni.isBlank()) {
                    throw new IllegalArgumentException("ERROR: El DNI no puede ser nulo o vacío.");
                }
                Alumno alumnoDni = new Alumno(ALUMNO_GUIA.getNombre(),dni, ALUMNO_GUIA.getCorreo(), ALUMNO_GUIA.getTelefono(), ALUMNO_GUIA.getFechaNacimiento());
                //Alumno alumnoDni = new Alumno("Ficticio", dni, "correo@ficticio.com", "600000000", LocalDate.of(2000, 1, 1));
                valido = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!valido);

        // Validación del correo
        valido = false;
        do {
            System.out.print("Introduce el correo del alumno: ");
            try {
                correo = Entrada.cadena();
                if (correo == null || correo.isBlank()) {
                    throw new IllegalArgumentException("ERROR: El correo no puede ser nulo o vacío.");
                }
                Alumno alumnoCorreo = new Alumno(ALUMNO_GUIA.getNombre(),ALUMNO_GUIA.getDni(),correo, ALUMNO_GUIA.getTelefono(), ALUMNO_GUIA.getFechaNacimiento());
                //Alumno alumnoCorreo = new Alumno("Ficticio", dni ,correo, "600000000", LocalDate.of(2000, 1, 1));

                valido = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!valido);

        // Validación del teléfono
        valido = false;
        do {
            System.out.print("Introduce el teléfono del alumno: ");
            try {
                telefono = Entrada.cadena();
                if (telefono == null || telefono.isBlank()) {
                    throw new IllegalArgumentException("ERROR: El telefono no puede ser nulo o vacío.");
                }
                Alumno alumnoDni = new Alumno(ALUMNO_GUIA.getNombre(),ALUMNO_GUIA.getDni(), ALUMNO_GUIA.getCorreo(), telefono, ALUMNO_GUIA.getFechaNacimiento());
                valido = true; // Si no lanza excepción, la entrada es válida
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!valido);

        // Validación de la fecha de nacimiento
        fechaNacimiento = leerFecha("Introduce la fecha de nacimiento del alumno");

        // Crear y retornar el alumno válido
        return new Alumno(nombre, dni, correo, telefono, fechaNacimiento);
    }

   //6-Obtener alumno por Dni alumno
   public static Alumno getAlumnoPorDni() {
       String dni = null;
       boolean valido = false;
       do {
           System.out.print("Introduce el DNI del alumno: ");
           try {
               dni = Entrada.cadena();

               if (dni == null || dni.isBlank()) {
                   throw new IllegalArgumentException("ERROR: El DNI no puede ser nulo ni estar vacío.");
               }
               // Crear un alumno temporal para validar el DNI
               Alumno alumnoDni = new Alumno("Ficticio", dni, "correo@ficticio.com", "600000000", LocalDate.of(2000, 1, 1));
               valido = true; // Si no lanza excepción, el DNI es válido
           } catch (IllegalArgumentException e) {
               System.out.println(e.getMessage());
           }
       } while (!valido);

       // Retornar un Alumno ficticio con el DNI válido
       return new Alumno("Ficticio", dni, "correo@ficticio.com", "600000000", LocalDate.of(2000, 1, 1));
   }

    //Introduce fecha
    public static LocalDate leerFecha(String mensaje) {
        // Alumno ficticio para validar la fecha
        final Alumno ALUMNO_GUIA = new Alumno("Carlos", "12345678Z", "carlos.perez@gmail.com", "612345678",
                LocalDate.parse("15/03/1998", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        LocalDate fechaNacimiento = null;
        boolean valida = false;
        do {
            System.out.print(mensaje + " (dd/MM/yyyy): ");
            String entradaFecha = Entrada.cadena();
            try {
                // Validar si la entrada es nula o vacía
                if (entradaFecha == null || entradaFecha.isBlank()) {
                    throw new IllegalArgumentException("ERROR: La fecha de nacimiento no puede ser nula ni estar vacía.");
                }
                // Convertir la fecha al formato `LocalDate`
                fechaNacimiento = LocalDate.parse(entradaFecha, DateTimeFormatter.ofPattern(Alumno.FORMATO_FECHA));
                // Intentar crear un alumno ficticio para validar la fecha usando `setFechaNacimiento`
                new Alumno(ALUMNO_GUIA.getNombre(), ALUMNO_GUIA.getDni(), ALUMNO_GUIA.getCorreo(), ALUMNO_GUIA.getTelefono(), fechaNacimiento);
                valida = true;
            } catch (DateTimeParseException e) {
                System.out.println("ERROR: El formato de la fecha no es válido. Use el formato dd/MM/yyyy.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!valida);
        return fechaNacimiento;
    }

    public static Grado leerGrado() {
        System.out.println("Grados disponibles:");
        Grado[] grados = Grado.values();

        for (Grado grado : grados) {
            System.out.println(grado.imprimir());
        }
        int opcion = -1;
        boolean valido = false;
        do {
            try {
                System.out.print("Elige un grado (introduce el número correspondiente): ");
                opcion = Entrada.entero();

                if (opcion < 0 || opcion >= grados.length) {
                    throw new IllegalArgumentException("ERROR: Debes elegir un número dentro del rango de opciones.");
                }
                valido = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("ERROR: Entrada no válida.");
            }
        } while (!valido);
        return grados[opcion];
    }


    public static CicloFormativo leerCicloFormativo() {
        int codigo = 0;
        String familiaProfesional = null;
        String nombre = null;
        int horas = 0;
        Grado grado = null;

        // Validación del código
        boolean valido = false;
        do {
            try {
                System.out.print("Introduce el código del ciclo formativo (4 dígitos): ");
                codigo = Entrada.entero();
                if (codigo < 1000 || codigo > 9999) {
                    throw new IllegalArgumentException("ERROR: El código debe ser un número de cuatro dígitos.");
                }
                valido = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("ERROR: Entrada no válida. Por favor, introduce un número.");
            }
        } while (!valido);

        // Validación de la familia profesional
        valido = false;
        do {
            try {
                System.out.print("Introduce la familia profesional: ");
                familiaProfesional = Entrada.cadena();
                if (familiaProfesional == null || familiaProfesional.isBlank()) {
                    throw new IllegalArgumentException("ERROR: La familia profesional no puede ser nula o vacía.");
                }
                valido = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!valido);

        // Validación del nombre
        valido = false;
        do {
            try {
                System.out.print("Introduce el nombre del ciclo formativo: ");
                nombre = Entrada.cadena();
                if (nombre == null || nombre.isBlank()) {
                    throw new IllegalArgumentException("ERROR: El nombre no puede ser nulo o vacío.");
                }
                valido = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!valido);

        // Validación de las horas
        valido = false;
        do {
            try {
                System.out.print("Introduce el número de horas del ciclo formativo: ");
                horas = Entrada.entero();
                if (horas <= 0 || horas > 2000) {
                    throw new IllegalArgumentException("ERROR: El número de horas debe ser un valor positivo y menor o igual a 2000.");
                }
                valido = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("ERROR: Entrada no válida. Por favor, introduce un número.");
            }
        } while (!valido);

        // Selección del grado usando leerGrado
        grado = leerGrado();

        // Creación y retorno del ciclo formativo
        return new CicloFormativo(codigo, familiaProfesional, grado, nombre, horas);
    }

    public static void mostrarCiclosFormativos(CicloFormativo[] ciclosFormativos) {
        if (ciclosFormativos == null || ciclosFormativos.length == 0) {
            System.out.println("No hay ciclos formativos registrados en el sistema.");
            return;
        }
        System.out.println("Listado de ciclos formativos registrados:");
        for (CicloFormativo ciclo : ciclosFormativos) {
            System.out.println(ciclo);
        }
    }

    public static CicloFormativo getCicloFormativoPorCodigo() {
        boolean valido = false;
        int codigo = 0;
        CicloFormativo validarCiclo = null;
        do {
            try {
                System.out.print("Introduce el código del ciclo formativo: ");
                codigo = Entrada.entero();
                validarCiclo =new CicloFormativo(codigo, "Ficticia", Grado.GDCFGM, "Ficticio", 1000);
                valido = true;
            }catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                valido=false;
            }

        } while (!valido);
        return new CicloFormativo(codigo, "Ficticia", Grado.GDCFGM, "Ficticio", 1000);
    }

    public static Curso leerCurso() {
        System.out.println("Cursos disponibles:");
        Curso[] cursos = Curso.values();

        for (Curso curso : cursos) {
            System.out.println(curso.imprimir());
        }
        int opcion = -1;
        boolean valido = false;
        do {
            try {
                System.out.print("Elige un curso (introduce el número correspondiente): ");
                opcion = Entrada.entero();

                if (opcion < 0 || opcion >= cursos.length) {
                    throw new IllegalArgumentException("ERROR: Debes elegir un número dentro del rango de opciones.");
                }
                valido = true; // Si no lanza excepción, la opción es válida.
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("ERROR: Entrada no válida. Por favor, introduce un número.");
            }
        } while (!valido);
        return cursos[opcion];
    }

    public static EspecialidadProfesorado leerEspecialidadProfesorado() {
        System.out.println("Especialidades disponibles:");
        int indice = 0;
        for (EspecialidadProfesorado especialidad : EspecialidadProfesorado.values()) {
            System.out.println(especialidad.imprimir());
            indice++;
        }
        EspecialidadProfesorado especialidadSeleccionada = null;
        boolean valido = false;
        do {
            System.out.print("Elige una especialidad (introduce el número correspondiente): ");
            try {
                int opcion = Entrada.entero();
                if (opcion < 0 || opcion >= EspecialidadProfesorado.values().length) {
                    throw new IllegalArgumentException("ERROR: La opción seleccionada no es válida.");
                }
                especialidadSeleccionada = EspecialidadProfesorado.values()[opcion];
                valido = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!valido);
        return especialidadSeleccionada;
    }

    //ASIGNATURA
    public static Asignatura leerAsignatura(CicloFormativo cicloFormativo) {
        if (cicloFormativo == null) {
            throw new IllegalArgumentException("ERROR: El ciclo formativo no puede ser nulo.");
        }
        final CicloFormativo CICLO_FORMATIVO_GUIA = new CicloFormativo(1234, "Informática", Grado.GDCFGM, "Informática", 1000);

        final Asignatura ASIGNATURA_GUIA = new Asignatura("1234", "Matemáticas", 200, Curso.PRIMERO, 5,
                EspecialidadProfesorado.INFORMATICA, CICLO_FORMATIVO_GUIA);

        String codigo = null;
        String nombre = null;
        int horasAnuales = 0;
        Curso curso = null;
        int horasDesdoble = 0;
        EspecialidadProfesorado especialidad = null;
        CicloFormativo ciclo = null;

        // Validar código
        boolean valido = false;
        do {
            System.out.print("Introduce el código de la asignatura: ");
            try {
                codigo = Entrada.cadena();

                if (codigo == null || codigo.isBlank()) {
                    throw new IllegalArgumentException("ERROR: El código no puede ser nulo o vacío.");
                }
                new Asignatura(codigo, ASIGNATURA_GUIA.getNombre(), ASIGNATURA_GUIA.getHorasAnuales(),
                        ASIGNATURA_GUIA.getCurso(), ASIGNATURA_GUIA.getHorasDesdoble(),
                        ASIGNATURA_GUIA.getEspecialidadProfesorado(), ASIGNATURA_GUIA.getCicloFormativo());
                valido = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!valido);

        // Validar nombre
        valido = false;
        do {
            System.out.print("Introduce el nombre de la asignatura: ");
            try {
                nombre = Entrada.cadena();
                if (nombre == null || nombre.isBlank()) {
                    throw new IllegalArgumentException("ERROR: El nombre no puede ser nulo o vacío.");
                }
                valido = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!valido);

        // Validar horas anuales
        valido = false;
        do {
            System.out.print("Introduce las horas anuales de la asignatura: ");
            try {
                horasAnuales = Entrada.entero();
                // Crear una asignatura temporal para validar las horas
                new Asignatura(ASIGNATURA_GUIA.getCodigo(), ASIGNATURA_GUIA.getNombre(),
                        horasAnuales, ASIGNATURA_GUIA.getCurso(), ASIGNATURA_GUIA.getHorasDesdoble(),
                        ASIGNATURA_GUIA.getEspecialidadProfesorado(), ASIGNATURA_GUIA.getCicloFormativo());
                valido = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!valido);


        // Validar curso
        curso = leerCurso();

        // Validar horas de desdoble
        valido = false;
        do {
            System.out.print("Introduce las horas de desdoble de la asignatura: ");
            try {
                horasDesdoble = Entrada.entero();
                // Crear una asignatura temporal para validar las horas de desdoble
                new Asignatura(ASIGNATURA_GUIA.getCodigo(), ASIGNATURA_GUIA.getNombre(),
                        ASIGNATURA_GUIA.getHorasAnuales(), ASIGNATURA_GUIA.getCurso(), horasDesdoble,
                        ASIGNATURA_GUIA.getEspecialidadProfesorado(), ASIGNATURA_GUIA.getCicloFormativo());
                valido = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!valido);

        // Validar especialidad del profesorado
        especialidad = leerEspecialidadProfesorado();

        return new Asignatura(codigo, nombre, horasAnuales, curso, horasDesdoble, especialidad, cicloFormativo);
    }

    public static Asignatura getAsignaturaPorCodigo() {
        String codigo = null;
        boolean valido = false;
        do {
            System.out.print("Introduce el código de la asignatura (4 dígitos): ");
            try {
                codigo = Entrada.cadena();
                if (codigo == null || codigo.isBlank() || !codigo.matches("\\d{4}")) {
                    throw new IllegalArgumentException("ERROR: El código debe ser un número de 4 dígitos.");
                }
                valido = true; // Si no lanza excepción, el código es válido
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!valido);
        return new Asignatura(codigo, "Ficticia", 100, Curso.PRIMERO, 5, EspecialidadProfesorado.INFORMATICA, new CicloFormativo(1234, "Ficticio", Grado.GDCFGM, "Grado Medio", 1000));
    }

    private static void mostrarAsignaturas(Asignatura[] asignaturas) {
        if (asignaturas == null || asignaturas.length == 0) {
            System.out.println("No hay asignaturas registradas en el sistema.");
            return;
        }
        System.out.println("Listado de asignaturas registradas:");
        for (Asignatura asignatura : asignaturas) {
            System.out.println(asignatura);
        }
    }

    static boolean asignaturaYaMatriculada(Asignatura[] asignaturasMatricula, Asignatura asignatura) {
        for (Asignatura asign : asignaturasMatricula) {
            if (asign != null && asign.equals(asignatura)) {
                return true;
            }
        }
        return false;
    }

    public static Matricula leerMatricula(Alumno alumno, Asignatura[] asignaturas) throws OperationNotSupportedException {
        final Matricula MATRICULA_GUIA = new Matricula(1, "23-24", LocalDate.now(),
                new Alumno("Carlos", "12345678Z", "carlos.perez@gmail.com", "612345678", LocalDate.of(2000, 1, 1)),
                new Asignatura[0]);

        if (alumno == null) {
            System.out.println("ERROR: El alumno no puede ser nulo.");
            return null;
        }

        if (asignaturas == null || asignaturas.length == 0) {
            System.out.println("ERROR: Debes proporcionar al menos una asignatura.");
            return null;
        }

        int idMatricula = 0;
        LocalDate fechaMatriculacion = null;
        LocalDate fechaAnulacion = null;

        /* Validar el DNI del alumno y verificar si existe en la colección
        boolean valido = false;
        do {
            try {
                alumno = getAlumnoPorDni(); // Obtener un objeto Alumno con el DNI ingresado
                if (alumnos.buscar(alumno) == null) {
                    System.out.println("ERROR: No se ha encontrado ningún alumno con el DNI proporcionado. Introduce antes dicho alumno o crea una matricula nueva con un alumno existente.");
                    return null; // Regresar al menú si no existe el alumno
                }
                valido = true;
            } catch (IllegalArgumentException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        } while (!valido); */

        // Validar el ID de matrícula
       boolean valido = false;
        do {
            System.out.print("Introduce el identificador de la matrícula: ");
            try {
                idMatricula = Entrada.entero();
                if (idMatricula <= 0) {
                    throw new IllegalArgumentException("ERROR: El identificador de la matrícula debe ser un número positivo.");
                }
                valido = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!valido);

        // Validar la fecha de matriculación
        valido = false;
        do {
            System.out.print("Introduce la fecha de matriculación (dd/MM/yyyy): ");
            try {
                String entrada = Entrada.cadena();
                fechaMatriculacion = LocalDate.parse(entrada, DateTimeFormatter.ofPattern(Matricula.FORMATO_FECHA));

                // Validar la fecha de matriculación con Matricula temporal
                Matricula matriculaTemporal = new Matricula(MATRICULA_GUIA.getIdMatricula(), MATRICULA_GUIA.getCursoAcademico(),
                        fechaMatriculacion, MATRICULA_GUIA.getAlumno(), MATRICULA_GUIA.getColeccionAsignaturas());
                valido = true;
            } catch (DateTimeParseException e) {
                System.out.println("ERROR: La fecha no tiene un formato válido. Use el formato dd/MM/yyyy.");
            } catch (IllegalArgumentException | NullPointerException e) {
                System.out.println(e.getMessage());
            }
        } while (!valido);

        // Validar la fecha de anulación (opcional)
        valido = false; // Variable para controlar el bucle de validación
        do {
            System.out.print("Introduce la fecha de anulación (dd/MM/yyyy) o presione Enter para omitir: ");
            String fechaAnulacionInput = Entrada.cadena();

            if (fechaAnulacionInput.isBlank()) {
                // Si el usuario presiona Enter, salimos del bucle sin validar
                valido = true;
                fechaAnulacion = null; // Se establece como nula porque el usuario quiere omitir
                break;
            }

            try {
                // Intentamos parsear la fecha y validarla
                fechaAnulacion = LocalDate.parse(fechaAnulacionInput, DateTimeFormatter.ofPattern(Matricula.FORMATO_FECHA));

                // Crear una matrícula temporal para validar la fecha de anulación
                Matricula matriculaTemporal = new Matricula(MATRICULA_GUIA.getIdMatricula(), MATRICULA_GUIA.getCursoAcademico(),
                        fechaMatriculacion, MATRICULA_GUIA.getAlumno(), MATRICULA_GUIA.getColeccionAsignaturas());
                matriculaTemporal.setFechaAnulacion(fechaAnulacion);

                valido = true; // Si no se lanza excepción, la fecha es válida y salimos del bucle
            } catch (DateTimeParseException e) {
                System.out.println("ERROR: La fecha no tiene un formato válido. Use el formato dd/MM/yyyy.");
            } catch (IllegalArgumentException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        } while (!valido);


        // Calcular el curso académico basado en la fecha de matriculación
        String cursoAcademico;
        int anioInicio = fechaMatriculacion.getYear();
        int anioFin = anioInicio + 1;
        cursoAcademico = String.format("%02d-%02d", anioInicio % 100, anioFin % 100);


        // Validar y seleccionar asignaturas
        System.out.println("Selecciona las asignaturas para la matrícula:");
        Asignatura[] asignaturasSeleccionadas = elegirAsignaturasMatricula(asignaturas);

        // Verificar si no se seleccionaron asignaturas
        if (asignaturasSeleccionadas == null || asignaturasSeleccionadas.length == 0) {
            System.out.println("ERROR: No se seleccionó ninguna asignatura para la matrícula.");
            return null;
        }

        /* Validar asignaturas
        Asignatura[] asignaturasMatricula = new Asignatura[Matricula.MAXIMO_NUMERO_ASIGNATURAS_POR_MATRICULA];
        int totalHoras = 0;
        int indice = 0;

        System.out.println("Introduce las asignaturas de la matrícula. Escribe 'FIN' para terminar.");
        while (indice < Matricula.MAXIMO_NUMERO_ASIGNATURAS_POR_MATRICULA) {
            System.out.print("Introduce el código de la asignatura: ");
            String codigo = Entrada.cadena();
            if (codigo.equalsIgnoreCase("FIN")) {
                // Verificar si no se ha introducido ninguna asignatura
                if (indice == 0) {
                    System.out.println("ERROR: Debes introducir al menos una asignatura antes de completar la matrícula.");
                    continue; // Volver al inicio del bucle
                }
                break; // Salir del bucle si ya hay asignaturas introducidas
            }
            try {
                // Buscar asignatura por código en la colección de asignaturas
                Asignatura asignaturaTemporal = new Asignatura(codigo, "Ficticia", 200, Curso.PRIMERO, 5,
                        EspecialidadProfesorado.INFORMATICA, new CicloFormativo(1234, "Ficticio", Grado.GDCFGM, "Grado Medio", 1000));
                Asignatura asignatura = asignaturas.buscar(asignaturaTemporal);

                // Verificar si la asignatura existe
                if (asignatura == null) {
                    System.out.println("ERROR: No se ha encontrado ninguna asignatura con el código proporcionado.Inserta antes una asignatura con dicho código o vuelve a crear la matrícula con una asignatura existente.");
                    return null; // Interrumpe el proceso y vuelve al menú principal
                }

                // Calcular las horas totales
                int horasTotales = totalHoras + asignatura.getHorasAnuales() + asignatura.getHorasDesdoble();

                // Validar si ya está matriculada o si supera el máximo de horas
                if (asignaturaYaMatriculada(asignaturasMatricula, asignatura)) {
                    System.out.println("ERROR: La asignatura ya está matriculada.");
                } else if (horasTotales > Matricula.MAXIMO_NUMERO_HORAS_MATRICULA) {
                    System.out.println("ERROR: No se puede añadir esta asignatura porque supera el máximo de 1000 horas.");
                } else {
                    asignaturasMatricula[indice] = asignatura;
                    totalHoras = horasTotales;
                    indice++;
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        // Verificar si no se introdujo ninguna asignatura
        if (indice == 0) {
            System.out.println("ERROR: No se puede crear la matrícula sin ninguna asignatura.");
            return null; // Volver al menú principal
        } */

        // Crear y devolver la matrícula válida
        Matricula matricula = new Matricula(idMatricula,cursoAcademico, fechaMatriculacion, alumno, asignaturas);
        matricula.setFechaAnulacion(fechaAnulacion); // Si no se introdujo fecha de anulación, será null
        return matricula;
    }

    public static Asignatura[] elegirAsignaturasMatricula(Asignatura[] asignaturas) {
        if (asignaturas == null || asignaturas.length == 0) {
            System.out.println("No hay asignaturas disponibles para elegir.");
            return new Asignatura[0];
        }

        System.out.println("Listado de asignaturas disponibles:");
        for (int i = 0; i < asignaturas.length; i++) {
            System.out.printf("%d. %s%n", i + 1, asignaturas[i]);
        }

        Asignatura[] seleccionadas = new Asignatura[Matricula.MAXIMO_NUMERO_ASIGNATURAS_POR_MATRICULA];
        int indiceSeleccionadas = 0;

        while (indiceSeleccionadas < Matricula.MAXIMO_NUMERO_ASIGNATURAS_POR_MATRICULA) {
            System.out.print("Introduce el número de la asignatura para añadirla a la matrícula (0 para finalizar): ");
            try {
                int opcion = Entrada.entero();

                if (opcion == 0) {
                    break; // Finaliza si el usuario introduce 0
                }

                if (opcion < 1 || opcion > asignaturas.length) {
                    System.out.println("ERROR: Selección no válida. Introduce un número entre 1 y " + asignaturas.length + ".");
                    continue;
                }

                Asignatura asignaturaSeleccionada = asignaturas[opcion - 1];

                if (asignaturaYaMatriculada(seleccionadas, asignaturaSeleccionada)) {
                    System.out.println("ERROR: La asignatura ya ha sido seleccionada.");
                    continue;
                }

                seleccionadas[indiceSeleccionadas] = asignaturaSeleccionada;
                indiceSeleccionadas++;
                System.out.println("Asignatura añadida: " + asignaturaSeleccionada);

            } catch (Exception e) {
                System.out.println("ERROR: Entrada no válida. Debes introducir un número.");
            }
        }

        if (indiceSeleccionadas == 0) {
            System.out.println("No se seleccionó ninguna asignatura.");
            return new Asignatura[0];
        }

        return Arrays.copyOf(seleccionadas, indiceSeleccionadas);
    }

    public static Matricula getMatriculaPorIdentificador() {
        int idMatricula = 0;
        boolean valido = false;

        do {
            System.out.print("Introduce el identificador de la matrícula: ");
            try {
                idMatricula = Entrada.entero(); // Leer el ID de la matrícula
                if (idMatricula <= 0) {
                    throw new IllegalArgumentException("ERROR: El identificador debe ser un número positivo.");
                }
                valido = true; // Si no hay excepciones, el identificador es válido
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!valido);

        // Crear una matrícula ficticia con datos válidos para devolver en caso de éxito
        try {
            Alumno alumnoFicticio = new Alumno("Ficticio", "12345678Z", "ficticio@correo.com", "600000000", LocalDate.of(2000, 1, 1));
            Asignatura[] asignaturasFicticias = {
                    new Asignatura("1234", "Ficticia", 200, Curso.PRIMERO, 5, EspecialidadProfesorado.INFORMATICA,
                            new CicloFormativo(1234, "Ficticio", Grado.GDCFGM, "Grado Medio", 1000))
            };
            return new Matricula(idMatricula, "23-24", LocalDate.now(), alumnoFicticio, asignaturasFicticias);
        } catch (Exception e) {
            // Manejo de errores en la creación de datos ficticios
            System.out.println("ERROR: No se pudo crear una matrícula ficticia válida.");
            return null;
        }
    }

}
