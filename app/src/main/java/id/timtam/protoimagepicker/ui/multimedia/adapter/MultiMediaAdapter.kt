package id.timtam.protoimagepicker.ui.multimedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.timtam.protoimagepicker.R
import id.timtam.protoimagepicker.abstraction.BaseListAdapter

class MultiMediaAdapter(
    private var listener: OnMultiImageListener?
) :
    BaseListAdapter<String>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MultiMediaViewHolder.create(
            listener,
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_multi_media_viewer,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MultiMediaViewHolder).bind(getItem(position))
    }

    fun onDestroy() {
        listener = null
    }

    interface OnMultiImageListener {
        fun onImageClick()
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem.hashCode() == newItem.hashCode()
        }
    }
}