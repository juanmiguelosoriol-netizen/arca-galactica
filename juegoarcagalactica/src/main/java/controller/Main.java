package controller;

import view.MainFrame;

/**
 * Clase principal del juego Arca Galáctica.
 * Desde aquí se ejecuta la aplicación.
 */
public class Main {

    /**
     * Constructor privado para evitar la cración de objetos de la clase principal.
     */
    private Main() {

    }

    /**
     * Punto de entrada principal de la aplicación.
     *
     * @param args argumentos recibidos desde la línea de comandos.
     */
    public static void main(String[] args) {
        new MainFrame();
    }
}