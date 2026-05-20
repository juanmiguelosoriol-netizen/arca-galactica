package model;

/**
 * Clase que implementa a los Alienigenas que seran los enemigos del juego
 * heredan atributos y metodos de la clase Entity
 */
public class Alien extends Entity {


    private int scoreValue;
    private int initialX;
    private int initialY;
    private int speed;

    /**
     * Constructor de la clase Alien.
     *
     * @param x          posicion en el eje x
     * @param y          posicion en el eje y
     * @param spriteName sprite de la imagen con la validacion de que no sea nula
     * @param speed      velocidad de movimiento del alien
     * @param scoreValue puntaje que otorga el alien al ser eliminado
     */
    public Alien(int x, int y, String spriteName, int speed, int scoreValue) {
        super(x, y, uploadImage(spriteName));

        this.initialX = x;
        this.initialY = y;
        this.speed = speed;
        this.scoreValue = scoreValue;

        setDirection(DIR_RIGHT);
    }

    /**
     * Metodo heredado de la clase Entity
     */
    @Override
    public void update() {
        if (isActive()) {
            setX(getX() + calculateDx() * speed);
            setY(getY() + calculateDy() * speed);

        }
    }

    /**
     * Metodo para revertir la direccion
     */
    public void reverseDirection() {
        if (getDirection() == DIR_RIGHT) {
            setDirection(DIR_LEFT);
        } else if (getDirection() == DIR_LEFT) {
            setDirection(DIR_RIGHT);
        }
    }

    /**
     * Metodo para que el alien descienda
     *
     * @param pixels cantidad de píxeles que descenderá el alien.
     */
    public void moveDown(int pixels) {
        setY(getY() + pixels);
    }

    /**
     * Metodo que verifica si el alien esta muerto
     */
    public void die() {
        setActive(false);
    }

    /**
     * Metodo que reinicia el alien
     */
    public void restart() {
        setX(initialX);
        setY(initialY);
        setActive(true);
        setDirection(DIR_RIGHT);
    }


    //getters y setters

    /**
     * Obtiene la posición inicial del alien en el eje X.
     *
     * @return posición inicial en el eje X.
     */
    public int getInitialX() {
        return initialX;
    }

    /**
     * Modifica la posición inicial del alien en el eje X.
     *
     * @param initialX nueva posición inicial en el eje X.
     */
    public void setInitialX(int initialX) {
        this.initialX = initialX;
    }

    /**
     * Obtiene la posición inicial del alien en el eje Y.
     *
     * @return posición inicial en el eje Y.
     */
    public int getInitialY() {
        return initialY;
    }

    /**
     * Modifica la posición inicial del alien en el eje Y.
     *
     * @param initialY nueva posición inicial en el eje Y.
     */
    public void setInitialY(int initialY) {
        this.initialY = initialY;
    }

    /**
     * Obtiene el puntaje que entrega el alien al ser eliminado.
     *
     * @return valor en puntos del alien.
     */
    public int getScoreValue() {
        return scoreValue;
    }

    /**
     * Modifica el puntaje que entrega el alien al ser eliminado.
     *
     * @param scoreValue nuevo valor en puntos del alien.
     */
    public void setScoreValue(int scoreValue) {
        this.scoreValue = scoreValue;
    }

    /**
     * Obtiene la velocidad de movimiento del alien.
     *
     * @return velocidad actual del alien.
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Modifica la velocidad de movimiento del alien.
     *
     * @param speed nueva velocidad del alien.
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }
}




