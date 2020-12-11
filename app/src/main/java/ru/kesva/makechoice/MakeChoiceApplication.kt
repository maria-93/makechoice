package ru.kesva.makechoice

import android.app.Application
import ru.kesva.makechoice.di.AppComponent
import ru.kesva.makechoice.di.DaggerAppComponent

open class MakeChoiceApplication : Application() {

    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent() =
        DaggerAppComponent.factory().create(applicationContext)

    override fun onCreate() {
        super.onCreate()
        appComponent.provideDependenciesFor(this)
    }

}