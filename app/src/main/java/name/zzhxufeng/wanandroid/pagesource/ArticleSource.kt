package name.zzhxufeng.wanandroid.pagesource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import name.zzhxufeng.wanandroid.repository.ArticleModel
import name.zzhxufeng.wanandroid.repository.ArticleRepository

class ArticleSource: PagingSource<Int, ArticleModel>() {
    override fun getRefreshKey(state: PagingState<Int, ArticleModel>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleModel> {
        Log.d("ArticleSource", "load more... ${params.key}")
        return try {
            val nextPage = params.key ?: 0
            val movieListResponse = ArticleRepository.refreshArticles(nextPage)

            LoadResult.Page(
                data = movieListResponse,
                prevKey = if (nextPage == 0) null else nextPage - 1,
                nextKey = nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}