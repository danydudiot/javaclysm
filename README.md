# Javaclysm

![Version](https://img.shields.io/badge/version-1.0.0-blue.svg)
![Langage](https://img.shields.io/badge/langage-Java-orange.svg)
![Licence](https://img.shields.io/badge/licence-MIT-green.svg)

## Présentation

Javaclysm est un jeu d'aventure au tour par tour inspiré du jeu open source "Cataclysm: Dark Days Ahead". Développé en Java dans le cadre d'un projet universitaire de Conception Orientée Objet.

## Caractéristiques principales

- **Interface en console** avec représentation visuelle du plateau de jeu
- **Gameplay au tour par tour** avec déplacements et actions stratégiques
- **Système d'inventaire et d'équipement** pour gérer les objets collectés
- **Différents environnements** (forêt, jungle) avec leurs propres caractéristiques
- **Entités interactives** (créatures, objets, nourriture...)
- **Plusieurs patterns de conception** (Singleton, Observateur, État, Commande...)

## Prérequis

- Java 17 ou supérieur
- Terminal supportant la coloration ANSI (pour l'affichage optimal)

## Comment jouer

1. Compilez le projet : `javac -d ./build/classes $(find ./src -name "*.java")`
2. Lancez le jeu : `java -cp ./build/classes main.Main`
3. Suivez les instructions à l'écran pour choisir une carte ou en générer une aléatoirement

### Commandes de base
- `z`, `q`, `s`, `d` : Déplacement (haut, gauche, bas, droite)
- `o`, `k`, `l`, `m` : Changement d'orientation
- `i` : Accéder à l'inventaire
- `e` : Interagir avec l'environnement
- `j` : Jeter l'objet équipé
- `h` : Pour afficher une aide

### Cartes prédéfinies

Le jeu propose plusieurs cartes prédéfinies avec différents environnements et défis :

| Carte | Environnement | Description |
|-------|--------------|-------------|
| `carteBasique.txt` | Jungle | Carte simple pour débuter |
| `carteHibou.txt` | Forêt | Carte avec des hiboux chasseurs |
| `carteRenard.txt` | Forêt | Territoire de renards |
| `carteSerpent.txt` | Jungle | Environnement dangereux avec des serpents |
| `carteScorpion.txt` | Jungle | Zone aride avec des scorpions |
| `carteJungle.txt` | Jungle | Jungle luxuriante avec divers fruits |

Pour changer de carte, modifiez la ligne suivante dans le fichier [BoardFactoryParser.java](src/modele/boardFactory/BoardFactoryParser.java) :
```java
this.mapFile = new File("nomDeLaCarte.txt");
```

### Créer une carte personnalisée

Vous pouvez créer votre propre carte en suivant ce format :
```
[F/J]    // F pour Forêt, J pour Jungle
[hauteur]
[largeur]
[ligne 1]
[ligne 2]
...
```

Symboles utilisés dans les cartes :
- `#` : Mur
- `P` : Joueur (Player)
- `T` : Arbre (Tree) en forêt / Palmier en jungle
- `R` : Rocher (Rock)
- `B` : Buisson (Bush)
- `F` : Renard (Fox) - prédateur
- `O` : Hibou (Owl) - prédateur
- `S` : Serpent (Snake) - prédateur
- `C` : Scorpion - prédateur
- `M` : Singe (Monkey) - proie
- `A` : Gland (Acorn) - nourriture
- `N` : Banane (baNana) - nourriture
- `H` : Champignon hallucinogène (Hallucinogenic mushroom)
- `X` : Champignon empoisonné (poisonous mushroom)
- `I` : Champignon (Mushroom)
- `G` : Pierre du temps (Gemstone)
- ` ` (espace) : Case vide

## Conteneurisation

Le projet peut être exécuté dans un conteneur Docker. Consultez le fichier [docker-instructions.md](docker-instructions.md) pour plus d'informations.

## Structure du projet

Le projet est structuré selon le modèle MVC (Modèle-Vue-Contrôleur) :
- **Modèle** : Classes de données et logique du jeu (`modele/`)
- **Vue** : Interface utilisateur en console (`vue/`)
- **Contrôleur** : Gestion des actions utilisateur (`controleur/`)

## Développeurs

- Agathe Papineau
- Dany Dudiot
- Nathan Rissot

## Licence

Ce projet est sous licence MIT. Voir le fichier LICENSE pour plus de détails.
