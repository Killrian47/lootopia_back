package fr.lootopia_back.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.lootopia_back.model.TreasureHunt;
import fr.lootopia_back.model.User;
import fr.lootopia_back.service.TreasureHuntService;
import fr.lootopia_back.service.UserService;

@RestController
@RequestMapping("/api/treasure-hunts")
public class TreasureHuntController {

  @Autowired
  private TreasureHuntService treasureHuntService;

  @Autowired
  private UserService userService;

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
  public TreasureHunt getTreasureHuntById(Long id) {
    return treasureHuntService.findById(id).orElse(null);
  }

}