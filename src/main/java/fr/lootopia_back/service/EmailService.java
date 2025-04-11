package fr.lootopia_back.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  private final JavaMailSender mailSender;

  public EmailService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void sendResetTokenEmail(String to, String token) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setFrom("killian.portier@yahoo.fr");
    message.setSubject("Réinitialisation de mot de passe - Lootopia");
    message.setText("Voici votre lien : http://localhost:3000/reset-password?token=" + token);
    mailSender.send(message);
  }

  // Test envoi mail
  /*
   * @PostConstruct
   * public void sendTestEmail() {
   * sendResetTokenEmail("killianportier47@yahoo.com", "test-token");
   * }
   */

}
