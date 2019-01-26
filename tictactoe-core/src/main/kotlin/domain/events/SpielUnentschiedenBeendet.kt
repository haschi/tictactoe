package domain.events

import com.github.haschi.tictactoe.domain.values.Aggregatkennung

data class SpielUnentschiedenBeendet(val spielId: Aggregatkennung)
