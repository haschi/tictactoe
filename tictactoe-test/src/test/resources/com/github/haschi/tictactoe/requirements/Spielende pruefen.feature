#language: de
Funktionalität: Spielende prüfen

    # Aufbau eines Spielfelds:
  #
  # ___|_A_|_B_|_C_|
  # _1_|___|___|___|
  # _2_|___|___|___|
  # _3_|___|___|___|

#  Grundlage:
#    Angenommen ich habe das Spiel begonnen

  @core
  Szenariogrundriss: Spieler X gewinnt

    Angenommen ich habe folgenden Spielverlauf:
      | Spieler | Feld |
      | X       | B2   |
      | O       | B1   |
      | X       | A1   |
      | O       | C1   |

    Wenn Spieler X sein Zeichen auf Feld <Feld> setzt
    Dann hat Spieler X <Spielergebnis>

    Beispiele:
      | Feld | Spielergebnis  |
      | C3   | gewonnen       |
      | A3   | nicht gewonnen |

  @core
  Szenario: Spieler O gewinnt

    Angenommen ich habe folgenden Spielverlauf:
      | Spieler | Feld |
      | X       | A1   |
      | O       | B2   |
      | X       | C3   |
      | O       | B1   |
      | X       | A2   |

    Wenn Spieler O sein Zeichen auf Feld B3 setzt
    Dann hat Spieler O gewonnen

  @core
  Szenario: Unentschieden

    Angenommen ich habe folgenden Spielverlauf:
      | Spieler | Feld |
      | X       | B2   |
      | O       | B1   |
      | X       | C2   |
      | O       | A2   |
      | X       | C1   |
      | O       | A3   |
      | X       | A1   |
      | O       | C3   |

    Wenn Spieler X sein Zeichen auf Feld B3 setzt
    Dann endet die Partie unentschieden