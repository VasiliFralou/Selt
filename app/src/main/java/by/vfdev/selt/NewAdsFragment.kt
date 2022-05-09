package by.vfdev.selt

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import by.vfdev.selt.databinding.FragmentNewAdsBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask

class NewAdsFragment : Fragment(R.layout.fragment_new_ads) {

    private val binding by viewBinding(FragmentNewAdsBinding::bind)

    private var mImageUri : Uri? = null
    private var mStorageRef: StorageReference? = null
    private var mDatabaseRef: DatabaseReference? = null
    private var mUploadTask: StorageTask<*>? = null
    private val PICK_IMAGE_REQUEST = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mStorageRef = FirebaseStorage.getInstance().getReference("ads_uploads")
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ads_uploads")

        binding.buttonChooseImage.setOnClickListener { openFileChoose() }
        binding.upLoadBtn.setOnClickListener {
            if (mUploadTask != null && mUploadTask!!.isInProgress){
                Toast.makeText(requireActivity(),
                    "An Upload is Still in Progress",
                    Toast.LENGTH_SHORT).show()
            }
            else{
                uploadFile()
            }
        }
    }

    private fun openFileChoose() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == AppCompatActivity.RESULT_OK
            && data != null && data.data != null) {

                mImageUri = data.data

            binding.chooseImageView.setImageURI(mImageUri)
        }
    }

    private fun getFileExtension(uri: Uri): String? {
        val cR = requireActivity().contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }

    private fun uploadFile() {
        if (mImageUri != null) {
            val fileReference = mStorageRef!!.child(
                System.currentTimeMillis()
                    .toString() + "." + getFileExtension(mImageUri!!)
            )
            binding.progressBar.visibility = View.VISIBLE
            binding.progressBar.isIndeterminate = true
            mUploadTask = fileReference.putFile(mImageUri!!)
                .addOnSuccessListener { taskSnapshot ->
                    val handler = Handler()
                    handler.postDelayed({
                        binding.progressBar.visibility = View.VISIBLE
                        binding.progressBar.isIndeterminate = false
                        binding.progressBar.progress = 0
                    }, 500)
                    Toast.makeText(
                        requireActivity(),
                        "Teacher data Upload successful",
                        Toast.LENGTH_LONG
                    )
                        .show()
                    val upload = Ads(
                        title = binding.nameEditText.text.toString().trim { it <= ' ' },
                        imageUrl = mImageUri.toString(),
                        description =  binding.descriptionEditText.text.toString().trim { it <= ' ' }
                    )
                    val uploadId = mDatabaseRef!!.push().key
                    mDatabaseRef!!.child((uploadId)!!).setValue(upload)
                    binding.progressBar.visibility = View.INVISIBLE
                    findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
                }
                .addOnFailureListener { e ->
                    binding.progressBar.visibility = View.INVISIBLE
                    Toast.makeText(requireActivity(), e.message, Toast.LENGTH_SHORT).show()
                    Log.e("data","${e.message}")
                }
                .addOnProgressListener { taskSnapshot ->
                    val progress =
                        (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount)
                    binding.progressBar.progress = progress.toInt()
                }
        } else {
            Toast.makeText(requireActivity(), "You haven't Selected Any file selected", Toast.LENGTH_SHORT)
                .show()
        }
    }
}