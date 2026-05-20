package view;

import controller.GameController;
import model.Alien;
import model.AlienProjectile;
import model.Animal;
import model.Entity;
import model.GameModel;
import model.PlayerProjectile;
import model.PlayerRecord;
import model.PowerUp;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Panel principal del juego.
 * Esta clase se encarga de dibujar las diferentes pantallas:
 * inicio, instrucciones, juego, game over y victoria.
 */
public class GamePanel extends JPanel {

    public static final int WIDTH = 670;
    public static final int HEIGHT = 510;

    private GameModel model;
    private String gameStatus;
    private String playerName;
    private String message;
    private int elapsedSeconds;
    private java.util.ArrayList<PlayerRecord> top3;

    private BufferedImage logo;
    private BufferedImage background;

    /**
     * Constructor del panel.
     * Inicializa el tamaño, el estado inicial del juego
     * y carga las imágenes usadas en la vista.
     */
    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        setLayout(null);

        gameStatus = GameController.STATUS_START;
        elapsedSeconds = 0;
        top3 = new java.util.ArrayList<>();
        playerName = "Jugador";
        message = "";

        background = Entity.uploadImage("fondo.png");
        logo = Entity.uploadImage("uamlogo.jpg");
    }

    /**
     * Método principal de dibujo.
     * Según el estado actual del juego, llama al método que debe pintar
     * la pantalla correspondiente.
     *
     * @param g objeto usado para dibujar en el panel
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameStatus.equals(GameController.STATUS_START)) {
            drawStart(g);
        }

        if (gameStatus.equals(GameController.STATUS_INSTRUCTIONS)) {
            drawInstructions(g);
        }

        if (gameStatus.equals(GameController.STATUS_PLAYING)) {
            drawGame(g);
        }

        if (gameStatus.equals(GameController.STATUS_GAME_OVER)) {
            drawGameOver(g);
        }

        if (gameStatus.equals(GameController.STATUS_WIN)) {
            drawWin(g);
        }
    }

    /**
     * Dibuja la pantalla inicial del juego.
     *
     * @param g objeto usado para dibujar
     */
    private void drawStart(Graphics g) {
        g.drawImage(background, 0, 0, WIDTH, HEIGHT, this);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Verdana", Font.BOLD, 40));
        g.drawString("ARCA GALÁCTICA", 115, 100);

        g.setFont(new Font("Verdana", Font.BOLD, 18));
        g.drawString("Programación Orientada a Objetos", 130, 140);

        g.setFont(new Font("Verdana", Font.BOLD, 18));
        g.drawString("Juan Miguel Osorio", 230, 180);
        g.drawString("Juan Daniel Pineda", 230, 200);
        g.drawString("Juan José Ospina", 230, 220);

        g.setFont(new Font("Verdana", Font.BOLD, 18));
        g.drawString("Escribe tu nombre y presiona INICIAR", 150, 285);

        g.setColor(Color.RED);
        g.setFont(new Font("Verdana", Font.BOLD, 15));
        g.drawString(message, 180, 420);

        if (logo != null) {
            g.drawImage(logo, 1, 1, this);
        }
    }

    /**
     * Dibuja la pantalla de instrucciones.
     *
     * @param g objeto usado para dibujar
     */
    private void drawInstructions(Graphics g) {
        g.drawImage(background, 0, 0, WIDTH, HEIGHT, this);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Verdana", Font.BOLD, 34));
        g.drawString("INSTRUCCIONES", 165, 80);

        g.setFont(new Font("Verdana", Font.PLAIN, 17));
        g.drawString("1. Usa las flechas izquierda y derecha para mover la nave.", 60, 150);
        g.drawString("2. Presiona espacio para disparar a los aliens.", 60, 190);
        g.drawString("3. Los aliens disparan contra los animales y la nave.", 60, 230);
        g.drawString("4. Tanto los animales como la nave tienen 3 vidas.", 60, 270);
        g.drawString("5. Si eliminas todos los aliens, los animales vivos son rescatados.", 60, 310);
        g.drawString("6. Si la nave pierde todas sus vidas, termina la partida.", 60, 350);
        g.drawString("7. Los corazones recuperan una vida de la nave.", 60, 390);
        g.drawString("8. Si coges el power-up x2, duplicas tus puntos.", 60, 420);

        g.setFont(new Font("Verdana", Font.BOLD, 17));
    }

    /**
     * Dibuja la pantalla principal del juego.
     *
     * @param g objeto usado para dibujar
     */
    private void drawGame(Graphics g) {
        if (model == null) {
            return;
        }

        drawBoard(g);
        drawAnimals(g);
        drawAliens(g);
        drawProjectiles(g);
        drawPowerUps(g);
        drawShip(g);
        drawHUD(g);
    }

    /**
     * Dibuja el tablero del juego y el panel lateral de información.
     *
     * @param g objeto usado para dibujar
     */
    private void drawBoard(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GameModel.BOARD_WIDTH, GameModel.BOARD_HEIGHT);

        g.setColor(Color.DARK_GRAY);
        g.drawRect(0, 0, GameModel.BOARD_WIDTH, GameModel.BOARD_HEIGHT);

        g.setColor(new Color(15, 15, 30));
        g.fillRect(GameModel.BOARD_WIDTH, 0, WIDTH - GameModel.BOARD_WIDTH, HEIGHT);
    }

    /**
     * Dibuja la nave del jugador.
     * Si tiene activo el power-up de doble puntaje, se muestra un borde amarillo.
     *
     * @param g objeto usado para dibujar
     */
    private void drawShip(Graphics g) {
        drawEntity(g, model.getShip());

        if (model.getShip().isDoublePoints()) {
            g.setColor(Color.YELLOW);
            g.drawRect(
                    model.getShip().getX() - 2,
                    model.getShip().getY() - 2,
                    model.getShip().getWidth() + 4,
                    model.getShip().getHeight() + 4
            );
        }
    }

    /**
     * Dibuja los aliens del juego.
     *
     * @param g objeto usado para dibujar
     */
    private void drawAliens(Graphics g) {
        Alien[] aliens = model.getAliens();

        for (int i = 0; i < aliens.length; i++) {
            drawEntity(g, aliens[i]);
        }
    }

    /**
     * Dibuja los animales protegidos y sus vidas.
     * Solo se dibujan los animales que siguen activos.
     *
     * @param g objeto usado para dibujar
     */
    private void drawAnimals(Graphics g) {
        Animal[] animals = model.getAnimals();

        for (int i = 0; i < animals.length; i++) {
            Animal animal = animals[i];

            if (animal.isActive()) {
                drawEntity(g, animal);

                g.setColor(Color.GREEN);
                g.setFont(new Font("Verdana", Font.BOLD, 12));
                g.drawString("Vidas: " + animal.getLives(), animal.getX(), animal.getY() - 6);
            }
        }
    }

    /**
     * Dibuja los proyectiles de la nave y de los aliens.
     *
     * @param g objeto usado para dibujar
     */
    private void drawProjectiles(Graphics g) {
        java.util.ArrayList<PlayerProjectile> playerProjectiles = model.getPlayerProjectiles();

        for (int i = 0; i < playerProjectiles.size(); i++) {
            drawEntity(g, playerProjectiles.get(i));
        }

        java.util.ArrayList<AlienProjectile> alienProjectiles = model.getAlienProjectiles();

        for (int i = 0; i < alienProjectiles.size(); i++) {
            drawEntity(g, alienProjectiles.get(i));
        }
    }

    /**
     * Dibuja los power-ups activos en pantalla.
     *
     * @param g objeto usado para dibujar
     */
    private void drawPowerUps(Graphics g) {
        java.util.ArrayList<PowerUp> powerUps = model.getPowerUps();

        for (int i = 0; i < powerUps.size(); i++) {
            drawEntity(g, powerUps.get(i));
        }
    }

    /**
     * Dibuja una entidad si se encuentra activa.
     * Este método se reutiliza para nave, aliens, animales,
     * proyectiles y power-ups.
     *
     * @param g      objeto usado para dibujar
     * @param entity entidad que se desea pintar
     */
    private void drawEntity(Graphics g, Entity entity) {
        if (entity == null || !entity.isActive()) {
            return;
        }

        if (entity.getSprite() != null) {
            g.drawImage(entity.getSprite(), entity.getX(), entity.getY(), this);
        } else {
            g.setColor(Color.WHITE);
            g.fillRect(entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight());
        }
    }

    /**
     * Dibuja el HUD del juego.
     * En esta zona se muestra puntaje, vidas, tiempo y controles.
     *
     * @param g objeto usado para dibujar
     */
    private void drawHUD(Graphics g) {
        int hudX = 514;

        g.setColor(Color.BLUE);
        g.setFont(new Font("Verdana", Font.BOLD, 18));
        g.drawString("PUNTAJE", hudX + 15, 40);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Verdana", Font.BOLD, 28));
        g.drawString(String.valueOf(model.getShip().getScore()), hudX + 45, 78);

        g.setColor(Color.BLUE);
        g.setFont(new Font("Verdana", Font.BOLD, 18));
        g.drawString("VIDAS", hudX + 30, 130);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Verdana", Font.BOLD, 28));
        g.drawString(String.valueOf(model.getShip().getLives()), hudX + 55, 168);

        g.setColor(Color.BLUE);
        g.setFont(new Font("Verdana", Font.BOLD, 18));
        g.drawString("TIEMPO", hudX + 20, 220);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Verdana", Font.BOLD, 24));
        g.drawString(elapsedSeconds + " s", hudX + 35, 255);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Verdana", Font.PLAIN, 12));
        g.drawString("← Izquierda", hudX + 15, 400);
        g.drawString("→ Derecha", hudX + 15, 420);
        g.drawString("Espacio: disparar", hudX + 15, 440);

        if (logo != null) {
            g.drawImage(logo, hudX + 20, 320, this);
        }
    }

    /**
     * Dibuja la pantalla de Game Over.
     * Muestra el jugador, puntaje final, tiempo y Top 3.
     *
     * @param g objeto usado para dibujar
     */
    private void drawGameOver(Graphics g) {
        drawGame(g);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Verdana", Font.BOLD, 48));
        g.drawString("GAME OVER", 90, 110);

        if (model != null) {
            g.setFont(new Font("Verdana", Font.BOLD, 20));
            g.drawString("Jugador: " + playerName, 130, 165);
            g.drawString("Puntaje final: " + model.getShip().getScore(), 130, 195);
            g.drawString("Tiempo total: " + elapsedSeconds + " s", 130, 225);
        }

        g.setColor(Color.YELLOW);
        g.setFont(new Font("Verdana", Font.BOLD, 24));
        g.drawString("TOP 3", 210, 250);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Verdana", Font.PLAIN, 17));

        for (int i = 0; i < top3.size(); i++) {
            PlayerRecord record = top3.get(i);

            String text = (i + 1) + ". "
                    + record.getPlayerName()
                    + " - "
                    + record.getScore()
                    + " pts - "
                    + record.getTime()
                    + " s";

            g.drawString(text, 100, 290 + (i * 30));
        }
    }

    /**
     * Dibuja la pantalla de victoria.
     * Se muestra cuando el jugador elimina todos los aliens.
     *
     * @param g objeto usado para dibujar
     */
    private void drawWin(Graphics g) {
        drawGame(g);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Verdana", Font.BOLD, 48));
        g.drawString("GANASTE!", 90, 110);

        if (model != null) {
            g.setFont(new Font("Verdana", Font.BOLD, 20));
            g.drawString("Jugador: " + playerName, 130, 165);
            g.drawString("Puntaje final: " + model.getShip().getScore(), 130, 195);
            g.drawString("Tiempo total: " + elapsedSeconds + " s", 130, 225);
        }

        g.setColor(Color.YELLOW);
        g.setFont(new Font("Verdana", Font.BOLD, 24));
        g.drawString("TOP 3", 210, 250);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Verdana", Font.PLAIN, 17));

        for (int i = 0; i < top3.size(); i++) {
            PlayerRecord record = top3.get(i);

            String text = (i + 1) + ". "
                    + record.getPlayerName()
                    + " - "
                    + record.getScore()
                    + " pts - "
                    + record.getTime()
                    + " s";

            g.drawString(text, 100, 290 + (i * 30));
        }
    }

    /**
     * Asigna el modelo del juego al panel.
     *
     * @param model modelo principal del juego
     */
    public void setModel(GameModel model) {
        this.model = model;
    }

    /**
     * Cambia el estado actual del juego.
     *
     * @param gameStatus nuevo estado del juego
     */
    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    /**
     * Actualiza el tiempo mostrado en pantalla.
     *
     * @param elapsedSeconds tiempo en segundos
     */
    public void setElapsedSeconds(int elapsedSeconds) {
        this.elapsedSeconds = elapsedSeconds;
    }

    /**
     * Actualiza el Top 3 mostrado en pantalla.
     *
     * @param top3 lista de mejores jugadores
     */
    public void setTop3(java.util.ArrayList<PlayerRecord> top3) {
        this.top3 = top3;
    }

    /**
     * Asigna el nombre del jugador actual.
     *
     * @param playerName nombre del jugador
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Cambia el mensaje de aviso mostrado en inicio.
     *
     * @param message mensaje a mostrar
     */
    public void setMessage(String message) {
        this.message = message;
    }
}