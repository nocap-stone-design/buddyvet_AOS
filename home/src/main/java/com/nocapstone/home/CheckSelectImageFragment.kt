package com.nocapstone.home

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.nocapstone.common_ui.MainActivityUtil
import com.nocapstone.home.databinding.FragmentCheckSelectImageBinding
import com.nocapstone.home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker

@AndroidEntryPoint
class CheckSelectImageFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels({ requireActivity() })
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
            viewModel = homeViewModel
            lifecycleOwner = viewLifecycleOwner
            dialogButton.setOnClickListener {
                findNavController().navigate(R.id.next)
            }
            retry.setOnClickListener {
                TedImagePicker.with(requireContext())
                    .errorListener { }
                    .start { uri ->
                        homeViewModel.setImage(uri)
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