package gini.ohadsa.day23.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gini.ohadsa.day23.R
import gini.ohadsa.day23.models.response.MediaGroup
import gini.ohadsa.day23.models.response.TmdbItem
import gini.ohadsa.day23.utils.Logger
import javax.inject.Inject


class AdapterRecycler @Inject constructor() :
    RecyclerView.Adapter<AdapterRecycler.RecyclerViewHolder>() {
    private var lists: MutableList<MediaGroup> = mutableListOf()
    lateinit var listener: SetRecyclersOnBindViewHolder
    lateinit var onItemClickedListener: OnItemClicked
    lateinit var onFavClicked: OnFavClicked
    lateinit var diffUtil: MediaDiffUtils



    fun interface SetRecyclersOnBindViewHolder {
        fun setRecyclersOnBindViewHolder(): GridLayoutManager
    }

    fun interface OnFavClicked {
        fun favCallback(item: TmdbItem, pos: Int)
    }

    fun interface OnItemClicked {
        fun callback(item: TmdbItem, pos: Int)
    }

    private fun getItem(position: Int): MediaGroup {
        return lists[position]
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.ricycler_card, viewGroup, false)
        return RecyclerViewHolder(view)
    }


    fun removeItem(item: MediaGroup) {
        lists.remove(item)
        notifyDataSetChanged()
    }

    fun addItem(item: MediaGroup) {
        lists.add(item)
        notifyItemInserted(lists.indexOf(item))
    }


    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        if (lists.isNotEmpty()) {
            val recycler = getItem(position)

            val adapter = AdapterItem(onFavClicked , R.layout.movie_card_preview , { item, pos ->
                onItemClickedListener.callback(item, pos)
            } , diffUtil )
            var grid = listener.setRecyclersOnBindViewHolder()
            grid.orientation = GridLayoutManager.HORIZONTAL
            holder.moviesRecycle.layoutManager = grid
            holder.moviesRecycle.adapter = adapter
            holder.titleRecycler.text = recycler.name
            recycler.list.forEach {
                adapter.addItem(it)
            }
        }

    }

    fun addProduct(item: MediaGroup) {
        lists.add(item)

        // notifyItemChanged()

        notifyItemInserted(lists.size)
    }


    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var moviesRecycle: RecyclerView
        var titleRecycler: TextView

        init {
            moviesRecycle = itemView.findViewById(R.id.movies_recycle)
            titleRecycler = itemView.findViewById(R.id.title_recycler)
        }
    }
}