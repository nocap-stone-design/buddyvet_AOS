package com.nocapstone.home.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.nocapstone.common.util.PermissionCallback
import com.nocapstone.common.util.PermissionObject
import com.nocapstone.common.util.PermissionType
import com.nocapstone.common_ui.MainActivityUtil
import com.nocapstone.home.databinding.FragmentHospitalBinding
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import java.util.*


@AndroidEntryPoint
class HospitalFragment : Fragment() {
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
        homeViewModel.searchKeyword("병원")

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivityUtil).run {
            setToolbarTitle("주위 동물 병원 찾기")
            setVisibilityBottomAppbar(View.GONE)
        }

        initMenu()

        mapView = MapView(requireActivity())
        (binding.mapView as ViewGroup).addView(mapView)

        getNowLocation()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivityUtil).run {
            setVisibilityBottomAppbar(View.GONE)
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

    private fun getNowLocation(){
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
                        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(location.latitude,location.longitude),false)
                    }else {
                        Log.d("Test1234", "최근 위치 정보가 없음")
                    }
                }
        }else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION),
                200
            )
        }

    }

}