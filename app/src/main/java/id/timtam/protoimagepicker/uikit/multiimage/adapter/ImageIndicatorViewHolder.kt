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
        data.let { (imageUrl, isSelected) ->
            binding.imageUrl = imageUrl
            binding.isSelected = isSelected
        }

//        binding.root.setOnClickListener {
//            binding.parentImageIndicator.strokeColor = binding.root.context.getColor(R.color.sb_main)
//            listener?.onClick(layoutPosition)
//        }
    }

    fun bind(data: Pair<String, Boolean>, position: Int) {
        data.let { (imageUrl, isSelected) ->
            binding.imageUrl = imageUrl
            binding.isSelected = isSelected
        }

        binding.root.setOnClickListener {
            binding.parentImageIndicator.strokeColor = binding.root.context.getColor(R.color.sb_main)
            updateSelected(isSelected = true, isSpecialCase = false)
            listener?.onClick(position)
        }
    }

    fun updateSelected(isSelected: Boolean, isSpecialCase: Boolean) {
        with(binding) {
            if (isSelected) {
                this.isSelected = isSelected
            }
            invalidateAll()
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