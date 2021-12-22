package id.timtam.protoimagepicker.ui.multimedia

import android.content.Context
import android.content.Intent
import android.util.Log
import id.timtam.protoimagepicker.R
import id.timtam.protoimagepicker.abstraction.BaseActivityBinding
import id.timtam.protoimagepicker.databinding.ActivityMultiMediaBinding
import id.timtam.protoimagepicker.model.Stream
import id.timtam.protoimagepicker.ui.multimedia.adapter.MultiMediaAdapter
import id.timtam.protoimagepicker.util.extension.orZero

class MultiMediaActivity:
    BaseActivityBinding<ActivityMultiMediaBinding>(R.layout.activity_multi_media),
    MultiMediaAdapter.OnMultiImageListener {

    private val stream by lazy { intent?.extras?.getParcelable(ARG_STREAM) as? Stream }
    private val imagePosition by lazy { intent?.extras?.getInt(ARG_IMAGES_POSITION) }

    private var imageViewerAdapter: MultiMediaAdapter? = null

    private var isStreamControlVisible = true

    private val runnableDelayedControlVisibility = Runnable {
        toggleStreamControlVisibility(isStreamControlVisible)
    }

    override fun setupView() {
        imageViewerAdapter = MultiMediaAdapter(this)

        // Show toolbar / post control delayed.
        binding.vpMultiMedia.postDelayed(
            runnableDelayedControlVisibility,
            1000 / 2
                .toLong()
        )

        val imageUrls = stream?.streamImage?.imagesData

        Log.i(TAG, "TIMUR MULTI IMAGE -> $imageUrls")

        imageViewerAdapter?.submitList(imageUrls)
        binding.vpMultiMedia.apply {
            adapter = imageViewerAdapter
            setCurrentItem(imagePosition.orZero(), false)
        }

        binding.iiMultiMedia.attachToViewPager2(
            binding.vpMultiMedia,
            imageUrls
        )
    }

    override fun onDestroy() {
        imageViewerAdapter?.onDestroy()
        imageViewerAdapter = null
        super.onDestroy()
    }

    override fun onImageClick() {
        isStreamControlVisible = !isStreamControlVisible
        toggleStreamControlVisibility(isStreamControlVisible)
    }

    private fun toggleStreamControlVisibility(isVisible: Boolean) {
        if (isVisible) {
            binding.iiMultiMedia
                .animate()
                .alpha(1f)
                .translationY(0f).duration = 200
        } else {
            binding.iiMultiMedia
                .animate()
                .alpha(0f)
                .translationY(resources.getDimension(R.dimen.item_gap_96)).duration = 200
        }
    }

    companion object {

        private val TAG = MultiMediaActivity::class.java.name

        private const val ARG_IMAGES_POSITION = "ARG_IMAGES_POSITION_MULTI"
        private const val ARG_STREAM = "ARG_STREAM_MULTI"

        fun launch(
            context: Context,
            imagePosition: Int,
            stream: Stream
        ) {
            val intent = Intent(context, MultiMediaActivity::class.java).apply {
                putExtra(ARG_IMAGES_POSITION, imagePosition)
                putExtra(ARG_STREAM, stream)
            }

            context.startActivity(intent)
        }

    }
}