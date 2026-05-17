package model;

/**
 * Clase que crea la nave espacial que controlará el jugador
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
     * @return
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
     * @param boardWidth
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
        if (lives > 0) lives--;
        if (lives == 0) setActive(false);
    }

    /**
     * Metodo para que el jugador pueda recuperar vidasl.
     */
    public void recoverLife() {
        if (lives < INITIAL_LIVES) {
            lives++;
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

    public void activateDoublePoints() {
        doublePoints = true;
    }

    public void deactivateDoublePoints() {
        doublePoints = false;
    }

    public boolean isDoublePoints() {
        return doublePoints;
    }

    //getters y setters

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
