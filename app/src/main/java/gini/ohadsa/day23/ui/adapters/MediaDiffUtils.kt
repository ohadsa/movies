package gini.ohadsa.day23.ui.adapters

import android.support.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import gini.ohadsa.day23.models.response.TmdbItem
import javax.inject.Inject


class MediaDiffUtils @Inject constructor(): DiffUtil.Callback(){
    lateinit var oldList : List<TmdbItem>
    lateinit var newList : List<TmdbItem>
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val media1 = oldList[oldItemPosition]
        val media2 = newList[newItemPosition]
        return media1 == media2
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}