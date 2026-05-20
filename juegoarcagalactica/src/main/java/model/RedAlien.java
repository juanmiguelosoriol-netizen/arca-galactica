package model;

/**
 * Alien rojo avanzado.
 * Es el más rápido y da más puntos.
 */
public class RedAlien extends Alien {

    /**
     * Crea un alien rojo en la posicion indicada.
     *
     * @param x posición inicial en el eje X.
     * @param y posición inicial en el eje Y.
     */
    public RedAlien(int x, int y) {
        super(x, y, "alien3.png", 3, 30);
    }
}