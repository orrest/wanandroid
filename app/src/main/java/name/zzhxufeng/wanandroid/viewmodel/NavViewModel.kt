package name.zzhxufeng.wanandroid.viewmodel

import androidx.compose.foundation.lazy.LazyListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import name.zzhxufeng.wanandroid.data.network.WAN_SUCCESS_CODE
import name.zzhxufeng.wanandroid.data.repository.NaviRepository
import name.zzhxufeng.wanandroid.state.NavUiState

class NavViewModel: BaseViewModel() {
    val uiState = MutableStateFlow(NavUiState())

    private fun refreshNavi() = launchDataLoad {
        val response = NaviRepository.refreshNavi()
        if (response.errorCode == WAN_SUCCESS_CODE) {
            uiState.update { it.copy(
                naviList = response.data
            ) }
        } else {
            errorMsg.value = response.errorMsg
        }
    }

    init {
        refreshNavi()
    }
}