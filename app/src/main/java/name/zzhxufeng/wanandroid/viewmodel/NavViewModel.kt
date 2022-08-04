package name.zzhxufeng.wanandroid.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import name.zzhxufeng.wanandroid.data.NaviRepository
import name.zzhxufeng.wanandroid.viewmodel.state.NavUiState

class NavViewModel: BaseViewModel() {
    val uiState = MutableStateFlow(NavUiState())

    private fun refreshNavi() = launchDataLoad {
        uiState.value = uiState.value.copy(
            naviResponse = NaviRepository.refreshNavi()
        )
        tackleResponseError(uiState.value.naviResponse)
    }

    init {
        refreshNavi()
    }
}