package fr.lootopia_back.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.lootopia_back.model.Step;
import fr.lootopia_back.repository.StepRepository;

@Service
public class StepService {

  private final StepRepository stepRepository;

  private final TreasureHuntService treasureHuntService;

  public StepService(StepRepository stepRepository, TreasureHuntService treasureHuntService) {
    this.stepRepository = stepRepository;
    this.treasureHuntService = treasureHuntService;
  }

  public Step addStepToHunt(Long treasureHuntId, Step step) {
    if (treasureHuntService.findById(treasureHuntId).isPresent()) {
      step.setTreasureHunt(treasureHuntService.findById(treasureHuntId).get());
      return stepRepository.save(step);
    } else {
      throw new IllegalArgumentException("Treasure Hunt not found");
    }
  }

  public List<Step> getStepsByHuntId(Long treasureHuntId) {
    if (treasureHuntService
        .findById(treasureHuntId)
        .isEmpty()) {
      throw new IllegalArgumentException("Treasure Hunt not found");
    }
    return stepRepository.findAll().stream()
        .filter(step -> step.getTreasureHunt().getId().equals(treasureHuntId))
        .toList();
  }

  public Step getStepById(Long stepId) {
    return stepRepository.findById(stepId)
        .orElseThrow(() -> new IllegalArgumentException("Step not found"));
  }
}
