package no.eyvind.otoliths.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Statistics {
    private final String statisticsPath = "src/main/webapp/WEB-INF/statistics.json";
    private CopyOnWriteArrayList<Stat> statistics;

    public Statistics() {
        // Loads statistics on class creation
        try {
            File jsonFile = new File(statisticsPath);
            if (!jsonFile.exists()) { // Creates a new file if no file was found
                jsonFile.createNewFile();
                System.out.println("Created statistics.json");
            }

            if (jsonFile.length() > 0) {
                ObjectMapper objectMapper = new ObjectMapper();
                List<Stat> list = objectMapper.readValue(new File(statisticsPath), new TypeReference<List<Stat>>() {});
                statistics = new CopyOnWriteArrayList<>(list);
                System.out.println("Stats loaded from statistics.json");
            } else {
                statistics = new CopyOnWriteArrayList<>();
                System.out.println("Nothing to load from statistics.json");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void add(int[] shownPictures, String[] chosenPictures) {
        System.out.print("Updated stats for: ");
        for (int i = 0; i < shownPictures.length; i++) {
            updateStatistics(shownPictures[i], chosenPictures[i]);
            System.out.print(shownPictures[i] + " ");
        }
        System.out.println();
    }

    public synchronized void updateStatistics(int ID, String result) {
        boolean found = false;
        for (Stat stat : statistics) {
            if (stat.getId() == ID) {
                if ("correct".equals(result)) {
                    stat.incrementCorrectGuesses();
                } else if ("wrong".equals(result)) {
                    stat.incrementWrongGuesses();
                }
                found = true;
                break;
            }
        }

        if (!found) {
            Stat newStat = new Stat(ID);
            if ("correct".equals(result)) {
                newStat.incrementCorrectGuesses();
            } else if ("wrong".equals(result)) {
                newStat.incrementWrongGuesses();
            }
            statistics.add(newStat);
            statistics.sort(Comparator.comparing(Stat::getId));
        }
        save();
    }

    public synchronized void save() {
        // Maps the java object to JSON, and saves it to file
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(statisticsPath), statistics);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
