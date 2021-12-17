package id.timtam.protoimagepicker.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Stream(
    val postId: String?,
    val title: String,
    val likes: Int,
    val comments: Int,
    val streamImage: StreamImage
) : Parcelable {
    @Parcelize
    data class StreamImage(
        val canvasType: String,
        val imagesData: List<String>
    ) : Parcelable
}
