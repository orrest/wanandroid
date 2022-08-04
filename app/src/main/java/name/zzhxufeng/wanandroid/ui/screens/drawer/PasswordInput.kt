package name.zzhxufeng.wanandroid.ui.screens.drawer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import name.zzhxufeng.wanandroid.R


@Composable
fun PasswordInput(
    modifier: Modifier = Modifier,
    indication: String = stringResource(id = R.string.label_password),
    password: String?,
    onPasswordChanged: (password: String) -> Unit,
    onDoneClicked: () -> Unit
) {
    var isPasswordHidden by remember {
        mutableStateOf(true)
    }

    TextField(
        modifier = modifier,
        value = password ?: "",
        onValueChange = onPasswordChanged,
        singleLine = true,
        label = {
            Text(text = indication)
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Lock, contentDescription = null)
        },
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable(
                    onClickLabel = if (isPasswordHidden) {
                        stringResource(id = R.string.cd_show_password)
                    } else stringResource(id = R.string.cd_hide_password)
                ) {
                    isPasswordHidden = !isPasswordHidden
                },
                imageVector = if (isPasswordHidden) {
                    Icons.Default.Visibility
                } else Icons.Default.VisibilityOff,
                contentDescription = null
            )
        },
        keyboardActions = KeyboardActions(
            onDone = {
                onDoneClicked()
            }
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        visualTransformation = if (isPasswordHidden) {
            PasswordVisualTransformation()
        } else VisualTransformation.None
    )
}

@Composable
fun PasswordInputNext(
    modifier: Modifier = Modifier,
    indication: String = stringResource(id = R.string.label_re_password),
    password: String?,
    onPasswordChanged: (password: String) -> Unit,
    onNextClicked: () -> Unit
) {
    var isPasswordHidden by remember {
        mutableStateOf(true)
    }

    TextField(
        modifier = modifier,
        value = password ?: "",
        onValueChange = onPasswordChanged,
        singleLine = true,
        label = {
            Text(text = indication)
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Lock, contentDescription = null)
        },
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable(
                    onClickLabel = if (isPasswordHidden) {
                        stringResource(id = R.string.cd_show_password)
                    } else stringResource(id = R.string.cd_hide_password)
                ) {
                    isPasswordHidden = !isPasswordHidden
                },
                imageVector = if (isPasswordHidden) {
                    Icons.Default.Visibility
                } else Icons.Default.VisibilityOff,
                contentDescription = null
            )
        },
        keyboardActions = KeyboardActions(
            onNext = {
                onNextClicked()
            }
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Password
        ),
        visualTransformation = if (isPasswordHidden) {
            PasswordVisualTransformation()
        } else VisualTransformation.None
    )
}