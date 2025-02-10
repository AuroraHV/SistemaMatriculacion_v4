package org.iesalandalus.programacion.matriculacion.vista;

import org.iesalandalus.programacion.matriculacion.controlador.Controlador;
import org.iesalandalus.programacion.matriculacion.dominio.*;
import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Vista {

    private Controlador controlador;

    public void setControlador(Controlador controlador) {
        if (controlador == null) {
            throw new NullPointerException("ERROR: El controlador no puede ser nulo.");
        }
        this.controlador = controlador;
    }

    // Menú
    public void comenzar() throws OperationNotSupportedException {
        Opcion opcion;
        do {
            Consola.mostrarMenu();
            opcion = Consola.elegirOpcion();
            ejecutarOpcion(opcion);
        } while (opcion != Opcion.SALIR);
    }

    public void terminar() {
        System.out.println("Gracias por utilizar la aplicación. ¡Hasta pronto!");
    }

    private void ejecutarOpcion(Opcion opcion) {
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
                terminar();
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    private void insertarAlumno() {
        try {
            Alumno alumno = Consola.leerAlumno();
            controlador.insertar(alumno);
            System.out.println("Alumno insertado correctamente.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void buscarAlumno() {
        try {
            Alumno alumno = Consola.getAlumnoPorDni();
            Alumno encontrado = controlador.buscar(alumno);
            System.out.println(Objects.requireNonNullElse(encontrado, "No se ha encontrado el alumno."));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void borrarAlumno() {
        try {
            Alumno alumno = Consola.getAlumnoPorDni();
            controlador.borrar(alumno);
            System.out.println("Alumno borrado correctamente.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void mostrarAlumnos() {
        ArrayList<Alumno> Alumnos = controlador.getAlumnos();
        try {
            if (Alumnos.size() == 0) {
                System.out.println("No hay alumnos registrados.");
            } else {
                Alumnos.sort(new Comparator<Alumno>(){
                    @Override
                    public int compare(Alumno alumno1, Alumno alumno2) {
                        return alumno1.getNombre().compareTo(alumno2.getNombre());
                    }
                });
                System.out.println("Alumnos registrados:");
                for (Alumno alumno : Alumnos) {
                    System.out.println(alumno);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void insertarAsignatura() {

        try {
            ArrayList<CicloFormativo> ciclosDisponibles = controlador.getCiclosFormativos();
            Consola.mostrarCiclosFormativos(ciclosDisponibles);

            if (ciclosDisponibles.isEmpty()) {
                return;
            }
            int opcion;
            do {
                System.out.print("Introduce el número del ciclo formativo: ");
                opcion = Entrada.entero();
            } while (opcion < 1 || opcion > ciclosDisponibles.size());

            CicloFormativo cicloSeleccionado = ciclosDisponibles.get(opcion - 1);

            Asignatura asignatura = Consola.leerAsignatura(cicloSeleccionado);
            controlador.insertar(asignatura);
            System.out.println("Asignatura insertada correctamente.");
        } catch (OperationNotSupportedException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void buscarAsignatura() {
        try {
            Asignatura asignatura = Consola.getAsignaturaPorCodigo();
            Asignatura encontrada = controlador.buscar(asignatura);
            System.out.println(Objects.requireNonNullElse(encontrada, "No se ha encontrado la asignatura."));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void borrarAsignatura() {
        try {
            Asignatura asignatura = Consola.getAsignaturaPorCodigo();
            controlador.borrar(asignatura);
            System.out.println("Asignatura borrada correctamente.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void mostrarAsignaturas() {
        try {
            ArrayList<Asignatura> asignaturas = controlador.getAsignaturas();
            if (asignaturas == null || asignaturas.size() == 0) {
                System.out.println("No hay asignaturas registradas en el sistema.");
                return;
            }
            asignaturas.sort(new Comparator<Asignatura>() {
                @Override
                public int compare(Asignatura asignatura1, Asignatura asignatura2) {
                    return asignatura1.getNombre().compareTo(asignatura2.getNombre());
                }
            });
            System.out.println("Listado de asignaturas registradas:");
            for (Asignatura asignatura : asignaturas) {
                System.out.println(asignatura);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void insertarCicloFormativo() {
        try {
            CicloFormativo ciclo = Consola.leerCicloFormativo();
            controlador.insertar(ciclo);
            System.out.println("Ciclo formativo insertado correctamente.");
        } catch (OperationNotSupportedException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void buscarCicloFormativo() {
        try {
            CicloFormativo ciclo = Consola.getCicloFormativoPorCodigo();
            CicloFormativo encontrado = controlador.buscar(ciclo);
            System.out.println(Objects.requireNonNullElse(encontrado, "No se ha encontrado el ciclo formativo."));
        }catch (Exception e) {
        System.out.println(e.getMessage());
    }
    }

    private void borrarCicloFormativo() {
        try {
            CicloFormativo ciclo = Consola.getCicloFormativoPorCodigo();
            controlador.borrar(ciclo);
            System.out.println("Ciclo formativo borrado correctamente.");
        } catch (OperationNotSupportedException e) {
            System.out.println(e.getMessage());
        }
    }

    private void mostrarCiclosFormativos() {
        try {
            ArrayList<CicloFormativo> ciclos = controlador.getCiclosFormativos();

            if (ciclos == null || ciclos.size() == 0) {
                System.out.println("No hay ciclos formativos registrados en el sistema.");
                return;
            }
            ciclos.sort(new Comparator<CicloFormativo>() {
                @Override
                public int compare(CicloFormativo ciclo1, CicloFormativo ciclo2) {
                    return ciclo1.getNombre().compareTo(ciclo2.getNombre());
                }
            });
            System.out.println("Ciclos formativos registrados:");
            for (CicloFormativo ciclo : ciclos) {
                System.out.println(ciclo);
            }
        } catch (Exception e) {
            System.out.println("ERROR: Ocurrió un error inesperado al intentar mostrar los ciclos formativos.");
        }
    }


    private void insertarMatricula() {
        try {
            ArrayList<Alumno> alumnoDisponibles = controlador.getAlumnos();
            if (alumnoDisponibles.size() == 0) {
                System.out.println("No hay alumnos disponibles para asociar a la matrícula.");
                return;
            }
            Alumno alumno = Consola.getAlumnoPorDni();
            Alumno alumnoExistente = controlador.buscar(alumno);

            if (alumnoExistente == null) {
                System.out.println("No se ha encontrado un alumno con el DNI proporcionado.");
                return;
            }

            ArrayList<Asignatura> asignaturasDisponibles = controlador.getAsignaturas();
            if (asignaturasDisponibles.size() == 0) {
                System.out.println("No hay asignaturas disponibles para asociar a la matrícula.");
                return;
            }

            Matricula matricula = Consola.leerMatricula(alumnoExistente, asignaturasDisponibles);
            if (matricula != null) {
                controlador.insertar(matricula);
                System.out.println("Matrícula insertada correctamente.");
            }
        } catch (OperationNotSupportedException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void buscarMatricula() {
        try {
            Matricula matricula = Consola.getMatriculaPorIdentificador();
            Matricula encontrada = controlador.buscar(matricula);
            System.out.println(Objects.requireNonNullElse(encontrada, "No se ha encontrado la matrícula."));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void anularMatricula() {
        try {

            System.out.print("Introduce el identificador de la matrícula que deseas anular: ");
            int idMatricula = Entrada.entero();

            ArrayList<Matricula> matriculas = controlador.getMatriculas();
            Matricula matriculaEncontrada = null;

            for (Matricula matricula : matriculas) {
                if (matricula.getIdMatricula() == idMatricula) {
                    matriculaEncontrada = matricula;
                    break;
                }
            }

            if (matriculaEncontrada == null) {
                System.out.println("No se encontró ninguna matrícula con el identificador proporcionado.");
                return;
            }

            if (matriculaEncontrada.getFechaAnulacion() != null) {
                System.out.println("La matrícula ya tiene una fecha de anulación establecida: " + matriculaEncontrada.getFechaAnulacion());

                // Borrar la matrícula si se anula
                System.out.print("¿Quieres eliminar completamente la matrícula? (S/N): ");
                String respuesta = Entrada.cadena().trim().toUpperCase();
                if (respuesta.equals("S")) {
                    controlador.borrar(matriculaEncontrada);
                    System.out.println("Matrícula eliminada del sistema.");
                } else {
                    System.out.println("La matrícula se mantiene en el sistema con la fecha de anulación.");
                }

                return;
            }

            boolean valido = false;
            LocalDate fechaAnulacion = null;
            do {
                System.out.print("Introduce la fecha de anulación (dd/MM/yyyy): ");
                String entradaFecha = Entrada.cadena();

                try {
                    fechaAnulacion = LocalDate.parse(entradaFecha, DateTimeFormatter.ofPattern(Matricula.FORMATO_FECHA));

                    matriculaEncontrada.setFechaAnulacion(fechaAnulacion);
                    System.out.println("Matrícula anulada correctamente.");
                    valido = true;
                    // Actualizar la lista de matrículas con la nueva fecha
                    controlador.borrar(matriculaEncontrada);
                    controlador.insertar(matriculaEncontrada);

                    // Borrar la matrícula si se anula
                    System.out.print("¿Quieres eliminar completamente la matrícula? (S/N): ");
                    String respuesta = Entrada.cadena().trim().toUpperCase();
                    if (respuesta.equals("S")) {
                        controlador.borrar(matriculaEncontrada);
                        System.out.println("Matrícula eliminada del sistema.");
                    } else {
                        System.out.println("La matrícula se mantiene en el sistema con la fecha de anulación.");
                    }

                } catch (DateTimeParseException e) {
                    System.out.println("ERROR: La fecha no tiene un formato válido. Use el formato dd/MM/yyyy.");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            } while (!valido);

        } catch (Exception e) {
            System.out.println("ERROR: Ocurrió un error al intentar anular la matrícula.");
        }
    }

    private void mostrarMatriculas() {
        try {
            ArrayList<Matricula> matriculas = controlador.getMatriculas();
            if (matriculas.size()== 0) {
                System.out.println("No hay matrículas registradas.");
            } else {
                matriculas.sort(new Comparator<Matricula>() {
                    @Override
                    public int compare(Matricula matricula1, Matricula matricula2) {
                        int comparar = -matricula1.getFechaMatriculacion().compareTo(matricula2.getFechaMatriculacion());
                        if(comparar==0){
                            return matricula1.getAlumno().getNombre().compareTo(matricula2.getAlumno().getNombre());
                        }
                        return comparar;
                    }
                });
                System.out.println("Listado de todas las matrículas registradas:");
                for (Matricula matricula : matriculas) {
                    System.out.println(matricula);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void mostrarMatriculasPorAlumno() {
        try {
            Alumno alumno = Consola.getAlumnoPorDni();
            ArrayList<Matricula> matriculas = controlador.getMatriculas(alumno);
            if (matriculas.size()== 0) {
                System.out.println("No hay matrículas registradas para este alumno.");
            } else {
                matriculas.sort(new Comparator<Matricula>() {
                    @Override
                    public int compare(Matricula matricula1, Matricula matricula2) {
                        int comparar = -matricula1.getFechaMatriculacion().compareTo(matricula2.getFechaMatriculacion());
                        if(comparar==0){
                            return matricula1.getAlumno().getNombre().compareTo(matricula2.getAlumno().getNombre());
                        }
                        return comparar;
                    }
                });

                System.out.println("Matrículas registradas para el alumno:");
                for (Matricula matricula : matriculas) {
                    System.out.println(matricula);
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void mostrarMatriculasPorCicloFormativo() {
        try {
            //matriculaCiclo =controlador.getMatriculas(cicloFormativo); hasta el if
            CicloFormativo ciclo = Consola.getCicloFormativoPorCodigo();
            ArrayList<Matricula> matriculas = controlador.getMatriculas(ciclo);
            if (matriculas.size() == 0) {
                System.out.println("No hay matrículas registradas para este ciclo formativo.");
            } else {
                System.out.println("Matrículas registradas para el ciclo formativo:");
                matriculas.sort(new Comparator<Matricula>() {
                    @Override
                    public int compare(Matricula matricula1, Matricula matricula2) {
                        int comparar = -matricula1.getFechaMatriculacion().compareTo(matricula2.getFechaMatriculacion());
                        if(comparar==0){
                            return matricula1.getAlumno().getNombre().compareTo(matricula2.getAlumno().getNombre());
                        }
                        return comparar;
                    }
                });
                for (Matricula matricula : matriculas) {
                    System.out.println(matricula);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void mostrarMatriculasPorCursoAcademico() {
        boolean valido = false;
        String cursoAcademico = null;

        do {
            try {
                System.out.print("Introduce el curso académico (formato yy-yy): ");
                cursoAcademico = Entrada.cadena();

                if (cursoAcademico == null || cursoAcademico.isBlank()) {
                    throw new IllegalArgumentException("ERROR: Debes introducir un curso académico.");
                }

                if (!cursoAcademico.matches(Matricula.ER_CURSO_ACADEMICO)) {
                    throw new IllegalArgumentException("ERROR: El curso académico debe cumplir con el formato adecuado (yy-yy).");
                }

                valido = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!valido);

        try {
            ArrayList<Matricula> matriculasCurso = controlador.getMatriculas(cursoAcademico);

            if (matriculasCurso == null || matriculasCurso.size()== 0) {
                System.out.println("No hay matrículas registradas para el curso académico proporcionado.");
                return;
            }

            System.out.println("Matrículas registradas para el curso académico " + cursoAcademico + ":");
            matriculasCurso.sort(new Comparator<Matricula>() {
                @Override
                public int compare(Matricula matricula1, Matricula matricula2) {
                    int comparar = -matricula1.getFechaMatriculacion().compareTo(matricula2.getFechaMatriculacion());
                    if(comparar==0){
                        return matricula1.getAlumno().getNombre().compareTo(matricula2.getAlumno().getNombre());
                    }
                    return comparar;
                }
            });
            for (Matricula matricula : matriculasCurso) {
                System.out.println(matricula);
            }
        } catch (Exception e) {
            System.out.println("ERROR: Ocurrió un error inesperado al buscar las matrículas. " + e.getMessage());
        }
    }
}
