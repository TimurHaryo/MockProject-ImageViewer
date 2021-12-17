package id.timtam.protoimagepicker.uikit.multiimage.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import id.timtam.protoimagepicker.R
import id.timtam.protoimagepicker.abstraction.RecyclerViewAdapter

class ImageIndicatorAdapter(
    data: List<Pair<String, Boolean>>,
    private var listener: OnImageIndicatorListener?,
    private val layoutManager: LinearLayoutManager?,
    private val recycler: RecyclerView.Recycler?
) : RecyclerViewAdapter<ImageIndicatorViewHolder, Pair<String, Boolean>>() {

    var currentSelected = 0
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
        holder.itemView.isActivated = holder.adapterPosition == currentSelected
    }

    override fun onViewRecycled(holder: ImageIndicatorViewHolder) {
        super.onViewRecycled(holder)
        Log.i("MAMIA", "recycled -> ${holder.adapterPosition}")
    }

//    override fun onBindViewHolder(
//        holder: ImageIndicatorViewHolder,
//        position: Int,
//        payloads: MutableList<Any>
//    ) {
//        super.onBindViewHolder(holder, position, payloads)
//        for (payload in payloads) {
//            if (payload is Boolean) {
//                if (payload) {
//                    select(position, holder.itemView)
//                } else {
//                    deselect(position, holder.itemView)
//                }
//                holder.updateSelected(payload)
//            }
//        }
//    }

    override fun onViewAttachedToWindow(holder: ImageIndicatorViewHolder) {
        holder.setIsRecyclable(true)
        if (holder.adapterPosition == currentSelected) {
            select(holder.adapterPosition, holder.itemView, holder, true)
        }
        super.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: ImageIndicatorViewHolder) {
        holder.setIsRecyclable(true)
        if (holder.adapterPosition == currentSelected) {
            deselect(holder.adapterPosition, holder.itemView, holder, true)
        }
        super.onViewDetachedFromWindow(holder)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun onDestroy() {
        listener = null
    }

    fun setCurrentSelected(i: Int, view: View?, vh: RecyclerView.ViewHolder?) {
        select(i, view, vh, false)
    }

    private fun getViewByPosition(pos: Int): View? {
        if (layoutManager == null) return null

        val firstListItemPosition = layoutManager.findFirstVisibleItemPosition()
        val lastListItemPosition = firstListItemPosition + layoutManager.childCount - 1

        return if (pos < firstListItemPosition || pos > lastListItemPosition) {
            null
        } else {
            layoutManager.findViewByPosition(pos)
        }
    }

    private fun select(
        position: Int,
        v: View? = null,
        vh: RecyclerView.ViewHolder?,
        isFromScrapView: Boolean
    ) {
        listData[position] = listData[position].copy(second = true)
        Log.i("TIMUR", "SELECT $position ${listData[position].second}")
        Log.i("HARYO", "SELECT $position ${getViewByPosition(position)}")

        deselect(currentSelected, v, vh, isFromScrapView)

        if (getViewByPosition(position) != null) {
            getViewByPosition(position)!!.run {
                Log.i("HERJUN", "SELECT up $position")
                invalidate()
                findViewById<MaterialCardView>(R.id.parent_image_indicator)?.strokeColor =
                    context.getColor(R.color.sb_main)
                if (isFromScrapView) {
                    (vh as? ImageIndicatorViewHolder)?.updateSelected(true, isFromScrapView || position == 4)
                }
//                requestLayout()
            }
        } else {
            v?.run {
                Log.i("HERJUN", "SELECT down $position")
                invalidate()
                findViewById<MaterialCardView>(R.id.parent_image_indicator)?.strokeColor =
                    context.getColor(R.color.sb_main)
                if (isFromScrapView) {
                    (vh as? ImageIndicatorViewHolder)?.updateSelected(true, isFromScrapView || position == 4)
                }
//                requestLayout()
            }
        }

//        listener?.onClick(position)

        currentSelected = position
    }

    private fun deselect(
        position: Int,
        v: View?,
        vh: RecyclerView.ViewHolder?,
        isFromScrapView: Boolean
    ) {
        listData[position] = listData[position].copy(second = false)
        Log.i("TIMUR", "UNSELECT $position ${listData[position].second}")
        Log.i("HARYO", "UNSELECT $position ${getViewByPosition(position)}")

        if (getViewByPosition(position) != null) {
            getViewByPosition(position)!!.run {
                Log.i("HERJUN", "UNSELECT up $position")
                invalidate()
                findViewById<MaterialCardView>(R.id.parent_image_indicator)?.strokeColor =
                    Color.TRANSPARENT
                if (isFromScrapView) {
                    (vh as? ImageIndicatorViewHolder)?.updateSelected(false, isFromScrapView || position == 4)
                }
//                requestLayout()
            }
        } else {
            v?.run {
                Log.i("HERJUN", "UNSELECT down $position")
                invalidate()
                findViewById<MaterialCardView>(R.id.parent_image_indicator)?.strokeColor =
                    Color.TRANSPARENT
                if (isFromScrapView) {
                    (vh as? ImageIndicatorViewHolder)?.updateSelected(false, isFromScrapView || position == 4)
                }
//                requestLayout()
            }
        }
//        currentSelected = -1
    }

    interface OnImageIndicatorListener {
        fun onClick(position: Int)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Pair<String, Boolean>>() {
            override fun areItemsTheSame(
                oldItem: Pair<String, Boolean>,
                newItem: Pair<String, Boolean>
            ): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(
                oldItem: Pair<String, Boolean>,
                newItem: Pair<String, Boolean>
            ): Boolean =
                oldItem.first == newItem.first &&
                        oldItem.second == newItem.second
        }
    }
}