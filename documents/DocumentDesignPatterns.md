# Projet de COO ~ Itération 1 ~ Design Patterns

## Pattern Etat

Nous avons utilisé le pattern état pour gérer le comportement des NPC (Non-Player-Character), ce qui nous semblait intéressant etant donné qu'ils ont deux états bien distinct (Affamé / Rassasié) mais que le sujet laissait entendre qu'un état Junky pourrait être ajouté par la suite.

## Pattern Observateur

Nous avons utilisé le pattern observateur pour gerer la relation entre l'Horloge (le systeme qui passe les tours) et les NPC qui decident de leur prochaine action au passage de celle ci. l'Horloge est donc un Observable et les NPC des Observateurs.

## Pattern Stratégie (Patron de méthode ???)

Nous avons utilisé le pattern Stratégie pour gerer les interactions, nous avons créé une interface interactible qui possede une ou plusieurs interactions qui sont définie dans les implémentations concrete de l'interface interaction.

## Pattern Composite

Nous avons utilisé le pattern composite pour gerer les terrains. ceux ci possedent une entitée, représentant une eventuelle entitée qui se tiendrait sur la case. Cela nous permet de simplifier la gestion en ne manipulant qu'un seul tableau.

