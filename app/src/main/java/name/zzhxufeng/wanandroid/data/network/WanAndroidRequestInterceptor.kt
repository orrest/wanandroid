package name.zzhxufeng.wanandroid.data.network

import android.util.Log
import name.zzhxufeng.wanandroid.utils.CookieMMKVRepo
import name.zzhxufeng.wanandroid.utils.CookieMMKVRepo.COOKIES
import name.zzhxufeng.wanandroid.utils.LoginManager
import okhttp3.Interceptor
import okhttp3.Response

class WanAndroidRequestInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val lastRequest = chain.request()
        return if (LoginManager.isLogin()) {
            val cookies = CookieMMKVRepo.getStringSet(COOKIES)
            if (cookies == null) {
                return chain.proceed(lastRequest)
            } else {
                val headeredRequest = lastRequest.newBuilder().apply {
                    var cookieString = ""
                    cookies.forEach { cookie ->
                        Log.d("WanAndroidRequestInterceptor", cookie)
                        cookieString += cookie.substringBefore(';') + "; "
                    }
                    this.header("Cookie", cookieString)
                }.build()
                chain.proceed(headeredRequest)
            }
        } else {
            chain.proceed(lastRequest)
        }
    }
}
