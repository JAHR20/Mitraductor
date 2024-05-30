package com.itsa.mitraductor.media

import android.app.Application


class App : Application() {

    companion object {
        lateinit var prefs: Prefs
            private set
    }

    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
    }
}