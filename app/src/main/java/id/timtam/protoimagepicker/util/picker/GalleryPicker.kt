package id.timtam.protoimagepicker.util.picker

import android.app.Activity
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.model.Image
import id.timtam.protoimagepicker.BuildConfig
import id.timtam.protoimagepicker.R
import java.lang.ref.WeakReference

class GalleryPicker(
    weakActivity: WeakReference<Activity>
) {

    private val picker by lazy {
        ImagePicker.create(weakActivity.get())
            .folderMode(true)
            .toolbarImageTitle("Tap to select")
            .includeVideo(false)
            .theme(R.style.Theme_ProtoImagePicker)
            .multi()
            .showCamera(false)
            .apply {
                if (BuildConfig.DEBUG) {
                    enableLog(true)
                }
            }
    }

    // Default image limit is 5
    private var imageLimit = 5

    fun setLimit(limit: Int) : GalleryPicker {
        this.imageLimit = limit
        return this
    }

    fun open() {
        picker.limit(imageLimit).start()
    }

    fun openWithOriginImage(images: ArrayList<Image>) {
        picker.limit(imageLimit).origin(images).start()
    }
}