package model;

/**
 * Representa un power-up que activa puntos dobles para el jugador.
 */
public class DoublePoints extends PowerUp {

    private static final int SPEED = 2;

    /**
     * Constructor de la clase  Double points.
     *
     * @param x posicion inicial en el eje x
     * @param y posicion inicial en el eje y
     */
    public DoublePoints(int x, int y) {
        super(x, y, "double_points.png", SPEED);
    }


    @Override
    public void applyEffect(Ship ship) {
        ship.activateDoublePoints();
        setActive(false);
    }
}
