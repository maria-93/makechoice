package ru.kesva.makechoice.data.model

import ru.kesva.makechoice.domain.model.Card

data class Cache (val cardList: MutableList<Card>) {
    val doublingCards = mutableListOf<Card>()
}