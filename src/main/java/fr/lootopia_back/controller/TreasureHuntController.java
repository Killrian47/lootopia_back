package fr.lootopia_back.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.lootopia_back.model.TreasureHunt;
import fr.lootopia_back.model.User;
import fr.lootopia_back.service.StepService;
import fr.lootopia_back.service.TreasureHuntParticipantService;
import fr.lootopia_back.service.TreasureHuntService;
import fr.lootopia_back.service.UserService;

@RestController
@RequestMapping("/api/treasure-hunts")
public class TreasureHuntController {

  @Autowired
  private TreasureHuntService treasureHuntService;

  @Autowired
  private UserService userService;

  @Autowired
  private TreasureHuntParticipantService treasureHuntParticipantService;

  @Autowired
  private StepService stepService;

  private static final Logger logger = LoggerFactory.getLogger(TreasureHuntController.class);

  @GetMapping("/all")
  public List<TreasureHunt> getAllTreasureHunt() {
    return treasureHuntService.findAll();
  }

  @PostMapping
  @PreAuthorize("hasRole('ORGANIZER') or hasRole('ADMIN')")
  public TreasureHunt createTreasureHunt(@RequestBody TreasureHunt treasureHunt) {
    Optional<User> organizer = userService.getCurrentUser();

    if (!organizer.isPresent()) {
      throw new RuntimeException("User not found");
    } else {
      treasureHunt.setOrganizer(organizer.get());
    }

    return treasureHuntService.save(treasureHunt);
  }

  @GetMapping("/{id}")
  public TreasureHunt getTreasureHuntById(@PathVariable Long id) {
    return treasureHuntService.findById(id).orElse(null);
  }

  @PostMapping("/{huntId}/join")
  public ResponseEntity<?> registerUserToHunt(
      @PathVariable Long huntId) {
    try {
      Optional<User> userOptional = userService.getCurrentUser();
      treasureHuntParticipantService.registerUserToHunt(userOptional, huntId);
      return ResponseEntity.ok("User registered successfully to the hunt");
    } catch (Exception e) {
      logger.error("Error registering user to hunt: {}", e.getMessage(), e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("An error occurred while processing the request.");
    }
  }

  @DeleteMapping("/{huntId}/leave")
  public ResponseEntity<?> unregisterUserFromHunt(@PathVariable Long huntId) {
    try {
      Optional<User> userOptional = userService.getCurrentUser();
      treasureHuntParticipantService.unregisterUserFromHunt(userOptional, huntId);
      return ResponseEntity.ok("User unregistered successfully from the hunt");
    } catch (Exception e) {
      logger.error("Error unregistering user from hunt: {}", e.getMessage(), e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("An error occurred while processing the request.");
    }
  }

  @GetMapping("/{huntId}/steps")
  public ResponseEntity<List<?>> getSteps(@PathVariable Long huntId) {
    if (stepService.getStepsByHuntId(huntId).isEmpty()) {
      return ResponseEntity.status(200).body(List.of("No steps found for this hunt"));
    }
    return ResponseEntity.ok(stepService.getStepsByHuntId(huntId));
  }

  @GetMapping("/{huntId}/participants")
  public ResponseEntity<List<User>> getAllTreasureHuntParticipants(@PathVariable Long huntId) {
    List<User> participants = treasureHuntParticipantService.getAllTreasureHuntParticipants(huntId);
    if (participants.isEmpty()) {
      return ResponseEntity.status(200).body(List.of());
    }
    return ResponseEntity.ok(participants);
  }
}