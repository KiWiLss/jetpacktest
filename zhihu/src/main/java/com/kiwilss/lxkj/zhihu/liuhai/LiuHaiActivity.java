package com.kiwilss.lxkj.zhihu.liuhai;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.kiwilss.lxkj.zhihu.R;
import com.kiwilss.lxkj.zhihu.base.BaseActivity;

/**
 * @author : Lss kiwilss
 * @FileName: LiuHaiActivity
 * @e-mail : kiwilss@163.com
 * @time : 2019-09-04
 * @desc : {DESCRIPTION}
 */
public class LiuHaiActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liuhai);

        ImmersionBar.with(this)
                .transparentStatusBar()
                //.fitsSystemWindows(true)
                .init();

    }
}
