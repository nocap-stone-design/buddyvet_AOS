package com.nocapstone.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nocapstone.home.databinding.FragmentHospitalBinding
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.MapView


@AndroidEntryPoint
class HospitalFragment : Fragment() {

    private lateinit var binding: FragmentHospitalBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHospitalBinding.inflate(layoutInflater)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapView = MapView(requireActivity())
        (binding.mapView as ViewGroup).addView(mapView)
    }
}