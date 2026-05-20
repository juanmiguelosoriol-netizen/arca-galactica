package model;

/**
 * Alien verde básico.
 * Es el enemigo más sencillo y da menos puntos.
 */
public class GreenAlien extends Alien {

    /**
     * Crea un alien vverde en la posición indicada.
     *
     * @param x posición inicial en el eje X.
     * @param y posición inicial en el eje Y.
     */
    public GreenAlien(int x, int y) {
        super(x, y, "alien1.png", 1, 10);
    }
}