# Projet de COO ~ Itération 2 ~ Design Patterns

## Pattern Etat

Nous avons utilisé le pattern état pour gérer le comportement des NPC (Non-Player-Character), ce qui nous semblait intéressant étant donné qu'ils ont deux états bien distincts (Affamé / Rassasié) mais que le sujet laissait entendre qu'un état Junky pourrait être ajouté par la suite.

## Pattern Observateur

Nous avons utilisé le pattern observateur pour gérer la relation entre l'Horloge (le système qui passe les tours) et les NPC qui décident de leur prochaine action au passage de celle-ci. l'Horloge est donc un Observable et les NPC des Observateurs.

## Pattern Stratégie

Nous avons utilisé le pattern Stratégie pour gérer les interactions, nous avons créé une interface interactible qui possède une ou plusieurs interactions qui sont définies dans les implémentations concrete de l'interface interaction.

## Pattern Fabrique Abstraite

Nous avons utilisé le pattern fabrique abstraite pour l'instanciation de l'Objet Board représentant le plateau de jeu. Cela nous permet de rajouter des méthode de création et/ou des thèmes de manière facile et sans modifier la classe Board.

## Pattern Singleton

Nous l'avons utilisée pour simplifier l'accès à la Board, la Clock, et l'Inventory et aussi pour qu'il y ait une seule instance.

## Pattern Commande

utilisé pour la pierre précieuse.