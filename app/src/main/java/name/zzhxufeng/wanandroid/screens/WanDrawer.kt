package name.zzhxufeng.wanandroid.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import name.zzhxufeng.wanandroid.ui.event.DrawerEvent
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
            PersonalItem(drawerItem)
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
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.name,
            modifier = Modifier.padding(10.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = item.name)
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun AuthenticationContent(
    state: DrawerUiState,
    handleEvent: (event: DrawerEvent) -> Unit
) {

}

@Composable
fun LoginView(
    login: (String, String) -> Unit
) {
    var accountName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(value = accountName, onValueChange = {
            accountName = it
        } )

        TextField(value = password, onValueChange = {
            password = it
        })

        Button(onClick = { login(accountName, password) }) {
            Text(text = "登录")
        }
    }
}

@Composable
fun FunctionList(
    list: List<DrawerItem>
) {
    Column {
        list.forEach {

        }
    }
}

@Composable
fun ListRow(
    image: ImageVector,
    description: String,
    state: String
) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Image(imageVector = image, contentDescription = description)
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = description)
        }
        Text(text = state, modifier = Modifier.align(Alignment.CenterEnd))
    }
}


