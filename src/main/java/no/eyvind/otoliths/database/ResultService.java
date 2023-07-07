package no.eyvind.otoliths.database;

import no.eyvind.otoliths.entity.EasyResults;
import no.eyvind.otoliths.entity.HardResults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ResultService {
    private final EasyResultsRepo easyResultsRepo;
    private final HardResultsRepo hardResultsRepo;

    public ResultService(EasyResultsRepo easyResultsRepo, HardResultsRepo hardResultsRepo) {
        this.easyResultsRepo = easyResultsRepo;
        this.hardResultsRepo = hardResultsRepo;
    }

    // Lagrer resultatet i databasen
    public void addResult(String username, int score, String difficulty) {
        if (difficulty.equals("easymode")) {
            easyResultsRepo.save(new EasyResults(username, score, new Date()));
        } else if (difficulty.equals("hardmode")) {
            hardResultsRepo.save(new HardResults(username, score, new Date()));
        }
    }

    public List<Integer> calculateHistogram(String difficulty) {

        List<Integer> scoreList;

        // Henter ut riktige resultater basert på vanskelighetsgraden
        if(difficulty.equals("easymode")) {
            scoreList = easyResultsRepo.findAll()
                    .stream()
                    .map(EasyResults::getScore)
                    .toList();
        } else {
            scoreList = hardResultsRepo.findAll()
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
