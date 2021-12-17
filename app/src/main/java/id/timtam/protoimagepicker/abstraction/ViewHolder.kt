package id.timtam.protoimagepicker.abstraction

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class ViewHolder<out Type, in Data>(itemView: ViewDataBinding) :
    RecyclerView.ViewHolder(itemView.root) {

    @Suppress("UNCHECKED_CAST")
    private val _binding: Type = itemView as Type

    val binding: Type
        get() {
            try {
                return _binding
            } catch (t: Throwable) {
                throw TypeCastException(t.message.toString())
            }
        }

    abstract fun bind(data: Data)

    object None
}