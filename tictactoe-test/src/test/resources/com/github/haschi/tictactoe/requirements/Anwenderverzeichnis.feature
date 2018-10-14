#language: de
Funktionalität: Anwenderverzeichnis

  Als Anwender möchte ich mich beim Anwenderverzeichnis registrieren

  @core
  Szenario: Anwenderverzeichnis anlegen
    Wenn ich das Anwenderverzeichnis anlege
    Dann wird das Anwenderverzeichnis keine Anwender enthalten

  @core
  Szenario: Anwender registrieren
    Angenommen ich habe das Anwenderverzeichnis angelegt
    Wenn ich mich als Anwender "Matthias" registriere
    Dann werde ich den Anwender "Matthias" im Anwenderverzeichnis nicht gefunden haben

  @core
  Szenario: Anwender doppelt registrieren
    Angenommen ich habe das Anwenderverzeichnis angelegt
    Und ich habe mich als Anwender "Matthias" registriert
    Wenn ich mich erneut als Anwender "Matthias" registriere
    Dann werde ich den Anwender "Matthias" im Anwenderverzeichnis gefunden haben
