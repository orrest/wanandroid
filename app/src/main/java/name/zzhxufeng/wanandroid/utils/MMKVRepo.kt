package name.zzhxufeng.wanandroid.utils

import android.content.SharedPreferences
import com.tencent.mmkv.MMKV

object MMKVRepo {
    private val kv: MMKV = MMKV.defaultMMKV()

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

    fun removeValuesForKeys(keys: Array<String>) {
        kv.removeValuesForKeys(keys)
    }
}