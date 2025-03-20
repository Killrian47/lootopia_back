package fr.lootopia_back.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

  @GetMapping
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/{username}")
  public Optional<User> getUser(@PathVariable String username) {
    return userService.getUserByUsername(username);
  }

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

  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    System.out.println("User with id " + id + " has been deleted");
  }
}
