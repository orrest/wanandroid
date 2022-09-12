package name.zzhxufeng.wanandroid.state

import name.zzhxufeng.wanandroid.data.repository.ProjectModel
import name.zzhxufeng.wanandroid.data.repository.ProjectNameModel

data class ProjectsUiState(
    val titles: List<ProjectNameModel>? = null,
    val projects: List<List<ProjectModel>?> = emptyList()
)