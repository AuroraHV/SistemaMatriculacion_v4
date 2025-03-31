package org.iesalandalus.programacion.matriculacion;

import org.iesalandalus.programacion.matriculacion.controlador.Controlador;
import org.iesalandalus.programacion.matriculacion.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.matriculacion.modelo.Modelo;
import org.iesalandalus.programacion.matriculacion.vista.Vista;

/**
 * Clase principal que inicia la aplicación del sistema de matriculación.
 * Se encarga de crear e inicializar el modelo, la vista y el controlador, e iniciar el flujo de ejecución.
 */
public class MainApp {

    /**
     * Método principal que se ejecuta al iniciar la aplicación.
     * Se encarga de crear las instancias necesarias del modelo, vista y controlador,
     * y de invocar los métodos para iniciar y terminar la ejecución de la aplicación.
     *
     * @param args Argumentos de la línea de comandos. Permiten seleccionar la fuente de datos.
     */
    public static void main(String[] args) {
        //USAR BASE DE DATOS CON MYSQL O USAR MEMORIA
        //args = new String[]{"-fdmysql"};
        //args = new String[]{"-fdmemoria"};

        try {
            // Creación del modelo, que maneja los datos de la aplicación.
            Modelo modelo = procesarArgumentosFuenteDatos(args);

            // Creación de la vista, que interactúa con el usuario.
            Vista vista = new Vista();

            // Creación del controlador, que coordina la interacción entre el modelo y la vista.
            Controlador controlador = new Controlador(modelo, vista);

            // Inicia la ejecución de la aplicación, comenzando el modelo y la vista.
            controlador.comenzar();

            // Termina la ejecución de la aplicación, terminando el modelo y la vista.
            controlador.terminar();

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    /**
     * Procesa los argumentos de la línea de comandos para determinar la fuente de datos.
     * Si no se proporciona ningún argumento, se usa la fuente de datos en memoria por defecto.
     * @param args Argumentos de la línea de comandos.
     * @return Un objeto {@code Modelo} configurado con la fuente de datos seleccionada.
     */
    private static Modelo procesarArgumentosFuenteDatos(String[] args) {
        if (args.length == 0) {

            //Fuente de datos por defecto. Muestra un mensaje de inicio y usa la fuente de datos en memoria.
            System.out.println("Fuente de datos por defecto: memoria.");
            return new Modelo(FactoriaFuenteDatos.MEMORIA);
        }else {
            if (args[0].equalsIgnoreCase("-fdmysql")) {

                //Fuente de datos en MySQL. Muestra un mensaje de inicio y usa la fuente de datos en MySQL.
                System.out.println("----------------------------------------");
                System.out.println("Fuente de datos seleccionada: MySQL.");
                System.out.println("----------------------------------------");
                return new Modelo(FactoriaFuenteDatos.MYSQL);
            }else if (args[0].equalsIgnoreCase("-fdmemoria")) {

                //Fuente de datos en memoria. Muestra un mensaje de inicio y usa la fuente de datos en memoria.
                System.out.println("----------------------------------------");
                System.out.println("Fuente de datos seleccionada: Memoria.");
                System.out.println("-----------------------------------------");
                System.out.println("Iniciando sistema de matriculación en memoria...");
                return new Modelo(FactoriaFuenteDatos.MEMORIA);
            }else {

                //Fuente de datos no reconocida. Muestra un mensaje de error y usa la fuente de datos en memoria por defecto.
                System.out.println("----------------------------------------------------------");
                System.out.println("Fuente de datos no reconocida.Usando memoria por defecto.");
                System.out.println("----------------------------------------------------------");
                return new Modelo(FactoriaFuenteDatos.MEMORIA);
            }
        }
    }
}

