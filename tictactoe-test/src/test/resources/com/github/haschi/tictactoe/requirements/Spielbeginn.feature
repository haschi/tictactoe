#language: de
Funktionalität: Spielbeginn

  Als Spieler möchte ich einer neuen Tic Tac Toe Partie beitreten
  um gegen einen anderen Spieler zu spielen

  Grundlage:
    Angenommen ich habe das Anwenderverzeichnis angelegt
    Und ich habe mich als Anwender "Matthias" registriert
    Und "Martin" hat sich als Anwender registriert
    Und ich habe das Symbol "X" ausgewählt

  @core
  Szenario: Einer Partie beitreten
    Wenn wenn der Anwender "Martin" das Symbol "O" auswählt
    Dann beginne ich mit dem Anwender "Martin" eine Partie Tic Tac Toe
