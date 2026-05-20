package model;

/**
 * Clase abstracta que representa un poder del juego.
 * Los power-ups pueden caer en la pantalla y aplicar un efecto
 * cuando la nave los recoge.
 */
public abstract class PowerUp extends Entity {

    private int speed;

    /**
     * Constructor de la clase PowerUp.
     *
     * @param x          posicion inicial en el eje x
     * @param y          posicion inicial en el eje y
     * @param nameSprite nombre de la imagen del power-up
     * @param speed      velocidad de caida
     */
    public PowerUp(int x, int y, String nameSprite, int speed) {
        super(x, y, uploadImage(nameSprite));

        this.speed = speed;
        setDirection(Entity.DIR_DOWN);
    }

    /**
     * Actualiza la posicion del power-up.
     * El power-up cae hacia abajo.
     */
    @Override
    public void update() {
        if (isActive()) {
            setY(getY() + calculateDy() * speed);
        }
    }

    /**
     * Aplica el efecto del power-up sobre la nave.
     *
     * @param ship nave del jugador
     */
    public abstract void applyEffect(Ship ship);

    /**
     * Verifica si el power-up salio del tablero.
     *
     * @param boardHeight alto del tablero
     */
    public void checkOutOfBoard(int boardHeight) {
        if (getY() > boardHeight) {
            setActive(false);
        }
    }

    /**
     * Obtiene la velocidad de caída del power-up.
     *
     * @return velocidad del power-up.
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Modifica la velocidad de caída del power-up.
     *
     * @param speed nueva velocidad del power-up.
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }
}