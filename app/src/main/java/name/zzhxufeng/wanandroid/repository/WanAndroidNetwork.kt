package name.zzhxufeng.wanandroid.repository

import android.util.Log
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object WanAndroidNetwork {
    private const val WAN_ANDROID = "https://www.wanandroid.com/"

    val retrofit: Retrofit =
        Retrofit.Builder()
            .client(
                OkHttpClient()
                    .newBuilder()
                    .cookieJar(SessionCookieJar())
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(WAN_ANDROID)
            .build()
}

class SessionCookieJar: CookieJar {
    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return listOf()
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        if (url.encodedPath.contains("login")) {
            Log.d("Cookie#login", cookies.toString())

            cookies.forEach { cookie ->
                Log.d("Cookie#login", "--------")
                Log.d("Cookie#login name:\t", cookie.name)
                Log.d("Cookie#login domain:\t", cookie.domain)
                Log.d("Cookie#login path\t", cookie.path)
                Log.d("Cookie#login value\t", cookie.value)
                Log.d("Cookie#login expiresAt\t", cookie.expiresAt.toString())
                Log.d("Cookie#login hostOnly\t", cookie.hostOnly.toString())
                Log.d("Cookie#login httpOnly\t", cookie.httpOnly.toString())
                Log.d("Cookie#login persistent\t", cookie.persistent.toString())
                Log.d("Cookie#login secure\t", cookie.secure.toString())
            }
        }
    }
}