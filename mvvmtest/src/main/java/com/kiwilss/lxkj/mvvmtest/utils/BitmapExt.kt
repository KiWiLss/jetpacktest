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

import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.os.Handler
import android.os.Looper
import com.kiwilss.lxkj.ktx.core.runOnUIThread
import com.kiwilss.lxkj.mvvmtest.MyApp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

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
        } catch (e: IOException) {
            e.printStackTrace()
            runOnUIThread { callback?.invoke(null, null) }
        }
    }
}


