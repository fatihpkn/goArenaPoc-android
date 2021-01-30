package com.mtek.goarenopoc.ui.fragment.home

import android.os.Bundle
import android.view.View
import com.mtek.goarenopoc.R
import com.mtek.goarenopoc.base.BaseAdapter
import com.mtek.goarenopoc.base.BaseFragment
import com.mtek.goarenopoc.data.model.Data
import com.mtek.goarenopoc.databinding.FragmentHomeBinding
import com.mtek.goarenopoc.ui.MainActivity
import com.mtek.goarenopoc.ui.adapter.homefeed.HomeFeedAdapter
import com.mtek.goarenopoc.ui.adapter.homefeed.LayoutType
import com.mtek.goarenopoc.utils.applyDivider
import com.mtek.goarenopoc.utils.gone


class HomeFragment : BaseFragment<FragmentHomeBinding,HomeViewModel>(HomeViewModel::class) {

    override fun getViewBinding()= FragmentHomeBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireContext() as MainActivity).showBottomNav()

        val dataList = ArrayList<Data>()
        dataList.add(Data(LayoutType.Thumnail.id, "1. Hi! I am in View 1"))
        dataList.add(Data( LayoutType.Thumnail.id, "2. Hi! I am in View 2"))
        dataList.add(Data( LayoutType.Text.id, "3. Hi! I am in View 3"))
        dataList.add(Data( LayoutType.Thumnail.id, "4. Hi! I am in View 4"))
        dataList.add(Data( LayoutType.Thumnail.id, "5. Hi! I am in View 5"))
        dataList.add(Data( LayoutType.Thumnail.id, "6. Hi! I am in View 6"))
        dataList.add(Data( LayoutType.Thumnail.id, "7. Hi! I am in View 7"))
        dataList.add(Data( LayoutType.Text.id, "8. Hi! I am in View 8"))
        dataList.add(Data( LayoutType.Thumnail.id, "9. Hi! I am in View 9"))
        dataList.add(Data( LayoutType.Thumnail.id, "10. Hi! I am in View 10"))
        dataList.add(Data( LayoutType.Text.id, "11. Hi! I am in View 11"))
        dataList.add(Data( LayoutType.Thumnail.id, "12. Hi! I am in View 12"))



        binding.recyclerStories.apply {
            adapter = BaseAdapter<String>(requireContext(),R.layout.row_item_feed_stories,
                arrayListOf("1","2","3","4","5","1","2","3","4","5")
            ) {v, item, position ->
                val view = v?.findViewById<View>(R.id.view)

                if (position != 0){
                    view?.gone()
                }

            }
        }


        binding.recyclerFeed.apply {
            adapter = HomeFeedAdapter(dataList){

            }
        }.also {
            it.applyDivider()
        }

//        binding.recyclerFeed.apply {
//            adapter = BaseAdapter<String>(requireContext(),R.layout.row_item_feed_thumnail_layout,
//                arrayListOf("1","2","3","4","5")
//            ) {v, item, position ->
//                val rc = v?.findViewById<RecyclerView>(R.id.recyclerViewThumnail)
//
//                rc?.adapter = BaseAdapter<String>(requireContext(),R.layout.row_item_thumnail_layout,
//                        arrayListOf("1","2","3","4","5")
//                    ) {v, item, position ->
//
//                    }
//            }
//        }.also {
//            it.applyDivider()
//        }
    }






}