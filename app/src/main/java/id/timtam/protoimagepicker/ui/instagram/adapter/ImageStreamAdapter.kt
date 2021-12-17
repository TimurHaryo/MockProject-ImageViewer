package id.timtam.protoimagepicker.ui.instagram.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import id.timtam.protoimagepicker.R
import id.timtam.protoimagepicker.abstraction.RecyclerViewAdapter
import id.timtam.protoimagepicker.abstraction.ViewHolder
import id.timtam.protoimagepicker.databinding.ItemStreamImageBinding
import id.timtam.protoimagepicker.model.type.CanvasType

class ImageStreamAdapter
    : RecyclerViewAdapter<ImageStreamAdapter.ImageStreamViewHolder, String>() {

    private var onImageClick: ((Int) -> Unit)? = null
    private var canvasType: String = CanvasType.PORTRAIT.type

    override val holderInflater: (LayoutInflater, ViewGroup, Boolean) -> ImageStreamViewHolder
        get() = { layoutInflater, viewGroup, b ->
            ImageStreamViewHolder(
                onImageClick,
                canvasType,
                DataBindingUtil.inflate(layoutInflater, R.layout.item_stream_image, viewGroup, b)
            )
        }

    override fun onBindViewHolder(holder: ImageStreamViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    fun setOnImageClick(action: ((Int) -> Unit)?) {
        this.onImageClick = action
    }

    fun setCanvasType(type: String) {
        this.canvasType = type
    }

    class ImageStreamViewHolder(
        private val onImageClick: ((Int) -> Unit)?,
        private val canvasType: String,
        itemView: ItemStreamImageBinding
    ) : ViewHolder<ItemStreamImageBinding, String>(itemView) {

        override fun bind(data: String) {
            with(binding) {
                imageUrl = data
                type = canvasType

                root.setOnClickListener {
                    onImageClick?.invoke(adapterPosition)
                }
            }
        }
    }
}