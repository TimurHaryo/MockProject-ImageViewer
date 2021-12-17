package id.timtam.protoimagepicker.util.extension

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.view.postDelayed
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference

fun Activity.toast(duration: Int = Toast.LENGTH_SHORT, messageBlock: () -> String) {
    Toast.makeText(this, messageBlock(), duration).show()
}

fun Activity.weaker() = WeakReference(this)

val RecyclerView?.currentItem : Int
    get() = (this?.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

fun RecyclerView.setMaxViewPoolSize(maxViewTypeId: Int, maxPoolSize: Int = Int.MAX_VALUE) {
    for (i in 0..maxViewTypeId) {
        recycledViewPool.setMaxRecycledViews(i, maxPoolSize)
    }
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

inline fun View.runWithDelay(
    delayMilis: Long = 3000L,
    crossinline block: () -> Unit
) {
    postDelayed(delayMilis, block)
}