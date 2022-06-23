package gini.ohadsa.day23.models.response

import android.icu.lang.UCharacter
import com.google.gson.annotations.SerializedName

class ItemWrapper<T : TmdbItem>(
    @SerializedName("results")
    val items: MutableList<T>,
    @SerializedName("page") val page: Int,
    @SerializedName("total_pages") val pages: Int
)



class VideoWrapper(
    @SerializedName("results")
    val videos: List<Video>
)

class CreditWrapper(
    val cast: List<Cast>,
    val crew: List<Crew>
)

