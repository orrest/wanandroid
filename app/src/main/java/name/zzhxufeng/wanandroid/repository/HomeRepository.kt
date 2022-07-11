package name.zzhxufeng.wanandroid.repository

import name.zzhxufeng.wanandroid.repository.model.ArticleData
import name.zzhxufeng.wanandroid.repository.model.ArticleModel
import name.zzhxufeng.wanandroid.repository.model.WanResponse
import retrofit2.http.GET
import retrofit2.http.Path

object HomeRepository {
    private val homeService = WanAndroidNetwork.retrofit.create(HomeInterface::class.java)

    suspend fun refreshBanner(): List<BannerModel> {
        return homeService.fetchBanner().data
    }
    suspend fun refreshArticles(pageId: Int): List<ArticleModel> {
        return homeService.fetchArticles(pageId).data.datas
    }
}

interface HomeInterface {
    @GET("article/list/{id}/json")
    suspend fun fetchArticles(@Path("id") id: Int): WanResponse<ArticleData>

    @GET("banner/json")
    suspend fun fetchBanner(): Banner
}

/**
 * Thrown when there was a error fetching a new article
 *
 * @property message user ready error message
 * @property cause the original cause of this exception
 */
class ArticleRefreshError(message: String, cause: Throwable) : Throwable(message, cause)

data class Banner(
    val data: List<BannerModel>,
    val errorCode: Int,
    val errorMsg: String,
)

data class BannerModel(
    val desc: String?,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    /*Banner链接的文章*/
    val url: String
)