package gini.ohadsa.day23.networking

import gini.ohadsa.day23.models.response.Person
import retrofit2.http.GET
import retrofit2.http.Path

interface PersonApi {
    @GET("3/person/{personId}")
    suspend fun getPerson(@Path("personId") personId: Any): Person
}