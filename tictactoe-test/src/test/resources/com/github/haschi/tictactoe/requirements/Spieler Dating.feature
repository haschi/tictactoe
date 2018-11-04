#language: de
Funktionalität: Spieler Dating

  Als Spieler möchte ich einen Gegenspieler zugewiesen bekommen, um eine
  Partie Tic Tac Toe mit ihm Spielen zu können.

  Grundlage:
    Angenommen ich habe das Anwenderverzeichnis angelegt
    Angenommen ich habe mich als Anwender "Matthias" registriert
    Angenommen "Martin" hat sich als Anwender registriert
    Angenommen "Maria" hat sich als Anwender registriert


  @core
  Szenariogrundriss: Zeichen aussuchen
    Wenn ich <mein Zeichen> als mein Zeichen für die nächste Partie Tic Tac Toe aussuche
    Dann werde ich den Dating Room als Spieler mit <mein Zeichen> betreten haben

    Beispiele:
      | mein Zeichen | sein Zeichen |
      | O            | X            |
      | X            | O            |


  @core
  Szenario: Zwei passende Spieler werden gefunden
    Angenommen ich habe den Dating Room als Spieler mit dem Zeichen X betreten
    Wenn Der Anwender "Martin" den Dating Room als Spieler mit dem Zeichen O betritt
    Dann werde ich mit "Martin" einen Spielpartner gefunden haben

  @core
  Szenario: Spieler hat Dating Room verlassen
    Angenommen die Anwender Martin und Matthias haben sich als Spielpartner gefunden
    Wenn Die Anwenderin "Maria" den Dating Room als Spieler mit dem Zeichen O betritt
    Dann wird die Anwenderin "Maria" den Dating Room mit O betreten haben

  @core
  Szenario: Dating erfolglos beenden
    Angenommen ich habe eine maximale Wartezeit von 500 Millisekunden für den Dating Room festgelegt
    Angenommen ich habe X als mein Zeichen für die nächste Partie Tic Tac Toe ausgesucht
    Wenn Wenn nach fünf Minuten kein Spieler O als sein Zeichen ausgesucht hat
    Wenn ich die maximale Wartezeit überschritten habe
    Dann werde ich den Dating Room ohne Spielpartner verlassen haben
