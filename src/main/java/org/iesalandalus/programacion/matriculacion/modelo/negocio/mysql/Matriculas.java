package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IMatriculas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL;

import javax.naming.OperationNotSupportedException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Clase que gestiona una colección de matrículas.
 * Permite insertar, buscar, borrar y obtener información sobre las matrículas,
 * así como filtrarlas por alumno, curso académico o ciclo formativo.
 */
public class Matriculas implements IMatriculas {

    private Connection conexion;
    private static Matriculas instancia = null;
    private ArrayList<Matricula> coleccionMatriculas;


    /**
     * Constructor de la clase Matriculas. Inicia la colección de matrículas.
     */
    public Matriculas() {
        coleccionMatriculas = new ArrayList<>();
        comenzar();
    }

    /**
     * Obtiene la instancia de la clase Matriculas.
     *
     * @return La instancia de la clase Matriculas.
     */
    public static Matriculas getInstancia() {
        if (instancia == null) {
            instancia = new Matriculas();
        }
        return instancia;
    }

    @Override
    public void comenzar() {
        conexion = MySQL.establecerConexion();
    }

    @Override
    public void terminar() {
        MySQL.cerrarConexion();

    }

    /**
     * Obtiene una copia profunda de la colección de matrículas.
     *
     * @return Un array con una copia de las matrículas.
     * @throws OperationNotSupportedException Si no se puede copiar la colección.
     * @throws SQLException Si hay problemas con la base de datos.
     */
    public ArrayList<Matricula> get() throws OperationNotSupportedException, SQLException {
        ArrayList<Matricula> copiaMatriculas = new ArrayList<>();
        String query = """
    			SELECT m.idMatricula,
				    m.cursoAcademico,
				    m.fechaMatriculacion,
				    m.fechaAnulacion,
				    m.dni
				FROM matricula m
				LEFT JOIN alumno a ON m.dni = a.dni
				ORDER BY m.fechaMatriculacion DESC, a.nombre
    			""";
        Statement sentencia = conexion.createStatement();
        ResultSet rs = sentencia.executeQuery(query);
        while (rs.next()) {
            Alumno alumno = Alumnos.getInstancia().buscar(new Alumno("ficticio", rs.getString("dni"), "ficticio@gmail.com", "627673847", LocalDate.of(2000, 1, 1)));

            // Crear la matrícula sin usar el setter de la fecha (para mostrar matrículas con fecha anterior a 15 días de la actual)
            LocalDate fechaMatriculacion = rs.getDate("fechaMatriculacion").toLocalDate();

            // Capturar posibles excepciones que puedan surgir si la fecha no es válida
            try {
                // Asignar la fecha directamente a la matrícula
                Matricula matricula = new Matricula(
                        rs.getInt("idMatricula"),
                        rs.getString("cursoAcademico"),
                        fechaMatriculacion,  // Asignar la fecha directamente sin usar el setter
                        alumno,
                        getAsignaturasMatricula(rs.getInt("idMatricula"))
                );
                // Verificar si existe una fecha de anulación
                if (rs.getDate("fechaAnulacion") != null) {
                    matricula.setFechaAnulacion(rs.getDate("fechaAnulacion").toLocalDate());
                }
                // Añadir la matrícula a la lista
                copiaMatriculas.add(matricula);
            } catch (IllegalArgumentException e) {
                // Capturar la excepción de los 15 días si se lanza
            }
        }
        return copiaMatriculas;
    }

    /**
     * Obtiene las asignaturas de una matrícula.
     *
     * @param idMatricula El ID de la matrícula.
     * @return Un array con las asignaturas de la matrícula.
     * @throws SQLException Si hay problemas con la base de datos.
     */
    private ArrayList<Asignatura> getAsignaturasMatricula(int idMatricula) throws SQLException{
        String query = """
    			SELECT a.codigo
					, a.nombre
					, a.horasAnuales
					, a.curso
					, a.horasDesdoble
					, a.especialidadProfesorado
					, a.codigoCicloFormativo
				FROM asignaturasMatricula am
				LEFT JOIN asignatura a ON am.codigo = a.codigo
				WHERE am.idMatricula = ?
    			""";
        PreparedStatement pstmt = conexion.prepareStatement(query);
        pstmt.setInt(1, idMatricula);
        ResultSet rs = pstmt.executeQuery();
        ArrayList<Asignatura> asignaturas = new ArrayList<>();
        while (rs.next()) {
            CicloFormativo cicloFormativo = CiclosFormativos.getInstancia().buscar(new CicloFormativo(
                    rs.getInt("codigoCicloFormativo"), "ficticio", new GradoE("gradoe", 1, 1), "ficticio", 1));
            Asignatura asignatura = new Asignatura(
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    rs.getInt("horasAnuales"),
                    Curso.valueOf(rs.getString("curso").toUpperCase()),
                    rs.getInt("horasDesdoble"),
                    EspecialidadProfesorado.valueOf(rs.getString("especialidadProfesorado").toUpperCase()),
                    cicloFormativo);
            asignaturas.add(asignatura);
        }
        return asignaturas;
    }

