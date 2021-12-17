package id.timtam.protoimagepicker.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.esafirm.imagepicker.model.Image
import id.timtam.protoimagepicker.R
import id.timtam.protoimagepicker.abstraction.RecyclerViewAdapter
import id.timtam.protoimagepicker.abstraction.ViewHolder
import id.timtam.protoimagepicker.databinding.ItemImagesBinding

class MainAdapter :
    RecyclerViewAdapter<MainAdapter.MainViewHolder, Image>() {

    private var onDelete: ((Int) -> Unit)? = null

    override val holderInflater: (LayoutInflater, ViewGroup, Boolean) -> MainViewHolder
        get() = { layoutInflater, viewGroup, b ->
            MainViewHolder(
                onDelete,
                DataBindingUtil.inflate(layoutInflater, R.layout.item_images, viewGroup, b)
            )
        }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    fun setOnDelete(block: (Int) -> Unit) {
        this.onDelete = block
    }

    class MainViewHolder(
        private val onDelete: ((Int) -> Unit)?,
        itemView: ItemImagesBinding
    ) :
        ViewHolder<ItemImagesBinding, Image>(itemView) {

        override fun bind(data: Image) {
            binding.path = data.path
            binding.btnDeleteImage.setOnClickListener {
                onDelete?.invoke(adapterPosition)
            }
        }
    }
}