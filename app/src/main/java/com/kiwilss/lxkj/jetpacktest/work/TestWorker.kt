/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: TestWorker
 * Author:   kiwilss
 * Date:     2019-08-15 19:52
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.jetpacktest.work

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 *@FileName: TestWorker
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-15
 * @desc   : {DESCRIPTION}
 */
class TestWorker(context: Context, workerParameters: WorkerParameters)
    : Worker(context, workerParameters){
    override fun doWork(): Result {//适用于需要保证系统即使应用程序退出也会运行的任务
        Log.e("MMM", "执行了 doWork() 操作！")
        return Result.success()
    }
}