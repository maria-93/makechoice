package ru.kesva.makechoice.data.model

import java.util.*

class LocalCacheImp : LocalCache {
    private val localCacheMap = mutableMapOf<String, String>()

    companion object {
        const val placeholderLink = "placeholder link"
    }
    //сохраняем в мапу все запросы, приведенные к нижнему регистру, получаем уникальные запросы,
    // которые будут служить ключами в мапе. Значения в мапе остаются пустыми до первой подгрузки данных.
    // При повторном вызове метода, если в мапе уже содержится этот запрос, то пропускаем этап его сохранения в мапу.
    // В противном случае, этот ключ заменится на новый и сохраненный ранее url для запроса будет стерт и
    // придется заново его получать.
    override fun saveQueriesToMapWithoutValues(userQueries: List<String>) {
        userQueries.forEach { query ->
            if (localCacheMap.containsKey(query.toLowerCase(Locale.ROOT))) {
                //do nothing
            } else {
                localCacheMap[query.toLowerCase(Locale.ROOT)] = placeholderLink
            }
        }
    }

    override fun getQueriesFromMap(): List<String> {
        val queryList = mutableListOf<String>()
        localCacheMap.forEach { entry ->
            val query = entry.key
            queryList.add(query)
        }
        return queryList
    }

    override fun saveFetchedUriToMap(query: String, uri: String) {
        localCacheMap[query] = uri
    }

    override fun getLinkForQuery(query: String): String {
        return localCacheMap[query.toLowerCase(Locale.ROOT)] ?: placeholderLink
    }

    override fun isNeedToFetch(query: String): Boolean {
        val key = query.toLowerCase(Locale.ROOT)
        val value = localCacheMap.getValue(key)
        return value == placeholderLink
    }


}