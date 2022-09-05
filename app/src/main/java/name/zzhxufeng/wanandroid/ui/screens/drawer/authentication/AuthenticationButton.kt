package name.zzhxufeng.wanandroid.ui.screens.drawer

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import name.zzhxufeng.wanandroid.R
import name.zzhxufeng.wanandroid.state.AuthenticationMode

@Composable
fun AuthenticationButton(
    modifier: Modifier = Modifier,
    authenticationMode: AuthenticationMode,
    onAuthenticate: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = {
            onAuthenticate()
        }
    ) {
        Text(
            text = stringResource(
                id = if (authenticationMode == AuthenticationMode.SIGN_IN) {
                    R.string.action_sign_in
                } else R.string.action_sign_up
            )
        )
    }
}