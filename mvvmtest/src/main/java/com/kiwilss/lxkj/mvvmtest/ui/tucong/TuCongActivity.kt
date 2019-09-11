/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: TuCongActivity
 * Author:   kiwilss
 * Date:     2019-09-04 16:42
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.tucong

import android.net.Uri
import android.os.Bundle
import android.util.SparseArray
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.github.ielse.imagewatcher.ImageWatcher
import com.github.ielse.imagewatcher.ImageWatcherHelper
import com.kiwilss.lxkj.mvvmtest.R
import com.kiwilss.lxkj.mvvmtest.base.BaseActivity
import com.kiwilss.lxkj.mvvmtest.base.postDelay
import com.kiwilss.lxkj.mvvmtest.model.api.RetrofitHelper
import com.kiwilss.lxkj.mvvmtest.ui.home.HomeModel
import com.kiwilss.lxkj.mvvmtest.utils.saveToAlbum
import com.kiwilss.lxkj.mvvmtest.utils.toast
import com.kiwilss.lxkj.mvvmtest.utils.tucong.DefaultIndexProvider
import com.kiwilss.lxkj.mvvmtest.utils.tucong.GlideImageWatcherLoader
import kotlinx.android.synthetic.main.activity_tucong.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 *@FileName: TuCongActivity
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-09-04
 * @desc   : {DESCRIPTION}
 */
class TuCongActivity : BaseActivity<HomeModel>() ,BaseQuickAdapter.RequestLoadMoreListener{
    override fun onLoadMoreRequested() {
        mPage++
        getData()
    }

    override fun startObserve() {

    }

    var mPage = 1
    override fun initData() {
        showLoadingDiloag()
        getData()
        postDelay (2000){
            toast("长按图片可保存到相册")
        }
    }

    private fun getData() {
        GlobalScope.launch(Dispatchers.Main) {
            val response = RetrofitHelper.apiService.getWallPaper(mPage)
            LogUtils.e(response)
            dismissLoadingDiloag()
            val feedList = response.feedList
            if (feedList.isNotEmpty()) {
                if (mPage == 1){
                    mAdapter.replaceData(feedList)
                }else{
                    mAdapter.addData(feedList)
                }
                mAdapter.loadMoreComplete()
            }
        }
    }

    override fun initOnClick() {
    }

    override fun initInterface(savedInstanceState: Bundle?) {

        initAdapter()

        imageWatcher = ImageWatcherHelper.with(this,
            GlideImageWatcherLoader()
        )
            .setIndexProvider(DefaultIndexProvider())

        //图片点击长按
        imageWatcher?.setOnPictureLongPressListener(object : ImageWatcher.OnPictureLongPressListener{
            override fun onPictureLongPress(p0: ImageView?, p1: Uri?, p2: Int) {
                val url = p1.toString()
                url.saveToAlbum(this@TuCongActivity) { path, uri ->
                    LogUtils.e("path=$path---uri=$uri")
                    toast("圖片已保存至$path")
                }
            }
        })


        //url.saveToAlbum(this, { s, uri -> null })
    }

    override fun onBackPressed() {
            if (!imageWatcher!!.handleBackPressed()){
                super.onBackPressed()
            }
    }

    val mAdapter by lazy { TuCongAdapter() }

    var imageWatcher : ImageWatcherHelper? = null

    private fun initAdapter() {

        rv_tucong_list.run {
            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            adapter = mAdapter
        }

        mAdapter.run {
            openLoadAnimation()
            setPreLoadNumber(6)

            setOnLoadMoreListener(this@TuCongActivity,rv_tucong_list)

            setOnItemChildClickListener { adapter, view, position ->
                val data: Feed = adapter.data[position] as Feed
                val urlList = arrayListOf<Uri>()
                val sparseArray = SparseArray<ImageView>()
                when(view.id){
                    R.id.iv_item_tucong_icon -> {
                        if (data.entry.images.isNullOrEmpty()){
                            return@setOnItemChildClickListener
                        }
                        for ((index,element) in data.entry.images.withIndex()){
                            val url = "https://photo.tuchong.com/" + element.user_id + "/f/" + element.img_id + ".jpg"
                            urlList.add(url.toUri())
                            sparseArray.put(index, view as ImageView?)
                        }
                        imageWatcher!!.show(view as ImageView?,sparseArray,urlList)

//                        XPopup.Builder(this@TuCongActivity)
//                            .asImageViewer(view as ImageView?,position,urlList,object :
//                                OnSrcViewUpdateListener {
//                                override fun onSrcViewUpdate(
//                                    popupView: ImageViewerPopupView,
//                                    position: Int
//                                ) {
//                                    popupView.updateSrcView(
//                                        rv_tucong_list.getChildAt(position).findViewById(R.id.iv_item_tucong_icon)
//                                                as ImageView)
//                                }
//                            },ImageLoader()).show()
                    }
                }
            }
        }


    }



    override fun getLayoutId(): Int = com.kiwilss.lxkj.mvvmtest.R.layout.activity_tucong

    override fun providerVMClass(): Class<HomeModel>? = HomeModel::class.java
}


