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

    public void addResult(String username, int score, String difficulty) {
        if (difficulty.equals("easymode")) {
            easyResultsRepo.save(new EasyResults(username, score, new Date()));
        } else if (difficulty.equals("hardmode")) {
            hardResultsRepo.save(new HardResults(username, score, new Date()));
        }
    }

    public List<Integer> calculateHistogram(String difficulty) {

        List<Integer> scoreList;

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

        int prosent = 100 / scoreList.size();

        List<Integer> list = new ArrayList<>();

        for (int i = 0; i <= 10; i++) {
            int finalI = i; // Denne måtte med fordi den likte ikke å bruke bare i alene i streamene

            // Hvis ingen har fått dette resultatet enda,
            // sett den til 1 slik at den er synlig i histogrammet
            if(scoreList.stream().filter(score -> score == finalI).count() == 0) {
                list.add(1);
                continue;
            }

            list.add((int) scoreList.stream().filter(score -> score == finalI).count() * prosent);
        }

        return list;
    }
}
