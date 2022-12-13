package by.vfdev.selt.UI

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import by.vfdev.selt.R
import by.vfdev.selt.databinding.FragmentAdsDetailBinding
import by.vfdev.selt.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapFragment : Fragment(R.layout.fragment_map), OnMapReadyCallback {

    private var mMap: GoogleMap? = null

    companion object {
        var mapFragment : SupportMapFragment? = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapFragment = childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap

        val location = LatLng(53.9, 27.5667)

        mMap!!.addMarker(
                MarkerOptions()
                    .position(location)
                    .title("Минск"))
            mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10.5f))
    }
}