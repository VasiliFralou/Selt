package by.vfdev.selt.UI

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import by.vfdev.selt.ViewModel.AdsViewModel
import by.vfdev.selt.Model.Ads
import by.vfdev.selt.R
import by.vfdev.selt.databinding.FragmentListAdsBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage

class MainFragment : Fragment(R.layout.fragment_list_ads) {

    lateinit var navController: NavController
    private val binding by viewBinding(FragmentListAdsBinding::bind)
    private val adsVM : AdsViewModel by activityViewModels()

    private var mStorage:FirebaseStorage? = null
    private var mDatabaseRef: DatabaseReference? = null
    private var mDBListener: ValueEventListener? = null

    private lateinit var mAdsList: MutableList<Ads>
    private lateinit var listAdapter: AdsListAdapter

    private val tabTitles = arrayListOf("Доска", "Карта")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        binding.listAdsRV.setHasFixedSize(true)
        binding.listAdsRV.layoutManager = GridLayoutManager(requireActivity(), 1)

        mAdsList = ArrayList()

        for (i in mAdsList) {
            if (i.approved == true) {
                adsVM.adsList.add(i)
            }
        }


        listAdapter = AdsListAdapter(mAdsList)
        binding.listAdsRV.adapter = listAdapter

        setUpTabLayoutWithViewPager()

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
                    Log.e("!!!", mAdsList.toString())
                }
                listAdapter.notifyDataSetChanged()
            }

        })
    }

    @SuppressLint("InflateParams")
    private fun setUpTabLayoutWithViewPager() {
        binding.viewPager.adapter = TabFragmentAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        for (i in 0..2) {
            val textView = LayoutInflater.from(requireContext())
                .inflate(R.layout.tab_title_layout, null) as TextView
            binding.tabLayout.getTabAt(i)?.customView = textView
        }
    }
}