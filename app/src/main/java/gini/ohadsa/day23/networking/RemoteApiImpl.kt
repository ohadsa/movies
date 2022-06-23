package gini.ohadsa.day23.networking

import gini.ohadsa.day23.models.response.*
import javax.inject.Inject

class RemoteApiImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val TVApi: TVShowApi,
    private val personApi: PersonApi,
) : RemoteApi {

    // movies implementation Api
    override suspend fun popularMovies(page: Int): Result<ItemWrapper<Movie>> =
        try {
            Success(movieApi.popularItems(page))
        } catch (e: Throwable) {
            Failure(e)
        }


    override suspend fun topRatedMovies(page: Int): Result<ItemWrapper<Movie>> =
        try {
            Success(movieApi.topRatedItems(page))
        } catch (e: Throwable) {
            Failure(e)
        }

    override suspend fun latestMovies(page: Int): Result<ItemWrapper<Movie>>  =
        try {
            Success(movieApi.latestItems(page))
        } catch (e: Throwable) {
            Failure(e)
        }

    override suspend fun searchMovies(page: Int, query: String): Result<ItemWrapper<Movie>>  =
        try {
            Success(movieApi.searchItems(page ,query ))
        } catch (e: Throwable) {
            Failure(e)
        }

    override suspend fun movieTrailers(movieId: Int): Result<VideoWrapper>  =
        try {
            Success(movieApi.movieTrailers(movieId))
        } catch (e: Throwable) {
            Failure(e)
        }

    override suspend fun movieCredits(movieId: Int): Result<CreditWrapper> =
        try {
            Success(movieApi.movieCredit(movieId))
        } catch (e: Throwable) {
            Failure(e)
        }

    // tvShow implementation Api
    override suspend fun popularTVShows(page: Int): Result<ItemWrapper<TVShow>>  =
        try {
            Success(TVApi.popularItems(page))
        } catch (e: Throwable) {
            Failure(e)
        }
    override suspend fun topRatedTVShows(page: Int): Result<ItemWrapper<TVShow>> =
        try {
            Success(TVApi.topRatedItems(page))
        } catch (e: Throwable) {
            Failure(e)
        }

    override suspend fun latestTVShows(page: Int): Result<ItemWrapper<TVShow>> =
        try {
            Success(TVApi.latestItems(page))
        } catch (e: Throwable) {
            Failure(e)
        }
    override suspend fun searchTVShows(page: Int, query: String): Result<ItemWrapper<TVShow>> =
        try {
            Success(TVApi.searchItems(page , query))
        } catch (e: Throwable) {
            Failure(e)
        }
    override suspend fun tvShowTrailers(tvId: Int): Result<VideoWrapper> =
        try {
            Success(TVApi.tvTrailers(tvId))
        } catch (e: Throwable) {
            Failure(e)
        }

    override suspend fun tvShowCredits(tvId: Int): Result<CreditWrapper> =
        try {
            Success(TVApi.tvCredit(tvId))
        } catch (e: Throwable) {
            Failure(e)
        }


    // person implementation Api
    override suspend fun getPerson(personId: Int): Result<Person> =
        try {
            Success(personApi.getPerson(personId))
        } catch (e: Throwable) {
            Failure(e)
        }
}