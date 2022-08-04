package name.zzhxufeng.wanandroid.viewmodel.drawer

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import name.zzhxufeng.wanandroid.data.DrawerRepository
import name.zzhxufeng.wanandroid.data.network.SUCCESS_CODE
import name.zzhxufeng.wanandroid.viewmodel.BaseViewModel
import name.zzhxufeng.wanandroid.viewmodel.event.DrawerEvent
import name.zzhxufeng.wanandroid.viewmodel.state.DrawerItemUiState

class DrawerItemViewModel: BaseViewModel() {
    val uiState = MutableStateFlow(DrawerItemUiState())

    init {
        getUserInfo()
    }

    fun handleEvent(event: DrawerEvent) {
    }

    private fun getUserInfo() = launchDataLoad {
        val response = DrawerRepository.userInfo()
        if (response.errorCode == SUCCESS_CODE) {
            uiState.value = uiState.value.copy(
                userName = response.data.userInfo.nickname,
                level = response.data.coinInfo.level.toString(),
                rank = response.data.coinInfo.rank,
                coinCount = response.data.coinInfo.coinCount,
                userId = response.data.userInfo.id,
                collectArticleCount = response.data.collectArticleInfo.count,
                collectArticleIds = response.data.userInfo.collectIds,
            )
        } else {
            errorMsg.value = response.errorMsg
        }
    }
}
