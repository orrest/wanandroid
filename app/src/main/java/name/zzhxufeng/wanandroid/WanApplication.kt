package name.zzhxufeng.wanandroid

import android.app.Application
import android.content.Context
import com.tencent.mmkv.MMKV

class WanApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initMMKV(this)
    }

    private fun initMMKV(context: Context) {
        MMKV.initialize(context)
    }
}