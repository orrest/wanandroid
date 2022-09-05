package name.zzhxufeng.wanandroid.viewmodel.drawer

import androidx.compose.foundation.lazy.LazyListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import name.zzhxufeng.wanandroid.data.network.START_PAGE_OLD_API
import name.zzhxufeng.wanandroid.data.network.WAN_SUCCESS_CODE
import name.zzhxufeng.wanandroid.data.repository.DrawerRepository
import name.zzhxufeng.wanandroid.event.drawer.CheckInEvent
import name.zzhxufeng.wanandroid.state.CheckInUiState
import name.zzhxufeng.wanandroid.viewmodel.BaseViewModel

class CheckInRecordViewModel: BaseViewModel() {
    val uiState = MutableStateFlow(CheckInUiState())

    init {
        refreshCheckInList()
    }

    fun handleEvent(event: CheckInEvent) {
        when (event) {
            CheckInEvent.LoadMore -> { loadMoreCheckInList() }
        }
    }

    private fun refreshCheckInList() = launchDataLoad {
        val response = DrawerRepository.coinCheckInList(START_PAGE_OLD_API)
        if (response.errorCode == WAN_SUCCESS_CODE) {
            uiState.update { it.copy(
                listState = LazyListState(),
                checkInRecords = response.data.datas,
                maxPage = response.data.pageCount,
                nextPage = if (response.data.curPage + 1 >= response.data.pageCount)
                    null else response.data.curPage + 1,
            ) }
        } else {
            errorMsg.value = response.errorMsg
        }
    }

    private fun loadMoreCheckInList() = launchDataLoad {
        val nextPage = uiState.value.nextPage
        if (nextPage != null) {
            val response = DrawerRepository.coinCheckInList(nextPage)
            if (response.errorCode == WAN_SUCCESS_CODE) {
                uiState.update { it.copy(
                    checkInRecords = it.checkInRecords.toMutableList().apply {
                        addAll(response.data.datas)
                    }.toList(),
                    maxPage = response.data.pageCount,
                    nextPage = if (response.data.curPage + 1
                        >= response.data.pageCount) null
                    else response.data.curPage + 1,
                ) }
            } else {
                errorMsg.value = response.errorMsg
            }
        } else {
            errorMsg.value = "没有更多数据"
        }
    }
}