package by.vfdev.selt.ViewModel

import androidx.lifecycle.ViewModel
import by.vfdev.selt.Model.Ads

class AdsViewModel : ViewModel() {

    val adsList = mutableListOf<Ads>()
}