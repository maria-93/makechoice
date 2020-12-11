package ru.kesva.makechoice.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import ru.kesva.makechoice.MakeChoiceApplication
import ru.kesva.makechoice.databinding.FragmentWelcomeBinding
import ru.kesva.makechoice.di.subcomponents.WelcomeComponent

class WelcomeFragment : Fragment() {
    private lateinit var component: WelcomeComponent
    private lateinit var navController: NavController
    private lateinit var binding: FragmentWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
        binding = FragmentWelcomeBinding.inflate(LayoutInflater.from(parentFragment?.context))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = NavHostFragment.findNavController(this)
    }

    private fun injectDependencies() {
        component =
            (requireContext().applicationContext as MakeChoiceApplication)
                .appComponent.welcomeComponent().create()
        component.provideDependenciesFor(this)
    }
}