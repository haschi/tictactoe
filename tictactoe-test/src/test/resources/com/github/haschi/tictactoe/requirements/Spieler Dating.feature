#language: de
Funktionalität: Spieler Dating

  Als Spieler möchte ich einen Gegenspieler zugewiesen bekommen, um eine
  Partie Tic Tac Toe mit ihm Spielen zu können.

  Grundlage:
    Angenommen ich habe das Anwenderverzeichnis angelegt
    Angenommen ich habe mich als Anwender "Matthias" registriert


  @core
  Szenariogrundriss: Zeichen aussuchen
    Wenn ich <mein Zeichen> als mein Zeichen für die nächste Partie Tic Tac Toe aussuche
    Dann werde ich den Dating Room als Spieler mit <mein Zeichen> betreten haben
    Dann Dann werde ich auf einen Spieler warten, der <sein Zeichen> ausgesucht hat

    Beispiele:
      | mein Zeichen | sein Zeichen |
      | O            | X            |
      | X            | O            |


  Szenario: Dating erfolglos beenden
    Angenommen ich habe X als mein Zeichen für die nächste Partie Tic Tac Toe ausgesucht
    Wenn Wenn nach fünf Minuten kein Spieler O als sein Zeichen ausgesucht hat
    Dann werde ich nicht mehr auf einen Gegenspieler warten
