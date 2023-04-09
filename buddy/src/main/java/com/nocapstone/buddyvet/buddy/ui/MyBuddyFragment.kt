package com.nocapstone.buddyvet.buddy.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ListAdapter
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.nocapstone.buddyvet.buddy.BuddyListAdapter
import com.nocapstone.buddyvet.buddy.R
import com.nocapstone.buddyvet.buddy.databinding.FragmentMyBuddyBinding
import com.nocapstone.common_ui.MainActivityUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyBuddyFragment : Fragment() {

    private var _binding: FragmentMyBuddyBinding? = null
    private val binding get() = _binding!!
    private val buddyViewModel: BuddyViewModel by viewModels({ requireActivity() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyBuddyBinding.inflate(inflater, container, false)

        (activity as MainActivityUtil).run {
            setToolbarTitle("My Buddy")
            setVisibilityBottomAppbar(View.VISIBLE)
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buddyViewModel.readMasterProfile()
        buddyViewModel.readBuddyList()
        initMenu()
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = buddyViewModel
            adapter = BuddyListAdapter()
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
                menuInflater.inflate(R.menu.my_buddy_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    android.R.id.home -> {
                        findNavController().popBackStack()
                    }
                    R.id.add_buddy -> {
                        findNavController().navigate(R.id.next)
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

}