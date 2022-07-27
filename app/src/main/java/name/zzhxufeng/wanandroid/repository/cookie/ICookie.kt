package name.zzhxufeng.wanandroid.repository.cookie

import okhttp3.Cookie

interface ICookie {

    fun saveAll(cookies: Collection<Cookie>)

    fun removeAll(cookies: Collection<Cookie>)

    fun clear()
}