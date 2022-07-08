package name.zzhxufeng.wanandroid.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import name.zzhxufeng.wanandroid.pagingsource.ArticleSource
import name.zzhxufeng.wanandroid.pagingsource.PostsSource
import name.zzhxufeng.wanandroid.pagingsource.ProjectsSource
import name.zzhxufeng.wanandroid.repository.*
import name.zzhxufeng.wanandroid.screens.WanScreen
import name.zzhxufeng.wanandroid.utils.DEFAULT_PAGING_SIZE
import name.zzhxufeng.wanandroid.utils.MAX_PAGING_SIZE

class MainViewModel : BaseViewModel() {
    val selectedScreen = mutableStateOf<WanScreen>(WanScreen.Home)

    /*bottom bar 1*/
    val articleFlow = Pager(
        config = PagingConfig(
            pageSize = DEFAULT_PAGING_SIZE,
            maxSize = MAX_PAGING_SIZE
        ),
        pagingSourceFactory = { ArticleSource() }
    ).flow.cachedIn(viewModelScope)

    val banners = mutableStateListOf<BannerModel>()
    private fun refreshBanner() = launchDataLoad {
        banners.addAll(BannerRepository.refreshBanner())
    }

    /*bottom bar 2*/
    val postsFlow = Pager(
        config = PagingConfig(pageSize = DEFAULT_PAGING_SIZE),
        pagingSourceFactory = { PostsSource() }
    ).flow.cachedIn(viewModelScope)

    /*bottom bar 4*/
    val projectsName = mutableStateListOf<ProjectNameModel>()
    val projectFlowMap = mutableMapOf<Int, Flow<PagingData<ProjectModel>>>()

    /*
    * TODO 这些数据都是基于网络的，几个问题
    *  1. 本地缓存（数据库 或者 OkHttp自带的http缓存功能（但这个是不是需要server端的头字段配合？））
    *  2. 如果网络裂开，那么UI该显示刷新，那么刷新的时候调用什么？
    *   flow 的主动触发更新。
    * */
    private fun refreshProjectColumnName() {
        val asyncRes = viewModelScope.async { ProjectsRepository.refreshProjectColumnName() }

        viewModelScope.launch {
            projectsName.addAll(asyncRes.await())
            projectsName.forEach {
                projectFlowMap.put(
                    key = it.id,
                    value = Pager(
                        config = PagingConfig(pageSize = DEFAULT_PAGING_SIZE, maxSize = MAX_PAGING_SIZE),
                        pagingSourceFactory = { ProjectsSource(it.id) })
                        .flow.cachedIn(viewModelScope)
                )
            }
        }
    }

    init {
        refreshBanner()
        refreshProjectColumnName()
    }
}