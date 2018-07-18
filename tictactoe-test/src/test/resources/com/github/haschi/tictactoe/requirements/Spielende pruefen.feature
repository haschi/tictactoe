#language: de
Funktionalität: Spielende prüfen

  Grundlage:
    Angenommen ich habe das Spiel begonnen

  @domäne
  Szenariogrundriss: Spieler hat drei Zeichen nebeneinander

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

  Szenario: Unentschieden