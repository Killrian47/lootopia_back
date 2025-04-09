package fr.lootopia_back.service;

import fr.lootopia_back.model.TreasureHunt;
import fr.lootopia_back.repository.TreasureHuntRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TreasureHuntService {

  private final TreasureHuntRepository treasureHuntRepository;

  public TreasureHuntService(TreasureHuntRepository treasureHuntRepository) {
    this.treasureHuntRepository = treasureHuntRepository;
  }

  public List<TreasureHunt> findAll() {
    return treasureHuntRepository.findAll();
  }

  public Optional<TreasureHunt> findById(Long id) {
    return treasureHuntRepository.findById(id);
  }

  public TreasureHunt save(TreasureHunt treasureHunt) {
    return treasureHuntRepository.save(treasureHunt);
  }

  public void delete(Long id) {
    treasureHuntRepository.deleteById(id);
  }
}
