package model;

/**
 * Clase que implementa los animales que debemos proteger
 * hereda los atributos y metodos de la clase entity.
 * El animal permanece quieto y tiene vidas.
 */
public class Animal extends Entity {

    /**
     * Atributos que declaran: Vidas de los animales, nombre del animal y  la variable para las vidas,.
     */
    private static final int INITIAL_LIVES = 3;
    private final String name;
    private int lives;
    private boolean rescued;


    /**
     * Constructor de la clase Animal.
     *
     * @param x          posicion en eje x
     * @param y          posicion en eje y
     * @param spriteName sprite de la imagen con la validacion de que no sea nula
     * @param name       nombre del animal
     */
    public Animal(int x, int y, String spriteName, String name) {
        super(x, y, uploadImage(spriteName));
        this.name = name;
        this.lives = INITIAL_LIVES;
        this.rescued = false;

        setDirection(DIR_NONE);
    }

    /**
     * Metodo heredado de la clase Entity
     */
    @Override
    public void update() {
        //el animal mantiene estatico al estar enjaulado

    }

    /**
     * Metodo para hacer que el animal pierda vida
     */

    public void loseLife() {
        if (lives > 1) lives--;
        else {
            lives = 0;
            setActive(false);
        }
    }

    /**
     * Metodo paraverificar si el animal esta rescatado
     */
    public void rescue() {
        if (isActive()) {
            rescued = true;
            setActive(false);
        }
    }

    /**
     * Metodo para reiniciar el animal cuando se reinicie el juego
     */
    public void restart() {
        lives = INITIAL_LIVES;
        rescued = false;
        setActive(true);
    }


    //getters y setters

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public String getName() {
        return name;
    }

    public boolean isRescued() {
        return rescued;
    }

    public void setRescued(boolean rescued) {
        this.rescued = rescued;
    }
}
