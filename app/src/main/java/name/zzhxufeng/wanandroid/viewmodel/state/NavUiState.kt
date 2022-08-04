package name.zzhxufeng.wanandroid.viewmodel.state

import name.zzhxufeng.wanandroid.data.model.NaviData
import name.zzhxufeng.wanandroid.data.model.WanResponse


data class NavUiState(
    val naviResponse: WanResponse<List<NaviData>>? = null,
    val isRefreshing: Boolean = false,
)