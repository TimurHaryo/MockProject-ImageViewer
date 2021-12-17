package id.timtam.protoimagepicker.ui.multimedia.adapter

import id.timtam.protoimagepicker.abstraction.ViewHolder
import id.timtam.protoimagepicker.databinding.ItemMultiMediaViewerBinding

class MultiMediaViewHolder private constructor(
    private val listener: MultiMediaAdapter.OnMultiImageListener?,
    itemView: ItemMultiMediaViewerBinding
) : ViewHolder<ItemMultiMediaViewerBinding, String>(itemView) {

    override fun bind(data: String) {
        with(binding) {
            image = data

            root.setOnClickListener {
                listener?.onImageClick()
            }
        }
    }

    companion object {
        fun create(
            listener: MultiMediaAdapter.OnMultiImageListener?,
            binding: ItemMultiMediaViewerBinding
        ) = MultiMediaViewHolder(listener, binding)
    }
}