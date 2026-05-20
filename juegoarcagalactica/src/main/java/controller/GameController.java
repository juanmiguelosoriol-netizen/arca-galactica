package controller;

import model.Alien;
import model.AlienProjectile;
import model.Animal;
import model.DoublePoints;
import model.GameModel;
import model.HeartPowerUp;
import model.PlayerProjectile;
import model.PlayerRecord;
import model.PowerUp;
import util.RankingManager;
import view.GamePanel;
import view.MainFrame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Controlador principal del juego.
 * Maneja el hilo principal, el timer, las colisiones,
 * los disparos, el ranking y los sonidos.
 */
public class GameController implements Runnable {

    public static final String STATUS_START = "inicio";
    public static final String STATUS_PLAYING = "jugando";
    public static final String STATUS_GAME_OVER = "gameover";
    public static final String STATUS_INSTRUCTIONS = "instrucciones";
    public static final String STATUS_WIN = "ganar";

    private static final int FPS_DELAY = 16; // controlamos la velocidad del hilo 16ms es casi igual a 60fps
    private static final int ALIEN_DOWN_STEP = 12;  // cuantos pixeles toca un alien en caso de tocar un borde
    private static final int ALIEN_SHOOT_DELAY = 90;
    private static final int POWER_UP_DELAY = 105;

    private MainFrame mainFrame;
    private GameModel model;
    private GamePanel view;

    private Thread gameThread;
    private boolean running;

    private String gameStatus;
    private String playerName;

    private Timer gameTimer;
    private int elapsedSeconds;

    private int frameCounter;

    private Random random;
    private RankingManager rankingManager;
    private boolean recordSaved;

    private Clip backgroundMusic;
    private Clip soundEffect; //este es el sonif¿do que se este reproduciendo actualmente que no se guardaba


    /**
     * Constructor de la clase GameController
     *
     * @param view
     * @param mainFrame
     */
    public GameController(GamePanel view, MainFrame mainFrame) {
        this.view = view;
        this.mainFrame = mainFrame;
        this.model = new GameModel();

        this.gameStatus = STATUS_START;
        this.playerName = "Jugador";
        this.elapsedSeconds = 0;
        this.frameCounter = 0;
        this.running = false;
        this.recordSaved = false;

        this.random = new Random();
        this.rankingManager = new RankingManager();

        this.view.setModel(model);
        this.view.setGameStatus(gameStatus);
        this.view.setElapsedSeconds(elapsedSeconds);
        this.view.setTop3(rankingManager.getTop3());

        this.view.addKeyListener(new InputHandler(this));
        this.view.setFocusable(true);

        createTimer();

        playMusic("lobbymusic.wav");
    }


    /**
     * Muestra la pantalla de instrucciones.
     */
    public void showInstructions() {
        gameStatus = STATUS_INSTRUCTIONS;
        view.setGameStatus(gameStatus);
        view.repaint();
    }

