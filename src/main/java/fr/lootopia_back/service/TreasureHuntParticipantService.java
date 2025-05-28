package fr.lootopia_back.service;

import java.util.List;
import java.util.Optional;

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

  public void registerUserToHunt(Optional<User> user, Long huntId) {
    User userNonOtptional = userRepository.findById(user.get().getId()).orElseThrow();
    TreasureHunt hunt = huntRepository.findById(huntId).orElseThrow();

    if (!participantRepository.existsByUserAndTreasureHunt(userNonOtptional, hunt)) {
      TreasureHuntParticipant participant = new TreasureHuntParticipant();
      participant.setUser(userNonOtptional);
      participant.setTreasureHunt(hunt);
      participantRepository.save(participant);
    }
  }

  public void unregisterUserFromHunt(Optional<User> user, Long huntId) {
    User userNonOtptional = userRepository.findById(user.get().getId()).orElseThrow();
    TreasureHunt hunt = huntRepository.findById(huntId).orElseThrow();
    TreasureHuntParticipant participant = participantRepository
        .findByUserAndTreasureHunt(userNonOtptional, hunt);
    participantRepository.delete(participant);

  }

  public List<User> getAllTreasureHuntParticipants(Long id) {
    return participantRepository.findAllUsersByTreasureHunt(id);
  }
}
