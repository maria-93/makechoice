package ru.kesva.makechoice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ru.kesva.makechoice.data.model.Cache
import ru.kesva.makechoice.di.ViewModelFactory
import ru.kesva.makechoice.di.subcomponents.MainActivityComponent
import ru.kesva.makechoice.ui.viewmodel.MakeChoiceViewModel
import ru.kesva.makechoice.ui.viewmodel.WelcomeViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var component: MainActivityComponent

    private lateinit var welcomeViewModel: WelcomeViewModel
    private lateinit var makeChoiceViewModel: MakeChoiceViewModel

    @Inject
    lateinit var factory: ViewModelFactory

    @Inject
    lateinit var cache: Cache

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
        setContentView(R.layout.activity_main)
    }

    private fun injectDependencies() {
        component =
            (applicationContext as MakeChoiceApplication).appComponent.mainActivityComponent()
                .create()
        component.provideDependenciesFor(this)
        welcomeViewModel = ViewModelProvider(this, factory).get(WelcomeViewModel::class.java)
        makeChoiceViewModel = ViewModelProvider(this, factory).get(MakeChoiceViewModel::class.java)
    }

}