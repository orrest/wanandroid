package name.zzhxufeng.wanandroid.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import name.zzhxufeng.wanandroid.repository.LoginManager

@Composable
fun WanDrawer(
    login: (String, String) -> Unit
) {
    if (LoginManager.isLogin()) {
        Column{
            Info()
            FunctionList()
        }
    } else {
        LoginView(login)
    }
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

data class Item(
    val image: ImageVector,
    val description: String,
    val state: String
)

@Composable
fun FunctionList() {
    /*TODO 除了图标其它部分应该由外面传进来*/
    val list = mutableListOf(
        Item(Icons.Outlined.Loyalty, "我的积分", "758"),
        Item(Icons.Outlined.Bookmarks, "我的收藏", "758"),
        Item(Icons.Outlined.Share, "我的分享", "758"),
        Item(Icons.Outlined.PendingActions, "TODO", "758"),
        Item(Icons.Filled.DarkMode, "夜间模式", "758"),
        Item(Icons.Outlined.Settings, "系统设置", "758"),
        Item(Icons.Outlined.Logout, "退出登录", "758"),
    )
    Column {
        list.forEach {
            ListRow(it.image, it.description, it.state)
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


@Composable
fun Info() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(imageVector = Icons.Default.AccountCircle, contentDescription = "avatar", Modifier.size(80.dp))
        Text(text = "name")
        /*TODO State hoisting.*/
        val level = 8
        val rank = 2656
        Text(text = "等级 $level  |  排名 $rank")
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewListRow() {
    FunctionList()
}
