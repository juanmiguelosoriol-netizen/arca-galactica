package model;

/**
 * Alien rojo avanzado.
 * Es el más rápido y da más puntos.
 */
public class RedAlien extends Alien {

    public RedAlien(int x, int y) {
        super(x, y, "alien3.png", 3, 30);
    }
}