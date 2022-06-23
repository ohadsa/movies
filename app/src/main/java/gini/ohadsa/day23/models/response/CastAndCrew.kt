package gini.ohadsa.day23.models.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

interface Credit : Parcelable {
    val id: Any
    val credit: String
    val name: String
    val profilePath: String?
}

@Parcelize //the shorter import
class Cast(
    @SerializedName("character")
    override val credit: String,
    override val name: String,
    @SerializedName("profile_path")
    override val profilePath: String?,
    override val id: Int) : Credit

@Parcelize
class Crew(
    @SerializedName("job")
    override val credit: String,
    override val name: String,
    @SerializedName("profile_path")
    override val profilePath: String?,
    override val id: String) : Credit
