package model;

/**
 * Proyectil disparado por los aliens.
 * Este proyectil se mueve hacia abajo y puede quitar vidas
 * a la nave o a los animales protegidos.
 */

public class AlienProjectile extends Projectile {
    /**
     * Atributo que declara la velocidad del proyectil del alien.
     */
    private static final int SPEED = 3;

    /**
     * Constructor de la clase AlienProjectile
     *
     * @param x posicion en el eje x
     * @param y posicion en el eje y
     */
    public AlienProjectile(int x, int y) {
        super(x, y, "alien_projectile.png", Entity.DIR_DOWN, SPEED);
    }
}
