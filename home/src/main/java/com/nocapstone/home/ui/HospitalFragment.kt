package com.nocapstone.home.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.*
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.nocapstone.common_ui.MainActivityUtil
import com.nocapstone.home.R
import com.nocapstone.home.databinding.FragmentHospitalBinding
import com.nocapstone.home.domain.Place
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import java.util.*


@AndroidEntryPoint
class HospitalFragment : Fragment(), MapView.POIItemEventListener {
    private var tag = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val homeViewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHospitalBinding? = null
    private val binding get() = _binding!!
    private lateinit var mapView: MapView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHospitalBinding.inflate(inflater, container, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivityUtil).run {
            setToolbarTitle("주위 동물 병원 찾기")
            setVisibilityBottomAppbar(View.GONE)
        }
        binding.webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
        }

        initMenu()
        mapView = MapView(requireActivity()).apply {
            setZoomLevel(4, true)
        }

        (binding.mapView as ViewGroup).addView(mapView)
        mapView.setPOIItemEventListener(this)
        getNowLocation()
        observeMarker()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivityUtil).run {
            setVisibilityBottomAppbar(View.VISIBLE)
        }
        _binding = null
    }


    private fun initMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {}

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    android.R.id.home -> {
                        findNavController().popBackStack()
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun observeMarker() {
        lifecycleScope.launch {
            homeViewModel.placeInfoList.collectLatest { placeList ->
                placeList?.forEach { place ->
                    makeKeyWordMarker(place)
                }
            }
        }
    }

    private fun makeKeyWordMarker(place: Place) {
        val marker = MapPOIItem()
        marker.itemName = place.place_name
        marker.tag = tag++
        marker.mapPoint = MapPoint.mapPointWithGeoCoord(place.y.toDouble(), place.x.toDouble())
        marker.markerType = MapPOIItem.MarkerType.BluePin
        marker.selectedMarkerType = MapPOIItem.MarkerType.YellowPin
        mapView.addPOIItem(marker)
    }

    private fun makeNowMarker(latitude: Double, longitude: Double) {
        val marker = MapPOIItem()
        marker.itemName = "현 위치"
        marker.tag = 0
        marker.mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude)
        marker.markerType = MapPOIItem.MarkerType.RedPin
        mapView.addPOIItem(marker)
    }


    private fun getNowLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    var geocoder = Geocoder(requireContext(), Locale.KOREA)
                    if (location != null) {
                        mapView.setMapCenterPoint(
                            MapPoint.mapPointWithGeoCoord(
                                location.latitude,
                                location.longitude
                            ), true
                        )
                        makeNowMarker(location.latitude, location.longitude)
                        homeViewModel.searchKeyword(
                            "동물 병원",
                            location.longitude.toString(),
                            location.latitude.toString()
                        )

                    } else {
                        Log.d("Test1234", "최근 위치 정보가 없음")
                    }
                }
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                200
            )
        }
    }

    override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
        val tag = p1?.tag!! // 클릭한 마커의 태그 값
        if (tag > 0){
            val url = homeViewModel.placeInfoList.value?.get(tag-1)?.place_url
            binding.webView.visibility = View.VISIBLE
            binding.webView.loadUrl(url!!)
        }
    }

    override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {
    }

    override fun onCalloutBalloonOfPOIItemTouched(
        p0: MapView?,
        p1: MapPOIItem?,
        p2: MapPOIItem.CalloutBalloonButtonType?
    ) {
    }

    override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {
    }
}