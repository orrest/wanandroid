package name.zzhxufeng.wanandroid.repository

import name.zzhxufeng.wanandroid.repository.model.ArticleData
import name.zzhxufeng.wanandroid.repository.model.ArticleModel
import name.zzhxufeng.wanandroid.repository.model.WanResponse
import retrofit2.http.GET
import retrofit2.http.Path


object ArticleRepository{
    suspend fun refreshArticles(pageId: Int): List<ArticleModel> {
        return WanAndroidNetwork.fetchArticles(pageId)
    }
}

interface ArticleInterface {
    @GET("article/list/{id}/json")
    suspend fun fetchArticles(@Path("id") id: Int): WanResponse<ArticleData>
}

/**
 * Thrown when there was a error fetching a new article
 *
 * @property message user ready error message
 * @property cause the original cause of this exception
 */
class ArticleRefreshError(message: String, cause: Throwable) : Throwable(message, cause)
