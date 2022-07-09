package name.zzhxufeng.wanandroid.repository

import name.zzhxufeng.wanandroid.repository.model.ArticleData
import name.zzhxufeng.wanandroid.repository.model.ArticleModel
import name.zzhxufeng.wanandroid.repository.model.WanResponse
import retrofit2.http.GET
import retrofit2.http.Path


object PostsRepository{
    suspend fun refreshPosts(pageId: Int): List<ArticleModel> {
        return WanAndroidNetwork.fetchPosts(pageId)
    }
}

interface PostsInterface {
    @GET("user_article/list/{id}/json")
    suspend fun fetchPosts(@Path("id") id: Int): WanResponse<ArticleData>
}

/**
 * Thrown when there was a error fetching a new article
 *
 * @property message user ready error message
 * @property cause the original cause of this exception
 */
class PostsRefreshError(message: String, cause: Throwable) : Throwable(message, cause)
