package com.kiwilss.lxkj.mvvmtest.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.kiwilss.lxkj.mvvmtest.utils.StringUtilsKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/**
 * @author : Lss kiwilss
 * @FileName: TestJ
 * @e-mail : kiwilss@163.com
 * @time : 2019-09-05
 * @desc : {DESCRIPTION}
 */
public class TestJ {

    public void test(Context context){
        String url = "";
        Bitmap bp = null;
        //BitmapExtKt.saveToAlbum(bp,);
        StringUtilsKt.saveToAlbum(url, context, new Function2<String, Uri, Unit>() {
            @Override
            public Unit invoke(String s, Uri uri) {
                return null;
            }
        });
    }
}
