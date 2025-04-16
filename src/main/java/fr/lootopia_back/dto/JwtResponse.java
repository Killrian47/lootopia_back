package fr.lootopia_back.dto;

import fr.lootopia_back.model.User;

public record JwtResponse(String token, User user) {

}
