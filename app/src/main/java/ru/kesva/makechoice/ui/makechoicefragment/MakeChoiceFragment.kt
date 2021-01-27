package ru.kesva.makechoice.ui.makechoicefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import ru.kesva.makechoice.MakeChoiceApplication
import ru.kesva.makechoice.databinding.FragmentMakeChoiceBinding
import ru.kesva.makechoice.di.ViewModelFactory
import ru.kesva.makechoice.di.subcomponents.MakeChoiceComponent
import ru.kesva.makechoice.extensions.getViewModel
import ru.kesva.makechoice.ui.viewmodel.MakeChoiceViewModel
import javax.inject.Inject

class MakeChoiceFragment : Fragment() {
    private lateinit var component: MakeChoiceComponent
    private lateinit var binding: FragmentMakeChoiceBinding
    private lateinit var makeChoiceViewModel: MakeChoiceViewModel
    private lateinit var navController: NavController
    private lateinit var listener: NavController.OnDestinationChangedListener

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
        binding = FragmentMakeChoiceBinding.inflate(LayoutInflater.from(parentFragment?.context))
        binding.makeChoiceViewModel = makeChoiceViewModel
        binding.isStartAnimationButtonVisible = makeChoiceViewModel.isStartAnimationButtonVisible
        listener =
            NavController.OnDestinationChangedListener { controller, destination, arguments ->
                if ((controller.graph.startDestination == controller.currentDestination?.id)) {
                    makeChoiceViewModel.isStartAnimationButtonVisible.set(true)
                }
            }
    }

    private fun injectDependencies() {
        component =
            (requireContext().applicationContext as MakeChoiceApplication)
                .appComponent.makeChoiceComponent().create()
        component.provideDependenciesFor(this)
        makeChoiceViewModel = getViewModel(factory, requireActivity())
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

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(listener)
    }

    override fun onPause() {
        navController.removeOnDestinationChangedListener(listener)
        super.onPause()
    }


}