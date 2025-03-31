package org.iesalandalus.programacion.matriculacion.modelo;

import org.iesalandalus.programacion.matriculacion.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria.FuenteDatosMemoria;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.FuenteDatosMySQL;

/**
 * Enum que representa las distintas fuentes de datos disponibles para la aplicación.
 * Define dos tipos de fuente de datos: MEMORIA y MYSQL.
 * Cada fuente de datos es capaz de crear una instancia correspondiente a la interfaz IFuenteDatos.
 * El propósito de este enum es proporcionar una forma flexible y extensible de cambiar la fuente de datos
 * sin modificar el resto de la aplicación.
 */
public enum FactoriaFuenteDatos {

    //Crea una instancia de la fuente de datos basada en memoria.
    MEMORIA {
        @Override
        public IFuenteDatos crear() {
            return new FuenteDatosMemoria();
        }
    },
    //Crea una instancia de la fuente de datos basada en MySQL.
    MYSQL {
        @Override
        public IFuenteDatos crear() {
            return new FuenteDatosMySQL();
        }
    };

    /**
     * Método abstracto que debe ser implementado por cada tipo de fuente de datos.
     * Crea una instancia de la fuente de datos correspondiente.
     *
     * @return Una instancia de la fuente de datos que implementa la interfaz IFuenteDatos.
     */
    public abstract IFuenteDatos crear();
}
