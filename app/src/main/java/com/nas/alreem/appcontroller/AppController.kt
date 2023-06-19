package com.nas.alreem.appcontroller

import android.app.Application


class AppController : Application() {
    companion object {
        var instance: AppController? = null
        var tibCoinPoints: Int = 0
        var scratchID: Int = 0
        var question_id: String? = null
        var answer_id: String? = null
        fun applicationContext(): AppController {
            return instance as AppController
        }
    }

    init {
        instance = this

    }
}