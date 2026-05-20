package model;

/**
 * Representa al mono protegido en el juego.
 */
public class Monkey extends Animal {
    /**
     * Constructor de la clase Monkey
     *
     * @param x posición inicial en el eje X.
     * @param y posición inicial en el eje Y.
     */
    public Monkey(int x, int y) {
        super(x, y, "monkey.png", "Monkey");
    }
}