package name.zzhxufeng.wanandroid.repository

import retrofit2.http.GET

object NaviRepository {
    private val naviService = WanAndroidNetwork.retrofit.create(NaviInterface::class.java)

    suspend fun refreshNavi(): List<NaviData> {
        return naviService.fetchNavi().data
    }
}

interface NaviInterface {
    @GET("navi/json")
    suspend fun fetchNavi(): NaviResponse
}

/*https://www.wanandroid.com/navi/json*/
/*
{
    data: [
    {
    articles: [],
    cid: 272,
    name: "常用网站"
},
* */
data class NaviResponse(
    val `data`: List<NaviData>,
    val errorCode: Int,
    val errorMsg: String
)

data class NaviData(
    val articles: List<NaviModel>,
    val cid: Int,
    val name: String
)

data class NaviModel(
    val apkLink: String,
    val audit: Int,
    val author: String,
    val canEdit: Boolean,
    val chapterId: Int,
    val chapterName: String,
    val collect: Boolean,
    val courseId: Int,
    val desc: String,
    val descMd: String,
    val envelopePic: String,
    val fresh: Boolean,
    val host: String,
    val id: Int,
    val link: String,
    val niceDate: String,
    val niceShareDate: String,
    val origin: String,
    val prefix: String,
    val projectLink: String,
    val publishTime: Long,
    val realSuperChapterId: Int,
    val selfVisible: Int,
    val shareDate: Long,
    val shareUser: String,
    val superChapterId: Int,
    val superChapterName: String,
    val tags: List<Any>,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int
)