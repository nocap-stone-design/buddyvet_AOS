package com.nocapstone.community.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.nocapstone.common_ui.ImageAdapter
import com.nocapstone.common_ui.MainActivityUtil
import com.nocapstone.community.domain.CreatePostRequest
import com.nocapstone.common_ui.ImageDetailAdapter
import com.nocapstone.community.R
import com.nocapstone.community.databinding.FragmentWritePostBinding
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker


@AndroidEntryPoint
class WritePostFragment : Fragment() {

    private val communityViewModel: CommunityViewModel by viewModels()
    private var _binding: FragmentWritePostBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentWritePostBinding.inflate(inflater, container, false)

        (activity as MainActivityUtil).run {
            setToolbarTitle("글 작성")
            setVisibilityBottomAppbar(View.GONE)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMenu()
        binding.apply {
            viewModel = communityViewModel
            lifecycleOwner = viewLifecycleOwner
            adapter = ImageAdapter()

            picture.setOnClickListener {
                TedImagePicker.with(requireContext())
                    .startMultiImage { communityViewModel.setImage(it) }
            }
        }
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

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.write_post_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    android.R.id.home -> {
                        findNavController().popBackStack()
                    }
                    R.id.menu_createPost -> {
                        with(binding) {
                            CreatePostRequest(
                                title.text.toString(),
                                content.text.toString()
                            ).let {
                                communityViewModel.createPost(it) {
                                    findNavController().popBackStack()
                                }
                            }
                        }
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }



}

