package com.jester.marvel.ui


import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.support.design.widget.Snackbar
import android.view.ViewGroup
import  com.jester.marvel.R
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener
import com.karumi.dexter.listener.multi.DialogOnAnyDeniedMultiplePermissionsListener
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener
import javax.inject.Inject

/**
 * Created by Héctor on 13/11/2017.
 */

class ManagePermissions @Inject constructor() {

    fun setRequestPermissions(activity: Activity, permission: List<String>, listener: MultiplePermissionsListener) {

        Dexter.withActivity(activity)
                .withPermissions(permission)
                .withListener(listener)
                .check()

    }

    fun setAllPermissionListener(context: Context, rootView: ViewGroup, onPermissionCheckedMethod: () -> Unit): CompositeMultiplePermissionsListener {

        val permissionListener = object : MultiplePermissionsListener {

            override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>?, token: PermissionToken) {
                token.continuePermissionRequest()
            }

            override fun onPermissionsChecked(report: MultiplePermissionsReport) {

                if (report.deniedPermissionResponses.isEmpty()) {

                    onPermissionCheckedMethod()
                }

            }

        }

        val snackbarPermissionListener = SnackbarOnAnyDeniedMultiplePermissionsListener.Builder
                .with(rootView, context.getString(R.string.edit_permissions))
                .withOpenSettingsButton(context.getString(R.string.settings))
                .withDuration(Snackbar.LENGTH_INDEFINITE)
                .build()

        val dialogPermissionListener = DialogOnAnyDeniedMultiplePermissionsListener.Builder
                .withContext(context)
                .withTitle(context.getString(R.string.permission_dialog_tittle))
                .withMessage(context.getString(R.string.permission_message))
                .withButtonText(android.R.string.ok)
                .build()

        return CompositeMultiplePermissionsListener(permissionListener, snackbarPermissionListener, dialogPermissionListener)

    }

    fun showPermissionRationale(token: PermissionToken, context: Context) {
        AlertDialog.Builder(context).setTitle("hola")
                .setMessage("Hola")
                .setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                    token.cancelPermissionRequest()
                })
                .setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                    token.continuePermissionRequest()
                })
                .setOnDismissListener(DialogInterface.OnDismissListener { token.cancelPermissionRequest() })
                .show()
    }

}