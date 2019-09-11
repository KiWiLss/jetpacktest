package com.kiwilss.lxkj.jetpacktest.ch;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kiwilss.lxkj.jetpacktest.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : Lss kiwilss
 * @FileName: RvActivity
 * @e-mail : kiwilss@163.com
 * @time : 2019-08-21
 * @desc : {DESCRIPTION}
 */
public class RvActivity extends AppCompatActivity {

     final int[] AVATARS = new int[] {
            R.drawable.avatar_1
            , R.drawable.avatar_2
            , R.drawable.avatar_3
            , R.drawable.avatar_4
            , R.drawable.avatar_5
            , R.drawable.avatar_6
            , R.drawable.avatar_7
            , R.drawable.avatar_8
            , R.drawable.avatar_9
            , R.drawable.avatar_10
            , R.drawable.avatar_11
            , R.drawable.avatar_12
            , R.drawable.avatar_13
            , R.drawable.avatar_14
            , R.drawable.avatar_15
            , R.drawable.avatar_16
            , R.drawable.avatar_17
            , R.drawable.avatar_18
            , R.drawable.avatar_19
            , R.drawable.avatar_20
    };
    private ArrayList<Map<String,Integer>> mData;
    private RecyclerView mRvList;
    private ChAdapter chAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv);

        mRvList = (RecyclerView) findViewById(R.id.rv_rv_list);

        //仿iOS的弹性留白效果：
//侧滑时表现为弹性留白效果，结束后自动恢复
//        SmartSwipe.wrap(mRvList)
//                .addConsumer(new SpaceConsumer())
//                .enableVertical(); //工作方向：纵向

        initAdapter();
    }

    private void initAdapter() {
        mData = new ArrayList<>();
        for (int i = 0; i < AVATARS.length; i++) {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("icon",AVATARS[i]);
            map.put("num",i);
            mData.add(map);
        }
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        chAdapter = new ChAdapter(this, mData);
        mRvList.setAdapter(chAdapter);
        mRvList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }
}
