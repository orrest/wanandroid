package name.zzhxufeng.wanandroid.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import name.zzhxufeng.wanandroid.pagingsource.ArticleSource
import name.zzhxufeng.wanandroid.pagingsource.PostsSource
import name.zzhxufeng.wanandroid.repository.*
import name.zzhxufeng.wanandroid.screens.WanScreen
import name.zzhxufeng.wanandroid.utils.DEFAULT_PAGING_SIZE

class MainViewModel : BaseViewModel() {
    val selectedScreen = mutableStateOf<WanScreen>(WanScreen.Home)

    /*bottom bar 1*/
    val articleFlow = Pager(
        config = PagingConfig(pageSize = DEFAULT_PAGING_SIZE /*这个要和服务器返回的每页数据量对应着才行...*/),
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
    fun refreshProjectsName() = launchDataLoad {
        projectsName.addAll(ProjectsRepository.refreshProjectsName())
    }

    init {
        refreshBanner()
        refreshProjectsName()
    }
}