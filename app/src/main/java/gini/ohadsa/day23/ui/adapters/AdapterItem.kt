package gini.ohadsa.day23.ui.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import gini.ohadsa.day23.R
import gini.ohadsa.day23.models.response.MediaGroup
import gini.ohadsa.day23.models.response.TmdbItem
import gini.ohadsa.day23.networking.POSTER_HEADER
import gini.ohadsa.day23.utils.Logger.Companion.log
import javax.inject.Inject


class AdapterItem (
    var onFavoriteClickedListener: AdapterRecycler.OnFavClicked,
    var layoutType: Int,
    var itemClickListener: ItemClickListener,
    var diffUtil: MediaDiffUtils

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: MutableList<TmdbItem> = mutableListOf()

    fun interface ItemClickListener {
        fun movieItemClicked(item: TmdbItem, position: Int)
    }

    private fun getItem(position: Int): TmdbItem {
        return items[position]
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MovieViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(layoutType, viewGroup, false)
        return MovieViewHolder(view, onFavoriteClickedListener, itemClickListener, this::getItem)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vh = holder as MovieViewHolder
        val movie = getItem(position)
        vh.movieTitle.text = movie.name
        vh.movieDuration.text = movie.voteAverage.toString()
        val rat: Float = movie.voteAverage.toFloat().div(2)
        vh.movieRating.rating = rat
        val path = if(layoutType == R.layout.favorite_card) movie.backdropPath else movie.posterPath
        Picasso
            .get()
            .load(Uri.parse("$POSTER_HEADER$path"))
            .into(vh.movieImage)

        if (!movie.isFavorite) vh.heart.setImageResource(R.drawable.ic_heart_empty)
        else vh.heart.setImageResource(R.drawable.ic_heart_filled)


    }

    fun removeItem(item: TmdbItem) {
        val index = items.indexOf(item)
        log(items.toString())
        notifyItemRemoved(index)
        log(items.toString())
        items.remove(item)
    }

    fun updateList(updatedUserList: MutableList<TmdbItem>) {
        diffUtil.oldList = items
        diffUtil.newList = updatedUserList
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        items.clear()
        items.addAll(updatedUserList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun addItem(item: TmdbItem) {
        items.add(item)
        notifyItemInserted(items.indexOf(item))
    }

    class MovieViewHolder(
        itemView: View,
        private val onFavoriteClickedListener: AdapterRecycler.OnFavClicked,
        private val itemClickListener: ItemClickListener,
        val getItem: (Int) -> TmdbItem
    ) : RecyclerView.ViewHolder(itemView) {
        val movieImage: ImageView
        val movieTitle: TextView
        val movieDuration: TextView
        val movieRating: AppCompatRatingBar
        val heart: ImageView


        init {

            movieImage = itemView.findViewById(R.id.movie_image)
            movieTitle = itemView.findViewById(R.id.movie_title)
            movieDuration = itemView.findViewById(R.id.movie_duration)
            movieRating = itemView.findViewById(R.id.movie_rating)
            heart = itemView.findViewById(R.id.favorite)
            itemView.setOnClickListener {
                val item = getItem(bindingAdapterPosition)
                itemClickListener.movieItemClicked(item, bindingAdapterPosition)
            }
            heart.setOnClickListener {
                val item = getItem(bindingAdapterPosition)
                onFavoriteClickedListener.favCallback(item, bindingAdapterPosition)
                if (!item.isFavorite) heart.setImageResource(R.drawable.ic_heart_empty)
                else heart.setImageResource(R.drawable.ic_heart_filled)
            }


        }
    }


}