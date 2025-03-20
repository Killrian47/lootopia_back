package fr.lootopia_back.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import fr.lootopia_back.model.User;
import fr.lootopia_back.repository.UserRepository;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public Optional<User> getUserByUsername(String username) {
    return Optional.ofNullable(userRepository.findByUsername(username));
  }

  public User createUser(User user) {
    if (user.getPassword() == null || user.getPassword().isEmpty()) {
      throw new IllegalArgumentException("Le mot de passe ne peut pas être vide.");
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    return userRepository.save(user);
  }

  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }
}
