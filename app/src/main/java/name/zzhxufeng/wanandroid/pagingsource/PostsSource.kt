package name.zzhxufeng.wanandroid.pagingsource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import name.zzhxufeng.wanandroid.repository.ArticleModel
import name.zzhxufeng.wanandroid.repository.PostsRepository

class PostsSource: PagingSource<Int, ArticleModel>() {
    override fun getRefreshKey(state: PagingState<Int, ArticleModel>): Int? {
        Log.d("PostsSource", "When this will be called?")
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleModel> {
        Log.d("PostsSource", "load more... ${params.key}")
        /*TODO max page count*/
        return try {
            val nextPage = params.key ?: 0
            val response = PostsRepository.refreshPosts(nextPage)

            LoadResult.Page(
                data = response,
                prevKey = if (nextPage == 0) null else nextPage - 1,
                nextKey = nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}