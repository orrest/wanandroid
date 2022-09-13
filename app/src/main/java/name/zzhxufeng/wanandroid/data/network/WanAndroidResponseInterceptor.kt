package name.zzhxufeng.wanandroid.data.network

import android.util.Log
import name.zzhxufeng.wanandroid.utils.CookieMMKVRepo
import name.zzhxufeng.wanandroid.utils.CookieMMKVRepo.COOKIES
import okhttp3.Interceptor
import okhttp3.Response

class WanAndroidResponseInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val lastRequest = chain.request()
        val response = chain.proceed(lastRequest)
        if (response.request.url.encodedPath.contains("login")
            && response.headers("Set-Cookie").isNotEmpty()
        ) {
            val cookies = mutableSetOf<String>().apply {
                for (ck in response.headers("Set-Cookie")) {
                    if (ck.contains("Expires")) {
                        add(ck)
                    }
                }
            }
            Log.d(WanAndroidNetwork.javaClass.name, "cookies: $cookies")
            CookieMMKVRepo.putStringSet(COOKIES, cookies)
        } else if (response.request.url.encodedPath.contains("logout")
            && response.headers("Set-Cookies").isNotEmpty()
        ) {
            val allExpire = response.headers("Set-Cookies").all { it.contains("1970") }
            if (allExpire) {
                CookieMMKVRepo.remove(COOKIES)
            }
        }
        return response
    }
}