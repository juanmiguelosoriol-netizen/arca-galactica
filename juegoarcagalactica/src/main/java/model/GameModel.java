package model;

import java.util.ArrayList;

/**
 * Modelo principal del juego.
 * Guarda los objetos principales de Arca Galáctica.
 */
public class GameModel {

    public static final int BOARD_WIDTH = 504;
    public static final int BOARD_HEIGHT = 504;

    private Ship ship;
    private Alien[] aliens;
    private Animal[] animals;

    private ArrayList<PlayerProjectile> playerProjectiles;
    private ArrayList<AlienProjectile> alienProjectiles;
    private ArrayList<PowerUp> powerUps;

    public GameModel() {
        ship = new Ship();

        aliens = new Alien[3];
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
    }

    private void createAnimals() {
        animals[0] = new Lion(48, 408);
        animals[1] = new Cow(204, 408);
        animals[2] = new Monkey(360, 408);
    }

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

    public void shootPlayerProjectile() {
        playerProjectiles.add(ship.shoot());
    }

    public void addAlienProjectile(AlienProjectile alienProjectile) {
        alienProjectiles.add(alienProjectile);
    }

    public void addPowerUp(PowerUp powerUp) {
        powerUps.add(powerUp);
    }

    public boolean allAliensDead() {
        for (int i = 0; i < aliens.length; i++) {
            if (aliens[i].isActive()) {
                return false;
            }
        }

        return true;
    }

    public boolean allAnimalsLost() {
        for (int i = 0; i < animals.length; i++) {
            if (animals[i].isActive()) {
                return false;
            }
        }

        return true;
    }

    public void rescueAnimals() {
        for (int i = 0; i < animals.length; i++) {
            if (animals[i].isActive()) {
                animals[i].rescue();
            }
        }
    }

    public void restart() {
        ship.resetAll();

        playerProjectiles.clear();
        alienProjectiles.clear();
        powerUps.clear();

        createAliens();
        createAnimals();
    }


    //getters y setters
    public Ship getShip() {
        return ship;
    }

    public Alien[] getAliens() {
        return aliens;
    }

    public Animal[] getAnimals() {
        return animals;
    }

    public ArrayList<PlayerProjectile> getPlayerProjectiles() {
        return playerProjectiles;
    }

    public ArrayList<AlienProjectile> getAlienProjectiles() {
        return alienProjectiles;
    }

    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }
}