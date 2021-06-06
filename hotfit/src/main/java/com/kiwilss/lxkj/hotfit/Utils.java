package com.kiwilss.lxkj.hotfit;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author : Lss kiwilss
 * @FileName: Utils
 * @e-mail : kiwilss@163.com
 * @time : 2019-12-26
 * @desc : {DESCRIPTION}
 */
public class Utils {


    //检查当前系统是否已开启暗黑模式
    public static boolean getDarkModeStatus(Context context) {
        int mode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return mode == Configuration.UI_MODE_NIGHT_YES;
    }

    /**判断公有目录文件是否存在
     * @param context
     * @param path
     * @return
     */
    public static boolean isAndroidQFileExists(Context context, String path){
        AssetFileDescriptor afd = null;
        ContentResolver cr = context.getContentResolver();
        try {
            Uri uri = Uri.parse(path);
            afd = cr.openAssetFileDescriptor(uri, "r");
            if (afd == null) {
                return false;
            } else {
                close(afd);
            }
        } catch (FileNotFoundException e) {
            return false;
        }finally {
            close(afd);
        }
        return true;
    }

    private static void close(AssetFileDescriptor afd) {
        if (afd != null){
            try {
                afd.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 通过MediaStore保存，兼容AndroidQ，保存成功自动添加到相册数据库，无需再发送广播告诉系统插入相册
     *
     * @param context      context
     * @param sourceFile   源文件
     * @param saveFileName 保存的文件名
     * @param saveDirName  picture子目录
     * @return 成功或者失败
     */
    public static boolean saveImageWithAndroidQ(Context context,
                                                File sourceFile,
                                                String saveFileName,
                                                String saveDirName) {
        //String extension = BitmapUtil.getExtension(sourceFile.getAbsolutePath());

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DESCRIPTION, "This is an image");
        values.put(MediaStore.Images.Media.DISPLAY_NAME, saveFileName);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        values.put(MediaStore.Images.Media.TITLE, "Image.png");
        values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + saveDirName);

        Uri external = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver resolver = context.getContentResolver();

        Uri insertUri = resolver.insert(external, values);
        BufferedInputStream inputStream = null;
        OutputStream os = null;
        boolean result = false;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(sourceFile));
            if (insertUri != null) {
                os = resolver.openOutputStream(insertUri);
            }
            if (os != null) {
                byte[] buffer = new byte[1024 * 4];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    os.write(buffer, 0, len);
                }
                os.flush();
            }
            result = true;
        } catch (IOException e) {
            result = false;
        } finally {
            close(os, inputStream);
        }
        return result;
    }

    private static void close(OutputStream os, BufferedInputStream inputStream) {
        if (os != null){
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (inputStream != null){
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //复制沙盒私有文件到Download公共目录下
    public static void copyToDownloadAndroidQ(Context context, String sourcePath, String fileName, String saveDirName){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Downloads.DISPLAY_NAME, fileName);
        values.put(MediaStore.Downloads.MIME_TYPE, "application/vnd.android.package-archive");
        values.put(MediaStore.Downloads.RELATIVE_PATH, "Download/" + saveDirName.replaceAll("/","") + "/");

        Uri external = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            external = MediaStore.Downloads.EXTERNAL_CONTENT_URI;
        }
        ContentResolver resolver = context.getContentResolver();

        Uri insertUri = resolver.insert(external, values);
        if(insertUri == null) {
            return;
        }

        String mFilePath = insertUri.toString();

        InputStream is = null;
        OutputStream os = null;
        try {
            os = resolver.openOutputStream(insertUri);
            if(os == null){
                return;
            }
            int read;
            File sourceFile = new File(sourcePath);
            if (sourceFile.exists()) { // 文件存在时
                is = new FileInputStream(sourceFile); // 读入原文件
                byte[] buffer = new byte[1444];
                while ((read = is.read(buffer)) != -1) {
                    os.write(buffer, 0, read);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //close(is,os);
        }

    }


    //复制沙盒私有文件到Download公共目录下
    //orgFilePath是要复制的文件私有目录路径
    //displayName复制后文件要显示的文件名称带后缀（如xx.txt）
    public static void copyPrivateToDownload(Context context,String orgFilePath,String displayName){
        ContentValues values = new ContentValues();
        //values.put(MediaStore.Images.Media.DESCRIPTION, "This is a file");
        values.put(MediaStore.Files.FileColumns.DISPLAY_NAME, displayName);
        values.put(MediaStore.Files.FileColumns.MIME_TYPE, "text/plain");//MediaStore对应类型名
        values.put(MediaStore.Files.FileColumns.TITLE, displayName);
        values.put(MediaStore.Images.Media.RELATIVE_PATH, "Download/Test");//公共目录下目录名

        Uri external = null;//内部存储的Download路径
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            external = MediaStore.Downloads.EXTERNAL_CONTENT_URI;
        }
        ContentResolver resolver = context.getContentResolver();

        Uri insertUri = resolver.insert(external, values);//使用ContentResolver创建需要操作的文件
        //Log.i("Test--","insertUri: " + insertUri);

        InputStream ist=null;
        OutputStream ost = null;
        try {
            ist=new FileInputStream(new File(orgFilePath));
            if (insertUri != null) {
                ost = resolver.openOutputStream(insertUri);
            }
            if (ost != null) {
                byte[] buffer = new byte[4096];
                int byteCount = 0;
                while ((byteCount = ist.read(buffer)) != -1) {  // 循环从输入流读取 buffer字节
                    ost.write(buffer, 0, byteCount);        // 将读取的输入流写入到输出流
                }
                // write what you want
            }
        } catch (IOException e) {
            //Log.i("copyPrivateToDownload--","fail: " + e.getCause());
        } finally {
            try {
                if (ist != null) {
                    ist.close();
                }
                if (ost != null) {
                    ost.close();
                }
            } catch (IOException e) {
                //Log.i("copyPrivateToDownload--","fail in close: " + e.getCause());
            }
        }
    }




}
