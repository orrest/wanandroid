package name.zzhxufeng.wanandroid.viewmodel

import androidx.compose.foundation.lazy.LazyListState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import name.zzhxufeng.wanandroid.data.network.START_PAGE_OLD_API
import name.zzhxufeng.wanandroid.data.network.WAN_SUCCESS_CODE
import name.zzhxufeng.wanandroid.data.repository.HomeRepository
import name.zzhxufeng.wanandroid.ui.screens.WanScreen
import name.zzhxufeng.wanandroid.event.MainContainerEvent
import name.zzhxufeng.wanandroid.state.MainContainerUiState

class MainContainerViewModel : BaseViewModel() {

    val uiState = MutableStateFlow(MainContainerUiState())

    init {
        refreshHome()
    }

    fun handleEvent(event: MainContainerEvent) {
        when (event) {
            is MainContainerEvent.ChangeScreen -> { changeScreen(event.screen) }
            is MainContainerEvent.HomeEvent.UpdateListState -> { updateListState(event.listState) }
            MainContainerEvent.HomeEvent.LoadMoreArticles -> { loadMoreArticles() }
            MainContainerEvent.HomeEvent.AutoChangeBanner -> { autoChangeBanner() }
        }
    }

    private fun autoChangeBanner() = launchDataLoad{
        val size = uiState.value.homeUiState.banners.size
        val index = uiState.value.homeUiState.currentBanner
        index?.let {
            if (index+1 >= size) {
                uiState.update { it.copy(
                    homeUiState = it.homeUiState.copy(currentBanner = 0)
                ) }
            } else {
                uiState.update { it.copy(
                    homeUiState = it.homeUiState.copy(currentBanner = index+1)
                ) }
            }
        }
    }

    private fun loadMoreArticles() = launchDataLoad{
        val nextPage = uiState.value.homeUiState.nextPage
        if (nextPage != null) {
            val response = HomeRepository.fetchArticles(nextPage)
            if (response.errorCode == WAN_SUCCESS_CODE) {
                uiState.update { it.copy(
                    homeUiState = it.homeUiState.copy(
                        articles = it.homeUiState.articles.toMutableList().apply {
                            addAll(response.data.datas)
                        }.toList(),
                        nextPage = nextPage(
                            START_PAGE_OLD_API,
                            response.data.curPage,
                            response.data.pageCount
                        ),
                    )
                ) }
            } else {
                errorMsg.value = response.errorMsg
            }
        } else {
            errorMsg.value = "没有更多数据"
        }
    }

    private fun updateListState(
        listState: LazyListState
    ) {
        uiState.update { it.copy(
            homeUiState = it.homeUiState.copy(
                articleListState = listState
            )
        ) }
    }

    private fun changeScreen(
        screen: WanScreen
    ) {
        uiState.update { it.copy(
            currentScreen = screen
        ) }
    }

    private fun refreshHome() {
        refreshHomeArticles()
        refreshHomeBanners()
    }

    private fun refreshHomeArticles() = launchDataLoad{
        val response = HomeRepository.fetchArticles(START_PAGE_OLD_API)
        if (response.errorCode == WAN_SUCCESS_CODE) {
            uiState.update { it.copy(
                homeUiState = it.homeUiState.copy(
                    articleListState = LazyListState(),
                    articles = response.data.datas,
                    nextPage = nextPage(
                        START_PAGE_OLD_API,
                        response.data.curPage,
                        response.data.pageCount
                    )
                )
            ) }
        } else {
            errorMsg.value = response.errorMsg
        }
    }

    private fun refreshHomeBanners() = launchDataLoad {
        val response = HomeRepository.refreshBanner()
        if (response.errorCode == WAN_SUCCESS_CODE) {
            uiState.update { it.copy(
                homeUiState = it.homeUiState.copy(
                    banners = response.data,
                    currentBanner = 0
                )
            ) }
        } else {
            errorMsg.value = response.errorMsg
        }
    }
}