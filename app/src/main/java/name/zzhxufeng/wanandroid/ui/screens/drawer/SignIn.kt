package name.zzhxufeng.wanandroid.ui.screens.drawer

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import name.zzhxufeng.wanandroid.viewmodel.event.DrawerEvent
import name.zzhxufeng.wanandroid.viewmodel.state.LoginUiState

@Composable
fun SignIn(
    state: LoginUiState,
    handleEvent: (DrawerEvent) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        AuthenticationTitle(authenticationMode = state.authenticationMode)
        Spacer(modifier = Modifier.height(40.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val passwordFocusRequester = FocusRequester()
                AccountInput(
                    modifier = Modifier.fillMaxWidth(),
                    accountName = state.inputName,
                    onNameChanged = { handleEvent(DrawerEvent.AccountNameChanged(it)) },
                    onNextClicked = {
                        passwordFocusRequester.requestFocus()
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                PasswordInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(passwordFocusRequester),
                    password = state.password,
                    onPasswordChanged = { handleEvent(DrawerEvent.PasswordChanged(it)) },
                    onDoneClicked = { handleEvent(DrawerEvent.Authenticate) }
                )
                Spacer(modifier = Modifier.height(16.dp))
                AuthenticationButton(
                    authenticationMode = state.authenticationMode,
                    onAuthenticate = { handleEvent(DrawerEvent.Authenticate) }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}