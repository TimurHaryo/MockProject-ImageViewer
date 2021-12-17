package id.timtam.protoimagepicker.abstraction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.timtam.protoimagepicker.util.extension.safeRemoveAt

abstract class RecyclerViewAdapter<Holder : RecyclerView.ViewHolder, Data> :
    RecyclerView.Adapter<Holder>() {

    protected val listData = ArrayList<Data>()

    protected abstract val holderInflater: (LayoutInflater, ViewGroup, Boolean) -> Holder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return holderInflater.invoke(LayoutInflater.from(parent.context), parent, false)
    }

    override fun getItemCount(): Int = listData.size

    fun setData(data: List<Data>) {
        listData.clear()
        listData.addAll(data)
        notifyDataSetChanged()
    }

    fun deleteDataAt(position: Int) {
        listData.safeRemoveAt(position)
        notifyItemRemoved(position)
    }
}