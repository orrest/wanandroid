package name.zzhxufeng.wanandroid.data.network

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
                    .addInterceptor(WanAndroidResponseInterceptor())
                    .addInterceptor(WanAndroidRequestInterceptor())
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(WAN_ANDROID)
            .build()
}

const val DEFAULT_PAGING_SIZE = 20
const val MAX_PAGING_SIZE = 60
const val START_PAGE_OLD_API = 0
const val START_PAGE = 1
const val WAN_SUCCESS_CODE = 0