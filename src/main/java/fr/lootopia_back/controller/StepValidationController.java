package fr.lootopia_back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fr.lootopia_back.model.StepValidation;
import fr.lootopia_back.model.User;
import fr.lootopia_back.service.StepValidationService;
import fr.lootopia_back.service.UserService;

@RestController
@RequestMapping("/api/steps-validation")
public class StepValidationController {

  private final StepValidationService stepValidationService;
  private final UserService userService;

  public StepValidationController(StepValidationService stepValidationService, UserService userService) {
    this.stepValidationService = stepValidationService;
    this.userService = userService;
  }

  @PostMapping("/{stepId}")
  public ResponseEntity<StepValidation> validateStep(@PathVariable Long stepId) {
    User currentUser = userService.getCurrentUser()
        .orElseThrow(() -> new IllegalStateException("Current user not found"));
    StepValidation validation = stepValidationService.validateStep(currentUser.getId(), stepId);
    return ResponseEntity.ok(validation);
  }

  @GetMapping("/{stepId}/validated/{userId}")
  public ResponseEntity<Boolean> isStepValidated(@PathVariable Long stepId, @PathVariable Long userId) {
    return ResponseEntity.ok(stepValidationService.getValidation(userId, stepId).isPresent());
  }
}
