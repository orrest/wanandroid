package name.zzhxufeng.wanandroid.ui.state

import name.zzhxufeng.wanandroid.repository.model.NaviData
import name.zzhxufeng.wanandroid.repository.model.WanResponse


data class NavUiState(
    val naviResponse: WanResponse<List<NaviData>>? = null,
    val isRefreshing: Boolean = false,
)