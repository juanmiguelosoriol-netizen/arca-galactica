package model;

/**
 * Alien morado intermedio.
 * Tiene mayor velocidad y da más puntos.
 */
public class PurpleAlien extends Alien {

    /**
     * Crea un alien morado en la posición indicada.
     *
     * @param x posición inicial en el eje X.
     * @param y posición inicial en el eje Y.
     */
    public PurpleAlien(int x, int y) {
        super(x, y, "alien2.png", 2, 20);
    }
}