package model;

/**
 * Representa un power-up que permite recuperar una vida.
 */
public class HeartPowerUp extends PowerUp {
    private static final int SPEED = 2;

    /**
     * Constructor del power-up de vida.
     *
     * @param x posicion inicial en el eje x
     * @param y posicion inicial en el eje y
     */
    public HeartPowerUp(int x, int y) {
        super(x, y, "heart.png", SPEED);
    }

    /**
     * Aplica el efecto del corazón sobre la nave.
     * La nave recupera una vida.
     *
     * @param ship nave del jugador
     */
    @Override
    public void applyEffect(Ship ship) {
        ship.recoverLife();
        setActive(false);
    }
}

