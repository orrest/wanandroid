package name.zzhxufeng.wanandroid.viewmodel.drawer

import kotlinx.coroutines.flow.MutableStateFlow
import name.zzhxufeng.wanandroid.data.DrawerRepository
import name.zzhxufeng.wanandroid.data.network.SUCCESS_CODE
import name.zzhxufeng.wanandroid.utils.LoginManager
import name.zzhxufeng.wanandroid.viewmodel.event.DrawerEvent
import name.zzhxufeng.wanandroid.viewmodel.state.AuthenticationMode
import name.zzhxufeng.wanandroid.viewmodel.state.DrawerItem
import name.zzhxufeng.wanandroid.viewmodel.state.LoginUiState
import name.zzhxufeng.wanandroid.viewmodel.BaseViewModel

class LoginViewModel: BaseViewModel() {
    val uiState = MutableStateFlow(LoginUiState())

    init {
        uiState.value = uiState.value.copy(
            authenticationMode = if (LoginManager.isLogin()) {
                AuthenticationMode.LOGGED_IN
            } else {
                AuthenticationMode.SIGN_IN
            }
        )
    }

    fun handleEvent(event: DrawerEvent) {
        when (event) {
            is DrawerEvent.OpenDrawerItem -> openDrawerItem(event.drawerItem)
            is DrawerEvent.AccountNameChanged -> updateName(event.name)
            is DrawerEvent.PasswordChanged -> updatePassword(event.pwd)
            is DrawerEvent.RepasswordChanged -> updateRepassword(event.repwd)

            DrawerEvent.Authenticate -> login()
            DrawerEvent.Register -> register()
            DrawerEvent.ToggleAuthenticationMode -> toggleDrawerMode()
            DrawerEvent.ErrorDismissed -> dismissError()
        }
    }

    private fun updateRepassword(pwd: String) {
        uiState.value = uiState.value.copy(repassword = pwd)
    }

    private fun openDrawerItem(drawerItem: DrawerItem) {
        when (drawerItem) {
            /*TODO open drawer item*/
            DrawerItem.LOYALTY      -> {}
            DrawerItem.BOOKMARKS    -> {}
            DrawerItem.SHARE        -> {}
            DrawerItem.TODO         -> {}
            DrawerItem.DARK_MODE    -> {}
            DrawerItem.SETTINGS     -> {}
            DrawerItem.LOGOUT       -> {}
        }
    }

    private fun register() = launchDataLoad {
        if (uiState.value.inputName == ""
            || uiState.value.password == ""
            || uiState.value.repassword == "") {
            errorMsg.value = "不能为空！"
        } else {
            val response = DrawerRepository.register(
                uiState.value.inputName,
                uiState.value.password,
                uiState.value.repassword,
            )
            uiState.value = uiState.value.copy(
                authenticationMode = AuthenticationMode.SIGN_IN
            )
            errorMsg.value = response.errorMsg
        }
    }

    private fun login() = launchDataLoad {
        if (uiState.value.password == "" || uiState.value.inputName == "") {
            errorMsg.value = "用户名或密码为空."
        } else {
            val response = DrawerRepository.login(
                name = uiState.value.inputName,
                pwd = uiState.value.password
            )
            if (response.errorCode == SUCCESS_CODE) {
                uiState.value = uiState.value.copy(
                    authenticationMode = AuthenticationMode.LOGGED_IN
                )
                val response = DrawerRepository.userInfo()

            }
            errorMsg.value = response.errorMsg
        }
    }

    private fun exit() {
        /*in login manager*/
        LoginManager.clearCookies()
    }

    private fun updateName(name: String) {
        uiState.value = uiState.value.copy(inputName = name)
    }


    private fun updatePassword(password: String) {
        uiState.value = uiState.value.copy(password = password)
    }

    private fun toggleDrawerMode() {
        val currentMode = uiState.value.authenticationMode
        val newMode = if (currentMode == AuthenticationMode.SIGN_IN) {
            AuthenticationMode.SIGN_UP
        } else AuthenticationMode.SIGN_IN
        uiState.value = uiState.value.copy(authenticationMode = newMode)
    }
}