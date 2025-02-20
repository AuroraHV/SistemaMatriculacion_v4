package org.iesalandalus.programacion.matriculacion.vista;

public enum Opcion {
    //1-Opciones del menu
    SALIR("Salir") {
        @Override
        public void ejecutar() {
            System.out.println("Salir");
            vista.terminar();
        }
    },
    INSERTAR_ALUMNO("Insertar alumno") {
        @Override
        public void ejecutar() {
            System.out.println("------------------------");
            System.out.println("Insertar alumno");
            System.out.println("------------------------");
            vista.insertarAlumno();
        }
    },
    BUSCAR_ALUMNO("Buscar alumno") {
        @Override
        public void ejecutar() {
            System.out.println("------------------------");
            System.out.println("Buscar alumno");
            System.out.println("------------------------");
            vista.buscarAlumno();
        }
    },
    BORRAR_ALUMNO("Borrar alumno") {
        @Override
        public void ejecutar() {
            System.out.println("------------------------");
            System.out.println("Borrar alumno");
            System.out.println("------------------------");
            vista.borrarAlumno();
        }
    },
    MOSTRAR_ALUMNOS("Mostrar alumnos") {
        @Override
        public void ejecutar() {
            System.out.println("------------------------");
            System.out.println("Mostrar alumno");
            System.out.println("------------------------");
            vista.mostrarAlumnos();
        }
    },
    INSERTAR_CICLO_FORMATIVO("Insertar ciclo formativo") {
        @Override
        public void ejecutar() {
            System.out.println("------------------------");
            System.out.println("Insertar ciclo formativo");
            System.out.println("------------------------");
            vista.insertarCicloFormativo();
        }
    },
    BUSCAR_CICLO_FORMATIVO("Buscar ciclo formativo") {
        @Override
        public void ejecutar() {
            System.out.println("------------------------");
            System.out.println("Buscar ciclo formativo");
            System.out.println("------------------------");
            vista.buscarCicloFormativo();
        }
    },
    BORRAR_CICLO_FORMATIVO("Borrar ciclo formativo") {
        @Override
        public void ejecutar() {
            System.out.println("------------------------");
            System.out.println("Borrar ciclo formativo");
            System.out.println("------------------------");
            vista.borrarCicloFormativo();
        }
    },
    MOSTRAR_CICLOS_FORMATIVOS("Mostrar ciclos formativos") {
        @Override
        public void ejecutar() {
            System.out.println("------------------------");
            System.out.println("Mostrar ciclo formativo");
            System.out.println("------------------------");
            vista.mostrarCiclosFormativos();
        }
    },
    INSERTAR_ASIGNATURA("Insertar asignatura") {
        @Override
        public void ejecutar() {
            System.out.println("------------------------");
            System.out.println("Insertar asignatura");
            System.out.println("------------------------");
            vista.insertarAsignatura();
        }
    },
    BUSCAR_ASIGNATURA("Buscar asignatura") {
        @Override
        public void ejecutar() {
            System.out.println("------------------------");
            System.out.println("Buscar asignatura");
            System.out.println("------------------------");
            vista.buscarAsignatura();
        }
    },
    BORRAR_ASIGNATURA("Borrar asignatura") {
        @Override
        public void ejecutar() {
            System.out.println("------------------------");
            System.out.println("Borrar asignatura");
            System.out.println("------------------------");
            vista.borrarAsignatura();
        }
    },
    MOSTRAR_ASIGNATURAS("Mostrar asignaturas") {
        @Override
        public void ejecutar() {
            System.out.println("------------------------");
            System.out.println("Mostrar asignaturas");
            System.out.println("------------------------");
            vista.mostrarAsignaturas();
        }
    },
    INSERTAR_MATRICULA("Insertar matrícula") {
        @Override
        public void ejecutar() {
            System.out.println("------------------------");
            System.out.println("Insertar matrícula");
            System.out.println("------------------------");
            vista.insertarMatricula();
        }
    },
    BUSCAR_MATRICULA("Buscar matrícula") {
        @Override
        public void ejecutar() {
            System.out.println("------------------------");
            System.out.println("Buscar matrícula");
            System.out.println("------------------------");
            vista.buscarMatricula();
        }
    },
    ANULAR_MATRICULA("Anular matrícula") {
        @Override
        public void ejecutar() {
            System.out.println("------------------------");
            System.out.println("Anular matrícula");
            System.out.println("------------------------");
            vista.anularMatricula();
        }
    },
    MOSTRAR_MATRICULAS("Mostrar matrículas") {
        @Override
        public void ejecutar() {
            System.out.println("------------------------");
            System.out.println("Mostrar matrículas");
            System.out.println("------------------------");
            vista.mostrarMatriculas();
        }
    },
    MOSTRAR_MATRICULAS_ALUMNO("Mostrar matrículas de un alumno") {
        @Override
        public void ejecutar() {
            System.out.println("---------------------------------");
            System.out.println("Mostrar matrículas de un alumno");
            System.out.println("---------------------------------");
            vista.mostrarMatriculasPorAlumno();
        }
    },
    MOSTRAR_MATRICULAS_CICLO_FORMATIVO("Mostrar matrículas de un ciclo formativo") {
        @Override
        public void ejecutar() {
            System.out.println("------------------------------------------");
            System.out.println("Mostrar matrículas de un ciclo formativo");
            System.out.println("------------------------------------------");
            vista.mostrarMatriculasPorCicloFormativo();
        }
    },
    MOSTRAR_MATRICULAS_CURSO_ACADEMICO("Mostrar matrículas de un curso académico") {
        @Override
        public void ejecutar() {
            System.out.println("------------------------------------------");
            System.out.println("Mostrar matrículas de un curso academico");
            System.out.println("------------------------------------------");
            vista.mostrarMatriculasPorCursoAcademico();
        }
    };

    //1-Atributo
    private final String mensajeaMostrar;

    private static Vista vista;

    //1-Constructor
    private Opcion (String mensajeaMostrar){
        this.mensajeaMostrar=mensajeaMostrar;
    }

    public abstract void ejecutar();

    public static void setVista(Vista vista) {
        Opcion.vista = vista;
    }

    //1-toString con ordinal
    @Override
    public String toString() {
        return this.ordinal() + ".- " + this.mensajeaMostrar;
    }
}
