package name.zzhxufeng.wanandroid.pagingsource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import name.zzhxufeng.wanandroid.repository.ProjectModel
import name.zzhxufeng.wanandroid.repository.ProjectsRepository

class ProjectsSource(private val cid: Int): PagingSource<Int, ProjectModel>() {
    companion object {
        const val START_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, ProjectModel>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProjectModel> {
        Log.d("ProjectsSource", "load more... ${params.key}")
        return try {
            val nextPage = params.key ?: START_INDEX
            val response = ProjectsRepository.refreshProjects(nextPage, cid)

            LoadResult.Page(
                data = response,
                prevKey = if (nextPage == START_INDEX) null else nextPage - 1,
                nextKey = if (response.isEmpty()) null else nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}