package model;

/**
 * Guarda los datos de una partida terminada.
 */
public class PlayerRecord {

    private String playerName;
    private int score;
    private int time;

    /**
     * Constructor de la clase PlayerRecord.
     *
     * @param playerName nombre del jugador
     * @param score      puntaje obtenido por el jugador
     * @param time       tiempo total jugado en segundos
     */
    public PlayerRecord(String playerName, int score, int time) {
        this.playerName = playerName;
        this.score = score;
        this.time = time;
    }

    /**
     * Retorna el nombre del jugador.
     *
     * @return nombre del jugador
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Retorna el puntaje del jugador.
     *
     * @return puntaje del jugador
     */
    public int getScore() {
        return score;
    }

    /**
     * Retorna el tiempo total jugado.
     *
     * @return tiempo jugado en segundos
     */
    public int getTime() {
        return time;
    }
}