package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal del juego Arca Galáctica.
 * Esta clase crea el frame, agrega el GamePanel y ubica los botones
 * que permiten iniciar el juego, ver instrucciones y volver al inicio.
 */
public class MainFrame extends JFrame {

    private GamePanel gamePanel;
    private GameController gameController;

    private JTextField nameField;
    private JLabel nameLabel;
    private JButton startButton;
    private JButton instructionsButton;
    private JButton backButton;

    /**
     * Constructor de la ventana principal.
     * Configura el JFrame, crea el panel del juego, el controlador
     * y agrega los botones a la interfaz.
     */
    public MainFrame() {
        setTitle("Arca Galáctica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        gamePanel = new GamePanel();
        gameController = new GameController(gamePanel, this);

        createButtons();

        add(gamePanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        gameController.startThread();
    }

    /**
     * Crea y configura los botones y el campo de texto.
     * Estos componentes se agregan directamente al GamePanel
     * para que aparezcan dentro de la pantalla del juego.
     */
    private void createButtons() {
        nameLabel = new JLabel("Nombre:");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setBounds(205, 325, 80, 25);

        nameField = new JTextField();
        nameField.setBounds(285, 325, 170, 25);

        startButton = new JButton("Iniciar");
        startButton.setBounds(200, 365, 120, 30);

        instructionsButton = new JButton("Instrucciones");
        instructionsButton.setBounds(330, 365, 150, 30);

        backButton = new JButton("Volver al inicio");
        backButton.setBounds(172, 430, 160, 30);
        backButton.setVisible(false);

        startButton.addActionListener(e -> startGame());

        nameField.addActionListener(e -> startGame());

        instructionsButton.addActionListener(e -> {
            gameController.showInstructions();
            showInstructionsButtons();
            gamePanel.requestFocusInWindow();
        });

        backButton.addActionListener(e -> {
            gameController.restartGame();

            showStartButtons();
            gamePanel.requestFocusInWindow();
        });

        gamePanel.add(nameLabel);
        gamePanel.add(nameField);
        gamePanel.add(startButton);
        gamePanel.add(instructionsButton);
        gamePanel.add(backButton);
    }

    /**
     * Inicia la partida.
     * Primero valida que el jugador haya escrito un nombre.
     * Luego envía el nombre al controlador y oculta los botones
     * de la pantalla inicial.
     */
    private void startGame() {
        String name = nameField.getText();

        if (name.trim().isEmpty()) {
            gamePanel.setMessage("Primero escribe el nombre del jugador");
            gamePanel.repaint();
            return;
        }

        gameController.setPlayerName(name);
        gameController.startGame();

        nameField.setVisible(false);
        startButton.setVisible(false);
        instructionsButton.setVisible(false);
        backButton.setVisible(false);
        nameLabel.setVisible(false);

        gamePanel.requestFocusInWindow();
    }

    /**
     * Muestra los botones de la pantalla inicial.
     * Se usa cuando el jugador vuelve al inicio desde instrucciones
     * o desde la pantalla final.
     */
    private void showStartButtons() {
        nameField.setVisible(true);
        startButton.setVisible(true);
        instructionsButton.setVisible(true);
        backButton.setVisible(false);
        nameLabel.setVisible(true);

        gamePanel.setMessage("");
        gamePanel.repaint();
    }

    /**
     * Muestra únicamente el botón para volver al inicio
     * cuando el jugador está en la pantalla de instrucciones.
     */
    private void showInstructionsButtons() {
        nameField.setVisible(false);
        startButton.setVisible(false);
        instructionsButton.setVisible(false);
        backButton.setVisible(true);
        nameLabel.setVisible(false);

        gamePanel.repaint();
    }

    /**
     * Muestra los botones necesarios al terminar la partida.
     * Se oculta el campo del nombre y se permite volver al inicio.
     */
    public void showGameOverButtons() {
        nameLabel.setVisible(false);
        nameField.setVisible(false);

        startButton.setVisible(true);
        instructionsButton.setVisible(false);
        backButton.setVisible(true);

        gamePanel.repaint();
    }
}