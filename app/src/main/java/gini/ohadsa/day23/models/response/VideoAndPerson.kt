package gini.ohadsa.day23.models.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(
    @SerializedName("birthday")
    val birthDay: String?,
    @SerializedName("deathday")
    val deathDay: String?,
    val id: Int,

    @SerializedName("also_known_as")
    val alsoKnownAs: List<String>?,

    val biography: String,

    @SerializedName("place_of_birth")
    val placeOfBirth: String?
) : Parcelable

@Parcelize
data class Video(
    val id: String,
    val name: String,
    val site: String, //youtube/vimeo/facebook/inst

    @SerializedName("key")
    val videoId: String,

    val type: String

) : Parcelable {
    companion object {
        private const val SITE_YOUTUBE = "YouTube"
        private const val YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v="
        private const val YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi"


        fun getVideoUrl(video: Video): String =
            if (video.site.equals(SITE_YOUTUBE, ignoreCase = true))
                YOUTUBE_VIDEO_URL + video.videoId
            else ""

        fun getVideoThumbnail(video: Video): String =
            if (video.site.equals(SITE_YOUTUBE, ignoreCase = true))
                YOUTUBE_THUMBNAIL_URL + video.videoId +".jpg"
            else ""
    }
}
