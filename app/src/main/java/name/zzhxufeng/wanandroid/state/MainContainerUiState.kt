package name.zzhxufeng.wanandroid.state

import androidx.compose.foundation.lazy.LazyListState
import name.zzhxufeng.wanandroid.data.model.ArticleModel
import name.zzhxufeng.wanandroid.data.model.BannerModel
import name.zzhxufeng.wanandroid.data.network.DEFAULT_PAGING_SIZE
import name.zzhxufeng.wanandroid.data.network.START_PAGE_OLD_API
import name.zzhxufeng.wanandroid.ui.screens.WanScreen

data class MainContainerUiState(
    val currentScreen: WanScreen = WanScreen.Home,
    val homeUiState: HomeUiState = HomeUiState(),
)

data class HomeUiState(
    val articleListState: LazyListState = LazyListState(),
    val articles: List<ArticleModel> = emptyList(),
    val nextPage: Int? = START_PAGE_OLD_API,
    val PER_PAGE: Int = DEFAULT_PAGING_SIZE,
    val banners: List<BannerModel> = emptyList(),
    val currentBanner: Int? = null
) {
    fun currentBanner(): BannerModel?{
        return if(currentBanner != null) banners[currentBanner] else null
    }
}