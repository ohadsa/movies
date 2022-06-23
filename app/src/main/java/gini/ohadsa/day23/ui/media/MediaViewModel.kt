package gini.ohadsa.day23.ui.media

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gini.ohadsa.day23.models.response.*
import gini.ohadsa.day23.networking.RemoteApi
import gini.ohadsa.day23.utils.Logger
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MediaViewModel @Inject constructor() : ViewModel() {


    @Inject
    lateinit var remoteApi: RemoteApi

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> get() = _title

    private val _overView = MutableLiveData<String>()
    val overView: LiveData<String> get() = _overView

    private val _poster_path = MutableLiveData<String>()
    val poster_path: LiveData<String> get() = _poster_path

    private val _rating = MutableLiveData<Float>()
    val rating: LiveData<Float> get() = _rating

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    private val _popularity = MutableLiveData<String>()
    val popularity: LiveData<String> get() = _popularity

    var media: TmdbItem? = null
        get() = field
        set(value) {
            field = value
            init()
        }

    private fun init() {
        media?.let {
            _title.postValue(it.name)
            _overView.postValue(it.overview)
            _poster_path.postValue(it.posterPath.toString())
            _popularity.postValue(it.voteAverage.toString())
            _rating.postValue(it.voteAverage.toFloat())
            _isFavorite.postValue(it.isFavorite)

        }
    }

    fun getTreiler(c: (String) -> Unit) {

        viewModelScope.launch {
            val result = remoteApi.movieTrailers(media?.id!!)
            if (result is Success) {

                // insert to model ..
                result.data.videos.forEach {
                    if (it.site == "YouTube") {
                        c(it.videoId)
                        return@launch
                    }
                }

            } else if (result is Failure) {
                Logger.log("no video\n\n" + result.exc.toString())
            }

        }

    }

}