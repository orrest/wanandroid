package name.zzhxufeng.wanandroid.utils

import android.util.Log
import name.zzhxufeng.wanandroid.utils.CookieMMKVRepo
import name.zzhxufeng.wanandroid.utils.CookieMMKVRepo.COOKIES

const val COOKIE_LOGIN_USER_NAME = "loginUserName_wanandroid_com"
const val COOKIE_LOGIN_USER_TOKEN = "token_pass_wanandroid_com"

object LoginManager{
    fun isLogin(): Boolean {
        val cookies = CookieMMKVRepo.getStringSet(COOKIES)
        Log.d(LoginManager.toString(), cookies.toString())
        return cookies != null && cookies.isNotEmpty()
    }

    fun clearCookies() {

    }
}