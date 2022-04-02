package com.qxc.kotlinpages.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

/**
 * 工具类
 *
 * @author 齐行超
 * @date 19.11.30
 */
class AppUtils {
    companion object{
        /**
         * 线程安全的单例（懒汉式单例）
         */
        val instance : AppUtils by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            AppUtils()
        }

        private lateinit var context:Context

        /**
         * 注册
         *
         * @param pContext 上下文
         */
        fun register(pContext: Context){
            context = pContext
        }
    }

    /**
     * 判断是否连接了网络
     */
    fun isConnectNetWork():Boolean{
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            cm?.run {
                this.getNetworkCapabilities(cm.activeNetwork)?.run {
                    result = when {
                        this.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        this.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        this.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
            }
        } else {
            cm?.run {
                cm.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = true
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = true
                    }
                }
            }
        }
        return result
    }
}