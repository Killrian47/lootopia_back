package fr.lootopia_back.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "step_validation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StepValidation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private User user;

  @ManyToOne
  private Step step;

  private LocalDateTime validationDate;

  private boolean isValid = false;
}
