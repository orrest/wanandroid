package name.zzhxufeng.wanandroid.repository

import retrofit2.http.GET

object ProjectsRepository {
    suspend fun refreshProjectsName(): List<ProjectNameModel> {
        return WanAndroidNetwork.fetchProjects()
    }
}

interface ProjectInterface {
    @GET("project/tree/json")
    suspend fun fetchProjectsName(): WanResponse<List<ProjectNameModel>>
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