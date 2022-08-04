package name.zzhxufeng.wanandroid.ui.screens.drawer

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import name.zzhxufeng.wanandroid.R

@Composable
fun AccountInput(
    modifier: Modifier = Modifier,
    accountName: String?,
    onNameChanged: (name: String) -> Unit,
    onNextClicked: () -> Unit
) {
    TextField(
        modifier = modifier,
        value = accountName ?: "",
        onValueChange = onNameChanged,
        label = {
            Text(text = stringResource(id = R.string.label_email_address))
        },
        singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Email, contentDescription = null)
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                onNextClicked()
            }
        )
    )
}