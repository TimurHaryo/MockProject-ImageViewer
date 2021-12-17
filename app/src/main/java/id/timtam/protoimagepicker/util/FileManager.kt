package id.timtam.protoimagepicker.util

import android.graphics.BitmapFactory
import id.timtam.protoimagepicker.model.type.CanvasType

object FileManager {
    fun determineCanvasType(imagePath: String?) : CanvasType {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true

        BitmapFactory.decodeFile(imagePath.orEmpty(), options)
        val canvasBias = options.outWidth / options.outHeight.toFloat()

        return when {
            canvasBias in 0.9f..1.35f -> CanvasType.SQUARE
            canvasBias < 0.9f -> CanvasType.PORTRAIT
            else -> CanvasType.LANDSCAPE
        }
    }
}