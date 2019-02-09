package com.github.haschi.tictactoe.requirements.shared.testing

import com.github.haschi.tictactoe.domain.values.Feld
import com.github.haschi.tictactoe.domain.values.Spieler
import com.github.haschi.tictactoe.domain.values.Spielzug
import com.github.haschi.tictactoe.domain.values.Zeichen
import com.github.haschi.tictactoe.requirements.core.testing.DieWelt
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

        registry.defineParameterType(ParameterType("name", ".*", String::class.java) { name: String ->
            name
        })

        registry.defineParameterType(ParameterType("spieler", "X|O", Resolver::class.java) { zeichen: String ->
            object : Resolver<Spieler> {
                override fun resolve(zustand: IZustand): Spieler {
                    try {
                        return zustand.spieler.getValue(zeichen[0])
                    } catch (exception: NoSuchElementException) {
                        throw IllegalArgumentException(zeichen, exception)
                    }

                }
            }
        })

        registry.defineParameterType(ParameterType("zeitraum", ".*", Duration::class.java) { _: String ->
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

        registry.defineDataTableType(
            DataTableType(
                SpielzugGenerator::class.java,
                object : TableEntryTransformer<SpielzugGenerator> {
                    override fun transform(entry: Map<String, String>): SpielzugGenerator {
                        return object : SpielzugGenerator {
                            override fun auflösen(zustand: DieWelt.Zustand): Spielzug {
                                val zeichen: Char = entry["Spieler"]!![0]
                                val spieler: Spieler = zustand.spieler[zeichen]!!
                                return Spielzug(spieler, feld(entry["Feld"]!!))
                            }
                        }
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