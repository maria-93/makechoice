package ru.kesva.makechoice.ui.welcomefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import ru.kesva.makechoice.MakeChoiceApplication
import ru.kesva.makechoice.R
import ru.kesva.makechoice.databinding.FragmentWelcomeBinding
import ru.kesva.makechoice.di.ViewModelFactory
import ru.kesva.makechoice.di.subcomponents.WelcomeComponent
import ru.kesva.makechoice.extensions.getViewModel
import ru.kesva.makechoice.ui.viewmodel.WelcomeViewModel
import javax.inject.Inject

class WelcomeFragment : Fragment() {
    private lateinit var binding: FragmentWelcomeBinding
    private lateinit var component: WelcomeComponent
    private lateinit var navController: NavController
    private lateinit var welcomeViewModel: WelcomeViewModel
    private lateinit var listener: NavController.OnDestinationChangedListener

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
        binding = FragmentWelcomeBinding.inflate(LayoutInflater.from(parentFragment?.context))
        binding.welcomeViewModel = welcomeViewModel
        binding.isNextButtonVisible = welcomeViewModel.isNextButtonVisible
        binding.isProgressBarVisible = welcomeViewModel.isProgressBarVisible
        listener =
            NavController.OnDestinationChangedListener { controller, destination, arguments ->
                if (controller.graph.startDestination == controller.currentDestination?.id) {
                    welcomeViewModel.isNextButtonVisible.set(true)
                }
            }
    }

    private fun injectDependencies() {
        component =
            (requireContext().applicationContext as MakeChoiceApplication).appComponent.welcomeComponent()
                .create()
        component.provideDependenciesFor(this)
        welcomeViewModel = getViewModel(factory, requireActivity())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = NavHostFragment.findNavController(this)
        subscribeToEvents()
    }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(listener)
    }

    override fun onPause() {
        super.onPause()
        navController.removeOnDestinationChangedListener(listener)
    }

    private fun subscribeToEvents() {
        with(welcomeViewModel) {
            nextButtonClicked.observe(viewLifecycleOwner, {
                it.getContentIfNotHandled()?.let {
                    navController.navigate(R.id.action_welcomeFragment_to_makeChoiceFragment)
                }
            })
            toastLiveDataCardListNullOrEmpty.observe(viewLifecycleOwner, {
                it.getContentIfNotHandled()?.let {
                    Toast.makeText(
                        context,
                        getString(R.string.toast_please_add_your_variants),
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
            toastLiveDataCardListLessThanTwo.observe(viewLifecycleOwner, {
                it.getContentIfNotHandled()?.let {
                    Toast.makeText(
                        context,
                        getString(R.string.toast_enter_at_least_two_variants),
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        }
    }
}
