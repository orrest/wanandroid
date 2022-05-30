package name.zzhxufeng.wanandroid.repository


object ArticleRepository{
    suspend fun refreshArticles(pageId: Int): List<ArticleModel> {
        return WanAndroidNetwork.fetchArticles(pageId)
    }
}

/**
 * Thrown when there was a error fetching a new article
 *
 * @property message user ready error message
 * @property cause the original cause of this exception
 */
class ArticleRefreshError(message: String, cause: Throwable) : Throwable(message, cause)