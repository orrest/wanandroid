package name.zzhxufeng.wanandroid.data.repository

import name.zzhxufeng.wanandroid.data.model.ArticleData
import name.zzhxufeng.wanandroid.data.model.BannerModel
import name.zzhxufeng.wanandroid.data.model.WanResponse
import name.zzhxufeng.wanandroid.data.network.WanAndroidNetwork
import retrofit2.http.GET
import retrofit2.http.Path

object HomeRepository {
    private val homeService = WanAndroidNetwork.retrofit.create(HomeInterface::class.java)

    suspend fun refreshBanner(): WanResponse<List<BannerModel>> {
        return homeService.fetchBanner()
    }
    suspend fun refreshArticles(pageId: Int): WanResponse<ArticleData> {
        return homeService.fetchArticles(pageId)
    }
}

interface HomeInterface {
    @GET("article/list/{id}/json")
    suspend fun fetchArticles(@Path("id") id: Int): WanResponse<ArticleData>

    @GET("banner/json")
    suspend fun fetchBanner(): WanResponse<List<BannerModel>>
}
