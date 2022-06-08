package name.zzhxufeng.wanandroid.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


object WanAndroidNetwork {
    private const val WAN_ANDROID = "https://www.wanandroid.com/"

    private val retrofit: Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(WAN_ANDROID)
            .build()

    private val articleService = retrofit.create(ArticleInterface::class.java)
    private val bannerService = retrofit.create(BannerInterface::class.java)

    suspend fun fetchArticles(id: Int): List<ArticleModel>
    = withContext(Dispatchers.Default){
        /*有可能会在这里进行Json解析，所以放到不同的线程上*/
        delay(1000)
        articleService.fetchArticles(id).data.datas
    }

    suspend fun fetchBanner(): List<BannerModel>
    = withContext(Dispatchers.Default) {
        delay(500)
        bannerService.fetchBanner().data
    }
}
