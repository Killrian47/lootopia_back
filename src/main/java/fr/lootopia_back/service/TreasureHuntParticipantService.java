package fr.lootopia_back.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.lootopia_back.model.TreasureHunt;
import fr.lootopia_back.model.TreasureHuntParticipant;
import fr.lootopia_back.model.User;
import fr.lootopia_back.repository.TreasureHuntParticipantRepository;
import fr.lootopia_back.repository.TreasureHuntRepository;
import fr.lootopia_back.repository.UserRepository;

@Service
public class TreasureHuntParticipantService {

  private final TreasureHuntParticipantRepository participantRepository;
  private final UserRepository userRepository;
  private final TreasureHuntRepository huntRepository;

  public TreasureHuntParticipantService(
      TreasureHuntParticipantRepository participantRepository,
      UserRepository userRepository,
      TreasureHuntRepository huntRepository) {
    this.participantRepository = participantRepository;
    this.userRepository = userRepository;
    this.huntRepository = huntRepository;
  }

  public void registerUserToHunt(Long userId, Long huntId) {
    User user = userRepository.findById(userId).orElseThrow();
    TreasureHunt hunt = huntRepository.findById(huntId).orElseThrow();

    if (!participantRepository.existsByUserAndTreasureHunt(user, hunt)) {
      TreasureHuntParticipant participant = new TreasureHuntParticipant();
      participant.setUser(user);
      participant.setTreasureHunt(hunt);
      participantRepository.save(participant);
    }
  }

  public List<User> getAllTreasureHuntParticipants(Long id) {
    return participantRepository.findAllUsersByTreasureHunt(id);
  }
}
