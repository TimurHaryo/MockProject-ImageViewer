package id.timtam.protoimagepicker.uikit.multiimage

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.core.util.toAndroidPair
import androidx.core.util.toAndroidXPair
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import id.timtam.protoimagepicker.R
import id.timtam.protoimagepicker.uikit.multiimage.adapter.ImageIndicatorAdapter
import id.timtam.protoimagepicker.util.extension.orZero
import id.timtam.protoimagepicker.util.extension.runWithDelay
import id.timtam.protoimagepicker.util.extension.setMaxViewPoolSize

class ImageIndicator : RecyclerView, ImageIndicatorAdapter.OnImageIndicatorListener {

    private val snapper by lazy { LinearSnapHelper() }

    private val recycler by lazy { Recycler() }

    private var mIndicatorAdapter: ImageIndicatorAdapter? = null

    private var onPageChangeCallback: ViewPager2.OnPageChangeCallback? = null

//    private var previousPosition = DEFAULT_POSITION
    private val indicatorSource = arrayListOf<Pair<String, Boolean>>()
    private var isFromIndicatorClick = false

    private var viewPager2: ViewPager2? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        setHasFixedSize(true)
        setBackgroundColor(context.getColor(R.color.sb_image_indicator))
        snapper.attachToRecyclerView(this)
    }

    override fun onDetachedFromWindow() {
        mIndicatorAdapter?.onDestroy()
        mIndicatorAdapter = null
        onPageChangeCallback?.let {
            viewPager2?.unregisterOnPageChangeCallback(it)
        }
        onPageChangeCallback = null
        viewPager2 = null
        adapter = null
        super.onDetachedFromWindow()
    }

    override fun onClick(position: Int) {
        if (viewPager2?.currentItem != position) {
            isFromIndicatorClick = true
//            previousPosition = viewPager2?.currentItem.orZero()

            Log.i("MAHIS", "indicator ${recycler.scrapList}")
            viewPager2?.setCurrentItem(position, false)
        }
    }

    fun attachToViewPager2(vp: ViewPager2, images: List<String>?) {
        if (images.isNullOrEmpty()) return

        viewPager2 = vp

        Log.i("TIMUR", "visible ${(layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()}")

//        if (previousPosition < 0) {
//            previousPosition = viewPager2?.currentItem.orZero()
//        }

        indicatorSource.addAll(
            images.mapIndexed { index, image ->
                Pair(image, viewPager2?.currentItem == index)
            }
        )

        mIndicatorAdapter = ImageIndicatorAdapter(
            indicatorSource,
            this,
            layoutManager as LinearLayoutManager,
            recycler
        ).apply { setHasStableIds(true) }

        if (viewPager2?.currentItem != 0 && viewPager2?.currentItem != null) {
            mIndicatorAdapter?.setCurrentSelected(
                viewPager2?.currentItem!!,
                layoutManager?.findViewByPosition(recycler.convertPreLayoutPositionToPostLayout(viewPager2?.currentItem!!)),
                findViewHolderForItemId(viewPager2?.currentItem!!.toLong())
            )
        }

//        mIndicatorAdapter?.setData(indicatorSource)
        adapter = mIndicatorAdapter

        viewPager2?.let {
            setupViewPagerListener(it)
        }
    }

    private fun setupViewPagerListener(vp: ViewPager2) {
        onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                scrollToPosition(position)
                mIndicatorAdapter?.setCurrentSelected(
                    position,
                    recycler.getViewForPosition(position),
                    findViewHolderForItemId(position.toLong())
                )
                isFromIndicatorClick = false
//                layoutManager?.scrollToPosition(position)

//                mIndicatorAdapter?.run {
//                        notifyItemChanged(previousPosition, false)
//                        notifyItemChanged(position, true)
//                    }
//                isFromIndicatorClick = false
            }

//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
//                if (!isFromIndicatorClick) {
//                    isFromIndicatorClick = false
//                    previousPosition = vp.currentItem
//                }
//            }
//
//            override fun onPageScrollStateChanged(state: Int) {
//                super.onPageScrollStateChanged(state)
//                if (state == ViewPager2.SCROLL_STATE_IDLE && !isFromIndicatorClick) {
//                    previousPosition = vp.currentItem
//                }
//            }
        }

        onPageChangeCallback?.let { pageCallback ->
            vp.registerOnPageChangeCallback(pageCallback)
        }
    }

    companion object {
        private const val DEFAULT_POSITION = -1
    }
}