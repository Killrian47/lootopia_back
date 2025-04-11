package fr.lootopia_back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.lootopia_back.model.Step;

@Repository
public interface StepRepository extends JpaRepository<Step, Long> {
  List<Step> findByTreasureHuntIdOrderByStepOrderAsc(Long treasureHuntId);
}
