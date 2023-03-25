package com.nocapstone.eye_check.ui

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.eye_check.R
import com.example.eye_check.databinding.FragmentCheckSelectImageBinding
import com.nocapstone.common_ui.MainActivityUtil
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker

@AndroidEntryPoint
class CheckSelectImageFragment : Fragment() {

    private val eyeCheckViewModel: EyeCheckViewModel by viewModels({ requireActivity() })
    private var _binding: FragmentCheckSelectImageBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCheckSelectImageBinding.inflate(inflater, container, false)

        (activity as MainActivityUtil).run {
            setToolbarTitle("선택한 사진")
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            viewModel = eyeCheckViewModel
            lifecycleOwner = viewLifecycleOwner

            startBtn.setOnClickListener {
                findNavController().navigate(R.id.next)
            }

            retryBtn.setOnClickListener {
                TedImagePicker.with(requireContext())
                    .errorListener { }
                    .start { uri ->
                        eyeCheckViewModel.setImage(uri)
                        findNavController().navigate(R.id.checkSelectImageFragment)
                    }
            }
        }

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    when (menuItem.itemId) {
                        android.R.id.home -> {
                            findNavController().popBackStack()
                        }
                    }
                    return true
                }
            }, viewLifecycleOwner, Lifecycle.State.RESUMED
        )

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}