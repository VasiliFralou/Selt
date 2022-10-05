package by.vfdev.selt.UI

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import by.vfdev.selt.ViewModel.AdsViewModel
import by.vfdev.selt.Model.Ads
import by.vfdev.selt.R
import by.vfdev.selt.databinding.FragmentListAdsBinding
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage

class MainFragment : Fragment(R.layout.fragment_list_ads) {

    private val binding by viewBinding(FragmentListAdsBinding::bind)
    private val adsVM : AdsViewModel by activityViewModels()

    private var mStorage:FirebaseStorage? = null
    private var mDatabaseRef: DatabaseReference? = null
    private var mDBListener: ValueEventListener? = null

    private lateinit var mAdsList: MutableList<Ads>
    private lateinit var listAdapter: AdsListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listAdsRV.setHasFixedSize(true)
        binding.listAdsRV.layoutManager = GridLayoutManager(requireActivity(), 1)

        mAdsList = ArrayList()

        for (i in mAdsList) {
            if (i.approved == true) {
                adsVM.adsList.add(i)
            }
        }



        listAdapter = AdsListAdapter(adsVM.adsList)
        binding.listAdsRV.adapter = listAdapter

        mStorage = FirebaseStorage.getInstance()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ads_uploads")
        mDBListener = mDatabaseRef!!.addValueEventListener(object : ValueEventListener{

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireActivity(),error.message, Toast.LENGTH_SHORT).show()
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                mAdsList.clear()
                for (teacherSnapshot in snapshot.children){
                    val upload = teacherSnapshot.getValue(Ads::class.java)
                    upload!!.key = teacherSnapshot.key
                    mAdsList.add(upload)

                }
                listAdapter.notifyDataSetChanged()
            }

        })
    }
}