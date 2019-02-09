package domain.commands

import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class KehreVomSpielZurück(@TargetAggregateIdentifier val spieler: Aggregatkennung)
