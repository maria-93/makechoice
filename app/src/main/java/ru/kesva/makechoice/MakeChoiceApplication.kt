package ru.kesva.makechoice

import android.app.Application
import ru.kesva.makechoice.data.model.Cache
import ru.kesva.makechoice.di.AppComponent
import ru.kesva.makechoice.di.DaggerAppComponent

open class MakeChoiceApplication : Application() {

    private val cache = Cache(mutableListOf())

    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent() =
        DaggerAppComponent.factory().create(applicationContext, cache)

    override fun onCreate() {
        super.onCreate()
        appComponent.provideDependenciesFor(this)
    }

}