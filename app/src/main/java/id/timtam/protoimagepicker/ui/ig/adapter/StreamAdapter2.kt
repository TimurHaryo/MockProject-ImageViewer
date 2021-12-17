package id.timtam.protoimagepicker.ui.ig.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import id.timtam.protoimagepicker.R
import id.timtam.protoimagepicker.abstraction.RecyclerViewAdapter
import id.timtam.protoimagepicker.abstraction.ViewHolder
import id.timtam.protoimagepicker.databinding.ItemStream2Binding
import id.timtam.protoimagepicker.model.Stream
import id.timtam.protoimagepicker.ui.instagram.adapter.ImageStreamAdapter

class StreamAdapter2
    : RecyclerViewAdapter<StreamAdapter2.StreamViewHolder2, Stream>() {

    private var onImageClick: ((Int) -> Unit)? = null

    override val holderInflater: (LayoutInflater, ViewGroup, Boolean) -> StreamViewHolder2
        get() = { layoutInflater, viewGroup, b ->
            StreamViewHolder2(
                onImageClick,
                DataBindingUtil.inflate(layoutInflater, R.layout.item_stream2, viewGroup, b)
            )
        }

    override fun onBindViewHolder(holder: StreamViewHolder2, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemId(position: Int): Long {
        return listData[position].postId.hashCode().toLong()
    }

    fun setOnImageClick(action: (Int) -> Unit) {
        this.onImageClick = action
    }

    class StreamViewHolder2(
        private val onImageClick: ((Int) -> Unit)?,
        itemView: ItemStream2Binding
    ) : ViewHolder<ItemStream2Binding, Stream>(itemView) {
        private val imagesAdapter by lazy { ImageStreamAdapter() }

        override fun bind(data: Stream) {
            with(binding) {
                stream = data
                ivStreamComment2.setOnClickListener {
                    Log.i("TIMUR", "main position $adapterPosition")
                }
                setupImageSlider(data.streamImage)
            }
        }

        private fun setupImageSlider(data: Stream.StreamImage) {
            with(binding) {
                if (data.imagesData.isNotEmpty()) {
                    imagesAdapter.apply {
                        setCanvasType(data.canvasType)
                        setOnImageClick(onImageClick)
                        setData(data.imagesData)
                    }

                    vpStreamImages2.apply {
                        adapter = imagesAdapter
                        offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
                    }

                    if (data.imagesData.size > 1) {
                        tiStream2.attachToViewPager(vpStreamImages2)
                        dotIndicatorStream2.attachToPager(vpStreamImages2)
                    }
                }
            }
        }
    }
}