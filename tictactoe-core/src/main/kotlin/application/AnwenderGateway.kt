package application

import com.sun.xml.internal.ws.util.CompletedFuture
import domain.commands.KehreVomSpielZurück

interface AnwenderGateway {
    fun send(kehreVomSpielZurück: KehreVomSpielZurück): CompletedFuture<Unit>
}