package fr.lootopia_back.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fr.lootopia_back.model.Step;
import fr.lootopia_back.service.StepService;

@RestController
@RequestMapping("/api/steps")
public class StepController {

  private final StepService stepService;

  public StepController(StepService stepService) {
    this.stepService = stepService;
  }

  @PostMapping("/{huntId}")
  public ResponseEntity<Step> addStep(@PathVariable Long huntId, @RequestBody Step step) {
    Step savedStep = stepService.addStepToHunt(huntId, step);
    return ResponseEntity.ok(savedStep);
  }

  @GetMapping("/{huntId}")
  public ResponseEntity<List<?>> getSteps(@PathVariable Long huntId) {
    if (stepService.getStepsByHuntId(huntId).isEmpty()) {
      return ResponseEntity.status(200).body(List.of("No steps found for this hunt"));
    }
    return ResponseEntity.ok(stepService.getStepsByHuntId(huntId));
  }
}
