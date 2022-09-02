package name.zzhxufeng.wanandroid.viewmodel.drawer

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import name.zzhxufeng.wanandroid.data.network.START_PAGE
import name.zzhxufeng.wanandroid.data.repository.DrawerRepository
import name.zzhxufeng.wanandroid.data.network.WAN_SUCCESS_CODE
import name.zzhxufeng.wanandroid.viewmodel.BaseViewModel
import name.zzhxufeng.wanandroid.viewmodel.event.DrawerEvent
import name.zzhxufeng.wanandroid.viewmodel.state.DrawerItem
import name.zzhxufeng.wanandroid.viewmodel.state.DrawerItemUiState

class DrawerItemViewModel: BaseViewModel() {
    val uiState = MutableStateFlow(DrawerItemUiState())

    init {
        getUserInfo()
    }

    fun handleEvent(event: DrawerEvent) {
        when (event){
            is DrawerEvent.OpenDrawerItem ->    openOrCloseDrawerItem(
                event.drawerItem,
                event.navBlock,
            )

            else -> {}
        }
    }

    private fun openOrCloseDrawerItem(
        drawerItem: DrawerItem,
        navBlock: () -> Unit,
    ) = launchDataLoad {
        if (uiState.value.drawerItemOpenState != null) {
            uiState.update { it.copy(drawerItemOpenState = null) }
        } else {
            uiState.update { it.copy(drawerItemOpenState = drawerItem) }
            when (drawerItem) {
                DrawerItem.COINS -> {
                    withContext(Dispatchers.Main) {
//                        navBlock()
                    }
                    getCoinList()
                }
                DrawerItem.BOOKMARKS -> {/*refresh bookmarks*/}
                DrawerItem.SHARE -> {/*refresh share*/}
                DrawerItem.TODO -> {/*open todo*/}
                DrawerItem.DARK_MODE -> {/*set theme locally*/}
                DrawerItem.SETTINGS -> {/*open settings locally*/}
                DrawerItem.LOGOUT -> {/*logout*/}
                else -> {}
            }
        }
    }

    private fun getUserInfo() = launchDataLoad {
        val response = DrawerRepository.userInfo()
        if (response.errorCode == WAN_SUCCESS_CODE) {
            uiState.update { it.copy(
                userInfo = response.data
            ) }
        } else {
            errorMsg.value = response.errorMsg
        }
    }

    private fun getCoinList() = launchDataLoad {
        val coinListData = uiState.value.coinListData
        val response = if (coinListData == null) {
            DrawerRepository.coinList(START_PAGE)
        } else {
            DrawerRepository.coinList(
                coinListData.curPage + 1
            )
        }
        if (response.errorCode == WAN_SUCCESS_CODE) {
            uiState.update { it.copy(
                coinListData = response.data
            ) }
        } else {
            errorMsg.value = response.errorMsg
        }
    }
}
