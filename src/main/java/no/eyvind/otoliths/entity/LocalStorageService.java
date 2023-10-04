package no.eyvind.otoliths.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class LocalStorageService {
    private final String easyPath = "src/main/webapp/WEB-INF/easy_results.json";
    private final String hardPath = "src/main/webapp/WEB-INF/hard_results.json";

    private CopyOnWriteArrayList<EasyResults> easyResultsList;
    private CopyOnWriteArrayList<HardResults> hardResultsList;

    public LocalStorageService() {
        // Laste inn easy_resultatene
        try {
            File jsonFile = new File(easyPath);
            if (!jsonFile.exists()) { // Oppretter ny fil hvis den ikke allerede finnes
                jsonFile.createNewFile();
                System.out.println("Created easy_results.json");
            }

            if (jsonFile.length() > 0) {
                ObjectMapper objectMapper = new ObjectMapper();
                List<EasyResults> list = objectMapper.readValue(new File(easyPath), new TypeReference<List<EasyResults>>() {});
                easyResultsList = new CopyOnWriteArrayList<>(list);
                System.out.println("Objects loaded from easy_results.json");
            } else {
                easyResultsList = new CopyOnWriteArrayList<>();
                System.out.println("Nothing to load from easy_results.json");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Laste inn hard_resultatene
        try {
            File jsonFile = new File(hardPath);
            if (!jsonFile.exists()) { // Oppretter ny fil hvis den ikke allerede finnes
                jsonFile.createNewFile();
                System.out.println("Created hard_results.json");
            }

            if (jsonFile.length() > 0) {
                ObjectMapper objectMapper = new ObjectMapper();
                List<HardResults> list = objectMapper.readValue(new File(hardPath), new TypeReference<List<HardResults>>() {});
                hardResultsList = new CopyOnWriteArrayList<>(list);
                System.out.println("Objects loaded from hard_results.json");
            } else {
                hardResultsList = new CopyOnWriteArrayList<>();
                System.out.println("Nothing to load from hard_results.json");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Legger resultatet inn i listen lokalt
    public synchronized void addResult(String username, int score, String difficulty) {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        if (difficulty.equals("easymode")) {
            easyResultsList.add(new EasyResults(username, score, date));
        } else if (difficulty.equals("hardmode")) {
            hardResultsList.add(new HardResults(username, score, date));
        }
        save(difficulty);
    }

    public synchronized void save(String difficulty) {
        // Lagre resultatene til lokal fil basert på vanskelighetsgrad
        try {
            // Vet ikke hva denne gjør, men er viktig
            ObjectMapper objectMapper = new ObjectMapper();

            if (difficulty.equals("easymode")) {
                objectMapper.writeValue(new File(easyPath), easyResultsList);
                System.out.println("Objects saved to easy_results.json");
            } else if (difficulty.equals("hardmode")) {
                objectMapper.writeValue(new File(hardPath), hardResultsList);
                System.out.println("Objects saved to hard_results.json");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> calculateHistogram(String difficulty) {

        List<Integer> scoreList;
        List<Integer> emptyList = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            emptyList.add(1);
        }

        // Henter ut riktige resultater basert på vanskelighetsgraden
        if(difficulty.equals("easymode")) {
            // Hvis listen er tom, returner emptyList
            if (easyResultsList.isEmpty())
                return emptyList;

            scoreList = easyResultsList.stream().map(EasyResults::getScore).toList();
        } else {
            // Hvis listen er tom, returner emptyList
            if (hardResultsList.isEmpty())
                return emptyList;

            scoreList = hardResultsList.stream().map(HardResults::getScore).toList();
        }

        // Regner ut hvor mange prosent hvert resultat er verdt
        if (scoreList.size() == 0)
            return emptyList;

        int prosent = 100 / scoreList.size();

        List<Integer> list = new ArrayList<>();

        for (int i = 0; i <= 10; i++) {
            // Denne virker unødvendig, men måtte med for at koden skulle funke
            int finalI = i;

            // Hvis ingen har fått dette resultatet enda,
            // setter vi den til 1 slik at den blir synlig i histogrammet
            if(scoreList.stream().noneMatch(score -> score == finalI)) {
                list.add(1);
                continue;
            }

            list.add((int) scoreList.stream().filter(score -> score == finalI).count() * prosent);
        }

        return list;
    }
}