package id.timtam.protoimagepicker.uikit

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import id.timtam.protoimagepicker.R
import id.timtam.protoimagepicker.util.extension.*

class TextIndicator : AppCompatTextView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var onScrollListener: RecyclerView.OnScrollListener? = null
    private var onPageChangeListener: ViewPager2.OnPageChangeCallback? = null

    fun attachToRecyclerView(rv: RecyclerView) {
        visible()
        text = context.getString(
            R.string.per_page_indicator,
            1,
            rv.adapter?.itemCount.orZero()
        )

        onScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                visible()
                text = context.getString(
                    R.string.per_page_indicator,
                    recyclerView.currentItem + 1,
                    rv.adapter?.itemCount.orZero()
                )
                runWithDelay(7000L) {
                    gone()
                }
            }
        }

        rv.addOnScrollListener(onScrollListener!!)
    }

    fun attachToViewPager(vp: ViewPager2) {
        visible()
        text = context.getString(
            R.string.per_page_indicator,
            1,
            vp.adapter?.itemCount.orZero()
        )

        onPageChangeListener = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                visible()
                text = context.getString(
                    R.string.per_page_indicator,
                    position + 1,
                    vp.adapter?.itemCount.orZero()
                )
                runWithDelay(7000L) {
                    gone()
                }
            }
        }

        vp.registerOnPageChangeCallback(onPageChangeListener!!)
    }
}