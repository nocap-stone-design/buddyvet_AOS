package com.nocapstone.home

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nocapstone.common_ui.MainActivityUtil
import com.nocapstone.home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker
import java.net.URI


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val storyViewModel: HomeViewModel by viewModels({ requireActivity()})
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        (activity as MainActivityUtil).run {
            setToolbarTitle("홈")
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.eyeCheckBtn.setOnClickListener {

            DialogForSelectBuddy.Builder(requireContext())
                .setOnClickButton {
                    TedImagePicker.with(requireContext())
                        .errorListener { }
                        .start { uri ->
                            storyViewModel.setImage(uri)
                            findNavController().navigate(R.id.next)
                        }
                }
                .build().show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}