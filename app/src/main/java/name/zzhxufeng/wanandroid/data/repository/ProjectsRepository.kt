package name.zzhxufeng.wanandroid.data.repository

import name.zzhxufeng.wanandroid.data.model.WanResponse
import name.zzhxufeng.wanandroid.data.network.WanAndroidNetwork
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

object ProjectsRepository {
    private val projectService = WanAndroidNetwork.retrofit.create(ProjectsInterface::class.java)

    suspend fun refreshTitles(): WanResponse<List<ProjectNameModel>>{
        return projectService.fetchTitles()
    }

    suspend fun refreshProjects(page: Int, id: Int): WanResponse<ProjectData> {
        return projectService.fetchProjects(page, id)
    }
}

interface ProjectsInterface {
    @GET("project/tree/json")
    suspend fun fetchTitles(): WanResponse<List<ProjectNameModel>>

    @GET("project/list/{page}/json")
    suspend fun fetchProjects(
        @Path("page") page: Int, @Query("cid") id: Int
    ): WanResponse<ProjectData>
}

data class ProjectNameModel(
    val author: String?,
    val children: List<String>,
    val courseId: Int,
    val cover: String,
    val desc: String,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)

data class ProjectData(
    val curPage: Int,
    val datas: List<ProjectModel>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int,
)

data class ProjectModel(
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
    val tags: List<Tag>,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int
)

data class Tag(
    val name: String,
    val url: String
)