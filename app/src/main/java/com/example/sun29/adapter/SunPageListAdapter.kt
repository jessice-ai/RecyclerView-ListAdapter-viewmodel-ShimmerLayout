package com.example.sun29.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.sun29.R
import com.example.sun29.data.room.SunUser
import kotlinx.android.synthetic.main.sun_cell.view.*


class SunPageListAdapter : ListAdapter<SunUser, SunPageListAdapter.SunMyViewHolder>(DIFFCALLBACK) {
    class SunMyViewHolder(var sunHolder_View : View) : RecyclerView.ViewHolder(sunHolder_View){

    }
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): SunMyViewHolder {
        /**
         * onCreateViewHolder 作用： 加载一个视图出来，并创建一个 viewHolder，而viewHolder作为recyclerView缓存管理的对象
         * 可复用
         * 负责承载每个子项的布局
         */
        val holder = SunMyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.sun_cell,parent,false))
        //println("Jessice-Sun-onCreateViewHolder----"+holder.getAdapterPosition());  // 打印一下 onCreateViewHolder 运行次数，recyclerView 可缓存
        return holder
    }
    /**
     * onBindViewHolder 为每一项赋值
     * 负责将每个子项 holder 绑定数据
     * 运行多次
     */
    override fun onBindViewHolder(holder: SunMyViewHolder, position: Int) {
        //打印出所有的图片地址
        println("Jessice:Picture----"+getItem(position).picture)
        //println("Jessice-Sun-onBindViewHolder————"+position);
        var imgUrl = getItem(position).picture
        /**
         * Shimmer 开启，与设置
         */
        holder.itemView.sun_shimmer_view_container_id.apply {
            setShimmerColor(0x55FFFFFF)  //设置颜色
            setShimmerAnimationDuration(1000) //设置微光动画持续时间，毫秒
            setShimmerAngle(30)  //设置倾斜角度，-45 至 45
            startShimmerAnimation() // 启动Shimmer
        }
        /**
         * 使用Glide 加载图片赋值给 holder.itemView.imageView3
         */
        Glide.with(holder.itemView)
            .load(imgUrl)
            .placeholder(R.drawable.ic_launcher_background)
            //.fitCenter()
            .listener(object : RequestListener<Drawable>{  //说明：这里的 Drawable 固定就好，一个类
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    /**
                     * 图片加载失败时执行这里，关闭 Shimmer
                     */
                    holder.itemView.sun_shimmer_view_container_id.stopShimmerAnimation()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    /**
                     * 图片加载成功时执行，关闭 Shimmer
                     */
                    return false.also {
                        holder.itemView.sun_shimmer_view_container_id.stopShimmerAnimation()
                    }
                }
            })
            .into(holder.itemView.imageView3)
    }
    object DIFFCALLBACK: DiffUtil.ItemCallback<SunUser>(){
        override fun areItemsTheSame(oldItem: SunUser, newItem: SunUser): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SunUser, newItem: SunUser): Boolean {
            return oldItem.id == newItem.id
        }
    }
}