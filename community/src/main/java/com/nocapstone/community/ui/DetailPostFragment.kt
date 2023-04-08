package com.nocapstone.community.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nocapstone.common_ui.MainActivityUtil
import com.nocapstone.common_ui.ImageDetailAdapter
import com.nocapstone.common_ui.ToastSet
import com.nocapstone.common_ui.ToastType
import com.nocapstone.community.R
import com.nocapstone.community.ReplyAdapter
import com.nocapstone.community.databinding.FragmentDetailPostBinding
import com.nocapstone.community.dto.Content
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailPostFragment : Fragment() {

    private var postId: Long = 0L
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
        postId = args.postID
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMenu()

        communityViewModel.readDetailPost(postId)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = communityViewModel
            adapter = ImageDetailAdapter()
            repleyAdapter = ReplyAdapter()
            replyRecyclerview.addItemDecoration(DividerItemDecoration(requireContext(), 1))
            replyIl.setEndIconOnClickListener {
                communityViewModel.createReply(postId, Content(replyEt.text.toString())){
                    communityViewModel.readDetailPost(postId)
                }
                replyEt.text?.clear()
            }
        }
    }

    private fun initMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.detail_community_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    android.R.id.home -> {
                        findNavController().popBackStack()
                    }
                    R.id.put_post -> {

                    }
                    R.id.delete_post -> {
                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle("글 삭제")
                            .setMessage("정말 삭제하시겠습니까")
                            .setNegativeButton("취소") {
                                dialog, which -> dialog.dismiss()
                            }.setPositiveButton("삭제") { dialog, which ->
                                communityViewModel.deletePost(postId)
                                communityViewModel.setToastMessage(ToastSet("일기 삭제 완료", ToastType.SUCCESS))
                                dialog.dismiss()
                                findNavController().popBackStack()
                            }.show()
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