package no.eyvind.otoliths.database;

import no.eyvind.otoliths.entity.HardResults;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HardResultsRepo extends JpaRepository<HardResults, Integer> {
    List<HardResults> findHardResultsByScore(int id);
}