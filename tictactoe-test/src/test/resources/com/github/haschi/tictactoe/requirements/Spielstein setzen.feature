#language: de
Funktionalität: Spielstein setzen

  # Aufbau eines Spielbretts:
  #
  # ___|_A_|_B_|_C_|
  # _1_|___|___|___|
  # _2_|___|___|___|
  # _3_|___|___|___|

#  @
#  Szenario: Spielbeginn
#    Wenn ich das Spiel beginne
#    Dann wird das Spielfeld leer sein

  @domäne
  Szenario: Spielstein setzen
    Angenommen ich habe das Spiel begonnen
    Wenn Spieler X sein Zeichen auf Feld B2 setzt
    Dann werde ich den Spielzug B2 von Spieler X akzeptiert haben

  Szenario: Spieler setzen abwechselnd Zeichen auf freie Felder
    Angenommen ich habe das Spiel begonnen
    Und Spieler X hat sein Zeichen auf Feld B2 gesetzt
    Wenn Spieler O sein Zeichen auf Feld B3 setzt
    Dann Dann werde ich den Spielzug B3 von Spieler O akzeptiert haben

  Szenario: Spieler setzt Spielstein auf belegtes Feld
    Angenommen ich habe das Spiel begonnen
    Und Spieler X hat sein Zeichen auf Feld B2 gesetzt
    Wenn Spieler O sein Zeichen auf Feld B2 setzt
    Dann Dann konnte Spieler O sein Zeichen nicht platzieren, weil das Feld belegt gewesen ist

  Szenario: Spieler setzt Spielstein in falscher Reihenfolge
    Angenommen ich habe das Spiel begonnen
    Und Spieler X hat sein Zeichen auf Feld B2 gesetzt
    Wenn Spieler X sein Zeichen auf Feld B3 setzt
    Dann konnte Spieler X sein Zeichen nicht platzieren, weil er nicht an der Reihe war

