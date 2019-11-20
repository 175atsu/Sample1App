package com.example.sample1app.ui.dashboard

import android.content.Context
import android.graphics.ColorSpace
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sample1app.R
import com.example.sample1app.SampleRecyclerViewAdapter
import com.example.sample1app.SampleService
import com.example.sample1app.SampleViewModel
import com.example.sample1app.db.SampleResponse
import kotlinx.android.synthetic.main.fragment_dashboard.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    var mContext: Context? = null
    val samppleService = SampleService()

    //謎
    companion object {
        fun createInstance(mc: Context): DashboardFragment {
            val listFragment = DashboardFragment()
            listFragment.mContext = mc
            return listFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewInitialSetting()
    }

    //アダプターなどなどの設定
    fun recyclerViewInitialSetting() {
        val rv = recyclerView
        val adapter = SampleRecyclerViewAdapter(fetchAllUserData(), object : SampleRecyclerViewAdapter.ListListener {
            fun onClickRow(tappedView: View, userListModel: ColorSpace.Model) {
                //toDetail(userListModel)
            }
        })
        //リストのtrueコンテンツの大きさがデータによって変わらないならを渡す。これをRecyclerViewにいつもすることで、パフォーマンスが良くなる。
        rv.setHasFixedSize(true)
        rv.layoutManager = LinearLayoutManager(activity)
        rv.layoutManager = GridLayoutManager(context, 2)
        rv.adapter = adapter

    }

    //データリストに保存し、そのデータの取得
    fun fetchAllUserData(): List<SampleViewModel> {

        val dataList = mutableListOf<SampleViewModel>()
        //リクエストURl作成してデータとる
        samppleService.createService().apiDemo(lat = 1, lng = 20, range = 3).enqueue(object :
            Callback<List<SampleResponse>> {

            //非同期処理
            override fun onResponse(call: Call<List<SampleResponse>>, response: Response<List<SampleResponse>>) {
                Log.d("TAGres","onResponse")

                //ステータスコードが200：OKなので、ここではちゃんと通信できたよー
                if (response.isSuccessful) {
                    response.body()?.let {
                        for (item in it) {
                            val data: SampleViewModel = SampleViewModel().also {
                                it.name = item.shop?.name
                                it.image = item.shop?.logo_image
                                Log.d("TAGname",it.name)
                            }
                            dataList.add(data)
                        }
                        //更新
                        recyclerView.adapter?.notifyDataSetChanged()
                    }
                } else {
                }
            }
            override fun onFailure(call: Call<List<SampleResponse>>, t: Throwable) {
                Log.d("TAGres","onFailure")
            }
        })
        return dataList
    }
}