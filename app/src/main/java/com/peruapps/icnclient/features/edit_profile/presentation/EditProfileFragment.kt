package com.peruapps.icnclient.features.edit_profile.presentation

import android.Manifest
import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.peruapps.icnclient.BR
import com.peruapps.icnclient.R

import com.peruapps.icnclient.databinding.FragmentEditProfileBinding
import com.peruapps.icnclient.features.change_password.presentation.ChangePasswordFragment
import com.peruapps.icnclient.features.edit_profile.presentation.dialogs.GalleryDialog
import com.peruapps.icnclient.features.reservations.presentation.views.ReservationActivity
import com.peruapps.icnclient.helpers.NavigationHelper
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.provider.MediaStore
import androidx.core.content.FileProvider
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import com.peruapps.icnclient.BuildConfig
import com.peruapps.icnclient.features.profile.presentation.ProfileFragment
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import androidx.lifecycle.Observer

class EditProfileFragment : Fragment(), EditProfileNavigator{

    private lateinit var binding: FragmentEditProfileBinding
    val model: EditProfileViewModel by viewModel()

    private lateinit var spGender: Spinner

    val REQUEST_TAKE_PHOTO = 101
    val REQUEST_GALLERY_PHOTO = 102

    private lateinit var mPhotoFile: File

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.viewModel, model)
        model.setNavigator(this)

        spGender = view.findViewById(R.id.sp_gender)
        val monthAdapter = ArrayAdapter.createFromResource(context!!, R.array.gender_list, R.layout.item_gender_spinner)
            .also {
                it.setDropDownViewResource(R.layout.item_gender_spinner)
            }
        spGender.adapter = monthAdapter

        setParentData(true)
        subscribeLiveData()
    }

    private fun setParentData(value: Boolean) {
        val myParentActivity = (activity) as ReservationActivity
        myParentActivity.hideActionBar(value)
    }

    override fun showChangePasswordView() {
        NavigationHelper.changeFragment(fragmentManager!!, R.id.main_container,
            ChangePasswordFragment(),
            "ChangePasswordFragment")
    }

    override fun updateUserData() {
        Toast.makeText(context!!, "Perfil actualizado", Toast.LENGTH_SHORT).show()

        NavigationHelper.changeFragment(fragmentManager!!, R.id.main_container,
            ProfileFragment(),
            "ProfileFragment")
    }

    override fun showGalleryDialog() {
        val dialog = GalleryDialog(context!!, ::openGallery, ::openCamera)
        dialog.show()
    }

    override fun openGallery() {
        val permission = ContextCompat.checkSelfPermission(context!!,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_GALLERY_PHOTO)
        } else {
            dispatchSelectFromGallery()
        }
    }

    override fun openCamera() {
        dispatchTakePictureIntent()
    }

    private fun dispatchSelectFromGallery() {
        val pickPhoto = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO)
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(activity!!.packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }

                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        context!!,
                        BuildConfig.APPLICATION_ID + ".provider",
                        it
                    )

                    mPhotoFile = it
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date())
        val mFileName = "JPEG_" + timeStamp + "_"
        val storageDir = activity!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(mFileName, ".jpg", storageDir)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if  (resultCode == Activity.RESULT_OK && requestCode == REQUEST_TAKE_PHOTO) {
            val uri = Uri.fromFile(File(mPhotoFile.toString()))
            profile_image.setImageURI(uri)
            model.loadedPicture.set(mPhotoFile)
        }

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_GALLERY_PHOTO) {
            val uri = data?.data
            profile_image.setImageURI(uri)
            val file = File(getRealPathFromUri(uri!!))
            model.loadedPicture.set(file)
        }
    }

    fun getRealPathFromUri(contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context!!.getContentResolver().query(contentUri, proj, null, null, null)
            assert(cursor != null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor!!.moveToFirst()
            return cursor!!.getString(column_index)
        } finally {
            if (cursor != null) {
                cursor!!.close()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_GALLERY_PHOTO -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    dispatchSelectFromGallery()
                } else {
                    Log.d("requestCode", "PERMISSION_DENIED")
                }
                return
            }
        }
    }

    override fun goBack() {
        activity!!.onBackPressed()
    }

    private fun subscribeLiveData() {
        model.validationMessage.observe(this, Observer {
            Toast.makeText(context!!, it, Toast.LENGTH_SHORT).show()
        })

        model.showError.observe(this, Observer {
            if  (it != "") {
                Toast.makeText(context!!, it, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
