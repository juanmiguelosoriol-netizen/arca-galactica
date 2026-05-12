package model;

/**
 * Guarda los datos de una partida terminada.
 */
public class PlayerRecord {

    private String playerName;
    private int score;
    private int time;

    public PlayerRecord(String playerName, int score, int time) {
        this.playerName = playerName;
        this.score = score;
        this.time = time;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public int getTime() {
        return time;
    }
}