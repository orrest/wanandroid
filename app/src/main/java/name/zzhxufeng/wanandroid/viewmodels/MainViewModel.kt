package name.zzhxufeng.wanandroid.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import name.zzhxufeng.wanandroid.pagesource.ArticleSource
import name.zzhxufeng.wanandroid.pagesource.PostsSource
import name.zzhxufeng.wanandroid.repository.*
import name.zzhxufeng.wanandroid.screens.WanScreen

class MainViewModel : BaseViewModel() {
/*ui state*/
    val selectedScreen = mutableStateOf<WanScreen>(WanScreen.Home)

/*data*/
    /*paging flow*/
    val articleFlow = Pager(
        config = PagingConfig(pageSize = 20 /*这个要和服务器返回的每页数据量对应着才行...*/),
        pagingSourceFactory = { ArticleSource() }
    ).flow.cachedIn(viewModelScope)

    val postsFlow = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { PostsSource() }
    ).flow.cachedIn(viewModelScope)

    /*banner*/
    /*Maybe there's no need to paging this.*/
    val banners = mutableStateListOf<BannerModel>()

    private fun refreshBanner() = launchDataLoad {
        banners.addAll(BannerRepository.refreshBanner())
    }

    init {
        /*refresh the main page*/
        refreshBanner()
    }
}