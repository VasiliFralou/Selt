package by.vfdev.selt.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import by.vfdev.selt.R
import by.vfdev.selt.ViewModel.AdsViewModel
import by.vfdev.selt.databinding.FragmentAdsDetailBinding
import by.vfdev.selt.loadImage

class AdsDetailFragment : DialogFragment() {

    lateinit var vm: AdsViewModel
    private val binding by viewBinding(FragmentAdsDetailBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        vm = ViewModelProvider(activity as MainActivity)[AdsViewModel::class.java]

        return inflater.inflate(R.layout.fragment_ads_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgAds.loadImage(vm.photoVM)
        binding.titleAdsTV.text = vm.titleVM
        binding.locationDetailTV.text = vm.locationVM
        binding.descriptionDetailTV.text = vm.descriptionVM
        binding.timeTV.text = vm.timeVM
        binding.dateTV.text = vm.dateVM
    }
}