package model;

/**
 * Clase que crea la nave espacial que controlarÃ¡ el jugador
 * hereda atributos y metodos de la clase Entity
 */
public class Ship extends Entity {
    /**
     * Atributos que declaran: Posicion en eje X y Y, velocidad de la nave, vidas iniciales, puntaje y la variable de las vidas.
     */
    //variables estaticas
    private static final int X_INITIAL = 240;
    private static final int Y_INITIAL = 468;
    private static final int SPEED = 4;
    private static final int INITIAL_LIVES = 3;
    private int score;
    private int lives;
    private boolean doublePoints; //para el power up de doble puntos

    /**
     * Constructor de la clase Ship
     *
     */
    //constructor por defecto
    public Ship() {
        super(X_INITIAL, Y_INITIAL, uploadImage("ship.png"));
        this.score = 0;
        this.lives = INITIAL_LIVES;

        this.doublePoints = false;

        setDirection(DIR_NONE);
    }

    /**
     * Metodo heredado de la clase Entity
     */
    @Override
    public void update() {
        setX(getX() + calculateDx() * SPEED);

    }

    /**
     * Metodo que hace que salga un proyectil desde la nave
     *
     * @return proyectil creado desde la posicion de la nave
     */
    public PlayerProjectile shoot() {
        int projectileX = getX() + (getWidth() / 2) - 3; //se divide entre 2 para que slaga de la mitad de la nave
        int projectileY = getY();

        return new PlayerProjectile(projectileX, projectileY);
    }

    /**
     * Metodo para sumerle puntos al jugador
     *
     * @param points puntos del jugador
     */
    public void addPoints(int points) {
        if (doublePoints) {
            this.score += points * 2; //x2 al estar en doble puntos
        } else {
            this.score += points;
        }
    }

    /**
     * Metodo que reinicia la posicion de la nave a la inicial
     */
    public void resetPosition() {
        setX(X_INITIAL);
        setY(Y_INITIAL);
        setDirection(DIR_NONE);
        setActive(true);
        setSprite(uploadImage("ship.png"));

    }

    /**
     * Metodo que reinicia la posicion y las vidas, puede usarse cuando el jugador pierde pero se inicia otra partida nueva.
     */
    public void resetAll() {
        resetPosition();
        this.score = 0;
        this.lives = INITIAL_LIVES;
        this.doublePoints = false;
    }

    /**
     * Metodo que ayuda a que la entidad no se salga de la ventana del juego
     *
     * @param boardWidth ancho del tablero de juego
     */
    public void keepInside(int boardWidth) {
        if (getX() < 0) {
            setX(0);
        }

        if (getX() + getWidth() > boardWidth) {
            setX(boardWidth - getWidth());
        }
    }

    /**
     * Metodo para que el jugador pierda vidas
     */
    public void loseLife() {
        if (lives > 0) {
            lives--;
            if (lives == 2) {
                setSprite(uploadImage("ship_damaged.png"));
            } else if (lives == 1) {
                setSprite(uploadImage("ship_semi_destroyed.png"));
            }
        }
        if (lives == 0) {
            setActive(false);

        }
    }

    /**
     * Metodo para que el jugador pueda recuperar vidasl.
     */
    public void recoverLife() {
        if (lives < INITIAL_LIVES) {
            lives++;
            setSprite(uploadImage("ship_healed.png"));
        }
    }

    /**
     * Metodo para mover la nave hacia la izquierda
     */
    public void moveLeft() {
        setDirection(DIR_LEFT);
    }

    /**
     * Metodo para mover la nave hacia la derecha
     */
    public void moveRight() {
        setDirection(DIR_RIGHT);
    }

    /**
     * Metodo para detener la nave
     */
    public void stop() {
        setDirection(DIR_NONE);
    }

    /**
     * Activa el power-up de doble puntaje.
     */
    public void activateDoublePoints() {
        doublePoints = true;
    }

    /**
     * Desactiva el power-up de doble puntaje.
     */
    public void deactivateDoublePoints() {
        doublePoints = false;
    }

    /**
     * Indica si la nave tiene activo el doble puntaje.
     *
     * @return true si el doble puntaje esta activo, false si no
     */
    public boolean isDoublePoints() {
        return doublePoints;
    }

    //getters y setters

    /**
     * Retorna las vidas actuales de la nave.
     *
     * @return vidas actuales de la nave
     */
    public int getLives() {
        return lives;
    }

    /**
     * Cambia las vidas actuales de la nave.
     *
     * @param lives nuevas vidas de la nave
     */
    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * Retorna el puntaje actual de la nave.
     *
     * @return puntaje actual de la nave
     */
    public int getScore() {
        return score;
    }

    /**
     * Cambia el puntaje actual de la nave.
     *
     * @param score nuevo puntaje de la nave
     */
    public void setScore(int score) {
        this.score = score;
    }
}