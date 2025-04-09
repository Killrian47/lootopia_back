package fr.lootopia_back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.lootopia_back.model.User;
import fr.lootopia_back.service.TreasureHuntParticipantService;

@RestController
@RequestMapping("/api/treasure-hunt-participants")
public class TreasureHuntParticipantController {

  @Autowired
  private TreasureHuntParticipantService treasureHuntParticipantService;

  @GetMapping("/{id}")
  public List<User> getAllTreasureHuntParticipants(@PathVariable Long id) {
    return treasureHuntParticipantService.getAllTreasureHuntParticipants(id);
  }
}
