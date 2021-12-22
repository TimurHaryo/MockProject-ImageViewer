package id.timtam.protoimagepicker.uikit.multiimage

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.viewpager2.widget.ViewPager2
import id.timtam.protoimagepicker.R
import id.timtam.protoimagepicker.uikit.multiimage.adapter.ImageIndicatorAdapter

class ImageIndicator : RecyclerView, ImageIndicatorAdapter.OnImageIndicatorListener {

    private val snapper by lazy { LinearSnapHelper() }

    private var mIndicatorAdapter: ImageIndicatorAdapter? = null

    private var onPageChangeCallback: ViewPager2.OnPageChangeCallback? = null

    private val indicatorSource = arrayListOf<Pair<String, Boolean>>()

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
        (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
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
            viewPager2?.setCurrentItem(position, false)
        }
    }

    fun attachToViewPager2(vp: ViewPager2, images: List<String>?) {
        if (images.isNullOrEmpty()) return

        viewPager2 = vp

        indicatorSource.addAll(
            images.mapIndexed { index, image ->
                Pair(image, viewPager2?.currentItem == index)
            }
        )

        mIndicatorAdapter =
            ImageIndicatorAdapter(indicatorSource, this)
                .apply {
                    setHasStableIds(true)
                }

        if (viewPager2?.currentItem != 0 && viewPager2?.currentItem != null) {
            if (viewPager2?.currentItem!! > (MAX_VISIBLE_INDICATORS - 1)) {
                scrollToPosition(viewPager2?.currentItem!!)
            }

            mIndicatorAdapter?.setCurrentSelected(viewPager2?.currentItem!!)
        }

        adapter = mIndicatorAdapter

        viewPager2?.let {
            setupViewPagerListener(it)
        }
    }

    private fun setupViewPagerListener(vp: ViewPager2) {
        onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                scrollToPosition(position)
                mIndicatorAdapter?.setCurrentSelected(position)
            }
        }

        onPageChangeCallback?.let { pageCallback ->
            vp.registerOnPageChangeCallback(pageCallback)
        }
    }

    companion object {
        private const val MAX_VISIBLE_INDICATORS = 5
    }
}