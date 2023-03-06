package com.nocapstone.community.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.nocapstone.common_ui.MainActivityUtil
import com.nocapstone.community.PostAdapter
import com.nocapstone.community.R
import com.nocapstone.community.databinding.FragmentCommunityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityFragment : Fragment() {

    private val communityViewModel: CommunityViewModel by viewModels()
    private var _binding: FragmentCommunityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommunityBinding.inflate(inflater, container, false)

        (activity as MainActivityUtil).run {
            setToolbarTitle("커뮤니티")
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMenu()


        binding.apply {
            this.lifecycleOwner = viewLifecycleOwner
            this.adapter = PostAdapter(this@CommunityFragment)
            this.viewModel = communityViewModel
        }
        communityViewModel.readPostList()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    private fun initMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.community_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.menu_writingCommunity -> {
                        findNavController().navigate(R.id.next)
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


}