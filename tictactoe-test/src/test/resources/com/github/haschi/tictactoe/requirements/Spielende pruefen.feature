#language: de
Funktionalität: Spielende prüfen

  Grundlage:
    Angenommen ich habe das Spiel begonnen

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

  Szenario: Unentschieden