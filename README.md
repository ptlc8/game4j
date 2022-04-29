# Game4J

## Présentation

à compléter

## Dépendences

- [MySQL Connector/J](https://mvnrepository.com/artifact/mysql/mysql-connector-java)
- [JavaFX](https://openjfx.io/)

## Récuperation et compilaition

- Cloner le projet
- Créer un fichier `Credentials.java` dans [/src/fr/kmmad/game4j](/src/fr/kmmad/game4j) de la forme :
```java
package fr.kmmad.game4j;
public class Credentials {
	static final String DB_USERNAME = "nom d'utilisateur mysql ici";
	static final String DB_PASSWORD = "mot de passe mysql à mettre ici";
}
```
- Télécharger le [SDK JavaFX](https://gluonhq.com/products/javafx/) correspondant à votre environnement

### Eclipse

Créer une JRE :
 - `Window` > `Preferences` > `Java` > `Installed JREs`
 - Sélectionner le JRE par défaut > `Dupplicate` > `Edit`
 - Mettre `javafx-jre-15` comme nom
 - Mettre `--module-path "/path/to/javafx-sdk-17/lib" --add-modules javafx.controls,javafx.fxml` dans les arguments par défaut (en remplaçcant `/path/to/javafx-sdk-17` par le chemin d'accès vers JavaFX téléchargé et dézippé précédemment)
 - `Add External JARs` > Sélectionner tous les .jar dans le dossier lib du dossier JavaFX dézippé > `Open`
 - `Finish`

### IntelliJ

à compléter

## Auteurs

Projet réalisé en avril et mai 2022 avec :
- Kévin Frick
- Alicia Beguin
- Marion Pinoit
- Mattéo Riedinger
- Duncan Boukhsibbi

Ce(tte) œuvre est mise à disposition selon les termes de la Licence Creative Commons Attribution - Pas d’Utilisation Commerciale - Partage dans les Mêmes Conditions 3.0 France.
https://creativecommons.org/licenses/by-nc-sa/3.0/fr/
