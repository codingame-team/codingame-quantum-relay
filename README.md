# Quantum Relay — CodinGame Multiplayer SDK Project

Un puzzle multijoueur algorithmique complet basé sur les **flots d'énergie dans un réseau dynamique** et la **géométrie algorithmique (intersection de faisceaux lasers)**, développé avec le **CodinGame Game Engine (`4.7.7`)** en **3 ligues progressives** (**Bois 2 → Bois 1 → Bronze+**).

---

## 1. Commandes Rapides

### Lancer le Viewer Web Local (`http://localhost:8888/test.html`)
```bash
cd quantum-relay-sdk
mvn test-compile exec:java -Dexec.mainClass="Main" -Dexec.classpathScope="test"
```

### Lancer le Banc de Simulation Rapide (Sans Serveur Web)
```bash
cd quantum-relay-sdk
mvn test-compile exec:java -Dexec.mainClass="Main" -Dexec.classpathScope="test" -Dexec.args="--simulate"
```

---

## 2. Structure du Projet

* [`pom.xml`](pom.xml) : Configuration Maven avec `core`, `module-entities`, `module-tooltip`, `module-endscreen`, `module-toggle` et `runner` (`4.7.7`).
* [`config/config.ini`](config/config.ini) : Paramètres du jeu (`type=multi`, `min_players=2`, `max_players=2`).
* [`config/stub.txt`](config/stub.txt) : Générateur de code d'entrée/sortie multi-langages.
* [`config/statement_en.html.tpl`](config/statement_en.html.tpl) & [`config/statement_fr.html.tpl`](config/statement_fr.html.tpl) : Énoncés HTML complets en anglais et en français pour chaque ligue.
* [`src/main/java/com/codingame/game/Referee.java`](src/main/java/com/codingame/game/Referee.java) : Arbitre complet, calcul de flot d'énergie dans les arbres de relais et détection d'interférences géométriques.
* [`src/main/java/com/codingame/game/Geometry.java`](src/main/java/com/codingame/game/Geometry.java) : Prédicats géométriques d'orientation (`crossProduct`) et point d'intersection de segments.
