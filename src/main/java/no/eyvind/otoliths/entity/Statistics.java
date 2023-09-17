package no.eyvind.otoliths.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Statistics {
    private final String statisticsPath = "src/main/webapp/WEB-INF/statistics.json";
    private ArrayList<Stat> statistics;

    public Statistics() {
        // Laste inn easy_resultatene
        try {
            File jsonFile = new File(statisticsPath);
            if (!jsonFile.exists()) { // Oppretter ny fil hvis den ikke allerede finnes
                jsonFile.createNewFile();
                System.out.println("Created statistics.json");
            }

            if (jsonFile.length() > 0) {
                ObjectMapper objectMapper = new ObjectMapper();
                statistics = (ArrayList<Stat>) objectMapper.readValue(new File(statisticsPath), new TypeReference<List<Stat>>() {});
                System.out.println("Stats loaded from statistics.json");
            } else {
                statistics = new ArrayList<>();
                System.out.println("Nothing to load from statistics.json");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(int[] shownPictures, String[] chosenPictures) {
        System.out.print("Updated stats for: ");
        for (int i = 0; i < shownPictures.length; i++) {
            updateStatistics(shownPictures[i], chosenPictures[i]);
            System.out.print(shownPictures[i] + " ");
        }
        System.out.println();
    }

    public void updateStatistics(int ID, String result) {
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
            save();
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
            save();
        }
    }

    public void save() {
        // Lagre resultatene til lokal fil basert på vanskelighetsgrad
        try {
            // Vet ikke hva denne gjør, men er viktig
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(statisticsPath), statistics);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
