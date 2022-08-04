package name.zzhxufeng.wanandroid.ui.screens.drawer

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import name.zzhxufeng.wanandroid.viewmodel.event.DrawerEvent
import name.zzhxufeng.wanandroid.viewmodel.state.AuthenticationMode
import name.zzhxufeng.wanandroid.viewmodel.state.LoginUiState


@Composable
fun AuthenticationContent(
    state: LoginUiState,
    handleEvent: (event: DrawerEvent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (state.authenticationMode) {
            AuthenticationMode.SIGN_IN -> {
                SignIn(state = state, handleEvent = handleEvent)
            }
            AuthenticationMode.SIGN_UP -> {
                SingUp(state = state, handleEvent = handleEvent)
            }
            else -> {/*no need*/}
        }

        ToggleAuthenticationMode(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            authenticationMode = state.authenticationMode,
            toggleAuthentication = { handleEvent(DrawerEvent.ToggleAuthenticationMode) }
        )
    }
}
