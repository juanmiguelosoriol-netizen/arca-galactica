package model;

/**
 * Representa a la vaca protegida en el juego.
 */
public class Cow extends Animal {
    /**
     * Constructor de la clase Cow
     *
     * @param x
     * @param y
     */
    public Cow(int x, int y) {
        super(x, y, "cow.png", "Cow");
    }
}