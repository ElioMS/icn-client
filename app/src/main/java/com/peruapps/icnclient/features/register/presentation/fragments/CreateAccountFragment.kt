package com.peruapps.icnclient.features.register.presentation.fragments

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.peruapps.icnclient.BR

import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.FragmentCreateAccountBinding
import com.peruapps.icnclient.features.account.presentation.views.AccountActivity
import com.peruapps.icnclient.features.register.presentation.RegisterNavigator
import com.peruapps.icnclient.features.register.presentation.RegisterViewModel
import kotlinx.android.synthetic.main.fragment_create_account.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import android.widget.RadioGroup
import android.R.id.toggle
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.peruapps.icnclient.BuildConfig
import com.peruapps.icnclient.features.edit_profile.presentation.dialogs.GalleryDialog
import kotlinx.android.synthetic.main.fragment_create_account.profile_image
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class CreateAccountFragment : Fragment(), RegisterNavigator {

    private val registerViewModel by sharedViewModel<RegisterViewModel>()
    private lateinit var binding: FragmentCreateAccountBinding

    private var name: String? = ""
    private var email: String? = ""

    val REQUEST_TAKE_PHOTO = 101
    val REQUEST_GALLERY_PHOTO = 102

    private lateinit var mPhotoFile: File

    companion object {
        fun setData(name: String?, email: String?) = CreateAccountFragment().apply {
            name?.let { this.name = it }
            email?.let { this.email = it }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_account, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerViewModel.resetMessage()

        binding.setVariable(BR.viewModel, registerViewModel)
        registerViewModel.setNavigator(this)

        optDni.setBackgroundResource(R.drawable.bg_dni_selected)
        optDni.setTextColor(Color.WHITE)
        optPasaporte.setBackgroundResource(R.drawable.bg_pasaporte)
        optPasaporte.setTextColor(ContextCompat.getColor(optPasaporte.context, R.color.colorPrimary))

        toggle.setOnCheckedChangeListener { radio, i ->
            val radioButton = radio.findViewById<View>(i)
            val index = radio.indexOfChild(radioButton)
            when (index) {
                0 -> {
                    optDni.setBackgroundResource(R.drawable.bg_dni_selected)
                    optDni.setTextColor(Color.WHITE)
                    optPasaporte.setBackgroundResource(R.drawable.bg_pasaporte)
                    optPasaporte.setTextColor(ContextCompat.getColor(optPasaporte.context, R.color.colorPrimary))
                }
                1 -> {
                    optPasaporte.setBackgroundResource(R.drawable.bg_pasaporte_selected)
                    optPasaporte.setTextColor(Color.WHITE)
                    optDni.setBackgroundResource(R.drawable.bg_dni)
                    optDni.setTextColor(ContextCompat.getColor(optDni.context, R.color.colorPrimary))
                }
            }
        }

        subscribeLiveData()
        initFragmentEvents()
    }

    private fun initFragmentEvents() {
        tb_create_account.setOnClickListener {
            activity?.onBackPressed()
        }

        ivAddPhoto.setOnClickListener {
            showGalleryDialog()
        }
    }

    private fun subscribeLiveData() {
        registerViewModel.showMessage.observe(this , androidx.lifecycle.Observer {
            if  (it != "") {
                Toast.makeText(context!!, it , Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun showPasswordView() {
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.setCustomAnimations(
            R.anim.transition_slide_right_in,
            R.anim.transition_slide_left_out,
            android.R.anim.slide_in_left,
            R.anim.transition_slide_right_out)

        fragmentTransaction?.replace(R.id.main_container, PasswordFragment(), "PasswordFragment")
        fragmentTransaction?.addToBackStack("PasswordFragment")
        fragmentTransaction?.commit()
    }

    private fun showGalleryDialog() {
        val dialog = GalleryDialog(context!!,::openGallery, ::openCamera)
        dialog.show()
    }

    private fun openGallery() {
        val permission = ContextCompat.checkSelfPermission(context!!,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_GALLERY_PHOTO)
        } else {
            dispatchSelectFromGallery()
        }
    }

    private fun openCamera() {
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
            registerViewModel.loadedPicture.set(mPhotoFile)
        }

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_GALLERY_PHOTO) {
            val uri = data?.data
            profile_image.setImageURI(uri)
            val file = File(getRealPathFromUri(uri!!))
            registerViewModel.loadedPicture.set(file)
        }
    }

    private fun getRealPathFromUri(contentUri: Uri): String {
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
}
