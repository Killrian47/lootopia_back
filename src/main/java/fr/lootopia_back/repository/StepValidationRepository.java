package fr.lootopia_back.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.lootopia_back.model.StepValidation;
import fr.lootopia_back.model.Step;
import fr.lootopia_back.model.User;

@Repository
public interface StepValidationRepository extends JpaRepository<StepValidation, Long> {
  boolean existsByUserAndStep(User user, Step step);

  Optional<StepValidation> findByUserAndStep(User user, Step step);
}
