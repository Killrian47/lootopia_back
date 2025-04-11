package fr.lootopia_back.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Getter
@Setter
@Table(name = "treasure_hunts")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TreasureHunt {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  @Column(length = 1000)
  private String description;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
  private LocalDateTime startDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
  private LocalDateTime endDate;

  @JsonProperty("isFinished")
  private boolean isFinished = false;

  @JsonProperty("isPublic")
  private boolean isPublic = true;

  private String location;

  @ManyToOne
  @JoinColumn(name = "organizer_id")
  private User organizer;

  @OneToMany(mappedBy = "treasureHunt", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonManagedReference
  private List<Step> steps;

}
