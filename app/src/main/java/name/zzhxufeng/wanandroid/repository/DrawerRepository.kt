package name.zzhxufeng.wanandroid.repository

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

object DrawerRepository {
    private val drawerService = WanAndroidNetwork.retrofit.create(DrawerInterface::class.java)

    suspend fun login(namePwd: NamePwd) {
        drawerService.login(namePwd)
    }
}

interface DrawerInterface {
    @POST("user/login")
    suspend fun login(@Body namePwd: NamePwd)

    @POST("user/register")
    suspend fun register(@Body namePwdRepwd: NamePwdRepwd)

    /*
    * TODO 登录与退出时客户端与服务端实际在做什么？抓包看一下。
    * 直接让OkHttp代理自己根据头去做缓存之类的就好了。
    * */
    @GET("user/logout/json")
    suspend fun logout()
}

data class NamePwd(
    val name: String,
    val pwd: String,
)

data class NamePwdRepwd(
    val name: String,
    val pwd: String,
    val repwd: String,
)