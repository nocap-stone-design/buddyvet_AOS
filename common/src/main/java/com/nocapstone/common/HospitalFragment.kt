package com.nocapstone.common
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.daum.mf.map.api.MapView

class HospitalFragment : Fragment() {

    private lateinit var mapView : MapView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hospital, container, false)
    }

}