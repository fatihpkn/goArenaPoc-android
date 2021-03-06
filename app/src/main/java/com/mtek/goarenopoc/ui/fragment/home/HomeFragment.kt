package com.mtek.goarenopoc.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.mtek.goarenopoc.R
import com.mtek.goarenopoc.base.BaseFragment
import com.mtek.goarenopoc.data.model.Feed
import com.mtek.goarenopoc.data.model.FeedModel
import com.mtek.goarenopoc.data.network.response.FeedResponseModel
import com.mtek.goarenopoc.databinding.FragmentHomeBinding
import com.mtek.goarenopoc.ui.MainActivity
import com.mtek.goarenopoc.ui.adapter.homefeed.HomeFeedAdapter
import com.mtek.goarenopoc.ui.fragment.SharedViewModel
import com.mtek.goarenopoc.utils.applyDivider
import com.mtek.goarenopoc.utils.setSafeOnClickListener


class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(HomeViewModel::class) {


    override fun getViewBinding() = FragmentHomeBinding.inflate(layoutInflater)

    private var homeAdapter: HomeFeedAdapter? = null
    lateinit var sharedViewModel: SharedViewModel
    private var responseFeedList: ArrayList<FeedModel> = arrayListOf()


    private val observerFeed: Observer<FeedResponseModel> = Observer {
        if (it != null) {
            responseFeedList?.clear()
            responseFeedList = (it.data as ArrayList<FeedModel>?)!!
            homeAdapter?.setList(responseFeedList)
            binding.swipeContainer.isRefreshing = false
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel {
            feedResponse.observe(viewLifecycleOwner, observerFeed)
        }
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        (requireContext() as MainActivity).showBottomNav()


        init()
        setAdapter()
        clickFun()
    }

    private fun init() {
        sharedViewModel.getFeedList().observe(viewLifecycleOwner,{
            homeAdapter?.setList(it.data as ArrayList<FeedModel>?)
        })
        

    }

    private fun clickFun() {
        binding.swipeContainer.setOnRefreshListener(OnRefreshListener {
            viewModel.senRequestVFeed()

        })

        binding.toolbar.btnBack.setSafeOnClickListener {


        }

        binding.toolbar.btnAdd.setSafeOnClickListener {
            sharedViewModel.cleanAllData()
            findNavController().navigate(R.id.action_homeFragment_to_postFragment2)
        }

    }

    private fun setAdapter() {

        homeAdapter = HomeFeedAdapter(responseFeedList as ArrayList<FeedModel>) {
            if (it.isDeleteFunWork) {
                viewModel.sendRequestDelete(it.id.toString())
                viewModel.senRequestVFeed()
            }
        }
        binding.recyclerFeed.apply {
            adapter = homeAdapter

        }.also {
            it.applyDivider()
        }
        homeAdapter?.setList(responseFeedList)
    }


}