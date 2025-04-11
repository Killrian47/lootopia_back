package fr.lootopia_back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.lootopia_back.dto.ResetPasswordRequest;
import fr.lootopia_back.service.PasswordResetService;

@RestController
@RequestMapping("/api/auth")
public class PasswordResetController {

  private final PasswordResetService resetService;

  public PasswordResetController(PasswordResetService resetService) {
    this.resetService = resetService;
  }

  @PostMapping("/reset-password-request")
  public ResponseEntity<?> requestReset(@RequestParam String email) {
    resetService.createResetToken(email);
    return ResponseEntity.ok("Un lien de réinitialisation a été envoyé.");
  }

  @GetMapping("/reset-password-verify")
  public ResponseEntity<Boolean> verifyToken(@RequestParam String token) {
    return ResponseEntity.ok(resetService.isTokenValid(token));
  }

  @PostMapping("/reset-password")
  public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest req) {
    resetService.resetPassword(req.getToken(), req.getNewPassword());
    return ResponseEntity.ok("Mot de passe mis à jour !");
  }
}
