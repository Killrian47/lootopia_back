package fr.lootopia_back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "step")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Step {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String description;

  private double latitude;

  private double longitude;

  private int reward;

  private int stepOrder;

  @ManyToOne
  @JsonIgnore
  private TreasureHunt treasureHunt;
}
