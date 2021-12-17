package id.timtam.protoimagepicker.ui.main

import androidx.lifecycle.ViewModel
import com.esafirm.imagepicker.model.Image

class MainViewModel : ViewModel() {

    var hasOriginImages = false
        private set

    private val _images = arrayListOf<Image>()
    val images: ArrayList<Image>
        get() = _images

    fun setOriginImages(images: List<Image>) {
        this.hasOriginImages = true

        this._images.apply {
            clear()
            addAll(images)
        }
    }
}