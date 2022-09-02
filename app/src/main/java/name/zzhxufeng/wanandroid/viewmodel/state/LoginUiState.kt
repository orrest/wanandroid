package name.zzhxufeng.wanandroid.viewmodel.state

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import name.zzhxufeng.wanandroid.R

data class LoginUiState(
    val inputName: String = "",
    val password: String = "",
    val repassword: String = "",
    val authenticationMode: AuthenticationMode = AuthenticationMode.SIGN_IN,

)

enum class AuthenticationMode {
    SIGN_IN, SIGN_UP, LOGGED_IN
}