package fr.lootopia_back.dto;

public record JwtResponse(String token) {

  public JwtResponse(String token) {
    this.token = token;
  }

}
