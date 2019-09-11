/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: SisterActivity
 * Author:   kiwilss
 * Date:     2019-09-05 14:58
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.sister

import android.content.Context
import android.os.Bundle
import android.util.SparseArray
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kiwilss.lxkj.mvvmtest.base.BaseActivity
import com.kiwilss.lxkj.mvvmtest.model.api.RetrofitHelper
import com.kiwilss.lxkj.mvvmtest.ui.home.HomeModel
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.ImageViewerPopupView
import com.lxj.xpopup.interfaces.OnSrcViewUpdateListener
import com.lxj.xpopup.interfaces.XPopupImageLoader
import kotlinx.android.synthetic.main.activity_sister.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File



/**
 *@FileName: SisterActivity
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-09-05
 * @desc   : {DESCRIPTION}
 */
class SisterActivity : BaseActivity<HomeModel>()
,BaseQuickAdapter.RequestLoadMoreListener{


    override fun onLoadMoreRequested() {
        mPage++
        getData()
    }

    override fun startObserve() {

    }

    override fun initData() {
        showLoadingDiloag()
        getData()

    }

    private fun getData() {
        GlobalScope.launch (Dispatchers.Main){
            //val sister = RetrofitHelper.apiService.getSisterIcon2(mPage).await()
            val sisterBean = RetrofitHelper.apiService.getSisterIcon(mPage)
            LogUtils.e(sisterBean)
            if (!sisterBean.error){
                //得到数据
                if (mPage == 1){
                    mAdapter.replaceData(sisterBean.results)
                }else{
                    mAdapter.addData(sisterBean.results)
                }
                dismissLoadingDiloag()
                mAdapter.loadMoreComplete()
                list.clear()
                for (i in mAdapter.data.indices){
                    list.add(mAdapter.data[i].url)
                    //sparseArray.put(i,mAdapter.data[i].img)
                }
            }

        }
    }

    override fun initOnClick() {



    }

//    override fun onBackPressed() {
//        if (!imageWatcher!!.handleBackPressed()){
//            super.onBackPressed()
//        }
//    }

    var mPage = 0
    val mAdapter by lazy { SisterAdapter() }

    val list = arrayListOf<Any>()
    //var imageWatcher : ImageWatcherHelper? = null

    val sparseArray = SparseArray<ImageView>()

    override fun initInterface(savedInstanceState: Bundle?) {

//        imageWatcher = ImageWatcherHelper.with(this,
//            GlideImageWatcherLoader()
//        )
//            .setIndexProvider(DefaultIndexProvider())



        rv_sister_list.run {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = mAdapter
        }

        mAdapter.run {
            setPreLoadNumber(6)
            openLoadAnimation()
            setOnLoadMoreListener(this@SisterActivity,rv_sister_list)

            setOnItemChildClickListener { adapter, view, position ->


                //imageWatcher!!.show(view as ImageView?,sparseArray,list)
                XPopup.Builder(this@SisterActivity)
                    .asImageViewer(view as ImageView?,position,list,object :
                        OnSrcViewUpdateListener {
                        override fun onSrcViewUpdate(
                            popupView: ImageViewerPopupView,
                            position: Int
                        ) {
                            popupView.updateSrcView(
                                view
//                                rv_sister_list.getChildAt(position).findViewById(R.id.iv_item_tucong_icon)
//                                        as ImageView
                            )
                        }
                    },ImageLoader()).show()

            }
        }

    }

    override fun getLayoutId(): Int = com.kiwilss.lxkj.mvvmtest.R.layout.activity_sister

    override fun providerVMClass(): Class<HomeModel>? = HomeModel::class.java
}



class ImageLoader : XPopupImageLoader {
    override fun loadImage(position: Int, @NonNull url: Any, @NonNull imageView: ImageView) {
        //必须指定Target.SIZE_ORIGINAL，否则无法拿到原图，就无法享用天衣无缝的动画
        Glide.with(imageView).load(url)
            .apply(
                RequestOptions().placeholder(com.kiwilss.lxkj.mvvmtest.R.mipmap.ic_launcher_round)
                    .override(Target.SIZE_ORIGINAL))
            .into(imageView)


    }

    override fun getImageFile(@NonNull context: Context, @NonNull uri: Any): File? {
        try {
            return Glide.with(context).downloadOnly().load(uri).submit().get()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }
}