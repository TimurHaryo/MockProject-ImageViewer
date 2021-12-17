package id.timtam.protoimagepicker.abstraction

import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivityBinding<T: ViewDataBinding>(
    @LayoutRes private val resourceLayoutId: Int
) : AppCompatActivity() {

    private var _binding: T? = null

    protected val binding: T
        get() {
            if (_binding == null) {
                throw IllegalArgumentException("${this.javaClass.simpleName} does not initialize view binding")
            }
            return _binding!!
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = DataBindingUtil.setContentView(this, resourceLayoutId)
        _binding?.lifecycleOwner = this

        setupView()
    }

    override fun onResume() {
        super.onResume()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    protected abstract fun setupView()

}