package name.zzhxufeng.wanandroid.utils

import android.content.SharedPreferences
import com.tencent.mmkv.MMKV

/*TODO*/
object CookieRepo {
    private var kv: MMKV = MMKV.mmkvWithID("cookie")

    fun putString(k: String, v: String): SharedPreferences.Editor{
        return kv.putString(k, v)
    }

    fun getString(k: String): String? {
        return kv.getString(k, null)
    }

    fun putBoolean(k: String, v: Boolean) {
        kv.putBoolean(k, v)
    }

    fun getBoolean(k: String): Boolean {
        return kv.getBoolean(k, false)
    }

    fun remove(key: String) {
        kv.removeValueForKey(key)
    }

    fun removeValuesForKeys(keys: Array<String>) {
        kv.removeValuesForKeys(keys)
    }
}