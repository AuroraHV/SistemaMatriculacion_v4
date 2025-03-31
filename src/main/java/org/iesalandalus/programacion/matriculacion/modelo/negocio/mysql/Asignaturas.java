package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAsignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL;

import javax.naming.OperationNotSupportedException;
import java.sql.*;
import java.util.ArrayList;

/**
 * Clase que gestiona una colección de asignaturas.
 * Permite insertar, buscar, borrar y obtener información sobre las asignaturas.
 */
public class Asignaturas implements IAsignaturas {

    private Connection conexion;
    private static Asignaturas instancia = null;
    private ArrayList<Asignatura> coleccionAsignaturas;

    /**
     * Constructor que inicializa la colección de Asignaturas.
     */
    public Asignaturas() {
        this.coleccionAsignaturas = new ArrayList<>();
        comenzar();
    }
    public static Asignaturas getInstancia() {
        if (instancia == null) {
            instancia = new Asignaturas();
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
     * Obtiene un objeto Curso a partir de su nombre.
     *
     * @param curso El nombre del curso.
     * @return Un objeto Curso.
     */
    public Curso getCurso(String curso) {
        return Curso.valueOf(curso.toUpperCase());
    }

    /**
     * Obtiene un objeto EspecialidadProfesorado a partir de su nombre.
     *
     * @param especialidad El nombre de la especialidad.
     * @return Un objeto EspecialidadProfesorado.
     */
    public EspecialidadProfesorado getEspecialidadProfesorado(String especialidad) {
        return EspecialidadProfesorado.valueOf(especialidad.toUpperCase());
    }

    /**
     * Obtiene una copia profunda de la colección de asignaturas.
     *
     * @return Un array con una copia de las asignaturas.
     * @throws SQLException Si hay problemas con la base de datos.
     */
    public ArrayList<Asignatura> get() throws SQLException {
        ArrayList<Asignatura> copiaAsignaturas = new ArrayList<>();
        String query = """
				SELECT a.codigo
					, a.nombre
				    , a.horasAnuales
				    , a.curso
				    , a.horasDesdoble
				    , a.especialidadProfesorado
				    , a.codigoCicloFormativo
				FROM asignatura a
				ORDER BY a.nombre
				    """;
        Statement sentencia = conexion.createStatement();
        ResultSet rs = sentencia.executeQuery(query);
        while (rs.next()) {
            CicloFormativo cicloFormativo = CiclosFormativos.getInstancia().buscar(new CicloFormativo(rs.getInt("codigoCicloFormativo"), "ficticio", new GradoE("E", 1, 1), "ficticio", 1));
            Asignatura asignatura = new Asignatura(
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    rs.getInt("horasAnuales"),
                    getCurso(rs.getString("curso")),
                    rs.getInt("horasDesdoble"),
                    getEspecialidadProfesorado(rs.getString("especialidadProfesorado")),
                    cicloFormativo);
            copiaAsignaturas.add(asignatura);
        }
        return copiaAsignaturas;
    }

    /**
     * Obtiene el tamaño actual de la colección (número de asignaturas almacenadas).
     *
     * @return El tamaño actual de la colección.
     * @throws SQLException Si hay problemas con la base de datos.
     */
    public int getTamano() throws SQLException {
        String query = """
	    		SELECT COUNT(codigo) 
	    		FROM cicloformativo
	    		""";
        Statement stmt = conexion.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        return rs.getInt(1);
    }

    /**
     * Inserta una asignatura en la colección.
     *
     * @param asignatura La asignatura a insertar.
     * @throws OperationNotSupportedException Si no se pueden insertar más asignaturas
     *         o si ya existe una asignatura con el mismo código.
     * @throws SQLException Si hay problemas con la base de datos.
     * @throws NullPointerException Si la asignatura es nula.
     */
    public void insertar(Asignatura asignatura) throws OperationNotSupportedException, SQLException {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No se puede insertar una asignatura nula.");
        }
        if(buscar(asignatura) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe una asignatura con ese código.");
        }
        // Validar el límite de horas del ciclo formativo.
        CicloFormativo cicloFormativo = asignatura.getCicloFormativo();
        // Horas de la asignatura que se quiere insertar.
        int horasTotales = asignatura.getHorasAnuales() + asignatura.getHorasDesdoble();
        // Consulta para sumar las horas de asignaturas ya insertadas para el mismo ciclo formativo.
        String querySuma = """
                SELECT COALESCE(SUM(horasAnuales + horasDesdoble), 0) 
                FROM asignatura WHERE codigoCicloFormativo = ?
                """;
        try (PreparedStatement pstmtSuma = conexion.prepareStatement(querySuma)) {
            pstmtSuma.setInt(1, cicloFormativo.getCodigo());
            try (ResultSet rs = pstmtSuma.executeQuery()) {
                if (rs.next()) {
                    horasTotales += rs.getInt(1);
                }
            }
        }
        // Comprobar si se supera el máximo permitido en el ciclo formativo.
        if (horasTotales > cicloFormativo.getHoras()) {
            throw new IllegalArgumentException("ERROR: El número total de horas de asignaturas en el ciclo formativo supera el máximo permitido de "
                    + cicloFormativo.getHoras() + " horas.");
        }
        String query = """
				INSERT INTO asignatura
					(codigo, 
					nombre, 
					horasAnuales, 
					curso, 
					horasDesdoble, 
					especialidadProfesorado, 
					codigoCicloFormativo)
				VALUES
					(?, ?, ?, ?, ?, ?, ?)
				""";
        PreparedStatement pstmt = conexion.prepareStatement(query);
        pstmt.setString(1, asignatura.getCodigo());
        pstmt.setString(2, asignatura.getNombre());
        pstmt.setInt(3, asignatura.getHorasAnuales());
        pstmt.setString(4, asignatura.getCurso().name().toLowerCase());
        pstmt.setInt(5, asignatura.getHorasDesdoble());
        pstmt.setString(6, asignatura.getEspecialidadProfesorado().name().toLowerCase());
        pstmt.setInt(7, asignatura.getCicloFormativo().getCodigo());
        pstmt.executeUpdate();
    }

    /**
     * Busca una asignatura en la colección.
     *
     * @param asignatura La asignatura a buscar.
     * @return La asignatura encontrada o null si no se encuentra.
     * @throws SQLException Si hay problemas con la base de datos.
     * @throws NullPointerException Si la asignatura es nula.
     */
    public Asignatura buscar(Asignatura asignatura) throws SQLException {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No se puede buscar una asignatura nula.");
        }
        String query = """
				SELECT a.codigo
					, a.nombre
				    , a.horasAnuales
				    , a.curso
				    , a.horasDesdoble
				    , a.especialidadProfesorado
				    , a.codigoCicloFormativo
				FROM asignatura a
				WHERE a.codigo = ?
				    """;
        PreparedStatement pstmt = conexion.prepareStatement(query);
        pstmt.setString(1, asignatura.getCodigo());
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            CicloFormativo cicloFormativo = CiclosFormativos.getInstancia().buscar(new CicloFormativo(rs.getInt("codigoCicloFormativo"), "ficticio", new GradoE("gradoe", 1, 1), "ficticio", 1));
            return new Asignatura(
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    rs.getInt("horasAnuales"),
                    getCurso(rs.getString("curso")),
                    rs.getInt("horasDesdoble"),
                    getEspecialidadProfesorado(rs.getString("especialidadProfesorado")),
                    cicloFormativo);
        }
        return null;
    }

    /**
     * Borra una asignatura de la colección.
     *
     * @param asignatura La asignatura a borrar.
     * @throws OperationNotSupportedException Si no existe la asignatura a borrar.
     * @throws SQLException Si hay problemas con la base de datos.
     * @throws NullPointerException Si la asignatura es nula.
     */
    public void borrar(Asignatura asignatura) throws OperationNotSupportedException, SQLException {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No se puede borrar una asignatura nula.");
        }
        if (buscar(asignatura) == null) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna asignatura como la indicada.");
        }
        String query = """
				DELETE FROM asignatura
                WHERE codigo = ?
				""";
        PreparedStatement pstmt = conexion.prepareStatement(query);
        pstmt.setString(1, asignatura.getCodigo());
        pstmt.executeUpdate();
    }
}
