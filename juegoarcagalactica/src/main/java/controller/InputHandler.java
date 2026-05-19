package controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Clase encargada de manejar las teclas del juego.
 * Permite mover la nave a la izquierda, a la derecha
 * y disparar con la tecla espacio.
 */
public class InputHandler extends KeyAdapter {

    private GameController gameController;

    public InputHandler(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            gameController.moveShipLeft();
        }

        if (key == KeyEvent.VK_RIGHT) {
            gameController.moveShipRight();
        }

        if (key == KeyEvent.VK_SPACE) {
            gameController.shootPlayerProjectile();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A ||
                key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            gameController.stopShip();
        }
    }
}