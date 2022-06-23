package gini.ohadsa.day23.networking

import gini.ohadsa.day23.models.response.*

interface RemoteApi {
    //Movies:
    suspend fun popularMovies(page: Int = 1): Result<ItemWrapper<Movie>>
    suspend fun topRatedMovies(page: Int = 1): Result<ItemWrapper<Movie>>
    suspend fun latestMovies(page: Int = 1): Result<ItemWrapper<Movie>>
    suspend fun searchMovies(page: Int = 1, query: String): Result<ItemWrapper<Movie>>
    suspend fun movieTrailers(movieId: Int): Result<VideoWrapper>
    suspend fun movieCredits(movieId: Int): Result<CreditWrapper>

    //TV:
    suspend fun popularTVShows(page: Int = 1): Result<ItemWrapper<TVShow>>
    suspend fun topRatedTVShows(page: Int = 1): Result<ItemWrapper<TVShow>>
    suspend fun latestTVShows(page: Int = 1): Result<ItemWrapper<TVShow>>
    suspend fun searchTVShows(page: Int = 1, query: String): Result<ItemWrapper<TVShow>>
    suspend fun tvShowTrailers(tvId: Int): Result<VideoWrapper>
    suspend fun tvShowCredits(tvId: Int): Result<CreditWrapper>

    //Person:
    suspend fun getPerson(personId: Int): Result<Person>
}


