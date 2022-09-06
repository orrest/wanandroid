package name.zzhxufeng.wanandroid.ui.screens.drawer

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import name.zzhxufeng.wanandroid.event.drawer.DrawerEvent
import name.zzhxufeng.wanandroid.state.drawer.AuthenticationMode
import name.zzhxufeng.wanandroid.state.drawer.LoginUiState
import name.zzhxufeng.wanandroid.ui.screens.drawer.authentication.SignIn
import name.zzhxufeng.wanandroid.ui.screens.drawer.authentication.SingUp
import name.zzhxufeng.wanandroid.ui.screens.drawer.authentication.ToggleAuthenticationMode


@Composable
fun AuthenticationContent(
    authenticationMode: AuthenticationMode,
    state: LoginUiState,
    handleEvent: (event: DrawerEvent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (authenticationMode) {
            AuthenticationMode.SIGN_IN -> {
                SignIn(state = state, handleEvent = handleEvent, authenticationMode)
            }
            AuthenticationMode.SIGN_UP -> {
                SingUp(state = state, handleEvent = handleEvent, authenticationMode)
            }
            else -> {/*no need*/}
        }

        ToggleAuthenticationMode(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            authenticationMode = authenticationMode,
            toggleAuthentication = { handleEvent(DrawerEvent.ToggleAuthenticationMode) }
        )
    }
}
