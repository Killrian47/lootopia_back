package fr.lootopia_back.dto;

public record RegisterRequest(String email, String password, String username) {
}