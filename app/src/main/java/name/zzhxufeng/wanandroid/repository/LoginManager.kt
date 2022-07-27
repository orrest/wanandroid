package name.zzhxufeng.wanandroid.repository

import name.zzhxufeng.wanandroid.utils.MMKVRepo
import okhttp3.Cookie

/* Indicator of whether the user has logged in. */
const val LOGIN_COOKIE_BOOLEAN = "LOGIN_COOKIE_BOOLEAN"
const val LOGIN_COOKIE = "LOGIN_COOKIE"

object LoginManager{

    fun isLogin(): Boolean {
        return MMKVRepo.getBoolean(LOGIN_COOKIE_BOOLEAN)
    }

    fun getCookies(): String? {
        return MMKVRepo.getString(LOGIN_COOKIE)
    }

    fun putCookies(cookies: List<Cookie>) {
        /*TODO 这个地方需要修改*/
        MMKVRepo.putBoolean(LOGIN_COOKIE_BOOLEAN, true)
        MMKVRepo.putString(LOGIN_COOKIE,
            cookies.toString().removeSurrounding("[", "]")
        )
    }

    fun clearCookies() {
        MMKVRepo.putBoolean(LOGIN_COOKIE_BOOLEAN, false)
        MMKVRepo.remove(LOGIN_COOKIE)
    }
}