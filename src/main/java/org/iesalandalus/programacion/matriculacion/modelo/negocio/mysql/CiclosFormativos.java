package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.ICiclosFormativos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL;

import javax.naming.OperationNotSupportedException;
import java.sql.*;
import java.util.ArrayList;

/**
 * Clase que gestiona una colección de ciclos formativos.
 * Permite insertar, buscar, borrar y obtener información sobre los ciclos formativos.
 */
public class CiclosFormativos implements ICiclosFormativos {

    private Connection conexion;
    private static CiclosFormativos instancia = null;
    private ArrayList<CicloFormativo> coleccionCiclosFormativos;

    /**
     * Constructor que inicializa la colección de ciclos formativos.
     */
    public CiclosFormativos () {
        this.coleccionCiclosFormativos = new ArrayList<>();
        comenzar();
    }

    /**
     * Devuelve la instancia de la clase.
     *
     * @return La instancia de la clase.
     */
    public static CiclosFormativos getInstancia() {
        if (instancia == null) {
            instancia = new CiclosFormativos();
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
     * Devuelve un ciclo formativo con los datos introducidos.
     */
    public Grado getGrado(String tipoGrado, String nombreGrado, int numAniosGrado, String modalidad, int numEdiciones) {
        Grado grado = null;
        if (tipoGrado.equals("gradod")) {
            Modalidad modalidadGrado = Modalidad.valueOf(modalidad.toUpperCase());
            grado = new GradoD(nombreGrado, numAniosGrado, modalidadGrado);
        }else {
            grado = new GradoE(nombreGrado, numAniosGrado, numEdiciones);
        }
        return grado;
    }

    /**
     * Obtiene una copia profunda de la colección de ciclos formativos.
     *
     * @return Un array con una copia de los ciclos formativos.
     * @throws SQLException Si hay problemas con la base de datos.
     */
    public ArrayList<CicloFormativo> get() throws SQLException {
        ArrayList<CicloFormativo> copiaCiclosFormativos = new ArrayList<>();
        String query = """
                SELECT cf.codigo
                    , cf.familiaProfesional as familiaprofesional
                    , cf.grado
                    , cf.nombre
                    , cf.horas
                    , cf.nombreGrado
                    , cf.numAniosGrado
                    , cf.modalidad
                    , cf.numEdiciones
                    FROM cicloFormativo cf
                    ORDER BY cf.nombre
                """;
        Statement stmt = conexion.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            Grado grado = getGrado(rs.getString(3), rs.getString(6), rs.getInt(7), rs.getString(8), rs.getInt(9));
            CicloFormativo cicloFormativo = new CicloFormativo(rs.getInt(1), rs.getString(2), grado, rs.getString(4), rs.getInt(5));
            copiaCiclosFormativos.add(cicloFormativo);
        }
        return copiaCiclosFormativos;
    }


    /**
     * Obtiene el tamaño actual de la colección (número de ciclos formativos almacenados).
     *
     * @return El tamaño actual de la colección.
     * @throws SQLException Si hay problemas con la base de datos.
     */
    public int getTamano() throws SQLException { //cuenta la primera columna (1 es codigo)
        String query = """
                SELECT COUNT(1) as n
                FROM cicloFormativo
                """;
        Statement stmt = conexion.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        if (rs.next()) {
            return rs.getInt("n"); // o rs.getInt(1)
        } else {
            return 0;
        }
    }

    /**
     * Inserta un ciclo formativo en la colección.
     *
     * @param cicloFormativo El ciclo formativo a insertar.
     * @throws OperationNotSupportedException Si no se pueden insertar más ciclos formativos
     *         o si ya existe un ciclo formativo con el mismo código.
     * @throws NullPointerException Si el ciclo formativo es nulo.
     */
    public void insertar(CicloFormativo cicloFormativo) throws OperationNotSupportedException, SQLException {
        if (cicloFormativo == null) {
            throw new NullPointerException("ERROR: No se puede insertar un ciclo formativo nulo.");
        }
        if (buscar(cicloFormativo) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe un ciclo formativo con ese código.");
        }
        String query = """
                INSERT INTO cicloFormativo
                     (codigo,
                      familiaProfesional,
                      grado,
                      nombre,
                      horas,
                      nombreGrado,
                      numAniosGrado,
                      modalidad,
                      numEdiciones)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        PreparedStatement pstmt = conexion.prepareStatement(query);
        pstmt.setInt(1, cicloFormativo.getCodigo());
        pstmt.setString(2, cicloFormativo.getFamiliaProfesional());
        pstmt.setString(3, cicloFormativo.getGrado().getClass().getSimpleName().toLowerCase());
        pstmt.setString(4, cicloFormativo.getNombre());
        pstmt.setInt(5, cicloFormativo.getHoras());
        pstmt.setString(6, cicloFormativo.getGrado().getNombre());
        pstmt.setInt(7, cicloFormativo.getGrado().getNumAnios());
        if(cicloFormativo.getGrado() instanceof GradoD) {
            pstmt.setString(8, ((GradoD)cicloFormativo.getGrado()).getModalidad().toString());
            pstmt.setNull(9, java.sql.Types.INTEGER);
        }else {
            pstmt.setNull(8, java.sql.Types.VARCHAR);
            pstmt.setInt(9, ((GradoE) cicloFormativo.getGrado()).getNumEdiciones());
        }
        pstmt.executeUpdate();
    }

    /**
     * Busca un ciclo formativo en la colección.
     *
     * @param cicloFormativo El ciclo formativo a buscar.
     * @return El ciclo formativo encontrado o null si no se encuentra.
     * @throws SQLException Si hay problemas con la base de datos.
     * @throws NullPointerException Si el ciclo formativo es nulo.
     */
    public CicloFormativo buscar(CicloFormativo cicloFormativo) throws SQLException {
        if (cicloFormativo == null) {
            throw new NullPointerException("ERROR: No se puede buscar un ciclo formativo nulo.");
        }
        String query = """
        		SELECT codigo
        			, familiaProfesional
        			,grado
        			,nombre
        			,horas
        			,nombreGrado
        			,numAniosGrado
        			,modalidad
        			,numEdiciones
        		FROM cicloFormativo
        		WHERE codigo = ?
        		""";
        PreparedStatement pstmt = conexion.prepareStatement(query);
        pstmt.setInt(1, cicloFormativo.getCodigo());
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            Grado grado = null;
            if(rs.getString(3).equals("gradod")) {
                grado = new GradoD(rs.getString(6), rs.getInt(7), Modalidad.valueOf(rs.getString(8).toUpperCase()));
            }else {
                grado = new GradoE(rs.getString(6), rs.getInt(7), rs.getInt(9));
            }
            return new CicloFormativo(rs.getInt(1), rs.getString(2), grado, rs.getString(4), rs.getInt(5));
        }
        return null;
    }

    /**
     * Borra un ciclo formativo de la colección.
     *
     * @param cicloFormativo El ciclo formativo a borrar.
     * @throws OperationNotSupportedException Si no existe el ciclo formativo a borrar.
     * @throws SQLException Si hay problemas con la base de datos.
     * @throws NullPointerException Si el ciclo formativo es nulo.
     */
    public void borrar(CicloFormativo cicloFormativo) throws OperationNotSupportedException, SQLException {
        if (cicloFormativo == null) {
            throw new NullPointerException("ERROR: No se puede borrar un ciclo formativo nulo.");
        }
        if (buscar(cicloFormativo) == null) {
            throw new OperationNotSupportedException("ERROR: No existe ningún ciclo formativo como el indicado.");
        }
        String query = """
				DELETE FROM cicloFormativo
				WHERE codigo = ?
				""";
        PreparedStatement pstmt = conexion.prepareStatement(query);
        pstmt.setInt(1, cicloFormativo.getCodigo());
        pstmt.executeUpdate();
    }
}
