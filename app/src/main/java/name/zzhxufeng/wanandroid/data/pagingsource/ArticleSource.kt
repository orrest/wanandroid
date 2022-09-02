package name.zzhxufeng.wanandroid.data.pagingsource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import name.zzhxufeng.wanandroid.data.model.ArticleModel
import name.zzhxufeng.wanandroid.data.repository.HomeRepository

class ArticleSource: PagingSource<Int, ArticleModel>() {
    override fun getRefreshKey(state: PagingState<Int, ArticleModel>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleModel> {
        Log.d("ArticleSource", "load more... ${params.key}")
        /*TODO max page count*/
        return try {
            val nextPage = params.key ?: 0
            val response = HomeRepository.refreshArticles(nextPage).data.datas

            LoadResult.Page(
                data = response,
                prevKey = if (nextPage == 0) null else nextPage - 1,
                nextKey = if (response.isEmpty()) null else nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}