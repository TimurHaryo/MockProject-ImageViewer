package id.timtam.protoimagepicker.uikit.multiimage.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import id.timtam.protoimagepicker.R
import id.timtam.protoimagepicker.abstraction.RecyclerViewAdapter

class ImageIndicatorAdapter(
    data: List<Pair<String, Boolean>>,
    private var listener: OnImageIndicatorListener?
) : RecyclerViewAdapter<ImageIndicatorViewHolder, Pair<String, Boolean>>() {

    var currentSelected = DEFAULT_POSITION
        private set

    init {
        listData.clear()
        listData.addAll(data)
    }

    override val holderInflater: (LayoutInflater, ViewGroup, Boolean) -> ImageIndicatorViewHolder
        get() = { layoutInflater, viewGroup, b ->
            ImageIndicatorViewHolder.create(
                listener,
                DataBindingUtil.inflate(
                    layoutInflater,
                    R.layout.item_image_indicator,
                    viewGroup,
                    b
                )
            )
        }

    override fun onBindViewHolder(holder: ImageIndicatorViewHolder, position: Int) {
        holder.bind(listData[position], position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun onDestroy() {
        listener = null
    }

    fun setCurrentSelected(i: Int) {
        select(i)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun select(position: Int) {
        if (currentSelected >= 0) {
            listData[currentSelected] = listData[currentSelected].copy(second = false)
        }
        listData[position] = listData[position].copy(second = true)
        currentSelected = position
        notifyDataSetChanged()
    }

    interface OnImageIndicatorListener {
        fun onClick(position: Int)
    }

    companion object {
        private const val DEFAULT_POSITION = -1
    }
}