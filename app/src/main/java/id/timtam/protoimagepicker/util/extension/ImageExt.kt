package id.timtam.protoimagepicker.util.extension

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.github.piasy.biv.loader.ImageLoader
import com.github.piasy.biv.view.BigImageView
import id.timtam.protoimagepicker.R
import java.io.File

fun ImageView.loadImage(context: Context, file: File?) {
    Glide.with(context)
        .load(file?.toUri())
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .dontAnimate()
        .priority(Priority.HIGH)
        .into(this)
}

fun ImageView.loadStreamImage(context: Context?, url: String?) {
    if ((context as? AppCompatActivity)?.isDestroyed == true) return
    if (context == null || url.isNullOrEmpty()) return

    Glide.with(context)
        .asDrawable()
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                this@loadStreamImage.scaleType = ImageView.ScaleType.CENTER_INSIDE

                if (File(url).exists()) {
                    Glide.with(context)
                        .load(Uri.fromFile(File(url)))
                        .error(R.drawable.ic_launcher_background)
                        .placeholder(R.color.black)
                        .into(this@loadStreamImage)
                }

                return false
            }

            override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                return false
            }
        })
        .into(object : DrawableImageViewTarget(this) {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                if (resource is GifDrawable) {
                    resource.setLoopCount(20)
                }
                super.onResourceReady(resource, transition)
            }
        })

}

fun BigImageView.loadUrl(url: String?) {
    this.apply {
        val isLocalImage = url.orEmpty().first() == '/'
        val imageUri = if (isLocalImage) {
            Uri.fromFile(File(url.orEmpty()))
        } else {
            Uri.parse(url)
        }

        showImage(imageUri)
        setImageLoaderCallback(object : ImageLoader.Callback {
            override fun onFinish() {
                onImageSSIVReady()
            }

            override fun onSuccess(image: File?) {
                onImageSSIVReady()
            }

            override fun onFail(error: Exception?) {
                onImageSSIVReady()
            }

            override fun onCacheHit(imageType: Int, image: File?) {
                onImageSSIVReady()
            }

            override fun onCacheMiss(imageType: Int, image: File?) {
                onImageSSIVReady()
            }

            override fun onProgress(progress: Int) {

            }

            override fun onStart() {

            }

        })
    }
}

private fun BigImageView.onImageSSIVReady() {
    try {
        this.ssiv.setDoubleTapZoomDuration(100)
        // use this line to force SSIV lib to auto-rotate the image as per EXIF data.
// It's exactly what's written in SSIV docs here: https://github.com/davemorrissey/subsampling-scale-image-view/wiki/07.-Configuration
        this.ssiv.orientation = SubsamplingScaleImageView.ORIENTATION_USE_EXIF
    } catch (npe: NullPointerException) {

    }
}