#language: de
Funktionalität: Zeichen setzen

  # Aufbau eines Spielfelds:
  #
  # ___|_A_|_B_|_C_|
  # _1_|___|___|___|
  # _2_|___|___|___|
  # _3_|___|___|___|

  Grundlage:
    Angenommen ich habe das Spiel begonnen

  @core @backend
  Szenario: Zeichen setzen
    Wenn Spieler X sein Zeichen auf Feld B2 setzt
    Dann werde ich den Spielzug B2 von Spieler X akzeptiert haben

  @core @backend
  Szenario: Spieler setzen abwechselnd Zeichen auf freie Felder
    Und Spieler X hat sein Zeichen auf Feld B2 gesetzt
    Wenn Spieler O sein Zeichen auf Feld B3 setzt
    Dann werde ich den Spielzug B3 von Spieler O akzeptiert haben

  @core @backend
  Szenario: Spieler setzt Zeichen auf belegtes Feld
    Und Spieler X hat sein Zeichen auf Feld B2 gesetzt
    Wenn Spieler O sein Zeichen auf Feld B2 setzt
    Dann konnte Spieler O sein Zeichen nicht platzieren, weil das Feld belegt gewesen ist

  @core
  Szenario: Spieler setzt Zeichen in falscher Reihenfolge
    Und Spieler X hat sein Zeichen auf Feld B2 gesetzt
    Wenn Spieler X sein Zeichen auf Feld B3 setzt
    Dann konnte Spieler X sein Zeichen nicht platzieren, weil er nicht an der Reihe war

