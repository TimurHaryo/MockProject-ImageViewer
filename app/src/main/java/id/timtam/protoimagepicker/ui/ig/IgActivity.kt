package id.timtam.protoimagepicker.ui.ig

import id.timtam.protoimagepicker.R
import id.timtam.protoimagepicker.abstraction.BaseActivityBinding
import id.timtam.protoimagepicker.databinding.ActivityIgBinding
import id.timtam.protoimagepicker.ui.ig.adapter.StreamAdapter2
import id.timtam.protoimagepicker.ui.instagram.adapter.StreamAdapter
import id.timtam.protoimagepicker.util.SampleData
import id.timtam.protoimagepicker.util.extension.toast

class IgActivity : BaseActivityBinding<ActivityIgBinding>(R.layout.activity_ig) {

    private val streamAdapter by lazy { StreamAdapter2() }

    override fun setupView() {
        streamAdapter.apply {
            setOnImageClick {
                toast { "position $it" }
            }
            setData(SampleData.streams)
            setHasStableIds(true)
        }

        binding.rvStream2.run {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            adapter = streamAdapter
        }
    }
}