package com.kiwilss.lxkj.jetpacktest.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NETConnectUtils {
    //需要权限
    // <uses-permission android:name="android.permission.INTERNET"></uses-permission>
    // <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>
    //    1.判断是否有网络连接
    public static boolean isConnectedNet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获得当前活动的网络
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            if (activeNetworkInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }

//    1.判断是否有网络连接
public static boolean isConnectedNetPing(Context context) {
    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    //获得当前活动的网络
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    if (activeNetworkInfo != null) {
        if (activeNetworkInfo.isConnected()) {
            //再测一次连接的网络是否可用
            boolean ping = ping();
            return ping;
        }
    }
    return false;
}

public static boolean ping() {
    String result = null;
        String ip = "m.baidu.com";// ping 的地址，可以换成任何一种可靠的外网
        Process p = null;// ping网址3次
        try {
            p = Runtime.getRuntime().exec("ping -c 2 -w 100 " + ip);
            // 读取ping的内容，可以不加
            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer stringBuffer = new StringBuffer();
            String content = "";
            while ((content = in.readLine()) != null) {
                stringBuffer.append(content);
            }
            //Log.e("------ping-----", "result content : " + stringBuffer.toString());
            // ping的状态
            int status = p.waitFor();
            if (status == 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    return false;
}
    //    2.获得当前的网络信息 wifi,moble
    public static String getNetworkInfoType(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            if (activeNetworkInfo.isConnected()) {
                //获得当前的网络信息
                return activeNetworkInfo.getTypeName();
            }
        }
        return null;
    }

    /**判断网络是否连接
     * @param context
     * @return
     */
    public static boolean isConnected(Context context)
    {

        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null != connectivity)
        {

            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected())
            {
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        //cm.getActiveNetworkInfo().getType()==ConnectivityManager.TYPE_MOBILE;
        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**判断是否是移动网络
     * @param context
     * @return
     */
    public boolean isMobile(Context context){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity==null){
            return false;
        }
        return connectivity.getActiveNetworkInfo().getType()==ConnectivityManager.TYPE_MOBILE;
    }
    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity)
    {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }
}