package name.zzhxufeng.wanandroid.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import name.zzhxufeng.wanandroid.repository.LoginManager
import name.zzhxufeng.wanandroid.ui.event.DrawerEvent
import name.zzhxufeng.wanandroid.ui.state.AuthenticationMode
import name.zzhxufeng.wanandroid.ui.state.DrawerUiState

class DrawerViewModel: BaseViewModel() {
    val drawerUiState = MutableStateFlow(DrawerUiState())

    fun handleEvent(event: DrawerEvent) {
        when (event) {

        }
    }

    /*
    * 先去实现一个，然后再重构进行抽象
    * */
    private fun bookmark() {

    }

    private fun cancelBookmark() {

    }

    private fun points() {

    }

    private fun authenticate() {
        drawerUiState.value = drawerUiState.value.copy(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            /*switch between thread*/
            withContext(Dispatchers.Main) {
                drawerUiState.value = drawerUiState.value.copy(
                    isLoading = false,
                    error = "TODO: Network error message."
                )
            }
        }
    }

    private fun exit() {
        /*in login manager*/
    }

    private fun userInfo() {

    }

    private fun toggleDrawerMode() {
        val currentMode = drawerUiState.value.authenticationMode
        val newMode = if (currentMode == AuthenticationMode.SIGN_IN) {
            AuthenticationMode.SIGN_UP
        } else AuthenticationMode.SIGN_IN
        drawerUiState.value =
            drawerUiState.value.copy(authenticationMode = newMode)
    }
}