package no.eyvind.otoliths.entity;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LocalStorageService {
    private String easyPath = "src/main/webapp/WEB-INF/easy_results.ser";
    private String hardPath = "src/main/webapp/WEB-INF/hard_results.ser";

    private ArrayList<EasyResults> easyResultsList;
    private ArrayList<HardResults> hardResultsList;

    public LocalStorageService() {
        // Read easy_results file
        // Må kanskje også gjøres via thread og synchronize
        try{
            FileInputStream readData = new FileInputStream(easyPath);
            if (readData.available() > 0) {
                ObjectInputStream readStream = new ObjectInputStream(readData);

                easyResultsList = (ArrayList<EasyResults>) readStream.readObject();
                readStream.close();
            } else {
                System.out.println("File is empty. Creating new Arraylist");
                easyResultsList = new ArrayList<>();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        // Read hard_results file
        try{
            FileInputStream readData = new FileInputStream(hardPath);
            if (readData.available() > 0) {
                ObjectInputStream readStream = new ObjectInputStream(readData);

                hardResultsList = (ArrayList<HardResults>) readStream.readObject();
                readStream.close();
            } else {
                System.out.println("File is empty. Creating new Arraylist");
                hardResultsList = new ArrayList<>();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Legger resultatet inn i listen
    public void addResult(String username, int score, String difficulty) {
        if (difficulty.equals("easymode")) {
            easyResultsList.add(new EasyResults(username, score, new Date()));
        } else if (difficulty.equals("hardmode")) {
            hardResultsList.add(new HardResults(username, score, new Date()));
        }
        // Lagrer til databasen
        // Bør gjøres via thread og synchronize
        save();
    }

    public void save() {
        // Lagre easyResults til lokal fil
        try{
            FileOutputStream writeData = new FileOutputStream(easyPath);
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

            writeStream.writeObject(easyResultsList);
            writeStream.flush();
            writeStream.close();

        }catch (IOException e) {
            e.printStackTrace();
        }

        // Lagre hardResults til lokal fil
        try{
            FileOutputStream writeData = new FileOutputStream(hardPath);
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

            writeStream.writeObject(hardResultsList);
            writeStream.flush();
            writeStream.close();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> calculateHistogram(String difficulty) {

        List<Integer> scoreList;

        // Henter ut riktige resultater basert på vanskelighetsgraden
        if(difficulty.equals("easymode")) {
            // Hvis listen er tom, returner null
            if (easyResultsList.isEmpty())
                return null;

            scoreList = easyResultsList
                    .stream()
                    .map(EasyResults::getScore)
                    .toList();
        } else {
            // Hvis listen er tom, returner null
            if (hardResultsList.isEmpty())
                return null;

            scoreList = hardResultsList
                    .stream()
                    .map(HardResults::getScore)
                    .toList();
        }

        // Regner ut hvor mange prosent hvert resultat er verdt
        int prosent = 100 / scoreList.size();

        List<Integer> list = new ArrayList<>();

        for (int i = 0; i <= 10; i++) {
            // Denne virker unødvendig, men måtte med for at koden skulle funke
            int finalI = i;

            // Hvis ingen har fått dette resultatet enda,
            // setter vi den til 1 slik at den blir synlig i histogrammet
            if(scoreList.stream().filter(score -> score == finalI).count() == 0) {
                list.add(1);
                continue;
            }

            list.add((int) scoreList.stream().filter(score -> score == finalI).count() * prosent);
        }

        return list;
    }
}