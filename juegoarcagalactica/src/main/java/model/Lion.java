package model;

/**
 * Representa al león protegido en el juego.
 */
public class Lion extends Animal {
    /**
     * Constructor de la clase Lion
     *
     * @param x posición inicial en el eje X.
     * @param y posición inicial en el eje Y.
     */
    public Lion(int x, int y) {
        super(x, y, "lion.png", "Lion");
    }
}
