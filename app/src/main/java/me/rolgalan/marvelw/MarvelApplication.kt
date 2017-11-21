package me.rolgalan.marvelw

import android.app.Application
import com.facebook.stetho.Stetho

/**
 * Created by Roldán Galán on 21/11/2017.
 */
class MarvelApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        //Init Crashlytics or any other traker for a real project!

        Stetho.initializeWithDefaults(this)
    }
}