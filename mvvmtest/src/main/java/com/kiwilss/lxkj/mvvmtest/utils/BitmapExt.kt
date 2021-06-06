/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: BitmapExt
 * Author:   kiwilss
 * Date:     2019-08-22 17:05
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import com.kiwilss.lxkj.ktx.core.runOnUIThread
import com.kiwilss.lxkj.mvvmtest.MyApp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

/**
 *@FileName: BitmapExt
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-22
 * @desc   : {DESCRIPTION}
 */

fun Any.runOnUIThread(action: ()->Unit){
    Handler(Looper.getMainLooper()).post { action() }
}


/**
 * 将Bitmap保存到相册
 */
fun Bitmap.saveToAlbum(format: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG, quality: Int = 100, filename: String = "", callback: ((path: String?, uri: Uri?)->Unit)? = null ) {
    GlobalScope.launch {
        try {
            //1. create path

            val dirPath = Environment.getExternalStorageDirectory().absolutePath + "/" + Environment.DIRECTORY_PICTURES
            val dirFile = File(dirPath)
            if (!dirFile.exists()) dirFile.mkdirs()
            val ext = when (format) {
                Bitmap.CompressFormat.PNG -> ".png"
                Bitmap.CompressFormat.JPEG -> ".jpg"
                Bitmap.CompressFormat.WEBP -> ".webp"
            }
            val target = File(dirPath, (if(filename.isEmpty()) System.currentTimeMillis().toString() else filename) + ext)
            if (target.exists()) target.delete()
            target.createNewFile()
            //2. save
            compress(format, quality, FileOutputStream(target))
            //3. notify
            MediaScannerConnection.scanFile(MyApp.CONTEXT, arrayOf(target.absolutePath),
                arrayOf("image/$ext")
            ) { path, uri ->
                runOnUIThread {
                    callback?.invoke(path, uri)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            runOnUIThread { callback?.invoke(null, null) }
        }
    }
}


fun Bitmap.saveToAlbumQ( context: Context,format: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG,
                        quality: Int = 100, filename: String = "",
                        callback: ((path: String?, uri: Uri?)->Unit)? = null){

    val target = if (filename.isEmpty()){
         System.currentTimeMillis().toString()
    }else{
        filename
    }

    val ext = when (format) {
        Bitmap.CompressFormat.PNG -> "png"
        Bitmap.CompressFormat.JPEG -> "jpg"
        Bitmap.CompressFormat.WEBP -> "webp"
    }
    try {
        //设置保存参数到ContentValues中
        val contentValues = ContentValues()
        //设置文件名
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, target)
        //兼容Android Q和以下版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //android Q中不再使用DATA字段，而用RELATIVE_PATH代替
            //RELATIVE_PATH是相对路径不是绝对路径
            //DCIM是系统文件夹，关于系统文件夹可以到系统自带的文件管理器中查看，不可以写没存在的名字
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/signImage")
            //contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Music/signImage");
        } else {
            contentValues.put(
                MediaStore.Images.Media.DATA,
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).path
            )
        }
        //设置文件类型
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/$ext")
        //执行insert操作，向系统文件夹中添加文件
        //EXTERNAL_CONTENT_URI代表外部存储器，该值不变
        val uri = context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
        if (uri != null) {
            //若生成了uri，则表示该文件添加成功
            //使用流将内容写入该uri中即可
            val outputStream = context.contentResolver.openOutputStream(uri)
            if (outputStream != null) {
                compress(format, 90, outputStream)
                outputStream.flush()
                outputStream.close()
                runOnUIThread { callback?.invoke(target, null) }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        runOnUIThread { callback?.invoke(null, null) }
    }


}



