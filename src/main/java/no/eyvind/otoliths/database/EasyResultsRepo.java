package no.eyvind.otoliths.database;

import no.eyvind.otoliths.entity.EasyResults;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EasyResultsRepo extends JpaRepository<EasyResults, Integer> {
    List<EasyResults> findEasyResultsByScore(int id);
}