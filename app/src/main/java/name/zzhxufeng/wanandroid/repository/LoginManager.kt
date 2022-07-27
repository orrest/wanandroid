package name.zzhxufeng.wanandroid.repository

import name.zzhxufeng.wanandroid.utils.MMKVDefaultRepo
import okhttp3.Cookie

/* Indicator of whether the user has logged in. */
const val LOGIN_COOKIE_BOOLEAN = "LOGIN_COOKIE_BOOLEAN"
const val LOGIN_COOKIE = "LOGIN_COOKIE"

object LoginManager{

    fun isLogin(): Boolean {
        return MMKVDefaultRepo.getBoolean(LOGIN_COOKIE_BOOLEAN)
    }

    fun getCookies(): String? {
        return MMKVDefaultRepo.getString(LOGIN_COOKIE)
    }

    fun putCookies(cookies: List<Cookie>) {
        MMKVDefaultRepo.putBoolean(LOGIN_COOKIE_BOOLEAN, true)
        MMKVDefaultRepo.putString(LOGIN_COOKIE,
            cookies.toString().removeSurrounding("[", "]")
        )
    }

    fun clearCookies() {
        MMKVDefaultRepo.putBoolean(LOGIN_COOKIE_BOOLEAN, false)
        MMKVDefaultRepo.remove(LOGIN_COOKIE)
    }
}