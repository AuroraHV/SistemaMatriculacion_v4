package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAlumnos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL;

import javax.naming.OperationNotSupportedException;
import java.sql.*;
import java.util.ArrayList;

/**
 * Clase que gestiona una colección de alumnos.
 * Permite realizar operaciones como insertar, buscar y borrar alumnos.
 */
public class Alumnos implements IAlumnos {

    private Connection conexion;
    private static Alumnos instancia = null;
    private ArrayList<Alumno> coleccionAlumnos;

    /**
     * Constructor que inicializa la colección.
     */
    public Alumnos() {
        this.coleccionAlumnos= new ArrayList<>();
        comenzar();
    }

    public static Alumnos getInstancia() {
        if (instancia == null) {
            instancia = new Alumnos();
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
     * Devuelve una copia profunda de los alumnos almacenados en la colección.
     *
     * @return Un array con copias de los alumnos.
     * @throws SQLException Si hay problemas con la base de datos.
     */
    public ArrayList<Alumno> get() throws SQLException {
        ArrayList<Alumno> copiaAlumnos = new ArrayList<>();
        String query= """
                SELECT nombre
                , telefono
                , correo
                , dni
                , fechaNacimiento
                FROM alumno;
                """;
        Statement stmt = conexion.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            Alumno a = new Alumno(
                    rs.getString("nombre"),
                    rs.getString("dni"),
                    rs.getString("correo"),
                    rs.getString("telefono"),
                    rs.getDate("fechaNacimiento").toLocalDate() //se podria poner por numeros
            );
            copiaAlumnos.add(a);
        }
        return copiaAlumnos;
    }

    /**
     * Devuelve el tamaño actual de la colección.
     *
     * @return El número de alumnos almacenados en la colección.
     * @throws SQLException Si hay problemas con la base de datos.
     */
    public int getTamano() throws SQLException {
        String query = """
                SELECT COUNT(1) AS cont
                FROM alumno
                """;
        Statement stmt = conexion.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        return rs.getInt("cont");
    }

    /**
     * Inserta un alumno en la colección.
     *
     * @param alumno Alumno a insertar. No puede ser nulo.
     * @throws OperationNotSupportedException Si la colección está llena o si ya existe un alumno con el mismo DNI.
     * @throws SQLException Si hay problemas con la base de datos.
     * @throws NullPointerException Si el alumno es nulo.
     */
    public void insertar(Alumno alumno) throws OperationNotSupportedException, SQLException {
        if (alumno == null) {
            throw new NullPointerException("ERROR: No se puede insertar un alumno nulo.");
        }
        if(buscar(alumno) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe un alumno con ese dni.");
        }
        String query = """
                INSERT INTO alumno
                (nombre, telefono, correo, dni, fechaNacimiento)
                VALUES
                (?, ?, ?, ?, ?)
                """;
        PreparedStatement pstmt = conexion.prepareStatement(query);
        pstmt.setString(1, alumno.getNombre());
        pstmt.setString(2, alumno.getTelefono());
        pstmt.setString(3, alumno.getCorreo());
        pstmt.setString(4, alumno.getDni());
        pstmt.setDate(5, java.sql.Date.valueOf(alumno.getFechaNacimiento()));
        pstmt.executeUpdate();
    }

    /**
     * Busca un alumno en la colección.
     *
     * @param alumno Alumno a buscar. No puede ser nulo.
     * @return Una copia del alumno si se encuentra, o null si no se encuentra.
     * @throws NullPointerException Si el alumno es nulo.
     * @throws SQLException Si hay problemas con la base de datos.
     */
    public Alumno buscar(Alumno alumno) throws SQLException {
        if (alumno == null) {
            throw new NullPointerException("ERROR: No se puede buscar un alumno nulo.");
        }
        String query = """
                SELECT nombre
                , telefono
                , correo
                , dni
                , fechaNacimiento
                FROM alumno
                WHERE dni = ?
                """;
        PreparedStatement pstmt = conexion.prepareStatement(query);
        pstmt.setString(1, alumno.getDni());
        ResultSet rs = pstmt.executeQuery();
        if (!rs.next()) return null;
        return new Alumno(
                rs.getString("nombre"),
                rs.getString("dni"),
                rs.getString("correo"),
                rs.getString("telefono"),
                rs.getDate("fechaNacimiento").toLocalDate()
        );
    }

    /**
     * Borra un alumno de la colección.
     *
     * @param alumno Alumno a borrar. No puede ser nulo.
     * @throws OperationNotSupportedException Si el alumno no existe en la colección.
     * @throws SQLException Si hay problemas con la base de datos.
     * @throws NullPointerException Si el alumno es nulo.
     */
    public void borrar(Alumno alumno) throws OperationNotSupportedException, SQLException {
        if (alumno == null) {
            throw new NullPointerException("ERROR: No se puede borrar un alumno nulo.");
        }
        if(buscar(alumno) == null) {
            throw new OperationNotSupportedException("ERROR: No existe ningún alumno como el indicado.");
        }
        String query = """
                DELETE FROM alumno
                WHERE dni = ?
                """;
        PreparedStatement pstmt = conexion.prepareStatement(query);
        pstmt.setString(1, alumno.getDni());
        pstmt.executeUpdate();
    }
}




