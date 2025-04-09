package fr.lootopia_back.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "treasure_hunt_participant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TreasureHuntParticipant {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private TreasureHunt treasureHunt;

  @ManyToOne
  private User user;
}
