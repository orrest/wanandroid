package name.zzhxufeng.wanandroid.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import name.zzhxufeng.wanandroid.repository.DrawerRepository
import name.zzhxufeng.wanandroid.repository.LoginManager
import name.zzhxufeng.wanandroid.ui.event.DrawerEvent
import name.zzhxufeng.wanandroid.ui.state.AuthenticationMode
import name.zzhxufeng.wanandroid.ui.state.DrawerItem
import name.zzhxufeng.wanandroid.ui.state.DrawerUiState

class DrawerViewModel: BaseViewModel() {
    val drawerUiState = MutableStateFlow(DrawerUiState())

    fun handleEvent(event: DrawerEvent) {
        when (event) {
            is DrawerEvent.OpenDrawerItem -> openDrawerItem(event.drawerItem)
            is DrawerEvent.EmailChanged -> updateName(event.name)
            is DrawerEvent.PasswordChanged -> updatePassword(event.pwd)
            is DrawerEvent.RepasswordChanged -> updateRepassword(event.repwd)

            DrawerEvent.Authenticate -> authenticate()
            DrawerEvent.Register -> register()
            DrawerEvent.ToggleAuthenticationMode -> toggleDrawerMode()
            DrawerEvent.ErrorDismissed -> dismissError()
        }
    }

    private fun updateRepassword(pwd: String) {
        drawerUiState.value = drawerUiState.value.copy(repassword = pwd)
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
        setLoading(true)
        if (drawerUiState.value.name == ""
            || drawerUiState.value.password == ""
            || drawerUiState.value.repassword == "") {
            alertMessage("不能为空.")
        } else {
            val response = DrawerRepository.register(
                drawerUiState.value.name,
                drawerUiState.value.password,
                drawerUiState.value.repassword,
            )
            if (response.body() == null) {
                alertMessage("网络异常, 请重试.")
            } else if (response.body()!!.errorMsg != "") {
                alertMessage(response.body()!!.errorMsg)
            } else {
                setLoading(isLoading = false)
            }
        }
    }

    private fun authenticate() = launchDataLoad {
        withContext(Dispatchers.Main) { setLoading(isLoading = true) }

        if (drawerUiState.value.password == "" || drawerUiState.value.name == "") {
            withContext(Dispatchers.Main) { alertMessage("用户名或密码为空.") }
        } else {
            val response = DrawerRepository.login(
                name = drawerUiState.value.name,
                pwd = drawerUiState.value.password
            )
            withContext(Dispatchers.Main) {
                if (response.body() == null) {
                    alertMessage("网络异常, 请重试.")
                } else if (response.body()!!.errorMsg != "") {
                    alertMessage(response.body()!!.errorMsg)
                } else {
                    setLoading(isLoading = false)
                }
            }
        }
    }

    private fun alertMessage(error: String) {
        drawerUiState.value = drawerUiState.value.copy(
            isLoading = false,
            error = error
        )
    }

    private fun setLoading(isLoading: Boolean) {
        drawerUiState.value = drawerUiState.value.copy(isLoading = isLoading)
    }

    private fun exit() {
        /*in login manager*/
        LoginManager.clearCookies()
    }

    private fun updateName(name: String) {
        drawerUiState.value = drawerUiState.value.copy(name = name)
    }


    private fun updatePassword(password: String) {
        drawerUiState.value = drawerUiState.value.copy(password = password)
    }

    private fun toggleDrawerMode() {
        val currentMode = drawerUiState.value.authenticationMode
        val newMode = if (currentMode == AuthenticationMode.SIGN_IN) {
            AuthenticationMode.SIGN_UP
        } else AuthenticationMode.SIGN_IN
        drawerUiState.value = drawerUiState.value.copy(authenticationMode = newMode)
    }
}