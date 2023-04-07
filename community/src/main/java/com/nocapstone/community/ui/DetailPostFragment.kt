package com.nocapstone.community.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nocapstone.common_ui.MainActivityUtil
import com.nocapstone.common_ui.ImageDetailAdapter
import com.nocapstone.community.R
import com.nocapstone.community.ReplyAdapter
import com.nocapstone.community.databinding.FragmentDetailPostBinding
import com.nocapstone.community.domain.CreatePostRequest
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailPostFragment : Fragment() {

    private val args: DetailPostFragmentArgs by navArgs()
    private val communityViewModel: CommunityViewModel by viewModels()
    private var _binding: FragmentDetailPostBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailPostBinding.inflate(inflater, container, false)

        (activity as MainActivityUtil).run {
            setToolbarTitle("글 상세 조회")
            setVisibilityBottomAppbar(View.GONE)
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMenu()

        communityViewModel.readDetailPost(args.postID)
        binding.apply {
            lifecycleOwner =  viewLifecycleOwner
            viewModel = communityViewModel
            adapter = ImageDetailAdapter()
            repleyAdapter = ReplyAdapter()
            replyIl.setEndIconOnClickListener {
                Toast.makeText(context,"댓글",Toast.LENGTH_SHORT).show()
            }
        }
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


    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivityUtil).run {
            setVisibilityBottomAppbar(View.VISIBLE)
        }
        _binding = null
    }



}