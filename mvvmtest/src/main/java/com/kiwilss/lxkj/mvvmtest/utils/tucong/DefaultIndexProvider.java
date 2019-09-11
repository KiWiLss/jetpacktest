package com.kiwilss.lxkj.mvvmtest.utils.tucong;

import android.content.Context;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.ielse.imagewatcher.ImageWatcher;

import java.util.List;

public class DefaultIndexProvider implements ImageWatcher.IndexProvider {
        TextView tCurrentIdx;

        @Override
        public View initialView(Context context) {
            tCurrentIdx = new TextView(context);
            FrameLayout.LayoutParams lpCurrentIdx = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);
            lpCurrentIdx.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            tCurrentIdx.setLayoutParams(lpCurrentIdx);
            tCurrentIdx.setTextColor(0xFFFFFFFF);
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            //float tCurrentIdxTransY = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, displayMetrics) + 0.5f;
            float tCurrentIdxTransY = TypedValue.applyDimension
                    (TypedValue.COMPLEX_UNIT_DIP, -30, displayMetrics) + 0.5f;
            tCurrentIdx.setTranslationY(tCurrentIdxTransY);
            return tCurrentIdx;
        }

        @Override
        public void onPageChanged(ImageWatcher imageWatcher, int position, List<Uri> dataList) {
            if (dataList.size() > 1) {
                tCurrentIdx.setVisibility(View.VISIBLE);
                final String idxInfo = (position + 1) + " / " + dataList.size();
                tCurrentIdx.setText(idxInfo);
            } else {
                tCurrentIdx.setVisibility(View.GONE);
            }
        }
    }