package gini.ohadsa.day23.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gini.ohadsa.day23.models.response.Failure
import gini.ohadsa.day23.models.response.Success
import gini.ohadsa.day23.models.response.TmdbItem
import gini.ohadsa.day23.networking.RemoteApi
import gini.ohadsa.day23.ui.adapters.MediaDiffUtils
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor() : ViewModel() {

    val listLiveData = MutableLiveData<MutableList<TmdbItem>>()
    val list = mutableListOf<TmdbItem>()
    @Inject
    lateinit var remoteApi: RemoteApi

    @Inject
    lateinit var diffUtil : MediaDiffUtils

    fun getMoviesByQuery(query: String) {
        viewModelScope.launch {
            val result = remoteApi.searchMovies(1, query)
            if (result is Success) {
                list.clear()
                result.data.items.forEach { list.add(it as TmdbItem) }
            } else if (result is Failure) {}
            listLiveData.postValue(list)
        }
    }

    fun getTVShowByQuery(query: String) {
        viewModelScope.launch {
            val result = remoteApi.searchTVShows(1, query)
            if (result is Success) {
                list.clear()
                result.data.items.forEach { list.add(it as TmdbItem) }
                //
            } else if (result is Failure) {
              //
            }
            listLiveData.postValue(list)
        }
    }


}