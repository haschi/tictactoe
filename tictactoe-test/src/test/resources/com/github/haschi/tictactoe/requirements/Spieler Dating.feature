#language: de
Funktionalität: Spieler Dating

  Als Spieler möchte ich auf einen Gegenspieler warten, damit ich Tic Tac Toe spielen kann

  Grundlage:

  Szenariogrundriss: Zeichen aussuchen
    Wenn ich <mein Zeichen> als mein Zeichen für die nächste Partie Tic Tac Toe aussuche
    Dann werde ich <mein Zeichen> für die nächste Partie Tic Tac Toe ausgesucht haben

    Beispiele:
      | mein Zeichen |
      | X            |
      | O            |

  Szenario: Auf Gegenspieler warten
    Angenommen ich habe X als mein Zeichen für die nächste Partie Tic Tac Toe ausgesucht
    Wenn ein Gegenspieler O als sein Zeichen für die nächste Partie auswählt
    Dann werde ich mit dem Gegenspieler eine Partie Tic Tac Toe begonnen haben

  Szenario: Nur eine Partie gleichzeitig starten
    Angenommen ich habe X als mein Zeichen für die nächste Partie Tic Tac Toe ausgesucht
    Wenn ich O als mein Zeichen für die nächste Partie Tic Tac Toe aussuche
    Dann Fehler