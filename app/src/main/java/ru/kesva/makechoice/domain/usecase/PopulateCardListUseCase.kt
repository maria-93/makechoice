package ru.kesva.makechoice.domain.usecase

import android.util.Log
import ru.kesva.makechoice.data.model.Cache
import ru.kesva.makechoice.data.model.LocalCache
import ru.kesva.makechoice.domain.model.Card
import javax.inject.Inject

class PopulateCardListUseCase @Inject constructor(
    private val cache: Cache,
    private val localCache: LocalCache
) {
    operator fun invoke(userQueries: List<String>) {
        cache.cardList.clear()
        userQueries.forEach { userQuery ->
            val link = localCache.getLinkForQuery(userQuery)
            val card = Card(userQuery, link)
           Log.d(
                "New",
                "query and link that was saved to card: userQuery ${card.query} + link ${card.uri}"
            )
            cache.cardList.add(card)
        }
    }
}