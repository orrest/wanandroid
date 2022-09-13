package name.zzhxufeng.wanandroid.data.repository

import name.zzhxufeng.wanandroid.data.model.*
import name.zzhxufeng.wanandroid.data.network.WanAndroidNetwork
import retrofit2.http.*

object HomeRepository {
    private val homeService = WanAndroidNetwork.retrofit.create(HomeInterface::class.java)

    suspend fun refreshBanner(): WanResponse<List<BannerModel>> {
        return homeService.fetchBanner()
    }

    suspend fun fetchArticles(pageId: Int): WanResponse<ArticleData> {
        return homeService.fetchArticles(pageId)
    }

    suspend fun fetchHotHistory(): WanResponse<List<HotHistoryModel>>
    = homeService.fetchHotHistory()

    suspend fun search(page: Int, key: String): WanResponse<SearchResultData>
    = homeService.search(page, key)
}

interface HomeInterface {
    @GET("article/list/{id}/json")
    suspend fun fetchArticles(@Path("id") id: Int): WanResponse<ArticleData>

    @GET("banner/json")
    suspend fun fetchBanner(): WanResponse<List<BannerModel>>

    @GET("/hotkey/json")
    suspend fun fetchHotHistory(): WanResponse<List<HotHistoryModel>>

    @FormUrlEncoded
    @POST("/article/query/{page}/json")
    suspend fun search(
        @Path("page") page: Int,
        @Field("k") key: String,
    ): WanResponse<SearchResultData>
}
