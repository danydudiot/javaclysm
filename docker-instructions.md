# Instructions pour conteneuriser le projet

## Prérequis
- Docker installé sur votre machine

## Construire et lancer le conteneur

Pour construire et lancer l'application dans un conteneur Docker, exécutez les commandes suivantes à la racine du projet :

```
# Construction de l'image
docker build -t javaclysm.

# Exécution du conteneur
docker run -it --name javaclysm-container javaclysm
```

Ces commandes vont :
1. Construire l'image Docker selon les instructions du Dockerfile
2. Créer et démarrer le conteneur
3. Exécuter l'application Java

## Pour arrêter le conteneur

Pour arrêter l'application, appuyez sur `Ctrl+C` dans le terminal où elle s'exécute ou, depuis un autre terminal :

```
docker stop javaclysm-container
```

Si vous souhaitez supprimer le conteneur après utilisation :

```
docker rm javaclysm-container
```

## Notes importantes

- L'option `-it` est nécessaire pour les applications interactives comme ce jeu, car elle permet :
  - `-i` : Maintenir l'entrée standard ouverte (pour saisir vos choix dans le jeu)
  - `-t` : Allouer un pseudo-terminal (pour afficher correctement l'interface)

