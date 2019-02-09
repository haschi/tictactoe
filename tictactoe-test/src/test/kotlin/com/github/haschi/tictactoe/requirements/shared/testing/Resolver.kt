package com.github.haschi.tictactoe.requirements.shared.testing

interface Resolver<T> {
    fun resolve(zustand: IZustand): T
}
