package util;

import model.PlayerRecord;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Administra el ranking del juego.
 * Guarda los jugadores y permite obtener el Top 3.
 */
public class RankingManager {

    private static final String FILE_NAME = "ranking/ranking.txt";

    private File file;

    public RankingManager() {
        File folder = new File("ranking");
        folder.mkdir();
        file = new File(FILE_NAME);
        try {
            file.createNewFile();

        } catch (Exception e) {
            System.err.println("Error al crear el fichero");
        }
    }

    /**
     * Guarda una partida terminada en el archivo.
     *
     * @param record registro del jugador
     */
    public void saveRecord(PlayerRecord record) {
        List<String> lines = FileManager.leerFile(file);

        String line = record.getPlayerName() + "," + record.getScore() + "," + record.getTime();

        lines.add(line);

        FileManager.escribirFile(file, lines);
    }

    /**
     * Lee todos los registros guardados.
     *
     * @return lista de jugadores registrados
     */
    public ArrayList<PlayerRecord> readRecords() {
        ArrayList<PlayerRecord> records = new ArrayList<>();

        List<String> lines = FileManager.leerFile(file);

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] data = line.split(",");

            if (data.length == 3) {
                String playerName = data[0];
                int score = Integer.parseInt(data[1]);
                int time = Integer.parseInt(data[2]);

                PlayerRecord record = new PlayerRecord(playerName, score, time);
                records.add(record);
            }
        }

        return records;
    }

    /**
     * Retorna los mejores 3 puntajes.
     *
     * @return lista con máximo 3 jugadores
     */
    public ArrayList<PlayerRecord> getTop3() {
        ArrayList<PlayerRecord> records = readRecords();

        sortByScore(records);

        ArrayList<PlayerRecord> top3 = new ArrayList<>();

        for (int i = 0; i < records.size() && i < 3; i++) {
            top3.add(records.get(i));
        }

        return top3;
    }

    /**
     * Ordena los registros de mayor a menor puntaje.
     *
     * @param records lista de registros
     */
    private void sortByScore(ArrayList<PlayerRecord> records) {
        for (int i = 0; i < records.size() - 1; i++) {
            for (int j = i + 1; j < records.size(); j++) {

                PlayerRecord current = records.get(i);
                PlayerRecord next = records.get(j);

                if (next.getScore() > current.getScore()) {
                    records.set(i, next);
                    records.set(j, current);
                }
            }
        }
    }
}