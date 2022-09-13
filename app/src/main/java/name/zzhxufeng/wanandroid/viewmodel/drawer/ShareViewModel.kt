package name.zzhxufeng.wanandroid.viewmodel.drawer

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import name.zzhxufeng.wanandroid.data.network.START_PAGE
import name.zzhxufeng.wanandroid.data.network.WAN_SUCCESS_CODE
import name.zzhxufeng.wanandroid.data.repository.DrawerRepository
import name.zzhxufeng.wanandroid.event.drawer.SharingEvent
import name.zzhxufeng.wanandroid.state.drawer.ShareUiState
import name.zzhxufeng.wanandroid.viewmodel.BaseViewModel

class ShareViewModel: BaseViewModel() {
    val uiState = MutableStateFlow(ShareUiState())

    init {
        refreshMySharing()
    }

    fun handleEvent(event: SharingEvent) {
        when (event) {
            is SharingEvent.LoadMore -> { loadMore() }
            is SharingEvent.AddBookmark -> { /*TODO*/ }
        }
    }

    private fun refreshMySharing() = launchDataLoad{
        val response = DrawerRepository.mySharing(START_PAGE)
        if (response.errorCode == WAN_SUCCESS_CODE) {
            uiState.update { it.copy(
                mySharing = response.data.sharingArticles.datas,
                nextPage = nextPage(
                    START_PAGE,
                    response.data.sharingArticles.curPage,
                    response.data.sharingArticles.pageCount
                )
            ) }
        } else {
            errorMsg.value = response.errorMsg
        }
    }

    private fun loadMore() = launchDataLoad{
        val nextPage = uiState.value.nextPage
        if (nextPage != null) {
            val response = DrawerRepository.mySharing(nextPage)
            if (response.errorCode == WAN_SUCCESS_CODE) {
                uiState.update { it.copy(
                    mySharing = it.mySharing.toMutableList().apply {
                        addAll(response.data.sharingArticles.datas)
                    }.toList(),
                    nextPage = nextPage(
                        START_PAGE,
                        response.data.sharingArticles.curPage,
                        response.data.sharingArticles.pageCount
                    )
                ) }
            } else {
                errorMsg.value = response.errorMsg
            }
        } else {
            errorMsg.value = "没有更多数据"
        }
    }
}