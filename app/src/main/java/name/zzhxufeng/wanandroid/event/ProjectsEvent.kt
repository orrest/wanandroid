package name.zzhxufeng.wanandroid.event

sealed class ProjectsEvent {
    class RefreshProjects(
        val id: Int, val index: Int) : ProjectsEvent()

}
