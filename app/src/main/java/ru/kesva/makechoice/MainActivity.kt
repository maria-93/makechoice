package ru.kesva.makechoice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SharedMemory
import ru.kesva.makechoice.di.subcomponents.MainActivityComponent
import ru.kesva.makechoice.ui.viewmodel.SharedViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var component: MainActivityComponent

    private lateinit var viewModel: SharedViewModel

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
    }
}