package model;

/**
 * Alien verde básico.
 * Es el enemigo más sencillo y da menos puntos.
 */
public class GreenAlien extends Alien {

    public GreenAlien(int x, int y) {
        super(x, y, "alien1.png", 1, 10);
    }
}