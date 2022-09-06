package name.zzhxufeng.wanandroid.viewmodel.drawer

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import name.zzhxufeng.wanandroid.data.network.START_PAGE
import name.zzhxufeng.wanandroid.data.network.WAN_SUCCESS_CODE
import name.zzhxufeng.wanandroid.data.repository.DrawerRepository
import name.zzhxufeng.wanandroid.event.drawer.ShareEvent
import name.zzhxufeng.wanandroid.state.drawer.ShareUiState
import name.zzhxufeng.wanandroid.viewmodel.BaseViewModel

class ShareViewModel: BaseViewModel() {
    val uiState = MutableStateFlow(ShareUiState())

    init {
        refreshMySharing()
    }

    fun handleEvent(event: ShareEvent) {
        when (event) {
            is ShareEvent.LoadMore -> { loadMore() }
            is ShareEvent.AddBookmark -> {}
        }
    }

    private fun refreshMySharing() = launchDataLoad{
        val response = DrawerRepository.mySharing(START_PAGE)
        if (response.errorCode == WAN_SUCCESS_CODE) {
            uiState.update { it.copy(
                mySharing = response.data.sharingArticles.datas,
                nextPage = nextPage(
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