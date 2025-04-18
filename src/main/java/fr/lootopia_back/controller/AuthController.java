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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentification", description = "Endpoints pour l'authentification des utilisateurs")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Operation(summary = "Inscription d'un nouvel utilisateur", description = "Permet de créer un nouveau compte utilisateur")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Inscription réussie"),
      @ApiResponse(responseCode = "400", description = "Email déjà utilisé ou nom d'utilisateur déjà pris")
  })
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

  @Operation(summary = "Connexion utilisateur", description = "Authentifie un utilisateur et retourne un token JWT")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Authentification réussie", content = @Content(schema = @Schema(implementation = JwtResponse.class))),
      @ApiResponse(responseCode = "401", description = "Identifiants invalides"),
      @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
  })
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
