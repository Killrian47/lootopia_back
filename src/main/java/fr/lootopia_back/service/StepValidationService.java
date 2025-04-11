package fr.lootopia_back.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.lootopia_back.model.Step;
import fr.lootopia_back.model.StepValidation;
import fr.lootopia_back.model.User;
import fr.lootopia_back.repository.StepRepository;
import fr.lootopia_back.repository.StepValidationRepository;
import fr.lootopia_back.repository.UserRepository;

@Service
public class StepValidationService {

  private final StepValidationRepository stepValidationRepository;
  private final UserRepository userRepository;
  private final StepRepository stepRepository;

  public StepValidationService(StepValidationRepository stepValidationRepository, UserRepository userRepository,
      StepRepository stepRepository) {
    this.stepValidationRepository = stepValidationRepository;
    this.userRepository = userRepository;
    this.stepRepository = stepRepository;
  }

  public StepValidation createStepValidation(Long userId, Long stepId) {
    User user = userRepository.findById(userId).orElseThrow();
    Step step = stepRepository.findById(stepId).orElseThrow();

    StepValidation stepValidation = new StepValidation();
    stepValidation.setUser(user);
    stepValidation.setStep(step);
    stepValidation.setValidationDate(LocalDateTime.now());
    stepValidation.setValid(false);

    return stepValidationRepository.save(stepValidation);
  }

  public StepValidation validateStep(Long userId, Long stepId) {
    User user = userRepository.findById(userId).orElseThrow();
    Step step = stepRepository.findById(stepId).orElseThrow();

    StepValidation existingValidation = stepValidationRepository.findByUserAndStep(user, step).orElse(null);
    if (existingValidation != null) {
      existingValidation.setValidationDate(LocalDateTime.now());
      existingValidation.setValid(true);
      return stepValidationRepository.save(existingValidation);
    } else {
      StepValidation newValidation = new StepValidation();
      newValidation.setUser(user);
      newValidation.setStep(step);
      newValidation.setValidationDate(LocalDateTime.now());
      newValidation.setValid(true);
      return stepValidationRepository.save(newValidation);
    }
  }

  public Optional<StepValidation> getValidation(Long userId, Long stepId) {
    User user = userRepository.findById(userId).orElseThrow();
    Step step = stepRepository.findById(stepId).orElseThrow();
    return stepValidationRepository.findByUserAndStep(user, step);
  }
}
