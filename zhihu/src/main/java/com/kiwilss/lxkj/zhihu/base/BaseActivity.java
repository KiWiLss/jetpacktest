package com.kiwilss.lxkj.zhihu.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kiwilss.lxkj.zhihu.R;
import com.kiwilss.lxkj.zhihu.theme.Theme;
import com.kiwilss.lxkj.zhihu.utils.PreUtils;

/**
 * @author : Lss kiwilss
 * @FileName: BaseActivity
 * @e-mail : kiwilss@163.com
 * @time : 2019-09-06
 * @desc : {DESCRIPTION}
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onPreCreate();


    }

    private void onPreCreate() {
        Theme theme = PreUtils.getCurrentTheme(this);
        switch (theme) {
            case Blue:
                setTheme(R.style.BlueTheme);
                break;
            case Red:
                setTheme(R.style.RedTheme);
                break;
            case Brown:
                setTheme(R.style.BrownTheme);
                break;
//            case Green:
//                setTheme(R.style.GreenTheme);
//                break;
//            case Purple:
//                setTheme(R.style.PurpleTheme);
//                break;
//            case Teal:
//                setTheme(R.style.TealTheme);
//                break;
//            case Pink:
//                setTheme(R.style.PinkTheme);
//                break;
//            case DeepPurple:
//                setTheme(R.style.DeepPurpleTheme);
//                break;
//            case Orange:
//                setTheme(R.style.OrangeTheme);
//                break;
//            case Indigo:
//                setTheme(R.style.IndigoTheme);
//                break;
//            case LightGreen:
//                setTheme(R.style.LightGreenTheme);
//                break;
//            case Lime:
//                setTheme(R.style.LimeTheme);
//                break;
//            case DeepOrange:
//                setTheme(R.style.DeepOrangeTheme);
//                break;
//            case Cyan:
//                setTheme(R.style.CyanTheme);
//                break;
//            case BlueGrey:
//                setTheme(R.style.BlueGreyTheme);
//                break;
        }

    }
}
