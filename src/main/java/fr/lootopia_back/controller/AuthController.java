package fr.lootopia_back.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.lootopia_back.dto.JwtResponse;
import fr.lootopia_back.dto.LoginRequest;
import fr.lootopia_back.dto.RegisterRequest;
import fr.lootopia_back.model.Role;
import fr.lootopia_back.model.User;
import fr.lootopia_back.repository.UserRepository;
import fr.lootopia_back.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
    if (userRepository.existsByEmail(request.email())) {
      return ResponseEntity.badRequest()
          .body(Map.of("message", "Un compte est déjà associé avec cet email"));
    }
    if (userRepository.existsByUsername(request.username())) {
      return ResponseEntity.badRequest()
          .body(Map.of("message", "Ce nom d'utilisateur est déjà utilisé, veuillez en choisir un autre"));
    }
    User user = new User();
    user.setEmail(request.email());
    user.setPassword(passwordEncoder.encode(request.password()));
    user.setUsername(request.username());
    user.setRole(Role.ROLE_USER);
    userRepository.save(user);
    return ResponseEntity.ok("Utilisateur enregistré");
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    User user = userRepository.findByEmail(request.email())
        .orElseThrow(() -> new RuntimeException("User not found"));

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.email(), request.password()));
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String token = jwtUtil.generateToken(userDetails);
    return ResponseEntity.ok(new JwtResponse(token, user));
  }
}