    /**
     * Crea el Timer que cuenta los segundos de la partida.
     */
    private void createTimer() {
        gameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameStatus.equals(STATUS_PLAYING)) {
                    elapsedSeconds++;
                    view.setElapsedSeconds(elapsedSeconds);
                    view.repaint();
                }
            }
        });
    }

    /**
     * Inicia el hilo principal del juego.
     */
    public void startThread() {
        if (!running) {
            running = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    /**
     * Ciclo principal del juego.
     */
    @Override
    public void run() {
        while (running) {
            updateGame();
            view.repaint();

            try {
                Thread.sleep(FPS_DELAY);
            } catch (InterruptedException e) {
                running = false;
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Actualiza la lógica del juego.
     */
    private void updateGame() {
        if (!gameStatus.equals(STATUS_PLAYING)) {
            return;
        }

        frameCounter++;

        model.update();

        checkAlienBorders();
        createAlienProjectile();
        createPowerUp();

        checkPlayerProjectilesCollisions();
        checkAlienProjectilesCollisions();
        checkPowerUpCollisions();

        checkGameOver();
    }

    /**
     * Inicia la partida.
     */
    public void startGame() {
        if (gameStatus.equals(STATUS_PLAYING)) {
            return;
        }

        model.restart();

        gameStatus = STATUS_PLAYING;
        view.setGameStatus(gameStatus);

        elapsedSeconds = 0;
        frameCounter = 0;
        recordSaved = false;

        view.setElapsedSeconds(elapsedSeconds);

        stopAllSounds();
        playMusic("inGame.wav");

        gameTimer.start();
        view.requestFocusInWindow();
    }

    /**
     * Reinicia el juego y vuelve al inicio.
     */
    public void restartGame() {

        model.restart();


        gameStatus = STATUS_START;
        view.setGameStatus(gameStatus);

        elapsedSeconds = 0;
        frameCounter = 0;
        recordSaved = false;

        view.setElapsedSeconds(elapsedSeconds);
        view.setTop3(rankingManager.getTop3());

        gameTimer.stop();

        stopAllSounds();
        playMusic("lobbymusic.wav");

        view.requestFocusInWindow();
    }

    /**
     * Mueve la nave a la izquierda.
     */
    public void moveShipLeft() {
        if (gameStatus.equals(STATUS_PLAYING)) {
            model.getShip().moveLeft();
        }
    }

    /**
     * Mueve la nave a la derecha.
     */
    public void moveShipRight() {
        if (gameStatus.equals(STATUS_PLAYING)) {
            model.getShip().moveRight();
        }
    }

    /**
     * Detiene la nave.
     */
    public void stopShip() {
        if (gameStatus.equals(STATUS_PLAYING)) {
            model.getShip().stop();
        }
    }

    /**
     * Dispara un proyectil de la nave.
     */
    public void shootPlayerProjectile() {
        if (gameStatus.equals(STATUS_PLAYING)) {
            model.shootPlayerProjectile();
            playSound("laser.wav");
        }
    }

    /**
     * Revisa si los aliens tocaron los bordes del tablero.
     */
    private void checkAlienBorders() {
        boolean touchBorder = false;

        Alien[] aliens = model.getAliens();

        for (int i = 0; i < aliens.length; i++) {
            Alien alien = aliens[i];

            if (alien.isActive()) {
                if (alien.getX() <= 0 || alien.getX() + alien.getWidth() >= GameModel.BOARD_WIDTH) {
                    touchBorder = true;
                }
            }
        }

        if (touchBorder) {
            for (int i = 0; i < aliens.length; i++) {
                Alien alien = aliens[i];

                if (alien.isActive()) {
                    alien.reverseDirection();
                    alien.moveDown(ALIEN_DOWN_STEP);
                }
            }
        }
    }

    /**
     * Crea disparos de aliens cada cierto tiempo.
     */
    private void createAlienProjectile() {
        if (frameCounter % ALIEN_SHOOT_DELAY != 0) {
            return;
        }

        Alien alien = getRandomActiveAlien();

        if (alien != null) {
            int projectileX = alien.getX() + alien.getWidth() / 2 - 3;
            int projectileY = alien.getY() + alien.getHeight();

            model.addAlienProjectile(new AlienProjectile(projectileX, projectileY));
        }
    }

    /**
     * Retorna un alien activo aleatorio.
     */
    private Alien getRandomActiveAlien() {
        Alien[] aliens = model.getAliens();

        int activeAliens = 0;

        for (int i = 0; i < aliens.length; i++) {
            if (aliens[i].isActive()) {
                activeAliens++;
            }
        }

        if (activeAliens == 0) {
            return null;
        }

        int selected = random.nextInt(activeAliens);

        for (int i = 0; i < aliens.length; i++) {
            if (aliens[i].isActive()) {
                if (selected == 0) {
                    return aliens[i];
                }

                selected--;
            }
        }

        return null;
    }

    /**
     * Crea un corazón ocasionalmente y los puntos x2.
     */
    private void createPowerUp() {
        if (frameCounter % POWER_UP_DELAY != 0) {
            return;
        }

        int probability = random.nextInt(100);

        if (probability < 30) {
            int x = random.nextInt(GameModel.BOARD_WIDTH - 20);

            int type = random.nextInt(2);

            if (type == 0) {
                model.addPowerUp(new HeartPowerUp(x, 0));
            } else {
                model.addPowerUp(new DoublePoints(x, 0));
            }
        }
    }

    /**
     * Revisa colisiones entre disparos de la nave y aliens.
     */
    private void checkPlayerProjectilesCollisions() {
        ArrayList<PlayerProjectile> projectiles = model.getPlayerProjectiles();
        Alien[] aliens = model.getAliens();

        for (int i = 0; i < projectiles.size(); i++) {
            PlayerProjectile projectile = projectiles.get(i);

            if (projectile.isActive()) {
                for (int j = 0; j < aliens.length; j++) {
                    Alien alien = aliens[j];

                    if (alien.isActive()) {
                        if (projectile.getHitBox().intersects(alien.getHitBox())) {
                            projectile.setActive(false);
                            alien.die();
                            model.getShip().addPoints(alien.getScoreValue());
                            //playSound("laser.wav"); se puede poner sonuido pero baja mucho el rendimiento
                        }
                    }
                }
            }
        }
    }

    /**
     * Revisa colisiones entre disparos aliens, nave y animales.
     */
    private void checkAlienProjectilesCollisions() {
        ArrayList<AlienProjectile> projectiles = model.getAlienProjectiles();
        Animal[] animals = model.getAnimals();

        for (int i = 0; i < projectiles.size(); i++) {
            AlienProjectile projectile = projectiles.get(i);

            if (projectile.isActive()) {
                if (projectile.getHitBox().intersects(model.getShip().getHitBox())) {
                    projectile.setActive(false);
                    model.getShip().loseLife();

                    //playSound("laser.wav");se puede poner sonuido pero baja mucho el rendimiento
                }
            }

            if (projectile.isActive()) {
                for (int j = 0; j < animals.length; j++) {
                    Animal animal = animals[j];

                    if (animal.isActive()) {
                        if (projectile.getHitBox().intersects(animal.getHitBox())) {
                            projectile.setActive(false);
                            animal.loseLife();
                            //playSound("laser.wav"); se puede poner sonuido pero baja mucho el rendimiento
                        }
                    }
                }
            }
        }
    }

    /**
     * Revisa si la nave recoge un power-up.
     */
    private void checkPowerUpCollisions() {
        ArrayList<PowerUp> powerUps = model.getPowerUps();

        for (int i = 0; i < powerUps.size(); i++) {
            PowerUp powerUp = powerUps.get(i);

            if (powerUp.isActive()) {
                if (powerUp.getHitBox().intersects(model.getShip().getHitBox())) {
                    powerUp.applyEffect(model.getShip());
                    playSound("powerup.wav"); //sonido para cuando coja los power ups
                }
            }
        }
    }

    /**
     * Verifica si terminó la partida.
     */
    private void checkGameOver() {
        if (model.getShip().getLives() <= 0 || !model.getShip().isActive()) {

            finishGame();
        }

        if (model.allAnimalsLost()) {
            finishGame();
        }

        if (model.allAliensDead()) { //aca se poine la ventana de win
            model.rescueAnimals();

            finishGameWin();
        }
    }

    /**
     * Termina la partida y guarda el ranking.
     */
    private void finishGame() {
        gameStatus = STATUS_GAME_OVER;
        view.setGameStatus(gameStatus);
        mainFrame.showGameOverButtons();

        gameTimer.stop();

        stopMusic();
        playSound("gameover.wav");

        if (!recordSaved) {
            PlayerRecord record = new PlayerRecord(
                    playerName,
                    model.getShip().getScore(),
                    elapsedSeconds
            );

            rankingManager.saveRecord(record);
            view.setTop3(rankingManager.getTop3());

            recordSaved = true;
        }
    }

    /**
     * Metodo que termina la partida en caso de que se mataron a los aliens
     */
    private void finishGameWin() {
        gameStatus = STATUS_WIN;
        view.setGameStatus(gameStatus);
        mainFrame.showGameOverButtons();

        gameTimer.stop();

        stopAllSounds();
        playSound("gameover.wav");

        if (!recordSaved) {
            PlayerRecord record = new PlayerRecord(
                    playerName,
                    model.getShip().getScore(),
                    elapsedSeconds
            );

            rankingManager.saveRecord(record);
            view.setTop3(rankingManager.getTop3());

            recordSaved = true;
        }
    }

    /**
     * Reproduce un sonido una sola vez.
     */
    private void playSound(String soundName) {
        try {
            stopSoundEffect();

            InputStream inputStream = getClass().getResourceAsStream("/sounds/" + soundName);

            if (inputStream == null) {
                System.out.println("No se encontró el sonido: /sounds/" + soundName);
                return;
            }

            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);

            soundEffect = AudioSystem.getClip();
            soundEffect.open(audioInputStream);
            soundEffect.setFramePosition(0);
            soundEffect.start();

        } catch (Exception e) {
            System.out.println("Error reproduciendo sonido: " + soundName);
            System.out.println(e.getMessage());
        }
    }

    /**
     * metodo que detiene los efetos de sonido
     */
    private void stopSoundEffect() {
        if (soundEffect != null) {
            soundEffect.stop();
            soundEffect.flush();
            soundEffect.close();
            soundEffect = null;
        }
    }

    /**
     * Reproduce música en bucle.
     */
    private void playMusic(String soundName) {
        try {
            stopMusic();

            InputStream inputStream = getClass().getResourceAsStream("/sounds/" + soundName);

            if (inputStream == null) {
                System.out.println("No se encontró la música: /sounds/" + soundName);
                return;
            }

            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);

            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioInputStream);
            backgroundMusic.setFramePosition(0);
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
            backgroundMusic.start();

        } catch (Exception e) {
            System.out.println("Error reproduciendo música: " + soundName);
            System.out.println(e.getMessage());
        }
    }

    /**
     * Detiene la música actual.
     */
    public void stopMusic() {

        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic.flush();
            backgroundMusic.close();
            backgroundMusic = null;
        }
    }

    /**
     * Detiene todo tipo de efectos y/o sonidos
     */
    public void stopAllSounds() {
        stopMusic();
        stopSoundEffect();
    }


    public void setPlayerName(String playerName) {
        if (playerName != null && !playerName.trim().isEmpty()) {
            this.playerName = playerName;
        } else {
            this.playerName = "Jugador";
        }
        view.setPlayerName(this.playerName);
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public int getElapsedSeconds() {
        return elapsedSeconds;
    }

    public GameModel getModel() {
        return model;
    }
}