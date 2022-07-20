package name.zzhxufeng.wanandroid.repository

import name.zzhxufeng.wanandroid.utils.LOGIN_COOKIE
import name.zzhxufeng.wanandroid.utils.MMKVRepo

object LoginManager{

    fun isLogin(): Boolean {
        return MMKVRepo.getString(LOGIN_COOKIE) != null
    }

    fun login(v: String /*TODO what v?*/) {
        MMKVRepo.putString(LOGIN_COOKIE, v)
    }

    fun exit() {
        MMKVRepo.removeStringKey(LOGIN_COOKIE)
    }
}