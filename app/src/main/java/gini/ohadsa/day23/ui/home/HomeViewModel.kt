package gini.ohadsa.day23.ui.home


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gini.ohadsa.day23.models.response.*
import gini.ohadsa.day23.networking.RemoteApi
import gini.ohadsa.day23.utils.Logger.Companion.log
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    // TODO model - name of list("top-rated..) to list of media..
    val listLiveData = MutableLiveData<MediaGroupList>()
    private val mediaGroupList = MediaGroupList()


    @Inject
    lateinit var remoteApi: RemoteApi

    fun initApi() {
        if(mediaGroupList.list.isEmpty()) {
            viewModelScope.launch {
                //movie recycle adding
                var resultMovie = remoteApi.topRatedMovies()
                handleResultMovie(MediaRequests.TOP_RATED_MOVIES.value, resultMovie)
                resultMovie = remoteApi.popularMovies()
                handleResultMovie(MediaRequests.POPULAR_MOVIES.value, resultMovie)
                resultMovie = remoteApi.latestMovies()
                handleResultMovie(MediaRequests.LATEST_MOVIES.value, resultMovie)
                // tvShow recycle adding
                var result = remoteApi.topRatedTVShows()
                handleResultTV(MediaRequests.TOP_RATED_TVSHOWS.value, result)
                result = remoteApi.latestTVShows()
                handleResultTV(MediaRequests.LATEST_TV_SHOWS.value, result)
                result = remoteApi.popularTVShows()
                handleResultTV(MediaRequests.POPULAR_TVSHOWS.value, result)
                listLiveData.postValue(mediaGroupList)
            }
        }


    }

    private fun handleResultMovie(name: String, result: Result<ItemWrapper<Movie>>) {
        if (result is Success) {
            mediaGroupList.list.add(MediaGroup(name , result.data.items as MutableList<TmdbItem>))
        } else if (result is Failure) {
            log(result.exc.toString())
        }
    }

    private fun handleResultTV(name: String, result: Result<ItemWrapper<TVShow>>) {
        if (result is Success) {
            mediaGroupList.list.add(MediaGroup(name , result.data.items as MutableList<TmdbItem>))
        } else if (result is Failure) {
            log(result.exc.toString())
        }
    }

    fun updateFavorite(item: TmdbItem, pos: Int) {
        item.isFavorite = !item.isFavorite
    }
}

enum class MediaRequests(val value: String) {
    TOP_RATED_MOVIES("Top rated Movies"), POPULAR_MOVIES("popular Movies"), LATEST_MOVIES("Latest Movies"),
    TOP_RATED_TVSHOWS("Top rated TV Shows"), POPULAR_TVSHOWS("popular TV Shows"), LATEST_TV_SHOWS("Latest TV Shows")

}

