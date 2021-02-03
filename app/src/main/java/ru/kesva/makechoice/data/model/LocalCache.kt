package ru.kesva.makechoice.data.model

interface LocalCache {

    fun saveQueriesToMapWithoutValues(userQueries: List<String>)
    fun getQueriesFromMap(): List<String>
    fun saveFetchedUriToMap(query: String, uri: String)
    fun getLinkForQuery(query: String): String
    fun isNeedToFetch(query: String): Boolean


}