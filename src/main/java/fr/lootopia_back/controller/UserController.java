package fr.lootopia_back.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.lootopia_back.model.User;
import fr.lootopia_back.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserService userService;

  // Endpoint pour récupérer un utilisateur par son nom d'utilisateur
  @GetMapping("/{username}")
  public Optional<User> getUser(@PathVariable String username) {
    return userService.getUserByUsername(username);
  }

  // Endpoint pour créer un nouvel utilisateur
  @PostMapping
  public ResponseEntity<?> createUser(@RequestBody User user) {

    if (user.getPassword() == null || user.getPassword().isEmpty()) {
      return ResponseEntity.badRequest().body("Password is required");
    }

    if (user.getUsername() == null || user.getUsername().isEmpty()) {
      return ResponseEntity.badRequest().body("Username is required");
    }

    if (user.getEmail() == null || user.getEmail().isEmpty()) {
      return ResponseEntity.badRequest().body("Email is required");
    }

    User savedUser = userService.createUser(user);
    return ResponseEntity.ok(savedUser);
  }

  // Supprimer son propre compte
  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable Long id) {
    Optional<User> currentUser = userService.getCurrentUser();
    if (currentUser.isEmpty() || !currentUser.get().getId().equals(id)) {
      throw new IllegalArgumentException("You can only delete your own account");
    }
    userService.deleteUser(id);
    System.out.println("User with id " + id + " has been deleted");
  }

  // Endpoint pour récupérer les informations de l'utilisateur connecté
  @GetMapping("/me")
  public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
    if (userDetails == null) {
      return ResponseEntity.status(401).body("Utilisateur non connecté");
    }

    return ResponseEntity.ok("Connecté en tant que : " + userDetails.getUsername()
        + ", rôles : " + userDetails.getAuthorities());
  }
}
