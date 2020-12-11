package ru.kesva.makechoice.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.kesva.makechoice.MakeChoiceApplication
import ru.kesva.makechoice.databinding.FragmentMakeChoiceBinding
import ru.kesva.makechoice.di.subcomponents.MakeChoiceComponent

class MakeChoiceFragment : Fragment() {
    private lateinit var component: MakeChoiceComponent
    private lateinit var binding: FragmentMakeChoiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
        binding = FragmentMakeChoiceBinding.inflate(LayoutInflater.from(parentFragment?.context))

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    private fun injectDependencies() {
        component =
            (requireContext().applicationContext as MakeChoiceApplication)
                .appComponent.makeChoiceComponent().create()
        component.provideDependenciesFor(this)
    }
}