# Lootopia Back

Lootopia Back est le backend du projet Lootopia, une application de chasse au trésor. Ce backend est développé en Java avec le framework Spring Boot et utilise une base de données PostgreSQL.

## Fonctionnalités

- **Gestion des utilisateurs** :
  - Inscription et connexion des utilisateurs.
  - Rôles utilisateurs : `ROLE_USER`, `ROLE_ORGANIZER`, `ROLE_ADMIN`.
  - Gestion des comptes utilisateurs.

- **Gestion des chasses au trésor** :
  - Création, récupération et suppression des chasses au trésor.
  - Gestion des chasses publiques et privées.

- **Sécurité** :
  - Authentification via JWT (JSON Web Token).
  - Gestion des autorisations basées sur les rôles.
  - Protection des endpoints sensibles.

- **Administration** :
  - Gestion des utilisateurs et des chasses au trésor par les administrateurs.

## Prérequis

- **Java 17** ou version supérieure.
- **Maven** pour la gestion des dépendances.
- **PostgreSQL** comme base de données.

## Installation

1. Clonez le dépôt :
  ```bash
  git clone <url-du-repo>
  cd lootopia_back
  ```
2. Configurez les variables d'environnement dans un fichier :
  ```bash
  SPRING_DATASOURCE_URL=<votre-base-de-données>
  SPRING_DATASOURCE_USERNAME=<votre-nom-utilisateur>
  SPRING_DATASOURCE_PASSWORD=<votre-mot-de-passe>
  JWT_SECRET=<votre-clé-secrète>
  ```
3. Installez les dépendances Maven :
  ```bash
  ./mvnw clean install
  ```
4. Lancez l'application :
  ```bash
  ./mvnw spring-boot:run
  ```