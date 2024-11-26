# 🌳 **Citronix - Farm Management System**

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/projects/jdk/17/)  
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.5-brightgreen.svg)](https://spring.io/projects/spring-boot)  
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Latest-blue.svg)](https://www.postgresql.org/)  
[![Tests](https://img.shields.io/badge/Tests-Passing-success.svg)](https://github.com/yourusername/Citronix/actions)

---

## 📋 **Description**

**Citronix** est un système de gestion agricole performant qui facilite la gestion des fermes, des champs, des arbres et
de leurs récoltes.  
Le système propose une API REST complète pour des opérations de gestion flexibles et intuitives.

---

## 🚀 **Fonctionnalités Principales**

### 🏡 Gestion des Fermes

- Création et gestion des fermes.
- Limite de 10 champs par ferme.
- Superficie minimale de 0,1 hectare (1 000 m²).
- Validation de l'unicité des noms.

### 🌱 Gestion des Récoltes

- Suivi des récoltes saisonnières.
- Calcul automatique des quantités récoltées.
- Validation des périodes de récolte.
- Gestion des détails spécifiques à chaque arbre.

### 📊 Suivi de la Production

- Calcul de la productivité des arbres.
- Historique détaillé des récoltes.
- Statistiques complètes de production.
- Traçabilité totale des données.

---

## 🛠️ **Technologies Utilisées**

### Backend

- Java 17
- Spring Boot 3.3.5
- Spring Data JPA
- PostgreSQL
- Lombok & MapStruct
- SpringDoc OpenAPI

### Tests

- JUnit 5
- Mockito
- Spring Boot Test

---

## 📝 **API Endpoints**

### 🏡 Gestion des Fermes

```http
GET /api/v1/farms              # Liste des fermes
POST /api/v1/farms             # Création d'une ferme
PUT /api/v1/farms/{id}         # Mise à jour d'une ferme
DELETE /api/v1/farms/{id}      # Suppression d'une ferme
```

### 🌾 Gestion des Champs

```http
GET /api/v1/fields             # Liste des champs
POST /api/v1/fields            # Création d'un champ
PUT /api/v1/fields/{id}        # Mise à jour d'un champ
DELETE /api/v1/fields/{id}     # Suppression d'un champ
GET /api/v1/fields/farm/{farmId} # Champs d'une ferme
```

### 🌳 Gestion des Arbres

```http
POST /api/v1/trees/fields/{fieldId} # Ajouter un arbre
GET /api/v1/trees/{id} # Détails d'un arbre
GET /api/v1/trees/fields/{fieldId} # Arbres d'un champ
GET /api/v1/trees/{id}/productivity # Productivité d'un arbre
DELETE /api/v1/trees/{id} # Supprimer un arbre
```

### 🌾 Gestion des Récoltes

```http
GET /api/v1/harvests # Liste des récoltes
POST /api/v1/harvests # Nouvelle récolte
GET /api/v1/harvests/{id}/quantity # Quantité totale
DELETE /api/v1/harvests/{id} # Supprimer une récolte
```

### 🔍 Détails des Récoltes

```http
POST /api/v1/harvest-details # Ajouter un détail
GET /api/v1/harvest-details/trees/{treeId} # Détails par arbre
GET /api/v1/harvest-details/{id} # Détail spécifique
DELETE /api/v1/harvest-details/{id} # Supprimer un détail
```

### 💰 Gestion des Ventes

```http
GET /api/v1/sales                # Liste des ventes
POST /api/v1/sales               # Création d'une vente
GET /api/v1/sales/{id}           # Détails d'une vente
PUT /api/v1/sales/{id}           # Mise à jour d'une vente
DELETE /api/v1/sales/{id}        # Suppression d'une vente
GET /api/v1/sales/harvests/{harvestId} # Ventes par récolte
GET /api/v1/sales/total-revenue  # Revenu total des ventes
```

## 🔧 Installation

### Prérequis

```bash
java -version # Java 17 ou supérieur
mvn -version # Maven 3.9 ou supérieur
```

### Configuration

1. Cloner le repository

```bash
https://github.com/nabilettihadi/Citronix.git
```

```bash
cd Citronix
```

2. Configurer la base de données dans `application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/citronix
spring.datasource.username=your_username
spring.datasource.password=your_password
```

3. Lancer l'application

```bash
./mvnw clean install
./mvnw spring-boot:run
```

## 📚 Documentation API

- Swagger UI: http://localhost:8080/swagger-ui/index.html
- API Docs: http://localhost:8080/v3/api-docs

## 🧪 Tests

### Exécuter tous les tests

```bash
./mvnw test
```

### Exécuter une classe de test spécifique

```bash
./mvnw test -Dtest=FarmServiceTest
```

## 📁 Structure du Projet

```plaintext
citronix/

├── src/

│ ├── main/
│ │ ├── java/
│ │ │ └── ma/nabil/Citronix/
│ │ │ ├── controller/ # Contrôleurs REST
│ │ │ ├── service/ # Logique métier
│ │ │ ├── repository/ # Accès aux données
│ │ │ ├── entity/ # Entités JPA
│ │ │ └── dto/ # Objets de transfert
│ │ └── resources/
│ │ └── application.properties
│ └── test/
│ └── java/ # Tests unitaires
├── UML/
│ └── diagramme de classe # Diagrammes UML
├── .mvn/ # Configuration Maven
├── mvnw # Script Maven Unix
├── mvnw.cmd # Script Maven Windows
├── pom.xml # Configuration Maven
└── README.md
```

## 📊 Diagramme de Classes

![Class Diagram](UML/Diagramme%20de%20Classe%20Citronix.png)

## 🤝 Contribution

1. Fork le projet
2. Créer une branche (`git checkout -b feature/AmazingFeature`)
3. Commit les changements (`git commit -m 'feat: add amazing feature'`)
4. Push vers la branche (`git push origin feature/AmazingFeature`)
5. Ouvrir une Pull Request

## 👥 Auteur

- **Nabil** - *Travail initial* - [GitHub](https://github.com/nabilettihadi)