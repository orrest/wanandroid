package name.zzhxufeng.wanandroid.viewmodel.state

import androidx.compose.foundation.lazy.LazyListState
import name.zzhxufeng.wanandroid.data.model.ArticleModel
import name.zzhxufeng.wanandroid.data.model.BannerModel
import name.zzhxufeng.wanandroid.data.network.DEFAULT_PAGING_SIZE
import name.zzhxufeng.wanandroid.data.network.START_PAGE
import name.zzhxufeng.wanandroid.ui.screens.WanScreen

data class MainContainerUiState(
    val currentScreen: WanScreen = WanScreen.Home,
    val homeUiState: HomeUiState = HomeUiState(),
)

data class HomeUiState(
    val articleListState: LazyListState = LazyListState(),
    val articles: List<ArticleModel> = emptyList(),
    val nextPage: Int = START_PAGE,
    val maxPage: Int = Int.MAX_VALUE,
    val PER_PAGE: Int = DEFAULT_PAGING_SIZE,
    val banners: List<BannerModel> = emptyList()
)