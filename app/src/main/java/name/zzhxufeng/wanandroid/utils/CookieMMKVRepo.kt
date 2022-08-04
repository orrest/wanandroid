package name.zzhxufeng.wanandroid.utils

import android.content.SharedPreferences
import com.tencent.mmkv.MMKV

object CookieMMKVRepo {
    const val COOKIES = "COOKIES"

    private val kv: MMKV = MMKV.mmkvWithID("cookie")

    fun putStringSet(key: String, set: Set<String>) {
        kv.putStringSet(key, set)
    }

    fun getStringSet(key: String): Set<String>? {
        return kv.getStringSet(key, null)
    }

    fun putString(k: String, v: String): SharedPreferences.Editor{
        return kv.putString(k, v)
    }

    fun getString(k: String): String? {
        return kv.getString(k, null)
    }

    fun remove(key: String) {
        kv.removeValueForKey(key)
    }

    fun removeValuesForKeys(keys: Array<String>) {
        kv.removeValuesForKeys(keys)
    }

    fun clear() {
        kv.clear()
    }

    fun getAll(): MutableMap<String, *>? = kv.all

}