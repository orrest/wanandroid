package name.zzhxufeng.wanandroid.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import name.zzhxufeng.wanandroid.uistate.DrawerUiState

class DrawerViewModel: BaseViewModel() {
    val drawerUiState = MutableStateFlow(DrawerUiState())

    
}