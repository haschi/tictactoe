# Tic Tac Toe

Ein einfaches Spiel. Gedacht DDD, CQRS / ES Konzepte mit Axon auszuprobieren
und eine Vorlage für weitere Projekte darzustellen

## Verzeichnis der verwendeten Werkzeuge

* [Kotlin](https://kotlinlang.org/) [[User Guide](https://kotlinlang.org/docs/reference/)]
* [Axon Framework](http://www.axonframework.org/) [[User Guide](https://docs.axonframework.org/)]
* [Spring Framework](https://spring.io/)
  * [Spring Boot](https://spring.io/projects/spring-boot)

Test

* [JUnit5](https://junit.org/junit5/) [[User Guide](https://junit.org/junit5/docs/current/user-guide/)]

* [Cucumber](https://docs.cucumber.io/) [[User Guide](https://docs.cucumber.io/guides/)]
* Spring Test
  * [AssertJ](http://joel-costigliola.github.io/assertj/)
  * [Mockito](http://site.mockito.org/) 
* [Mockito-Kotlin](https://github.com/nhaarman/mockito-kotlin/wiki/Mocking-and-verifying)

Standards

* [vnd.error](https://github.com/blongden/vnd.error)

## Konventionen

### Commands und Command Handler

Commands sind Anweisungen an die Anwendung. Sie stellen einen Befehl
dar. Sie werden deshalb im Imperativ formuliert.

Beispiel:

> Richte Warteraum ein

Command Handler sind Funktionen, welche die durch Commands angefragten
Anweisungen bearbeiten: 

> bearbeite "Richte Warteraum ein" 

### Events und Event Handler
Ereignisse sind beobachtbare Geschehnisse, die in der Regel eine 
Zustandsänderung beschreiben. Ereignisse passieren als Reaktion
auf Anweisungen. Aus der Sicht des Beobachters sind nur vergangene
Ereignisse bekannt. Da Ereignisse somit eine in der Vergangenheit
abgeschlossene Handlung beschreiben werden sie im Perfekt formuliert.

Beispiel

> Spieler hat Warteraum betreten
 
Event Handler sind Funktionen, mit denen Zustände geändert werden,
falls ein bestimmtes Ereignis eingetreten ist:

> falls "Spieler hat Warteraum betreten"
 
### Queries und Query Handler

Queries sind Fragen zur Ermittlung eines Zustandes, die an das System
gestellt werden.

Beispiel:

> Welche Anwender sind bekannt?

Query Handler sind Funktionen, die Fragen beantworten. 

> beantworte "Welche Anwender sind bekannt?" 