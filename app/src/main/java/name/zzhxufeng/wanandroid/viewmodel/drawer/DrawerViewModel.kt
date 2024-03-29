package name.zzhxufeng.wanandroid.viewmodel.drawer

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import name.zzhxufeng.wanandroid.data.network.WAN_SUCCESS_CODE
import name.zzhxufeng.wanandroid.data.repository.DrawerRepository
import name.zzhxufeng.wanandroid.event.drawer.DrawerEvent
import name.zzhxufeng.wanandroid.state.drawer.AuthenticationMode
import name.zzhxufeng.wanandroid.state.drawer.DrawerUiState
import name.zzhxufeng.wanandroid.utils.LoginManager
import name.zzhxufeng.wanandroid.viewmodel.BaseViewModel

class DrawerViewModel: BaseViewModel() {
    val uiState = MutableStateFlow(DrawerUiState())

    init {
        if (LoginManager.isLogin()) {
            getUserInfo()
            changeLoginState(AuthenticationMode.LOGGED_IN)
        } else {
            changeLoginState(AuthenticationMode.SIGN_IN)
        }
    }

    private fun changeLoginState(authenticationMode: AuthenticationMode) {
        uiState.update { it.copy(
            authenticationMode = authenticationMode
        ) }
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

    fun handleEvent(event: DrawerEvent) {
        when (event) {
            is DrawerEvent.AccountNameChanged -> updateName(event.name)
            is DrawerEvent.PasswordChanged -> updatePassword(event.pwd)
            is DrawerEvent.RepasswordChanged -> updateRepassword(event.repwd)

            DrawerEvent.Authenticate -> login()
            DrawerEvent.Register -> register()
            DrawerEvent.ToggleAuthenticationMode -> toggleDrawerMode()
            DrawerEvent.ErrorDismissed -> dismissError()
            DrawerEvent.ThemeDropdownMenu -> themeExpanded()
            DrawerEvent.Logout -> logout()
        }
    }

    private fun themeExpanded() {
        uiState.update { it.copy(
            themeDropdownExpanded = !uiState.value.themeDropdownExpanded
        ) }
    }

    private fun updateRepassword(pwd: String) {
        uiState.update { it.copy(
            loginUiState = it.loginUiState.copy(
                repassword = pwd
            )
        ) }
    }

    private fun register() = launchDataLoad {
        if (uiState.value.loginUiState.inputName == ""
            || uiState.value.loginUiState.password == ""
            || uiState.value.loginUiState.repassword == "") {
            errorMsg.value = "不能为空！"
        } else {
            val response = DrawerRepository.register(
                uiState.value.loginUiState.inputName,
                uiState.value.loginUiState.password,
                uiState.value.loginUiState.repassword,
            )
            uiState.value = uiState.value.copy(
                authenticationMode = AuthenticationMode.SIGN_IN
            )
            errorMsg.value = response.errorMsg
        }
    }

    private fun login() = launchDataLoad {
        if (uiState.value.loginUiState.password == "" || uiState.value.loginUiState.inputName == "") {
            errorMsg.value = "用户名或密码为空."
        } else {
            val response = DrawerRepository.login(
                name = uiState.value.loginUiState.inputName,
                pwd = uiState.value.loginUiState.password
            )
            if (response.errorCode == WAN_SUCCESS_CODE) {
                getUserInfo()
                changeLoginState(AuthenticationMode.LOGGED_IN)
            }
            errorMsg.value = response.errorMsg
        }
    }

    private fun logout() = launchDataLoad{
        val response = DrawerRepository.logout()
        if (response.errorCode == WAN_SUCCESS_CODE) {
            uiState.update { it.copy(
                userInfo = null
            )}
            changeLoginState(AuthenticationMode.SIGN_IN)
            errorMsg.value = "已退出登录"
        } else {
            errorMsg.value = response.errorMsg
        }
    }

    private fun updateName(name: String) {
        uiState.update { it.copy(
            loginUiState = it.loginUiState.copy(
                inputName = name
            )
        ) }
    }


    private fun updatePassword(password: String) {
        uiState.update { it.copy(
            loginUiState = it.loginUiState.copy(
                password = password
            )
        ) }
    }

    private fun toggleDrawerMode() {
        val currentMode = uiState.value.authenticationMode
        val newMode = if (currentMode == AuthenticationMode.SIGN_IN) {
            AuthenticationMode.SIGN_UP
        } else AuthenticationMode.SIGN_IN
        uiState.value = uiState.value.copy(authenticationMode = newMode)
    }
}