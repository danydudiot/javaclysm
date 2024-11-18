# Use Cases

```Mermaid
flowchart LR
L([Lancer une Partie])
C([Changer d'Orientation])
B([Se Deplacer])
E([Ouvir l'inventaire])
J([Jeter l'Objet])
EE([Equiper un Objet])
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

## Changer d'Orientation

### Scénario nominal

1. l'Utilisateur presse la touche d'Orientation (O,K,L,M)
2. le Systeme met à jour l'Orientation du Personnage
3. le Systeme affiche la nouvelle Orientation du Personnage

### Extensions

- 2 : le Personnage est déja dans la bonne Orientation
    - 2.1 : Retour à la fin du Scénario Nominal

## Se Deplacer

### Scénario nominal

1. l'Utilisateur presse la touche de Direction (Z,Q,S,D)
2. le Systeme verifie que la case est Accessible
3. le Systeme met à jour la Position du Personnage
4. le Systeme met à jour l'Orientation du Personnage
5. le Systeme affiche les nouvelles Position et Orientation du Personnage
6. le Systeme passe un Tour

### Extensions

- 2 : La Case ciblée est Inaccessible
    - 1 : Affichage d'un message d'erreur
    - 2 : Retour à la fin du Scénario Nominal (sans passer de tour)

- 4 : Le Personnage est déjà dans la bonne orientation
    - 1 : Retour au point 5 du Scénario Nominal


## Jeter l'Objet Equipé

### Scénario Nominal

1. l'Utilisateur presse la touche de jet (G)
2. Le Systeme Verifie que la case ciblée est Accessible
3. Le systeme retire l'Objet équipé de l'Inventaire du Personnage
4. Le systeme place l'Objet dans la case ciblée
5. le Systeme passe un Tour

### Extensions

- 2 : La Case ciblée est Inaccessible :
    - 1 : Affichage d'un message d'erreur
    - 2 : Retour à la fin du Scénario Nominal (sans passer de tour)

- 3 : Le personnage n'a pas d'objet équipé :
    - 1 : Affichage d'un message d'erreur
    - 2 : Retour à la fin du Scénario Nominal (sans passer de tour)


## Interagir

### Scénario Nominal

1. L'Utilisateur presse la touche d'interaction (I)
2. le Systeme affiche la listes des interaction possibles avec l'objet
3. l'Utilisateur selectionne une option d'interaction
4. le Systeme lance l'interaction

### Extensions

- 2 : l'objet n'a pas d'interaction possible
    - 1 : Affichage d'un message d'erreur
    - 2 : Retour à la fin du Scénario Nominal

- 3 : le Joueur presse a nouveau la touche d'Interaction
    - 1 : le Systeme ferme le menu
    - 2 : Retour à la fin du Scénario Nominal


## Attaquer 

### Scénario Nominal

1. UC Interagir
2. l'Entitée attaquée est repoussée
3. l'Entitée enregistre qu'elle à été frappée
4. le Système passe un tour

### Extensions

- 2 : l'Entitée ne peut pas être repoussée (Case Inaccessible)
    - 1 : Retour au point 3 du Scénario Nominal


## Ramasser un Objet

### Scénario Nominal

1. UC Interagir
2. Le Systeme affiche la liste des objets a ramasser
3. L'Utilisateur choisi un objet
4. l'Utilisateur comfirme le ramassage de l'Objet
5. le Systeme ajoute l'Objet à l'inventaire du personnage et le retire de la liste
6. Retour au point 2 du Scénario Nominal
7. le Systeme masque la liste
8. le Systeme passe un tour

### Extensions 

- 2 : La liste est vide
    - 1 : le Systeme affiche un message d'erreur
    - 2 : Retour au point 8 du Scénario Nominal

- 5 : l'Utilisateur a déjà 10 objets dans son inventaire
    - 1 : le Systeme affiche un message d'erreur
    - 2 : Retour au point 6 du Scénario Nominal

- 2-5 : l'utilisateur appuie sur I
    - 1 : retour au point 8 du Scénario Nominal

- 8 : l'Utilisateur n'a pas ramassé d'Objet
    - 1 : fin du Scénario Nominal (Sans passer de tour)

## Ouvrir l'Inventaire

### Scénario Nominal

1. l'Utilisateur appuie sur la touche d'Ouverture d'inventaire (I)
2. le Systeme affiche le menu d'inventaire
3. le Systeme met en valeur l'objet équipé

### Extensions

- 2 : l'Utilisateur rappuie sur la touche d'Inventaire (I)
    - 1 : le Systeme masque le menu d'inventaire
    - 2 : Fin du Scénario Nominal

- 3 : il n'y a pas d'objet équipé:
    - 1 : retour au point 3 du Scénario Nominal sans rien mettre en valeur

- 3 + Ext 3.1 : Il n'y a pas d'objet dans l'inventaire
    - 1 : retour au point 3 du scénario Nominal sans rien mettre en valeur


## Equiper un objet

### Scénario Nominal

1. UC Ouvrir l'Inventaire
2. l'Utilisateur presse une des touches numériques (0-9)
3. le Systeme equipe l'objet correspondant
4. le Systeme affiche le nom de l'objet correspondant

### Extensions

- 3 : il n'y a pas d'objets associé au numéro choisi
    - 1 : le Systeme ne selectionne pas de nouvel objet
    - 2 : Retour au point 4 du scénario Nominal, sans changer l'Affichage

- 2-4 : l'Utilisateur rappuie sur la touche d'Inventaire (I)
    - 1 : le Systeme masque le menu d'inventaire
    - 2 : Fin du Scénario Nominal