    /**
     * Obtiene el tamaño actual de la colección (número de matrículas almacenadas).
     *
     * @return El tamaño actual de la colección.
     * @throws SQLException Si hay problemas con la base de datos.
     */
    public int getTamano() throws SQLException {
        String query = """
	    		SELECT COUNT(codigo) 
	    		FROM matricula
	    		""";
        Statement stmt = conexion.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        return rs.getInt(1);
    }

    /**
     * Inserta una matrícula en la colección.
     *
     * @param matricula La matrícula a insertar.
     * @throws OperationNotSupportedException Si no se pueden insertar más matrículas
     *         o si ya existe una matrícula con el mismo identificador.
     * @throws NullPointerException Si la matrícula es nula.
     * @throws SQLException Si hay problemas con la base de datos.
     */
    public void insertar(Matricula matricula) throws OperationNotSupportedException, SQLException {
        if (matricula == null) {
            throw new NullPointerException("ERROR: No se puede insertar una matrícula nula.");
        }
        if (buscar(matricula) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe una matrícula con ese identificador.");
        }
        String query = """
				INSERT INTO matricula
					(idMatricula,
					cursoAcademico,
					fechaMatriculacion,
					fechaAnulacion,
					dni)
				VALUES
					(?, ?, ?, ?, ?)
				""";
        PreparedStatement pstmt = conexion.prepareStatement(query);
        pstmt.setInt(1, matricula.getIdMatricula());
        pstmt.setString(2, matricula.getCursoAcademico());
        pstmt.setDate(3, java.sql.Date.valueOf(matricula.getFechaMatriculacion()));
        if (matricula.getFechaAnulacion() == null) {
            pstmt.setNull(4, java.sql.Types.DATE);
        }else {
            pstmt.setDate(4, java.sql.Date.valueOf(matricula.getFechaAnulacion()));
        }
        pstmt.setString(5, matricula.getAlumno().getDni());
        pstmt.executeUpdate();
        insertarAsignaturasMatricula(matricula.getIdMatricula(), matricula.getColeccionAsignaturas());
    }

    /**
     * Inserta las asignaturas de una matrícula en la colección.
     *
     * @param idMatricula El ID de la matrícula.
     * @param coleccionAsigntauras La colección de asignaturas de la matrícula.
     * @throws SQLException Si hay problemas con la base de datos.
     */
    private void insertarAsignaturasMatricula(int idMatricula, ArrayList<Asignatura> coleccionAsigntauras) throws SQLException{
        String query = """
    			INSERT INTO asignaturasMatricula
					(idMatricula
				    ,codigo)
				VALUES 
					(?, ?)
    			""";
        PreparedStatement pstmt = conexion.prepareStatement(query);
        for (Asignatura asignatura : coleccionAsigntauras) {
            pstmt.setInt(1, idMatricula);
            pstmt.setString(2, asignatura.getCodigo());
            pstmt.executeUpdate();
        }
    }

