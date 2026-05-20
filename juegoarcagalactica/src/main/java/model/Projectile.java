package model;

/**
 * Clase que implementa los proyectiles que seran disparados ya sea desde la nave o desde los aliens
 * hereda atributos y metodos de la clase Entity
 */
public abstract class Projectile extends Entity {

    private int speed;

    /**
     * Constructor de la clase Projectile.
     *
     * @param x          posicion inicial en el eje x
     * @param y          posicion inicial en el eje y
     * @param nameSprite nombre del sprite del proyectil
     * @param direction  direccion en la que se mueve el proyectil
     * @param speed      velocidad del proyectil
     */
    public Projectile(int x, int y, String nameSprite, int direction, int speed) {
        super(x, y, uploadImage(nameSprite));
        this.speed = speed;
        setDirection(direction);

    }

    /**
     * Metodo heredado de la clase Entity
     */
    @Override
    public void update() {
        setX(getX() + calculateDx() * speed);
        setY(getY() + calculateDy() * speed);
    }

    /**
     * metodo que chequea si el proyectil salio del tablero
     *
     * @param boardWidth  ancho del tablero
     * @param boardHeight alto del tablero
     */
    public void checkOutOfBoard(int boardWidth, int boardHeight) {
        if (getX() < 0 || getX() > boardWidth || getY() < 0 || getY() > boardHeight) {
            setActive(false);
        }
    }


    //getters y setters

    /**
     * Retorna la velocidad del proyectil.
     *
     * @return velocidad del proyectil
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Cambia la velocidad del proyectil.
     *
     * @param speed nueva velocidad del proyectil
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
