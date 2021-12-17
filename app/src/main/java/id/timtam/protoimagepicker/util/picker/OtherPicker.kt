package id.timtam.protoimagepicker.util.picker

import android.app.Activity
import com.github.dhaval2404.imagepicker.ImagePicker
import java.lang.ref.WeakReference

class OtherPicker(
    weakRef: WeakReference<Activity>
) {

    private val picker by lazy {
        ImagePicker.with(weakRef.get()!!)
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
    }

    fun launch() {
        picker.start()
    }
}