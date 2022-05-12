package by.vfdev.selt.Model

import com.google.firebase.database.Exclude

data class Ads(
    var title: String? = null,
    var imageUrl: String? = null,
    var description: String? = null,
    @get:Exclude
    @set:Exclude
    var key: String? = null

)