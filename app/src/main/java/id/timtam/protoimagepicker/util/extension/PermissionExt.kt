package id.timtam.protoimagepicker.util.extension

import android.app.Activity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

inline fun Activity.runAndCheckPermissions(
    vararg perms: String,
    crossinline onGranted: () -> Unit
) {
    Dexter.withContext(this)
        .withPermissions(perms.asList())
        .withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                p0?.let {
                    if (it.areAllPermissionsGranted()) {
                        onGranted()
                    }
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: MutableList<PermissionRequest>?,
                p1: PermissionToken?
            ) {
                p1?.continuePermissionRequest()
            }
        })
        .withErrorListener {
            toast { "Aksi sedang berjalan" }
        }
        .onSameThread()
        .check()
}