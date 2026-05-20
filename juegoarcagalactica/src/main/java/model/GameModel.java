package model;

import java.util.ArrayList;

/**
 * Modelo principal del juego.
 * Guarda los objetos principales de Arca GalÃ¡ctica.
 */
public class GameModel {

    /**
     * Ancho del tablero de juego en píxeles.
     */
    public static final int BOARD_WIDTH = 504;

    /**
     * Alto del tablero de juego en píxeles.
     */
    public static final int BOARD_HEIGHT = 504;

    private Ship ship;
    private Alien[] aliens;
    private Animal[] animals;

    private ArrayList<PlayerProjectile> playerProjectiles;
    private ArrayList<AlienProjectile> alienProjectiles;
    private ArrayList<PowerUp> powerUps;

    /**
     * Crea el modelo principal del juego e inicializa la nave,
     * los aliens, los animales, los proyectiles y los power-ups.
     */
    public GameModel() {
        ship = new Ship();

        aliens = new Alien[4];
        animals = new Animal[3];

        playerProjectiles = new ArrayList<>();
        alienProjectiles = new ArrayList<>();
        powerUps = new ArrayList<>();

        createAliens();
        createAnimals();
    }

    private void createAliens() {
        aliens[0] = new GreenAlien(96, 72);
        aliens[1] = new PurpleAlien(240, 72);
        aliens[2] = new RedAlien(384, 72);
        aliens[3] = new GreenAlien(400, 72);


    }

    private void createAnimals() {
        animals[0] = new Lion(48, 408);
        animals[1] = new Cow(204, 408);
        animals[2] = new Monkey(360, 408);
    }

    /**
     * Actualiza el estado general del modelo del juego.
     * Mueve la nave, aliens, animales, proyectiles y power-ups.
     */
    public void update() {
        ship.update();
        ship.keepInside(BOARD_WIDTH);

        for (int i = 0; i < aliens.length; i++) {
            aliens[i].update();
        }

        for (int i = 0; i < animals.length; i++) {
            animals[i].update();
        }

        for (int i = 0; i < playerProjectiles.size(); i++) {
            PlayerProjectile projectile = playerProjectiles.get(i);
            projectile.update();
            projectile.checkOutOfBoard(BOARD_WIDTH, BOARD_HEIGHT);
        }

        for (int i = 0; i < alienProjectiles.size(); i++) {
            AlienProjectile projectile = alienProjectiles.get(i);
            projectile.update();
            projectile.checkOutOfBoard(BOARD_WIDTH, BOARD_HEIGHT);
        }

        for (int i = 0; i < powerUps.size(); i++) {
            PowerUp powerUp = powerUps.get(i);
            powerUp.update();
            powerUp.checkOutOfBoard(BOARD_HEIGHT);
        }

        removeInactiveObjects();
    }

    private void removeInactiveObjects() {
        for (int i = playerProjectiles.size() - 1; i >= 0; i--) {
            if (!playerProjectiles.get(i).isActive()) {
                playerProjectiles.remove(i);
            }
        }

        for (int i = alienProjectiles.size() - 1; i >= 0; i--) {
            if (!alienProjectiles.get(i).isActive()) {
                alienProjectiles.remove(i);
            }
        }

        for (int i = powerUps.size() - 1; i >= 0; i--) {
            if (!powerUps.get(i).isActive()) {
                powerUps.remove(i);
            }
        }
    }

    /**
     * Crea y agrega un proyectil disparado por la nave del jugador.
     */
    public void shootPlayerProjectile() {
        playerProjectiles.add(ship.shoot());
    }

    /**
     * Agrega un proyectil disparado por un alien.
     *
     * @param alienProjectile proyectil del alien que se agregará al modelo.
     */
    public void addAlienProjectile(AlienProjectile alienProjectile) {
        alienProjectiles.add(alienProjectile);
    }

    /**
     * Agrega un power-up al juego.
     *
     * @param powerUp power-up que se agrega al juego
     */
    public void addPowerUp(PowerUp powerUp) {
        powerUps.add(powerUp);
    }

    /**
     * Verifica si todos los aliens fueron eliminados.
     *
     * @return true si todos los aliens están inactivos, false si queda alguno activo
     */
    public boolean allAliensDead() {
        for (int i = 0; i < aliens.length; i++) {
            if (aliens[i].isActive()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Verifica si todos los animales fueron eliminados.
     *
     * @return true si todos los animales están inactivos, false si queda alguno activo
     */
    public boolean allAnimalsLost() {
        for (int i = 0; i < animals.length; i++) {
            if (animals[i].isActive()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Marca como rescatados a los animales que siguen activos.
     */
    public void rescueAnimals() {
        for (int i = 0; i < animals.length; i++) {
            if (animals[i].isActive()) {
                animals[i].rescue();
            }
        }
    }

    /**
     * Reinicia el modelo para comenzar una nueva partida.
     */
    public void restart() {
        ship.resetAll();

        playerProjectiles.clear();
        alienProjectiles.clear();
        powerUps.clear();

        createAliens();
        createAnimals();
    }


    //getters y setters

    /**
     * Retorna la nave del jugador.
     *
     * @return nave del jugador
     */
    public Ship getShip() {
        return ship;
    }

    /**
     * Retorna los aliens del juego.
     *
     * @return arreglo con los aliens
     */
    public Alien[] getAliens() {
        return aliens;
    }

    /**
     * Retorna los animales del juego.
     *
     * @return arreglo con los animales
     */
    public Animal[] getAnimals() {
        return animals;
    }

    /**
     * Retorna los proyectiles disparados por el jugador.
     *
     * @return lista de proyectiles del jugador
     */
    public ArrayList<PlayerProjectile> getPlayerProjectiles() {
        return playerProjectiles;
    }

    /**
     * Retorna los proyectiles disparados por los aliens.
     *
     * @return lista de proyectiles de aliens
     */
    public ArrayList<AlienProjectile> getAlienProjectiles() {
        return alienProjectiles;
    }

    /**
     * Retorna los power-ups del juego.
     *
     * @return lista de power-ups
     */
    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }
}