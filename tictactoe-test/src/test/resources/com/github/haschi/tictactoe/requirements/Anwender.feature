#language: de
Funktionalität: Anwender

  Grundlage:
    Angenommen ich habe das Anwenderverzeichnis angelegt
    Angenommen ich habe mich als Anwender "Matthias" registriert


  @core
  Szenariogrundriss: Zeichen aussuchen
    Wenn ich <mein Zeichen> als mein Zeichen für die nächste Partie Tic Tac Toe aussuche
    Dann werde ich den Warteraum als Spieler mit <mein Zeichen> betreten haben

    Beispiele:
      | mein Zeichen |
      | O            |
      | X            |

  @core
  Szenario: Anwender kann immer nur ein Zeichen als Spieler besitzen
    Angenommen ich habe X als mein Zeichen für die nächste Partie Tic Tac Toe ausgesucht
    Wenn ich X als mein Zeichen für die nächste Partie Tic Tac Toe aussuche
    Dann werde ich eine Fehlermeldung erhalten
