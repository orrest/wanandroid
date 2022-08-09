package name.zzhxufeng.wanandroid.repository.cookie

import android.util.Log
import kotlinx.serialization.json.Json
import name.zzhxufeng.wanandroid.utils.MMKVDefaultRepo
import okhttp3.Cookie

/*
* The path could have many cookies, but these cookies have different names.
* */
internal inline val Cookie.key: String
    get() = "${(if (secure) "https" else "http")}://$domain$path|$name"

internal fun Cookie.isExpired() = expiresAt < System.currentTimeMillis()

const val COOKIE_LOGIN_USER_NAME = "loginUserName_wanandroid_com"
const val COOKIE_LOGIN_USER_TOKEN = "token_pass_wanandroid_com"

class CookieCacheManager: ICookie {
    override fun saveAll(cookies: Collection<Cookie>) {
        if (cookies.isEmpty()) return
        cookies.forEach {
            val encodeCookieToString = Json.encodeToString(CookieSerializer, it)
            MMKVDefaultRepo.putString(it.key, encodeCookieToString)
            Log.d("CookieCacheManager", "${it.key}#${encodeCookieToString} added.")
        }
    }

    override fun removeAll(cookies: Collection<Cookie>) {
        cookies.forEach {
            MMKVDefaultRepo.remove(it.key)
        }
    }

    override fun clear() {
        /*TODO*/
    }
}