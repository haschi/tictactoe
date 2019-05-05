#language: de
Funktionalität: Anwenderverzeichnis

  Als Anwender möchte ich mich beim Anwenderverzeichnis registrieren

  @core
  Szenario: Anwenderverzeichnis anlegen
    Wenn ich das Anwenderverzeichnis anlege
    Dann wird das Anwenderverzeichnis keine Anwender enthalten

  @core
  Szenario: Für jedes Anwenderverzeichnis wird ein Warteraum angelegt
    Wenn ich das Anwenderverzeichnis anlege
    Dann werde ich einen Warteraum dazu eingerichtet haben

  @core
  Szenariogrundriss: Anwender registrieren
    Angenommen ich habe das Anwenderverzeichnis angelegt
    Wenn ich mich als Anwender "<Name>" registriere
    Dann werde ich den Anwender "<Name>" im Anwenderverzeichnis nicht gefunden haben
    Und ich werde die vom Profilersteller erstellten Eigenschaften von "<Name>" abrufen können

    Beispiele:
      | Name     |
      | Matthias |
      | Martin   |

  @core
  Szenario: Anwender doppelt registrieren
    Angenommen ich habe das Anwenderverzeichnis angelegt
    Und ich habe mich als Anwender "Matthias" registriert
    Wenn ich mich erneut als Anwender "Matthias" registriere
    Dann werde ich den Anwender "Matthias" im Anwenderverzeichnis gefunden haben

  @core
  Szenario: Anwenderverzeichnisübersicht
    Wenn ich das Anwenderverzeichnis anlege
    Dann werde ich das Anwenderverzeichnis in der Übersicht sehen