    /**
     * Busca una matrícula en la colección.
     *
     * @param matricula La matrícula a buscar.
     * @return La matrícula encontrada o null si no se encuentra.
     * @throws OperationNotSupportedException Si no se puede buscar la matrícula.
     * @throws SQLException Si hay problemas con la base de datos.
     * @throws NullPointerException Si la matrícula es nula.
     */
    public Matricula buscar(Matricula matricula) throws OperationNotSupportedException, SQLException {
        if (matricula == null) {
            throw new NullPointerException("ERROR: No se puede buscar una matrícula nula.");
        }
        String query = """
        		SELECT m.idMatricula,
	                m.cursoAcademico,
	                m.fechaMatriculacion,
	                m.fechaAnulacion,
	                m.dni
                FROM matricula m
                WHERE m.idMatricula = ?
        		""";
        PreparedStatement pstmt = conexion.prepareStatement(query);
        pstmt.setInt(1, matricula.getIdMatricula());
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            Alumno alumno = Alumnos.getInstancia().buscar(new Alumno("ficticio", rs.getString("dni"), "ficticio@fake.com", "666554433", LocalDate.of(2000, 1, 1)));
            Matricula matriculaEncontrada = new Matricula(rs.getInt("idMatricula"),
                    rs.getString("cursoAcademico"),
                    rs.getDate("fechaMatriculacion").toLocalDate(),
                    alumno,
                    getAsignaturasMatricula(rs.getInt("idMatricula")));
            if (rs.getDate("fechaAnulacion") != null) {
                matriculaEncontrada.setFechaAnulacion(rs.getDate("fechaAnulacion").toLocalDate());
            }
            return matriculaEncontrada;
        }
        return null;
    }

    /**
     * Borra una matrícula de la colección.
     *
     * @param matricula La matrícula a borrar.
     * @throws OperationNotSupportedException Si no existe la matrícula a borrar.
     * @throws SQLException Si hay problemas con la base de datos.
     * @throws NullPointerException Si la matrícula es nula.
     */
    public void borrar(Matricula matricula) throws OperationNotSupportedException, SQLException {
        if (matricula == null) {
            throw new NullPointerException("ERROR: No se puede borrar una matrícula nula.");
        }
        if (buscar(matricula) == null) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna matrícula como la indicada.");
        }
        if (matricula.getFechaAnulacion() == null) {
            // Caso 1: Anular la matrícula (establecer fecha de anulación).
            String query = """
                UPDATE matricula SET fechaAnulacion = ?
                WHERE idMatricula = ?
                """;
            try (PreparedStatement pstmt = conexion.prepareStatement(query)) {
                pstmt.setDate(1, java.sql.Date.valueOf(LocalDate.now())); // Establece la fecha actual como fecha de anulación
                pstmt.setInt(2, matricula.getIdMatricula());
                pstmt.executeUpdate();
                System.out.println("Matrícula anulada correctamente en la base de datos.");
            }
        } else {
            // Caso 2: Eliminar completamente la matrícula.
            String query = """
               DELETE FROM matricula 
               WHERE idMatricula = ?
               """;
            try (PreparedStatement pstmt = conexion.prepareStatement(query)) {
                pstmt.setInt(1, matricula.getIdMatricula());
                pstmt.executeUpdate();
            }
        }
    }

    /**
     * Obtiene las matrículas asociadas a un alumno específico.
     *
     * @param alumno El alumno del cual obtener las matrículas.
     * @return Un arreglo con las matrículas del alumno.
     * @throws OperationNotSupportedException Si no se puede obtener las matrículas.
     * @throws SQLException Si hay problemas con la base de datos.
     */
    public ArrayList<Matricula> get(Alumno alumno) throws OperationNotSupportedException, SQLException {
        ArrayList<Matricula> copiaMatriculas = new ArrayList<>();
        String query = """
    			SELECT m.idMatricula,
				    m.cursoAcademico,
				    m.fechaMatriculacion,
				    m.fechaAnulacion,
				    m.dni,
                    a.nombre,
                    a.telefono,
                    a.correo,
                    a.fechaNacimiento 
				FROM matricula m
				LEFT JOIN alumno a ON m.dni = a.dni
				WHERE a.dni = ?
				ORDER BY m.fechaMatriculacion DESC, a.nombre
    			""";
        PreparedStatement pstmt = conexion.prepareStatement(query);
        pstmt.setString(1, alumno.getDni());
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Alumno a = new Alumno(rs.getString("nombre"), rs.getString("dni"), rs.getString("correo"), rs.getString("telefono"), rs.getDate("fechaNacimiento").toLocalDate());
            // Obtener la fecha de matriculación
            LocalDate fechaMatriculacion = rs.getDate("fechaMatriculacion").toLocalDate();

            // Intentar crear la matrícula, manejando posibles excepciones
            try {
                // Crear el objeto Matricula a partir de la información recuperada
                Matricula matricula = new Matricula(
                        rs.getInt("idMatricula"),
                        rs.getString("cursoAcademico"),
                        fechaMatriculacion,  // Asignar directamente la fecha sin validación
                        a,  // Se pasa el objeto Alumno
                        getAsignaturasMatricula(rs.getInt("idMatricula"))
                );
                // Si existe una fecha de anulación, se asigna
                if (rs.getDate("fechaAnulacion") != null) {
                    matricula.setFechaAnulacion(rs.getDate("fechaAnulacion").toLocalDate());
                }
                // Añadir la matrícula a la lista
                copiaMatriculas.add(matricula);
            } catch (IllegalArgumentException e) {
                // Capturar la excepción de los 15 días si se lanza, no hacer nada
            }
        }
        return copiaMatriculas;
    }

    /**
     * Obtiene las matrículas asociadas a un curso académico específico.
     *
     * @param cursoAcademico El curso académico del cual obtener las matrículas.
     * @return Un arreglo con las matrículas del curso académico.
     * @throws OperationNotSupportedException Si no se puede obtener las matrículas.
     * @throws SQLException Si hay problemas con la base de datos.
     */
    public ArrayList<Matricula> get(String cursoAcademico) throws OperationNotSupportedException, SQLException {
        ArrayList<Matricula> copiaMatriculas = new ArrayList<>();
        String query = """
    			SELECT m.idMatricula,
				    m.cursoAcademico,
				    m.fechaMatriculacion,
				    m.fechaAnulacion,
				    m.dni,
                    a.nombre,
                    a.telefono,
                    a.correo,
                    a.fechaNacimiento 
				FROM matricula m
				LEFT JOIN alumno a ON m.dni = a.dni
				WHERE m.cursoAcademico = ?
				ORDER BY m.fechaMatriculacion DESC, a.nombre
    			""";
        PreparedStatement pstmt = conexion.prepareStatement(query);
        pstmt.setString(1, cursoAcademico);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Alumno a = new Alumno(rs.getString("nombre"), rs.getString("dni"), rs.getString("correo"), rs.getString("telefono"), rs.getDate("fechaNacimiento").toLocalDate());
            // Obtener la fecha de matriculación
            LocalDate fechaMatriculacion = rs.getDate("fechaMatriculacion").toLocalDate();

            // Intentar crear la matrícula, manejando posibles excepciones
            try {
                // Crear el objeto Matricula a partir de la información recuperada
                Matricula matricula = new Matricula(
                        rs.getInt("idMatricula"),
                        rs.getString("cursoAcademico"),
                        fechaMatriculacion,  // Asignar directamente la fecha sin validación
                        a,  // Se asigna el objeto Alumno
                        getAsignaturasMatricula(rs.getInt("idMatricula"))
                );
                // Si existe una fecha de anulación, se asigna
                if (rs.getDate("fechaAnulacion") != null) {
                    matricula.setFechaAnulacion(rs.getDate("fechaAnulacion").toLocalDate());
                }
                // Añadir la matrícula a la lista
                copiaMatriculas.add(matricula);
            } catch (IllegalArgumentException e) {
                // Capturar la excepción de los 15 días si se lanza, no hacer nada
            }
        }
        return copiaMatriculas;
    }

    /**
     * Obtiene las matrículas asociadas a un ciclo formativo específico.
     *
     * @param cicloFormativo El ciclo formativo del cual obtener las matrículas.
     * @return Un arreglo con las matrículas del ciclo formativo.
     * @throws OperationNotSupportedException Si no se puede obtener las matrículas.
     * @throws SQLException Si hay problemas con la base de datos.
     */
    public ArrayList<Matricula> get(CicloFormativo cicloFormativo) throws OperationNotSupportedException, SQLException {
        ArrayList<Matricula> copiaMatriculas = new ArrayList<>();
        String query = """
    			SELECT m.idMatricula,
					m.cursoAcademico,
					m.fechaMatriculacion,
					m.fechaAnulacion,
					m.dni,
                    al.nombre,
                    al.telefono,
                    al.correo,
                    al.fechaNacimiento 
				FROM matricula m
				LEFT JOIN asignaturasMatricula am ON m.idMatricula = am.idMatricula
				LEFT JOIN asignatura a ON am.codigo = a.codigo
				LEFT JOIN alumno al ON m.dni = al.dni                
				WHERE a.codigoCicloFormativo = ?
                GROUP BY m.idMatricula
				ORDER BY m.fechaMatriculacion DESC, al.nombre
    			""";
        PreparedStatement pstmt = conexion.prepareStatement(query);
        pstmt.setInt(1, cicloFormativo.getCodigo());
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Alumno al = new Alumno(rs.getString("nombre"), rs.getString("dni"), rs.getString("correo"), rs.getString("telefono"), rs.getDate("fechaNacimiento").toLocalDate());
            // Obtener la fecha de matriculación
            LocalDate fechaMatriculacion = rs.getDate("fechaMatriculacion").toLocalDate();

            // Intentar crear la matrícula, manejando posibles excepciones
            try {
                // Crear el objeto Matricula a partir de la información recuperada
                Matricula matricula = new Matricula(
                        rs.getInt("idMatricula"),
                        rs.getString("cursoAcademico"),
                        fechaMatriculacion,  // Asignar directamente la fecha sin validación
                        al,  // Se asigna el objeto Alumno
                        getAsignaturasMatricula(rs.getInt("idMatricula"))
                );
                // Si existe una fecha de anulación, se asigna
                if (rs.getDate("fechaAnulacion") != null) {
                    matricula.setFechaAnulacion(rs.getDate("fechaAnulacion").toLocalDate());
                }
                // Añadir la matrícula a la lista
                copiaMatriculas.add(matricula);
            } catch (IllegalArgumentException e) {
                // Capturar la excepción de los 15 días si se lanza, no hacer nada
            }
        }
        return copiaMatriculas;
    }
}
