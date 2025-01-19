package org.iesalandalus.programacion.matriculacion;

import org.iesalandalus.programacion.matriculacion.dominio.*;
import org.iesalandalus.programacion.matriculacion.vista.Consola;
import org.iesalandalus.programacion.matriculacion.negocio.Alumnos;
import org.iesalandalus.programacion.matriculacion.negocio.Asignaturas;
import org.iesalandalus.programacion.matriculacion.negocio.CiclosFormativos;
import org.iesalandalus.programacion.matriculacion.negocio.Matriculas;
import org.iesalandalus.programacion.matriculacion.vista.Opcion;
import org.iesalandalus.programacion.utilidades.Entrada;
import java.time.format.DateTimeParseException;

import java.util.Objects;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.iesalandalus.programacion.matriculacion.vista.Consola.getMatriculaPorIdentificador;
import static org.iesalandalus.programacion.matriculacion.vista.Consola.leerMatricula;
//p
public class MainApp {
    public static final int CAPACIDAD = 10;

    private static Alumnos alumnos = new Alumnos(CAPACIDAD);
    private static Asignaturas asignaturas = new Asignaturas(CAPACIDAD);
    private static CiclosFormativos ciclosFormativos = new CiclosFormativos(CAPACIDAD);
    private static Matriculas matriculas = new Matriculas(CAPACIDAD);

    public static void main(String[] args) {
        Opcion opcion;
        do {
            Consola.mostrarMenu();
            opcion = Consola.elegirOpcion();
            ejecutarOpcion(opcion);
        } while (opcion != Opcion.SALIR);

        System.out.println("Hasta luego!!!!");
    }

