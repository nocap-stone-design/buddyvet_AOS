package com.nocapstone.buddyvet.buddy.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.nocapstone.buddyvet.buddy.R
import com.nocapstone.buddyvet.buddy.databinding.FragmentInputBuddyTypeBinding
import com.nocapstone.common_ui.MainActivityUtil
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class InputBuddyTypeFragment : Fragment() {

    private var _binding: FragmentInputBuddyTypeBinding? = null
    private val binding get() = _binding!!
    private val buddyViewModel: BuddyViewModel by viewModels({ requireActivity() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInputBuddyTypeBinding.inflate(inflater, container, false)


        (activity as MainActivityUtil).run {
            setToolbarTitle("버디 추가")
            setVisibilityBottomAppbar(View.GONE)
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMenu()

        binding.viewModel = buddyViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.dogIv.setOnClickListener {
            buddyViewModel.setKind("D")
        }

        binding.catIv.setOnClickListener {
            buddyViewModel.setKind("C")
        }

        binding.next.setOnClickListener {
            findNavController().navigate(R.id.next)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {

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
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

}