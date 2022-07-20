package name.zzhxufeng.wanandroid.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import name.zzhxufeng.wanandroid.R
import name.zzhxufeng.wanandroid.ui.event.DrawerEvent
import name.zzhxufeng.wanandroid.ui.state.AuthenticationMode
import name.zzhxufeng.wanandroid.ui.state.DrawerItem
import name.zzhxufeng.wanandroid.ui.state.DrawerUiState
import name.zzhxufeng.wanandroid.viewmodel.DrawerViewModel

@Composable
fun WanDrawer(
    viewModel: DrawerViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {
    val state = viewModel.drawerUiState.collectAsState().value
    when (state.login) {
        true ->
            DrawerContent(
                state = state,
                handleEvent = {event -> viewModel.handleEvent(event) }
            )

        false ->
            AuthenticationContent(
                state = state,
                handleEvent = {event -> viewModel.handleEvent(event) }
            )
    }
}

@Composable
fun DrawerContent(
    state: DrawerUiState,
    handleEvent: (event: DrawerEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        PersonalInfo(
            level = state.level,
            rank = state.rank
        )
        state.drawerList.forEach { drawerItem ->
            PersonalItem(
                item = drawerItem,
                onClick = { handleEvent(DrawerEvent.OpenDrawerItem(drawerItem)) }
            )
        }
    }
}

@Composable
fun PersonalInfo(
    level: String?,
    rank: String?,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(imageVector = Icons.Default.AccountCircle, contentDescription = "avatar", Modifier.size(80.dp))
        Text(text = "name")
        Text(text = "等级 ${level ?: ""}  |  排名 ${rank ?: ""}")
    }
}

@Composable
fun PersonalItem(
    item: DrawerItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.clickable{
            onClick()
        }.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.name,
            modifier = Modifier.padding(10.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(text = item.name, modifier = Modifier.padding(10.dp))
    }
}

@Composable
fun AuthenticationContent(
    state: DrawerUiState,
    handleEvent: (event: DrawerEvent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else {
            AuthenticationForm(
                modifier = Modifier.fillMaxSize(),
                authenticationMode = state.authenticationMode,
                email = state.email,
                password = state.password,
                onEmailChanged = {
                    handleEvent(DrawerEvent.EmailChanged(it))
                },
                onPasswordChanged = {
                    handleEvent(DrawerEvent.PasswordChanged(it))
                },
                onAuthenticate = {
                    handleEvent(DrawerEvent.Authenticate)
                },
                onToggleMode = {
                    handleEvent(DrawerEvent.ToggleAuthenticationMode)
                },
            )
            state.error?.let { error ->
                AuthenticationErrorDialog(
                    error = error,
                    dismissError = {
                        handleEvent(DrawerEvent.ErrorDismissed)
                    }
                )
            }
        }
    }
}

@Composable
fun AuthenticationForm(
    modifier: Modifier = Modifier,
    enableAuthentication: Boolean = true,
    authenticationMode: AuthenticationMode,
    email: String?,
    password: String?,
    onEmailChanged: (email: String) -> Unit,
    onPasswordChanged: (password: String) -> Unit,
    onAuthenticate: () -> Unit,
    onToggleMode: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        AuthenticationTitle(authenticationMode = authenticationMode)
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
                EmailInput(
                    modifier = Modifier.fillMaxWidth(),
                    email = email,
                    onEmailChanged = onEmailChanged,
                    onNextClicked = {
                        passwordFocusRequester.requestFocus()
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                PasswordInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(passwordFocusRequester),
                    password = password,
                    onPasswordChanged = onPasswordChanged,
                    onDoneClicked = {
                        onAuthenticate()
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
                AuthenticationButton(
                    authenticationMode = authenticationMode,
                    enableAuthentication = enableAuthentication,
                    onAuthenticate = onAuthenticate
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        ToggleAuthenticationMode(
            modifier = Modifier.fillMaxWidth(),
            authenticationMode = authenticationMode,
            toggleAuthentication = onToggleMode
        )
    }
}

@Composable
fun AuthenticationButton(
    modifier: Modifier = Modifier,
    authenticationMode: AuthenticationMode,
    enableAuthentication: Boolean,
    onAuthenticate: () -> Unit
) {
    Button(
        modifier = modifier,
        enabled = enableAuthentication,
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

@Composable
fun AuthenticationTitle(
    modifier: Modifier = Modifier,
    authenticationMode: AuthenticationMode
) {
    Text(
        modifier = modifier,
        text = stringResource(
            id = if (authenticationMode == AuthenticationMode.SIGN_IN) {
                R.string.title_sign_in
            } else R.string.title_sign_up
        ),
        fontSize = 24.sp,
        fontWeight = FontWeight.Black
    )
}

@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    email: String?,
    onEmailChanged: (email: String) -> Unit,
    onNextClicked: () -> Unit
) {
    TextField(
        modifier = modifier,
        value = email ?: "",
        onValueChange = onEmailChanged,
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

@Composable
fun PasswordInput(
    modifier: Modifier = Modifier,
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
            Text(text = stringResource(id = R.string.label_password))
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
fun ToggleAuthenticationMode(
    modifier: Modifier = Modifier,
    authenticationMode: AuthenticationMode,
    toggleAuthentication: () -> Unit
) {
    Surface(modifier = modifier, elevation = 8.dp) {
        TextButton(
            onClick = {
                toggleAuthentication()
            }) {
            Text(
                text = stringResource(
                    id = if (authenticationMode == AuthenticationMode.SIGN_IN) {
                        R.string.action_need_account
                    } else R.string.action_already_have_account
                )
            )
        }
    }
}

@Composable
fun AuthenticationErrorDialog(
    modifier: Modifier = Modifier,
    error: String,
    dismissError: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            dismissError()
        },
        confirmButton = {
            TextButton(onClick = {
                dismissError()
            }) {
                Text(text = stringResource(id = R.string.error_action))
            }
        },
        title = {
            Text(text = stringResource(id = R.string.error_title), fontSize = 18.sp)
        },
        text = {
            Text(text = error)
        }
    )
}