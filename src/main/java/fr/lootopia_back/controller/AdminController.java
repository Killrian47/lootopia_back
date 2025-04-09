package fr.lootopia_back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.lootopia_back.model.TreasureHunt;
import fr.lootopia_back.model.User;
import fr.lootopia_back.service.TreasureHuntService;
import fr.lootopia_back.service.UserService;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

  @Autowired
  private UserService userService;

  @Autowired
  private TreasureHuntService treasureHuntService;

  // Endpoint pour récupérer tous les utilisateurs (admin uniquement)
  @GetMapping("/users")
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  // Endpoint pour supprimer un utilisateur
  @DeleteMapping("/users/delete/{id}")
  public String deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return "User with id " + id + " has been deleted";
  }

  // Endpoint pour récupérer tous les trésors (admin uniquement)
  @RequestMapping("/treasure-hunts")
  public List<TreasureHunt> getAllTreasureHunts() {
    return treasureHuntService.findAll();
  }

  // Endpoint pour récupérer un trésor par son ID (admin uniquement)
  @DeleteMapping("/treasure-hunts/delete/{id}")
  public String deleteTreasureHunt(@PathVariable Long id) {
    treasureHuntService.delete(id);
    return "Treasure Hunt with id " + id + " has been deleted";
  }
}
