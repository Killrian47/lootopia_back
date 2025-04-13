package fr.lootopia_back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fr.lootopia_back.model.Step;
import fr.lootopia_back.model.StepValidation;
import fr.lootopia_back.model.User;
import fr.lootopia_back.service.StepService;
import fr.lootopia_back.service.StepValidationService;
import fr.lootopia_back.service.UserService;

@RestController
@RequestMapping("/api/steps")
public class StepController {

  private final StepService stepService;
  private final StepValidationService stepValidationService;
  private final UserService userService;

  public StepController(StepService stepService,
      StepValidationService stepValidationService,
      UserService userService) {
    this.userService = userService;
    this.stepValidationService = stepValidationService;
    this.stepService = stepService;
  }

  @PostMapping("/{huntId}")
  public ResponseEntity<Step> addStep(@PathVariable Long huntId, @RequestBody Step step) {
    Step savedStep = stepService.addStepToHunt(huntId, step);
    return ResponseEntity.ok(savedStep);
  }

  @PostMapping("/{stepId}/validate")
  public ResponseEntity<StepValidation> validateStep(@PathVariable Long stepId) {
    User currentUser = userService.getCurrentUser()
        .orElseThrow(() -> new IllegalStateException("Current user not found"));
    StepValidation validation = stepValidationService.validateStep(currentUser.getId(), stepId);
    return ResponseEntity.ok(validation);
  }
}
