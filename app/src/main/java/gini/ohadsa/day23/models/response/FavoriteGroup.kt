package gini.ohadsa.day23.models.response

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import com.google.android.youtube.player.internal.x
import com.google.android.youtube.player.internal.y
import dagger.hilt.android.lifecycle.HiltViewModel
import gini.ohadsa.day23.models.response.TmdbItem
import gini.ohadsa.day23.utils.Logger.Companion.log
import kotlinx.parcelize.Parcelize
import javax.inject.Inject


@HiltViewModel
class FavoriteGroup @Inject constructor() : ViewModel() {

    val listOfFav: MutableList<TmdbItem> = mutableListOf()

    fun updateFavorite(item: TmdbItem, pos: Int) {
        var itemIndex: Int? = null
        listOfFav.forEachIndexed { index, it ->
            if (it.name == item.name) {
                itemIndex = index
            }
        }
        itemIndex?.let {
            listOfFav.removeAt(it)
        } ?: listOfFav.add(item)
    }

    fun updateListIfInFavorite(list: MutableList<TmdbItem>) {

        // list1 && list2 -> isFavorit = true
        list.toSet().intersect(listOfFav.toSet()).forEach {   it.isFavorite = true }

    }

}