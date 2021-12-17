package id.timtam.protoimagepicker.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.databinding.BindingAdapter
import com.github.piasy.biv.view.BigImageView
import id.timtam.protoimagepicker.model.type.CanvasType
import id.timtam.protoimagepicker.util.FileManager.determineCanvasType
import id.timtam.protoimagepicker.util.extension.loadImage
import id.timtam.protoimagepicker.util.extension.loadStreamImage
import id.timtam.protoimagepicker.util.extension.loadUrl
import java.io.File
import kotlin.math.roundToInt

object Binding {

    @JvmStatic
    @BindingAdapter("app:imagePath")
    fun setImagePath(view: ImageView, path: String?) {
        path?.let {
            view.loadImage(view.context, File(it))
        }
    }

    @JvmStatic
    @BindingAdapter("app:imageUrl")
    fun setImageUrl(view: ImageView, path: String?) {
        view.loadStreamImage(view.context, path)
    }

    @JvmStatic
    @BindingAdapter("app:imageUrl")
    fun setBigImageUrl(view: BigImageView, path: String?) {
        if (path.isNullOrEmpty()) return
        view.loadUrl(path)
    }

    @JvmStatic
    @BindingAdapter("app:detectRatio")
    fun detectRatio(view: View, source: String?) {
        try {
            view.updateLayoutParams<ConstraintLayout.LayoutParams> {
                dimensionRatio = CanvasType.getRatio(determineCanvasType(source))
            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        }
    }

    @JvmStatic
    @BindingAdapter("app:detectScaleType")
    fun detectScaleType(view: AppCompatImageView, source: String?) {
        view.scaleType =
            if (determineCanvasType(source) == CanvasType.PORTRAIT) ImageView.ScaleType.FIT_CENTER else ImageView.ScaleType.CENTER_CROP
    }

    @JvmStatic
    @BindingAdapter("app:detectMultiScaleType")
    fun detectMultiScaleType(view: AppCompatImageView, type: String?) {
        view.scaleType =
            if (CanvasType.getTypeValue(type) == CanvasType.PORTRAIT) ImageView.ScaleType.FIT_CENTER else ImageView.ScaleType.CENTER_CROP
    }

    @JvmStatic
    @BindingAdapter("app:adjustHeightRatio")
    fun adjustHeightRatio(view: View, type: String?) {
        try {
            view.updateLayoutParams {
                height = (view.measuredWidth / CanvasType.getTypeValue(type).basis).roundToInt()
            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        }
    }

    @JvmStatic
    @BindingAdapter("app:adjustWidthRatio")
    fun adjustWidthRatio(view: View, type: String?) {
        try {
            view.updateLayoutParams {
                width = (height * CanvasType.getTypeValue(type).basis).roundToInt()
            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        }
    }

    @JvmStatic
    @BindingAdapter("app:toStringText")
    fun setToStringText(view: TextView, textNonString: Any?) {
        view.text = textNonString.toString()
    }
}