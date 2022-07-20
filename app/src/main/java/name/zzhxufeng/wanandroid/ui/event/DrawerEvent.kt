package name.zzhxufeng.wanandroid.ui.event

import name.zzhxufeng.wanandroid.ui.state.DrawerItem

sealed class DrawerEvent {
    class OpenDrawerItem(val drawerItem: DrawerItem): DrawerEvent()
}