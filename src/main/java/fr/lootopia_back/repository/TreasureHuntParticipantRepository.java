package fr.lootopia_back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.lootopia_back.model.TreasureHunt;
import fr.lootopia_back.model.TreasureHuntParticipant;
import fr.lootopia_back.model.User;

@Repository
public interface TreasureHuntParticipantRepository extends JpaRepository<TreasureHuntParticipant, Long> {
  boolean existsByUserAndTreasureHunt(User user, TreasureHunt treasureHunt);

  TreasureHuntParticipant findByUserAndTreasureHunt(User user, TreasureHunt treasureHunt);

  @Query("SELECT p.user FROM TreasureHuntParticipant p WHERE p.treasureHunt.id = :id")
  List<User> findAllUsersByTreasureHunt(@Param("id") Long treasureHuntId);
}
