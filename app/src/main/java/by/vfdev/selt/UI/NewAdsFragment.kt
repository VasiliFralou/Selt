package by.vfdev.selt.UI

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import by.vfdev.selt.Model.Ads
import by.vfdev.selt.R
import by.vfdev.selt.databinding.FragmentNewAdsBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import java.util.*

class NewAdsFragment : Fragment(R.layout.fragment_new_ads) {

    private val binding by viewBinding(FragmentNewAdsBinding::bind)
    private var mUploadTask: StorageTask<*>? = null

    var selectedPhotoUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonChooseImage.setOnClickListener { openFileChoose() }
        binding.upLoadBtn.setOnClickListener { uploadImageToFirebaseStorage() }

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            selectedPhotoUri = data.data

            binding.chooseImageView.setImageURI(selectedPhotoUri)
        }
    }

    private fun openFileChoose() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 0)
    }

    private fun uploadImageToFirebaseStorage() {

        if(selectedPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/ads_uploads/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.e("!!!","Successfully uploaded image")
                ref.downloadUrl.addOnSuccessListener {
                    saveUserToFirebaseDatabase(it)
                }
            }
    }

    private fun saveUserToFirebaseDatabase(imageUri: Uri) {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("ads_uploads")

        val uploadAds = Ads(
            title = binding.nameEditText.text.toString().trim { it <= ' ' },
            imageUrl = imageUri.toString(),
            description =  binding.descriptionEditText.text.toString().trim { it <= ' ' }
        )

        val id = myRef.push().key
        myRef.child(id!!).setValue(uploadAds)
    }
}