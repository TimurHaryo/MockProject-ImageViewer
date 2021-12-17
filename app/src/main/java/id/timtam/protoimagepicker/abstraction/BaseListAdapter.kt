package id.timtam.protoimagepicker.abstraction

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseListAdapter<T>(
    COMPARATOR: DiffUtil.ItemCallback<T>
) : ListAdapter<T, RecyclerView.ViewHolder>(COMPARATOR)