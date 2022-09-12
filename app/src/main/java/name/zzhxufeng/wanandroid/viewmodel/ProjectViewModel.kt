package name.zzhxufeng.wanandroid.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import name.zzhxufeng.wanandroid.data.network.START_PAGE_OLD_API
import name.zzhxufeng.wanandroid.data.network.WAN_SUCCESS_CODE
import name.zzhxufeng.wanandroid.data.repository.ProjectsRepository
import name.zzhxufeng.wanandroid.event.ProjectsEvent
import name.zzhxufeng.wanandroid.state.ProjectsUiState

class ProjectViewModel: BaseViewModel() {
    val uiState = MutableStateFlow(ProjectsUiState())

    init {
        initContent()
    }

    fun handleEvent(event: ProjectsEvent) {
        when (event){
            is ProjectsEvent.RefreshProjects -> refreshProjects(event.id, event.index)
        }
    }

    /*
    * Refresh projects for the first time.
    * This should after init{}.
    * */
    private fun refreshProjects(
        id: Int,
        index: Int
    ) = launchDataLoad {
        val response = ProjectsRepository.refreshProjects(
            START_PAGE_OLD_API, id
        )
        if (response.errorCode == WAN_SUCCESS_CODE) {
            uiState.update { it.copy(
                projects = it.projects.toMutableList().apply {
                    this[index] = response.data.datas
                }.toList()
            ) }
        } else {
            errorMsg.value = response.errorMsg
        }
    }

    private fun initContent() = launchDataLoad{
        val titles = ProjectsRepository.refreshTitles()
        if (titles.errorCode == WAN_SUCCESS_CODE){
            uiState.update { it.copy(
                titles = titles.data,
                projects = List(titles.data.size, init = {null})
            ) }
        } else {
            errorMsg.value = titles.errorMsg
        }
    }

    private fun loadMoreProjects(id: Int) = launchDataLoad {

    }
}