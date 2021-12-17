package id.timtam.protoimagepicker.ui.instagram.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import id.timtam.protoimagepicker.R
import id.timtam.protoimagepicker.abstraction.RecyclerViewAdapter
import id.timtam.protoimagepicker.abstraction.ViewHolder
import id.timtam.protoimagepicker.databinding.ItemStreamBinding
import id.timtam.protoimagepicker.model.Stream

class StreamAdapter
    : RecyclerViewAdapter<StreamAdapter.StreamViewHolder, Stream>() {


    private val sharedViewPool by lazy { RecyclerView.RecycledViewPool() }
    private var onImageClick: ((Int) -> Unit)? = null

    override val holderInflater: (LayoutInflater, ViewGroup, Boolean) -> StreamViewHolder
        get() = { layoutInflater, viewGroup, b ->
            StreamViewHolder(
                onImageClick,
                DataBindingUtil.inflate(layoutInflater, R.layout.item_stream, viewGroup, b)
            )
        }

    override fun onBindViewHolder(holder: StreamViewHolder, position: Int) {
        holder.binding.rvStreamImages.setRecycledViewPool(sharedViewPool)
        holder.bind(listData[position])
    }

    fun setOnImageClick(action: (Int) -> Unit) {
        this.onImageClick = action
    }

    class StreamViewHolder(
        private val onImageClick: ((Int) -> Unit)?,
        itemView: ItemStreamBinding
    ) : ViewHolder<ItemStreamBinding, Stream>(itemView) {

        private val linearSnapHelper by lazy { PagerSnapHelper() }
        private val imagesAdapter by lazy { ImageStreamAdapter() }

        override fun bind(data: Stream) {
            with(binding) {
                stream = data
                ivStreamComment.setOnClickListener {
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

                    linearSnapHelper.attachToRecyclerView(rvStreamImages)
                    rvStreamImages.apply {
                        adapter = imagesAdapter
                        setHasFixedSize(true)
                    }

                    if (data.imagesData.size > 1) {
                        tiStream.attachToRecyclerView(rvStreamImages)
                        dotIndicatorStream.attachToRecyclerView(rvStreamImages)
                    }
                }
            }
        }
    }
}