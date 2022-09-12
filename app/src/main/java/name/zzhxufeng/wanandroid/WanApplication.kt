package name.zzhxufeng.wanandroid

import android.app.Application
import android.content.Context
import android.content.Intent
import com.tencent.mmkv.MMKV

class WanApplication: Application() {

    companion object{
        lateinit var instance: Application

        fun startActivity(intent: Intent){
            if (intent.flags != Intent.FLAG_ACTIVITY_NEW_TASK) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            getContext().startActivity(intent)
        }

        fun getContext(): Context {
            return instance
        }
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