    private static void ejecutarOpcion(Opcion opcion) {
        switch (opcion) {
            case INSERTAR_ALUMNO:
                insertarAlumno();
                break;
            case BUSCAR_ALUMNO:
                buscarAlumno();
                break;
            case BORRAR_ALUMNO:
                borrarAlumno();
                break;
            case MOSTRAR_ALUMNOS:
                mostrarAlumnos();
                break;
            case INSERTAR_ASIGNATURA:
                insertarAsignatura();
                break;
            case BUSCAR_ASIGNATURA:
                buscarAsignatura();
                break;
            case BORRAR_ASIGNATURA:
                borrarAsignatura();
                break;
            case MOSTRAR_ASIGNATURAS:
                mostrarAsignaturas();
                break;
            case INSERTAR_CICLO_FORMATIVO:
                insertarCicloFormativo();
                break;
            case BUSCAR_CICLO_FORMATIVO:
                buscarCicloFormativo();
                break;
            case BORRAR_CICLO_FORMATIVO:
                borrarCicloFormativo();
                break;
            case MOSTRAR_CICLOS_FORMATIVOS:
                mostrarCiclosFormativos();
                break;
            case INSERTAR_MATRICULA:
                insertarMatricula();
                break;
            case BUSCAR_MATRICULA:
                buscarMatricula();
                break;
            case ANULAR_MATRICULA:
                anularMatricula();
                break;
            case MOSTRAR_MATRICULAS:
                mostrarMatriculas();
                break;
            case MOSTRAR_MATRICULAS_ALUMNO:
                mostrarMatriculasPorAlumno();
                break;
            case MOSTRAR_MATRICULAS_CICLO_FORMATIVO:
                mostrarMatriculasPorCicloFormativo();
                break;
            case MOSTRAR_MATRICULAS_CURSO_ACADEMICO:
                mostrarMatriculasPorCursoAcademico();
                break;
            case SALIR:
                System.out.println("Hasta luego!!!");
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    private static void insertarAlumno() {
        try {
            Alumno alumno = Consola.leerAlumno();
            alumnos.insertar(alumno);
            System.out.println("Alumno insertado correctamente.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void buscarAlumno() {
        try {
            Alumno alumno = alumnos.buscar(Consola.getAlumnoPorDni());
            System.out.println(Objects.requireNonNullElse(alumno, "No se ha encontrado al alumno."));
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void borrarAlumno() {
        try {
            alumnos.borrar(Consola.getAlumnoPorDni());
            System.out.println("Alumno borrado correctamente.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void mostrarAlumnos() {
        try {
            if (alumnos.getTamano() == 0) {
                throw new IllegalArgumentException("ERROR:No hay alumnos para mostrar.");
            }
            for (Alumno alumno : alumnos.get()) {
                System.out.println(alumno.imprimir());
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void insertarAsignatura() {
        try {
            Asignatura asignatura = Consola.leerAsignatura(ciclosFormativos);
            if (asignatura == null) {
                // Se regresa al menú porque no se encontró un ciclo
                return;
            }
            asignaturas.insertar(asignatura);
            System.out.println("Asignatura insertada correctamente.");
        } catch (Exception e) {
            String mensaje = e.getMessage();
            if (!mensaje.startsWith("ERROR: ")) {
                mensaje = "ERROR: " + mensaje;
            }
            System.out.println(mensaje);
        }
    }


    private static void buscarAsignatura() {
        try {
            Asignatura asignatura = asignaturas.buscar(Consola.getAsignaturaPorCodigo());
            System.out.println(Objects.requireNonNullElse(asignatura, "No se ha encontrado la asignatura."));
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void borrarAsignatura() {
        try {
            asignaturas.borrar(Consola.getAsignaturaPorCodigo());
            System.out.println("Asignatura borrada correctamente.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void mostrarAsignaturas() {
        try {
            Consola.mostrarAsignaturas(asignaturas);
        } catch (Exception e) {
            System.out.println("No hay asignaturas para mostrar.");
        }
    }


    private static void insertarCicloFormativo() {
        try {
            CicloFormativo ciclo = Consola.leerCicloFormativo();
            ciclosFormativos.insertar(ciclo);
            System.out.println("Ciclo formativo insertado correctamente.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void buscarCicloFormativo() {
        try {
            CicloFormativo ciclo = ciclosFormativos.buscar(Consola.getCicloFormativoPorCodigo());
            System.out.println(Objects.requireNonNullElse(ciclo, "No se ha encontrado el ciclo formativo."));
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void borrarCicloFormativo() {
        try {
            ciclosFormativos.borrar(Consola.getCicloFormativoPorCodigo());
            System.out.println("Ciclo formativo borrado correctamente.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void mostrarCiclosFormativos() {
        try {
            Consola.mostrarCiclosFormativos(ciclosFormativos);
        } catch (Exception e) {
            System.out.println("No hay ciclos formativos registrados en el sistema.");
        }
    }


    private static void insertarMatricula() {
        try {
            Matricula matricula = Consola.leerMatricula(alumnos, asignaturas);
            if (matricula == null) {
                return; // Volver al menú si la matrícula no se pudo crear
            }
            matriculas.insertar(matricula);
            System.out.println("Matrícula insertada correctamente.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void buscarMatricula() {
        try {
            Matricula matricula = matriculas.buscar(getMatriculaPorIdentificador());
            System.out.println(Objects.requireNonNullElse(matricula, "No se ha encontrado la matrícula."));
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void anularMatricula() {
        // Pedimos el identificador de la matrícula
        System.out.print("Introduce el identificador de la matrícula que deseas anular: ");
        int idMatricula = Entrada.entero();

        Matricula matriculaEncontrada;

        try {
            // Intentamos obtener la matrícula por su identificador
            matriculaEncontrada = getMatriculaPorIdentificador();

            // Verificamos si la matrícula encontrada coincide con el identificador proporcionado
            if (matriculaEncontrada == null || matriculaEncontrada.getIdMatricula() != idMatricula) {
                System.out.println("ERROR: No hay ninguna matrícula registrada con ese identificador.");
                return; // Salimos del método si no hay matrícula
            }

            // Si la matrícula existe, pedimos la fecha de anulación
            System.out.print("Introduce la fecha de anulación (dd/MM/yyyy): ");
            String entradaFecha = Entrada.cadena();

            try {
                // Intentamos convertir la fecha ingresada
                LocalDate fechaAnulacion = LocalDate.parse(entradaFecha, DateTimeFormatter.ofPattern(Matricula.FORMATO_FECHA));

                // Intentamos asignar la fecha usando el método validado de Matricula
                matriculaEncontrada.setFechaAnulacion(fechaAnulacion);
                System.out.println("Matrícula anulada correctamente.");
            } catch (DateTimeParseException e) {
                System.out.println("ERROR: La fecha no tiene un formato válido. Use el formato dd/MM/yyyy.");
            } catch (IllegalArgumentException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: No se pudo encontrar la matrícula con el identificador proporcionado.");
        }
    }

    private static void mostrarMatriculas() {
        try {
            // Verificamos si hay matrículas registradas
            if (matriculas == null || matriculas.getTamano() == 0) {
                System.out.println("No hay matrículas registradas en el sistema.");
                return;
            }

            // Mostramos todas las matrículas
            System.out.println("Listado de todas las matrículas registradas:");
            for (Matricula matricula : matriculas.get()) {
                System.out.println(matricula.toString());
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }


    private static void mostrarMatriculasPorAlumno() {
        try {
            // Obtenemos el alumno a partir del DNI
            Alumno alumno = Consola.getAlumnoPorDni();

            // Verificamos si existen matrículas asociadas al alumno
            if (matriculas.getTamano() == 0) {
                System.out.println("No hay matrículas registradas para el alumno proporcionado.");
                return;
            }

            // Mostramos las matrículas asociadas al alumno
            System.out.println("Matrículas registradas para el alumno:");
            for (Matricula matricula : matriculas.get(alumno)) {
                System.out.println(matricula.toString());
            }
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("ERROR: Ocurrió un error inesperado.");
        }
    }


    private static void mostrarMatriculasPorCicloFormativo() {
        try {
            // Obtenemos el ciclo formativo por código
            CicloFormativo ciclo = Consola.getCicloFormativoPorCodigo();

            // Verificamos si existen matrículas asociadas al ciclo formativo
            if (matriculas.getTamano() == 0) {
                System.out.println("No hay matrículas registradas para el ciclo formativo proporcionado.");
                return;
            }

            // Mostramos las matrículas asociadas al ciclo formativo
            System.out.println("Matrículas registradas para el ciclo formativo:");
            for (Matricula matricula : matriculas.get(ciclo)) {
                System.out.println(matricula.toString());
            }
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("ERROR: Ocurrió un error inesperado.");
        }
    }


    private static void mostrarMatriculasPorCursoAcademico() {
        boolean valido = false;
        String cursoAcademico = null;

        // Bucle para garantizar que se introduce un curso válido
        do {
            try {
                System.out.print("Introduce el curso académico (formato yy-yy): ");
                cursoAcademico = Entrada.cadena();

                // Validar si el curso académico se ha introducido y cump9le el formato
                if (cursoAcademico == null || cursoAcademico.isBlank()) {
                    throw new IllegalArgumentException("ERROR: Debes introducir un curso académico.");
                }

                if (!cursoAcademico.matches(Matricula.ER_CURSO_ACADEMICO)) {
                    throw new IllegalArgumentException("ERROR: El curso académico debe cumplir con el formato adecuado (23-24).");
                }

                valido = true; // Salir del bucle si no hay excepciones

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!valido);

        // Verificar si hay matrículas registradas para el curso académico
        try {
            if (matriculas.getTamano() == 0) {
                System.out.println("No hay matrículas registradas en el sistema.");
                return;
            }

            // Filtrar y mostrar matrículas correspondientes al curso académico
            boolean encontrado = false;
            System.out.println("Matrículas registradas para el curso académico " + cursoAcademico + ":");
            for (Matricula matricula : matriculas.get(cursoAcademico)) {
                System.out.println(matricula.toString());
                encontrado = true; // Indicar que se encontró al menos una matrícula
            }

            if (!encontrado) {
                System.out.println("No hay matrículas registradas para el curso académico proporcionado.");
            }
        } catch (Exception e) {
            System.out.println("ERROR: Ocurrió un error inesperado al buscar las matrículas.");
        }
    }


}

