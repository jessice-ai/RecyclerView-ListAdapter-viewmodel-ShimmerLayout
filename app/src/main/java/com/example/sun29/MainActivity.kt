package com.example.sun29

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.sun29.adapter.SunPageListAdapter
import com.example.sun29.data.viewmodel.MyViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        /**
         * 对 模板 RecyclerView 进行管理 并切割成两列
         * sun_list_item_Recyclerview_id 是一个 RecyclerView 的ID
         */
        var adapterxd = SunPageListAdapter()  //创建一个适配器
        sun_list_item_Recyclerview_id.apply {
            adapter = adapterxd
            // fragment 中 context 替换为 requireContext()
            //layoutManager = GridLayoutManager(context,2)  //整齐两列
            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)  //不整齐两列
        }

        /**
         * 创建一个ViewModel 对象，并观察ViewModel 里面元素值是否变化
         */
        // 当 ViewModelProvider 显示红色虚线时，检查ViewModel 依赖是否添加
        var sunMyViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        // Observer 导入时，选择 androidx.lifecycle
        sunMyViewModel.allWords.observe(this, Observer { sunit->
            /**
             * 侦听对象数据发生变化，使用适配器，提交变化的内容，更新界面
             */
            sunit?.let {
                println("Jessice:观察到数据发生变化，准备调用适配器")
                adapterxd.submitList(it)
            }
        })
        sunMyViewModel.allWords.value?:sunMyViewModel.getmAllWords(1)

        swipeRefresh.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            val randoms = (1..20).random() //随机数
            sunMyViewModel.getmAllWords(randoms)
            swipeRefresh.setRefreshing(false);  //关闭下拉刷新动画
        })
//        sunMyViewModel.getmAllWords()
//        val userDao = SunUserDataBase.getInstance(this)?.getUserDao()
//        var userDataArray = userDao?.loadAllUsers()
//        var names_str = "";
//        userDataArray?.let {
//            for (sun in it) {
//                println("Jessice - firstName："+sun.picture)
//            }
//        }

//        /**
//         * 方法二
//         * 不适用数据库，直接把远程数据，使用适配器，更新界面
//         */
//        getremotedata()
//        shimmerViewContainer = sun_shimmer_view_container_id
//        shimmerViewContainer?.setVisibility(View.VISIBLE);
//        //recyclerView.setVisibility(View.GONE);
//        shimmerViewContainer?.startShimmer()  //启动 Shimmer
//        shimmerViewContainer?.setBackground(null)
//        val shimmerBuilder = Shimmer.AlphaHighlightBuilder()   // 创建高光生成器
//        shimmerViewContainer?.setShimmer(
//            shimmerBuilder
//                //.setAutoStart(true) //设置是否自动启动微光动画
////                .setDuration(1000)  //从一个布局到另一个布局亮点需要时间,毫秒
////                .setDirection(Shimmer.Direction.TOP_TO_BOTTOM)  //闪动方向
////                .setRepeatDelay(1000) //延迟多长时间，重复当前动画
////                .setRepeatMode(ObjectAnimator.RESTART) //设置动画重复的模式，1、ObjectAnimator.REVERSE #闪光从左到右，再从右至左往复，2、ObjectAnimator.RESTART #闪光每次总是从左到右（默认）
////                .setBaseAlpha(0.1f) //设置基视图的背景颜色
////                .setDropoff(0.5f) //设置光的宽度，相对宽度，即 ShimmerFrameLayout 整体宽度
////                .setTilt(30F)   //设置光的倾斜角度,正值表示顺时针倾斜，负值表示逆时针倾斜 比如：30F
//                .build()
//        )

    }
}