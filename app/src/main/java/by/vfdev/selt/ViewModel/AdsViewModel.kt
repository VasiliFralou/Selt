package by.vfdev.selt.ViewModel

import androidx.lifecycle.ViewModel
import by.vfdev.selt.Model.Ads

class AdsViewModel : ViewModel() {

    val adsList = mutableListOf<Ads>()

    var photoVM: String? = null
    var titleVM: String? = null
    var locationVM: String? = null
    var descriptionVM: String? = null
    var timeVM: String? = null
    var dateVM: String? = null
}