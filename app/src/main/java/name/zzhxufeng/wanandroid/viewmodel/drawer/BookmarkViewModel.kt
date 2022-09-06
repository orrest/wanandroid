package name.zzhxufeng.wanandroid.viewmodel.drawer

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import name.zzhxufeng.wanandroid.data.network.START_PAGE_OLD_API
import name.zzhxufeng.wanandroid.data.network.WAN_SUCCESS_CODE
import name.zzhxufeng.wanandroid.data.repository.DrawerRepository
import name.zzhxufeng.wanandroid.event.BookmarkEvent
import name.zzhxufeng.wanandroid.state.drawer.BookmarkUiState
import name.zzhxufeng.wanandroid.viewmodel.BaseViewModel

class BookmarkViewModel: BaseViewModel() {
    val uiState = MutableStateFlow(BookmarkUiState())

    init {
        refreshBookmarks()
    }

    fun handleEvent(event: BookmarkEvent) {
        when(event) {
            BookmarkEvent.LoadMore -> { loadMore() }
        }
    }

    private fun refreshBookmarks() = launchDataLoad {
        val response = DrawerRepository.bookmarks(START_PAGE_OLD_API)
        if (response.errorCode == WAN_SUCCESS_CODE) {
            uiState.update {
                it.copy(
                    bookmarks = response.data.datas,
                    nextPage = nextPage(response.data.curPage, response.data.pageCount)
                )
            }
        } else {
            errorMsg.value = response.errorMsg
        }
    }

    private fun loadMore() = launchDataLoad {
        val nextPage = uiState.value.nextPage
        if (nextPage != null) {
            val response = DrawerRepository.bookmarks(nextPage)
            if (response.errorCode == WAN_SUCCESS_CODE) {
                uiState.update {
                    it.copy(
                        bookmarks = it.bookmarks.toMutableList().apply {
                            addAll(response.data.datas)
                        }.toList(),
                        nextPage = nextPage(
                            response.data.curPage, response.data.pageCount
                        ),
                    )
                }
            } else {
                errorMsg.value = response.errorMsg
            }
        } else {
            errorMsg.value = "没有更多数据"
        }
    }
}