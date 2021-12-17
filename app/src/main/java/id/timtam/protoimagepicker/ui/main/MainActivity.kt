package id.timtam.protoimagepicker.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.esafirm.imagepicker.features.ImagePicker
import id.timtam.protoimagepicker.R
import id.timtam.protoimagepicker.abstraction.BaseActivityBinding
import id.timtam.protoimagepicker.databinding.ActivityMainBinding
import id.timtam.protoimagepicker.ui.main.adapter.MainAdapter
import id.timtam.protoimagepicker.util.extension.currentItem
import id.timtam.protoimagepicker.util.extension.runAndCheckPermissions
import id.timtam.protoimagepicker.util.extension.safeRemoveAt
import id.timtam.protoimagepicker.util.extension.weaker
import id.timtam.protoimagepicker.util.picker.GalleryPicker
import id.timtam.protoimagepicker.util.picker.OtherPicker

class MainActivity : BaseActivityBinding<ActivityMainBinding>(R.layout.activity_main) {

    private val mainAdapter: MainAdapter by lazy { MainAdapter() }

    private val linearSnapHelper by lazy { LinearSnapHelper() }

    private val viewModel: MainViewModel by lazy {
        defaultViewModelProviderFactory.create(MainViewModel::class.java)
    }

    private val picker by lazy {
        GalleryPicker(this.weaker()).setLimit(10)
    }

    private val otherPicker by lazy {
        OtherPicker(this.weaker())
    }

    override fun setupView() {
        binding.btnOpenGallery.setOnClickListener { openGallery() }
        binding.tvTextIndicator.setOnClickListener {
            Log.i("TIMURR", "position: ${binding.rvImages.currentItem}")
        }
    }

    override fun onDestroy() {
        binding.rvImages.clearOnScrollListeners()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            viewModel.setOriginImages(ImagePicker.getImages(data))

            showImage()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun openGallery() {

        runAndCheckPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) {
            if (viewModel.hasOriginImages) {
                picker.openWithOriginImage(viewModel.images)
            } else {
                picker.open()
            }
        }

//        if (SDK_INT >= Build.VERSION_CODES.R) {
//            runAndCheckPermissions(
//                Manifest.permission.READ_EXTERNAL_STORAGE
//            ) {
//                if (viewModel.hasOriginImages) {
//                    picker.openWithOriginImage(viewModel.images)
//                } else {
//                    picker.open()
//                }
//            }
//        } else {
//            runAndCheckPermissions(
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//            ) {
//                if (viewModel.hasOriginImages) {
//                    picker.openWithOriginImage(viewModel.images)
//                } else {
//                    picker.open()
//                }
//            }
//        }
    }

    private fun showImage() {
        mainAdapter.apply {
            setData(viewModel.images)

            setOnDelete { position ->
                deleteDataAt(position)
                viewModel.images.safeRemoveAt(position)
            }
        }

        linearSnapHelper.attachToRecyclerView(binding.rvImages)
        binding.rvImages.adapter = mainAdapter
        binding.rvImages.setHasFixedSize(true)
        setupIndicator()
    }

    private fun setupIndicator() {
        binding.dotIndicator.attachToRecyclerView(binding.rvImages)
        binding.tvTextIndicator.attachToRecyclerView(binding.rvImages)
    }
}