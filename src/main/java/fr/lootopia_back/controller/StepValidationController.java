package fr.lootopia_back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fr.lootopia_back.model.User;
import fr.lootopia_back.service.StepValidationService;
import fr.lootopia_back.service.UserService;

@RestController
@RequestMapping("/api/steps-validation")
public class StepValidationController {

  private final StepValidationService stepValidationService;
  private final UserService userService;

  public StepValidationController(StepValidationService stepValidationService, UserService userService) {
    this.userService = userService;
    this.stepValidationService = stepValidationService;
  }

  @GetMapping("/{stepId}/validated")
  public ResponseEntity<Boolean> isStepValidated(@PathVariable Long stepId) {
    User currentUser = userService.getCurrentUser()
        .orElseThrow(() -> new IllegalStateException("Current user not found"));

    return ResponseEntity.ok(stepValidationService.getValidation(currentUser.getId(), stepId).isPresent());
  }
}
