package fr.lootopia_back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.lootopia_back.model.User;
import fr.lootopia_back.service.UserService;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

  @Autowired
  private UserService userService;

  // Endpoint pour récupérer tous les utilisateurs (admin uniquement)
  @RequestMapping("/users")
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  // Endpoint pour supprimer un utilisateur
  @RequestMapping("/users/delete/{id}")
  public String deleteUser(@PathParam("id") Long id) {
    userService.deleteUser(id);
    return "User with id " + id + " has been deleted";
  }
}
