package com.github.haschi.tictactoe.requirements.core.testing

import com.github.haschi.tictactoe.domain.values.Feld
import com.github.haschi.tictactoe.domain.values.Spieler
import com.github.haschi.tictactoe.domain.values.Spielzug
import com.github.haschi.tictactoe.domain.values.Zeichen
import cucumber.api.TypeRegistry
import cucumber.api.TypeRegistryConfigurer
import io.cucumber.cucumberexpressions.ParameterType
import io.cucumber.datatable.DataTableType
import io.cucumber.datatable.TableEntryTransformer
import java.time.Duration
import java.util.*

class TypeRegistryConfiguration : TypeRegistryConfigurer {
    override fun configureTypeRegistry(registry: TypeRegistry) {
        registry.defineParameterType(ParameterType("feld", "[ABC][123]", Feld::class.java) { feld: String ->
            val regex = Regex("(?<spalte>[ABC])(?<zeile>[123])")
            val match = regex.find(feld)
            val spalte = match?.groups?.get("spalte")?.value!![0]
            val zeile = match.groups["zeile"]?.value?.toInt()!!

            Feld(spalte, zeile)
        })

        registry.defineParameterType(ParameterType("spieler", "X|O", Spieler::class.java) { spieler: String ->
            Spieler(spieler[0], "")
        })

        registry.defineParameterType(ParameterType("zeitraum", ".*", Duration::class.java) { zeitdauer: String ->
            println(zeitdauer)
            Duration.ofMillis(500)
//            val pattern = Pattern.compile("^((?<wert>\\d+) (?<einheit>Sekunde(n*)|Millisekunde(n*)))$")
//            val matcher = pattern.matcher(zeitdauer)
//
//            if (matcher.matches()) {
//                 when (matcher.group("einheit")) {
//                    "Sekunden", "Sekunde" -> Duration.ofSeconds(matcher.group("wert").toLong())
//                    "Millisekunden", "Millisekunde" -> Duration.ofMillis(matcher.group("wert").toLong())
//                    else -> throw IllegalArgumentException(zeitdauer)
//                }
//            } else {
//                throw IllegalArgumentException(zeitdauer)
//            }
        })

        registry.defineParameterType(ParameterType("zeichen", "X|O", Zeichen::class.java) { zeichen: String ->
            Zeichen(zeichen[0])
        })

        registry.defineDataTableType(DataTableType(Spielzug::class.java, object : TableEntryTransformer<Spielzug> {
            override fun transform(entry: Map<String, String>): Spielzug {
                return Spielzug(Spieler(entry["Spieler"]!![0], ""), feld(entry["Feld"]!!))
            }
        }))
    }

    private fun feld(wert: String): Feld {
        val regex = Regex("(?<spalte>[ABC])(?<zeile>[123])")
        val match = regex.find(wert)
        val spalte = match?.groups?.get("spalte")?.value!![0]
        val zeile = match.groups["zeile"]?.value?.toInt()!!

        return Feld(spalte, zeile)
    }

    override fun locale(): Locale {
        return Locale.GERMAN
    }
}