package name.zzhxufeng.wanandroid.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import name.zzhxufeng.wanandroid.repository.NaviRepository
import name.zzhxufeng.wanandroid.ui.state.NavUiState

class NavViewModel: BaseViewModel() {
    val uiState = MutableStateFlow(NavUiState())

    private fun refreshNavi() = launchDataLoad {
        uiState.value = uiState.value.copy(isRefreshing = true)

        uiState.value = uiState.value.copy(
            naviResponse = NaviRepository.refreshNavi()
        )

        tackleResponseError(uiState.value.naviResponse)

        uiState.value = uiState.value.copy(isRefreshing = false)
    }

    init {
        refreshNavi()
    }
}