# Utilisation de l'image OpenJDK officielle comme base
FROM openjdk:17-slim

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier les fichiers source du projet
COPY src/ ./src/

# Créer le répertoire pour les fichiers compilés
RUN mkdir -p ./build/classes

# Compiler le projet Java
RUN javac -d ./build/classes $(find ./src -name "*.java")

# Définir la commande pour exécuter l'application
CMD ["java", "-cp", "./build/classes", "main.Main"]
