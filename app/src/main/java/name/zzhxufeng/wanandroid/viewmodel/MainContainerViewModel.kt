package name.zzhxufeng.wanandroid.viewmodel

import androidx.compose.foundation.lazy.LazyListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import name.zzhxufeng.wanandroid.data.network.WAN_SUCCESS_CODE
import name.zzhxufeng.wanandroid.data.repository.HomeRepository
import name.zzhxufeng.wanandroid.ui.screens.WanScreen
import name.zzhxufeng.wanandroid.viewmodel.event.MainContainerEvent
import name.zzhxufeng.wanandroid.viewmodel.state.MainContainerUiState

class MainContainerViewModel : BaseViewModel() {

    val uiState = MutableStateFlow(MainContainerUiState())

    init {
        refreshHome()
    }

    fun handleEvent(event: MainContainerEvent) {
        when (event) {
            is MainContainerEvent.ChangeScreen -> { changeScreen(event.screen) }
            is MainContainerEvent.HomeEvent.UpdateListState -> { updateListState(event.listState) }
            else -> {}
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

    /*bottom bar 1*/
    private fun refreshHomeArticles() = launchDataLoad{
        run condition@ {
            // The last time next page is this time current page.
            val curPage = uiState.value.homeUiState.nextPage
            val maxPage = uiState.value.homeUiState.maxPage
            if (curPage >= maxPage) {
                return@condition
            }
            val response = HomeRepository.refreshArticles(curPage)
            if (response.errorCode == WAN_SUCCESS_CODE) {
                uiState.update { it.copy(
                    homeUiState = it.homeUiState.copy(
                        articles = it.homeUiState.articles.toMutableList().apply {
                            addAll(response.data.datas)
                        }.toList(),
                        maxPage = response.data.pageCount,
                        nextPage = response.data.curPage,
                    )
                ) }
            } else {
                errorMsg.value = response.errorMsg
            }
        }
    }

    private fun refreshHomeBanners() = launchDataLoad {
        val response = HomeRepository.refreshBanner()
        if (response.errorCode == WAN_SUCCESS_CODE) {
            uiState.update { it.copy(
                homeUiState = it.homeUiState.copy(
                    banners = response.data
                )
            ) }
        } else {
            errorMsg.value = response.errorMsg
        }
    }


//    /*bottom bar 3*/
//    val projectsName = mutableStateListOf<ProjectNameModel>()
//    val projectFlowMap = mutableMapOf<Int, Flow<PagingData<ProjectModel>>>()
//
//    private fun refreshProjectColumnName() {
//        val asyncRes = viewModelScope.async { ProjectsRepository.refreshProjectColumnName() }
//
//        viewModelScope.launch {
//            projectsName.addAll(asyncRes.await())
//            projectsName.forEach {
//                projectFlowMap.put(
//                    key = it.id,
//                    value = Pager(
//                        config = PagingConfig(pageSize = DEFAULT_PAGING_SIZE, maxSize = MAX_PAGING_SIZE),
//                        pagingSourceFactory = { ProjectsSource(it.id) })
//                        .flow.cachedIn(viewModelScope)
//                )
//            }
//        }
//    }
}