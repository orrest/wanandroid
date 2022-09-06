package name.zzhxufeng.wanandroid

import android.app.Application
import android.content.Context
import com.tencent.mmkv.MMKV

class WanApplication: Application() {

    companion object{
        var instance: Application? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initMMKV(this)
    }

    private fun initMMKV(context: Context) {
        MMKV.initialize(context)
    }
}