# Use Cases

```Mermaid
flowchart LR
L([Lancer une Partie])
C([Changer d'orientation])
B([Se Deplacer])
E([Ouvir l'inventaire])
J([Jeter l'objet])
EE([Equiper un objet])
A([Attaquer])
R([Ramasser])
I([Interagir])
P([🧍‍♂️])

P --> L
P --> B
P --> C
P --> EE -. "Include" .-> E
P --> E
P --> J
P --> A -. "Include" .-> I
P --> R -. "Include" .-> I
```

| Touche | Action      |
| ------ | ----------- |
| ZQSD   | Deplacement |
| OKLM   | Regarder    |
| I      | Inventaire  |
| E      | Interagir   |
| J      | Jeter       |

# Scénario

## Changer d'orientation

### Scénario nominal

1. L'utilisateur presse la touche d'orientation (O, K, L, M).
2. Le système met à jour l'orientation du personnage.
3. Le système affiche la nouvelle orientation du personnage.

### Extensions

- 2 : Le personnage est déja dans la bonne orientation.
    - 2.1 : Retour à la fin du scénario nominal.

## Se déplacer

### Scénario nominal

1. L'utilisateur presse la touche de direction (Z, Q, S, D).
2. Le système vérifie que la case est accessible.
3. Le système met à jour la Position du personnage.
4. Le système met à jour l'orientation du personnage.
5. Le système affiche les nouvelles position et orientation du personnage.
6. Le système passe un tour.

### Extensions

- 2 : La case ciblée est inaccessible.
    - 1 : Affichage d'un message d'erreur.
    - 2 : Retour à la fin du scénario nominal (sans passer de tour).

- 4 : Le personnage est déjà dans la bonne orientation.
    - 1 : Retour au point 5 du scénario nominal.


## Jeter l'objet équipé

### Scénario nominal

1. L'utilisateur presse la touche de jet (J).
2. Le système vérifie que la case ciblée est accessible.
3. Le système retire l'objet équipé de l'inventaire du personnage.
4. Le système place l'objet dans la case ciblée.
5. Le système passe un tour.

### Extensions

- 2 : La case ciblée est inaccessible :
    - 1 : Affichage d'un message d'erreur.
    - 2 : Retour à la fin du scénario nominal (sans passer de tour).

- 3 : Le personnage n'a pas d'objet équipé :
    - 1 : Affichage d'un message d'erreur.
    - 2 : Retour à la fin du scénario nominal (sans passer de tour).


## Interagir

### Scénario nominal

1. L'utilisateur presse la touche d'interaction (I).
2. Le système affiche la liste des interactions possibles avec l'objet.
3. L'utilisateur sélectionne une option d'interaction.
4. Le système lance l'interaction.

### Extensions

- 2 : L'objet n'a pas d'interaction possible.
    - 1 : Affichage d'un message d'erreur.
    - 2 : Retour à la fin du scénario nominal (sans passer de tour).

- 3 : Le joueur presse à nouveau la touche d'interaction.
    - 1 : Le système ferme le menu.
    - 2 : Retour à la fin du scénario nominal (sans passer de tour).


## Attaquer 

### Scénario nominal

1. UC Interagir.
2. L'entité enregistre qu'elle a été frappée.
3. L'entité réinitialise son status d'amitié
4. Le système passe un tour.

### Extensions

Aucune


## Ramasser un objet

### Scénario nominal

1. UC Interagir
2. Le système ajoute l'objet à l'inventaire du personnage et le retire de la liste.
3. Le système passe un tour.

### Extensions 

- 5 : L'Utilisateur a déjà 9 objets dans son inventaire.
    - 1 : Le système affiche un message d'erreur.
    - 2 : Retour à la fin du scénario nominal (sans passer de tour).


## Ouvrir l'Inventaire

### Scénario nominal

1. L'utilisateur appuie sur la touche d'ouverture d'inventaire (I).
2. Le système affiche le menu d'inventaire.
3. Le système met en valeur l'objet équipé.

### Extensions

- 2 : L'utilisateur rappuie sur la touche d'inventaire (I).
    - 1 : Le système masque le menu d'inventaire.
    - 2 : Fin du scénario nominal.

- 3 : Il n'y a pas d'objet équipé.
    - 1 : Retour au point 3 du scénario nominal sans rien mettre en valeur.

- 3 + Ext 3.1 : Il n'y a pas d'objet dans l'inventaire.
    - 1 : Retour au point 3 du scénario nominal sans rien mettre en valeur.


## Equiper un objet

### Scénario nominal

1. UC Ouvrir l'Inventaire.
2. L'Utilisateur presse une des touches numériques (1-9).
3. Le système équipe l'objet correspondant.
4. Le système affiche le nom de l'objet correspondant.

### Extensions

- 3 : Il n'y a pas d'objets associés au numéro choisi.
    - 1 : Le système ne sélectionne pas de nouvel objet.
    - 2 : Retour au point 4 du scénario nominal, sans changer l'affichage.

- 2-4 : L'Utilisateur rappuie sur la touche d'inventaire (I).
    - 1 : Le système masque le menu d'inventaire.
    - 2 : Fin du scénario nominal.

## Lancer partie

### Scénario nominal

1. L'utilisateur démarre le programme.
2. Le système demande à l'utilisateur s'il veut charger une carte.
3. L'utilisateur répond.
4. Le système demande le chemin d'accès pour le fichier .txt.
5. L'utilisateur donne le chemin d'accès.
6. Le système parse la carte et commence le jeu.

### Extensions

- 4 : L'utilisateur ne veut pas charger de carte
  - UC Générer carte

- 4 : L'utilisateur répond autre chose que y ou n.
  - Le système affiche un message d'erreur
  - Retour au point 2 du scénario nominal.

- 6 : Le chemin d'accès est invalide.
  - Le système affiche un message d'erreur
  - Retour au point 4 du scénario nominal.

- 6 : Le fichier n'est pas un .txt
  - Le système affiche un message d'erreur
  - Retour au point 4 du scénario nominal.

- 6 : Le fichier n'est pas valide
  - Le système affiche un message d'erreur
  - Retour au point 4 du scénario nominal.

## Générer carte

### Scénario nominal

1. Le système demande le thème de la carte (F, J).
2. L'utilisateur répond.
3. Le système demande la hauteur.
4. L'utilisateur répond.
5. Le système demande la largeur.
6. L'utilisateur répond.
7. Le système génère la carte grâce aux informations et commence le jeu.

### Extensions

- 3 : L'utilisateur répond un thème non valide.
  - Le système affiche un message d'erreur
  - Retour au point 1 du scénario nominal.

- 5 : L'utilisateur répond une hauteur non valide.
  - Le système affiche un message d'erreur
  - Retour au point 3 du scénario nominal.

- 7 : L'utilisateur répond une largeur non valide.
  - Le système affiche un message d'erreur
  - Retour au point 5 du scénario nominal.