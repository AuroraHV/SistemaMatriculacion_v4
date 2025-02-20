package org.iesalandalus.programacion.matriculacion;

import org.iesalandalus.programacion.matriculacion.controlador.Controlador;
import org.iesalandalus.programacion.matriculacion.modelo.Modelo;
import org.iesalandalus.programacion.matriculacion.vista.Vista;

public class MainApp {

    public static void main(String[] args) {
        try {
            // Crear el modelo
            Modelo modelo = new Modelo();

            // Crear la vista sin pasar el controlador
            Vista vista = new Vista();

            // Crear el controlador y pasar modelo y vista
            Controlador controlador = new Controlador(modelo, vista);

            // Iniciar la aplicación
            controlador.comenzar();
            controlador.terminar();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}

