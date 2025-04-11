package fr.lootopia_back.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fr.lootopia_back.model.PasswordResetToken;
import fr.lootopia_back.model.User;
import fr.lootopia_back.repository.PasswordResetTokenRepository;
import fr.lootopia_back.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PasswordResetService {

  private final UserRepository userRepository;
  private final PasswordResetTokenRepository tokenRepository;
  private final EmailService emailService;

  public PasswordResetService(UserRepository userRepository, PasswordResetTokenRepository tokenRepository,
      EmailService emailService) {
    this.emailService = emailService;
    this.userRepository = userRepository;
    this.tokenRepository = tokenRepository;
  }

  @Transactional
  public void createResetToken(String email) {
    User user = userRepository.findByEmail(email).orElseThrow();
    tokenRepository.deleteByUser(user); // Supprimer les anciens

    String token = UUID.randomUUID().toString();
    PasswordResetToken resetToken = new PasswordResetToken();
    resetToken.setUser(user);
    resetToken.setToken(token);
    resetToken.setExpirationDate(LocalDateTime.now().plusMinutes(30));
    tokenRepository.save(resetToken);

    // TODO: envoyer le token par email
    System.out.println("Lien de reset : http://localhost:3000/reset-password?token=" + token);
    emailService.sendResetTokenEmail(user.getEmail(), token);
  }

  public boolean isTokenValid(String token) {
    return tokenRepository.findByToken(token)
        .filter(t -> t.getExpirationDate().isAfter(LocalDateTime.now()))
        .isPresent();
  }

  public void resetPassword(String token, String newPassword) {
    PasswordResetToken resetToken = tokenRepository.findByToken(token)
        .filter(t -> t.getExpirationDate().isAfter(LocalDateTime.now()))
        .orElseThrow(() -> new IllegalArgumentException("Token invalide ou expiré"));

    User user = resetToken.getUser();
    user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
    userRepository.save(user);
    tokenRepository.delete(resetToken);
  }
}
