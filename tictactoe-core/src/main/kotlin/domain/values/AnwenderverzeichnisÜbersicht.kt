package domain.values

import com.github.haschi.tictactoe.domain.values.Aggregatkennung

data class AnwenderverzeichnisÜbersicht(val verzeichnisse: List<Aggregatkennung>) :
    List<Aggregatkennung> by verzeichnisse {
    constructor(vararg verzeichnis: Aggregatkennung) : this(verzeichnis.toList())
}