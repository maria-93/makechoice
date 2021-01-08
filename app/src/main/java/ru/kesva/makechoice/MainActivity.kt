package ru.kesva.makechoice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ru.kesva.makechoice.di.ViewModelFactory
import ru.kesva.makechoice.di.subcomponents.MainActivityComponent
import ru.kesva.makechoice.ui.viewmodel.SharedViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var component: MainActivityComponent

    private lateinit var viewModel: SharedViewModel

    @Inject
    lateinit var factory: ViewModelFactory

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
        viewModel = ViewModelProvider(this, factory).get(SharedViewModel::class.java)
    }

}