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
     * @param pixels
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
    public int getInitialX() {
        return initialX;
    }

    public void setInitialX(int initialX) {
        this.initialX = initialX;
    }

    public int getInitialY() {
        return initialY;
    }

    public void setInitialY(int initialY) {
        this.initialY = initialY;
    }

    public int getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(int scoreValue) {
        this.scoreValue = scoreValue;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}




