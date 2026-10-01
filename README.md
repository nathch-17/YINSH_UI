# YINSH - Interface Graphique (JavaFX)

Ce projet a été réalisé dans le cadre d'une **SAÉ (Situation d'Apprentissage et d'Évaluation)**. L'objectif principal de ce projet était de concevoir et d'implémenter une interface graphique en **JavaFX** pour le jeu de plateau **YINSH**, dont la logique avait préalablement été codée en Java.

## Description

**YINSH** est un jeu de stratégie abstrait pour deux joueurs (faisant partie du projet GIPF). Le but du jeu est de former des alignements de marqueurs à sa couleur pour pouvoir retirer 3 de ses anneaux du plateau avant son adversaire. 

Ce projet universitaire met en pratique la programmation orientée objet en Java, l'architecture logicielle (séparation entre la logique du jeu et l'interface utilisateur), ainsi que la gestion d'un affichage interactif sur un plateau hexagonal via JavaFX.

## Prérequis

- **Java Development Kit (JDK) 21** (ou version compatible)
- **Maven** (pour la gestion des dépendances et du build)

## Comment lancer le jeu

Le projet utilise Maven et est divisé en plusieurs modules (comme `application` et `hexagonalCoordinate`). Il est donc nécessaire de construire le projet complet avant de lancer l'interface.

Ouvrez un terminal à la racine du projet et exécutez les commandes suivantes :

1. Placez-vous dans le dossier contenant les sources Maven :
   ```bash
   cd YINSH
   ```

2. Compilez et installez les modules en local
   ```bash
   mvn clean install
   ```

3. Déplacez-vous dans le sous-dossier de l'application :
   ```bash
   cd application
   ```

4. Lancez l'interface graphique du jeu :
   ```bash
   mvn javafx:run
   ```

## Tester le projet

Pour lancer les tests automatisés du projet, placez-vous dans le dossier `YINSH` et utilisez la commande standard de Maven :

```bash
cd YINSH
mvn test
```
