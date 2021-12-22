package id.timtam.protoimagepicker.uikit.multiimage.adapter

import android.graphics.Color
import id.timtam.protoimagepicker.R
import id.timtam.protoimagepicker.abstraction.ViewHolder
import id.timtam.protoimagepicker.databinding.ItemImageIndicatorBinding

class ImageIndicatorViewHolder private constructor(
    private val listener: ImageIndicatorAdapter.OnImageIndicatorListener?,
    itemView: ItemImageIndicatorBinding
) : ViewHolder<ItemImageIndicatorBinding, Pair<String, Boolean>>(itemView) {

    override fun bind(data: Pair<String, Boolean>) {

    }

    fun bind(data: Pair<String, Boolean>, position: Int) {
        data.let { (imageUrl, isSelected) ->
            binding.imageUrl = imageUrl

            updateSelected(isSelected)
        }

        binding.root.setOnClickListener {
            binding.parentImageIndicator.strokeColor = binding.root.context.getColor(R.color.sb_main)
            updateSelected(true)
            listener?.onClick(position)
        }
    }

    private fun updateSelected(isSelected: Boolean) {
        with(binding) {
            parentImageIndicator.strokeColor =
                if (isSelected) root.context.getColor(R.color.sb_main) else Color.TRANSPARENT
        }
    }

    companion object {
        fun create(
            listener: ImageIndicatorAdapter.OnImageIndicatorListener?,
            binding: ItemImageIndicatorBinding
        ) = ImageIndicatorViewHolder(listener, binding)
    }
}