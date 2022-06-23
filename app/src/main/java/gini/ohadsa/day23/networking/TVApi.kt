package gini.ohadsa.day23.networking

import gini.ohadsa.day23.models.response.CreditWrapper
import gini.ohadsa.day23.models.response.ItemWrapper
import gini.ohadsa.day23.models.response.TVShow
import gini.ohadsa.day23.models.response.VideoWrapper
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TVShowApi {

    @GET("3/tv/popular?language=en")
    suspend fun popularItems(@Query("page") page: Int = 1 ): ItemWrapper<TVShow>

    @GET("3/tv/top_rated?language=en")
    suspend fun topRatedItems(@Query("page") page: Int = 1 ): ItemWrapper<TVShow>

    @GET("3/tv/on_the_air?language=en")
    suspend fun latestItems(@Query("page") page: Int = 1 ): ItemWrapper<TVShow>

    @GET("3/search/tv?language=en")
    suspend fun searchItems(
        @Query("page") page: Int,
        @Query("query") query: String
    ): ItemWrapper<TVShow>

    @GET("3/tv/{tvId}/videos")
    suspend fun tvTrailers(@Path("tvId") tvId: Int): VideoWrapper

    @GET("3/tv/{tvId}/credits")
    suspend fun tvCredit(@Path("tvId") tvId: Int): CreditWrapper
}

const val POSTER_HEADER = "https://image.tmdb.org/t/p/original/"