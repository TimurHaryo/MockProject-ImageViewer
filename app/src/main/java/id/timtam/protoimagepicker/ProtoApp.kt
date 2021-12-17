package id.timtam.protoimagepicker

import android.app.Application
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideImageLoader

class ProtoApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // https://github.com/Piasy/BigImageViewer
        BigImageViewer.initialize(GlideImageLoader.with(this))
    }
}