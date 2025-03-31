package org.iesalandalus.programacion.matriculacion.modelo.negocio;

/**
 * Interfaz que define los métodos necesarios para la creación de los distintos servicios de datos
 * relacionados con las entidades del sistema, como los alumnos, asignaturas, ciclos formativos y matrículas.
 * Las clases que implementen esta interfaz proporcionarán la lógica de acceso a los datos para cada una de estas entidades.
 * Esta interfaz proporciona métodos para crear instancias de las interfaces específicas que gestionan
 * cada tipo de entidad, permitiendo que los datos sean gestionados de forma independiente dependiendo
 * de la fuente de datos utilizada (por ejemplo, memoria o base de datos).
 */
public interface IFuenteDatos {

    public IAlumnos crearAlumnos();

    public IAsignaturas crearAsignaturas();

    public ICiclosFormativos crearCiclosFormativos();

    public IMatriculas crearMatriculas();
}
