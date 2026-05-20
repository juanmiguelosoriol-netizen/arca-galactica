package model;

/**
 * Representa a la vaca protegida en el juego.
 */
public class Cow extends Animal {
    /**
     * Constructor de la clase Cow
     *
     * @param x posición inicial en el eje X.
     * @param y posición inicial en el eje Y.
     */
    public Cow(int x, int y) {
        super(x, y, "cow.png", "Cow");
    }
}