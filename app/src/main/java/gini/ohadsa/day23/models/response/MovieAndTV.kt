package gini.ohadsa.day23.models.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

interface TmdbItem : Parcelable {
    val id: Int
    val overview: String
    val releaseDate: String?
    val posterPath: String?
    val backdropPath: String?
    val name: String
    val voteAverage: Double
    var isFavorite: Boolean
}

@Parcelize
data class Movie(
    override val id: Int,
    override val overview: String,
    @SerializedName("release_date")
    override val releaseDate: String?,
    @SerializedName("poster_path")
    override val posterPath: String?,
    @SerializedName("backdrop_path")
    override val backdropPath: String?,
    @SerializedName("title")
    override val name: String,
    @SerializedName("vote_average")
    override val voteAverage: Double,
    override var isFavorite: Boolean = false
) : TmdbItem {


    override fun equals(other: Any?): Boolean {
        if (other is Movie) {
            if (
                id == other.id
                && isFavorite == other.isFavorite
                && overview == other.overview
                && releaseDate == other.releaseDate
                && posterPath == other.posterPath
                && backdropPath == other.backdropPath
                && name == other.name
                && voteAverage == other.voteAverage
            )
                return true
        }
        return false

    }
}

@Parcelize
data class TVShow(
    override val id: Int,
    override val overview: String,
    @SerializedName("first_air_date")
    override val releaseDate: String?,
    @SerializedName("poster_path")
    override val posterPath: String?,
    @SerializedName("backdrop_path")
    override val backdropPath: String?,
    override val name: String,
    @SerializedName("vote_average")
    override val voteAverage: Double,
    override var isFavorite: Boolean = false
) : TmdbItem
