package name.zzhxufeng.wanandroid.repository

import name.zzhxufeng.wanandroid.utils.MMKVRepo
import okhttp3.Cookie

/* Indicator of whether the user has logged in. */
const val LOGIN_COOKIE_BOOLEAN = "LOGIN_COOKIE"

object LoginManager{

    fun isLogin(): Boolean {
        return MMKVRepo.getBoolean(LOGIN_COOKIE_BOOLEAN)
    }

    fun putCookies(cookies: List<Cookie>) {
        MMKVRepo.putBoolean(LOGIN_COOKIE_BOOLEAN, true)
        cookies.forEach {

        }
    }

    fun clearCookies() {
        MMKVRepo.putBoolean(LOGIN_COOKIE_BOOLEAN, false)

    }
}