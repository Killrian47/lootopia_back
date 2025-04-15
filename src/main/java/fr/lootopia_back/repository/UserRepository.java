package fr.lootopia_back.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.lootopia_back.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);

  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);

  boolean existsByUsername(String username);
}
