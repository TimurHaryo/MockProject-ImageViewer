package id.timtam.protoimagepicker.ui.instagram

import id.timtam.protoimagepicker.R
import id.timtam.protoimagepicker.abstraction.BaseActivityBinding
import id.timtam.protoimagepicker.databinding.ActivityInstagramBinding
import id.timtam.protoimagepicker.ui.instagram.adapter.StreamAdapter
import id.timtam.protoimagepicker.ui.multimedia.MultiMediaActivity
import id.timtam.protoimagepicker.util.SampleData
import id.timtam.protoimagepicker.util.SampleData.data3
import id.timtam.protoimagepicker.util.extension.toast

class InstagramActivity :
    BaseActivityBinding<ActivityInstagramBinding>(R.layout.activity_instagram) {

    private val streamAdapter by lazy { StreamAdapter() }

    override fun setupView() {
        streamAdapter.apply {
            setOnImageClick {
                MultiMediaActivity.launch(this@InstagramActivity, it, data3)
            }
            setData(listOf(data3))
            setHasStableIds(true)
        }

        binding.rvStream.run {
            adapter = streamAdapter
            setHasFixedSize(true)
            setItemViewCacheSize(20)
        }
    }
}