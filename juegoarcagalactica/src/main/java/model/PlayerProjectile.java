package model;

/**
 * Clase p  royectil disparado por la nave del jugador.
 */
public class PlayerProjectile extends Projectile {
    /**
     * Atributo que declara la velocidad del proyectil del jugador
     */
    private static final int SPEED = 5;

    /**
     * Constructor de la clase PlayerProjectile
     *
     * @param x posicion en el eje x
     * @param y posicion en el eje y
     */
    public PlayerProjectile(int x, int y) {
        super(x, y, "player_projectile.png", Entity.DIR_UP, SPEED);
    }

}
