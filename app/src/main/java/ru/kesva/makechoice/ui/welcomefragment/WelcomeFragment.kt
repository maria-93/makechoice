package ru.kesva.makechoice.ui.welcomefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
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
import ru.kesva.makechoice.ui.viewmodel.SharedViewModel
import javax.inject.Inject

class WelcomeFragment : Fragment() {
    private lateinit var binding: FragmentWelcomeBinding
    private lateinit var component: WelcomeComponent
    private lateinit var navController: NavController
    private lateinit var viewModel: SharedViewModel
    private lateinit var listener: NavController.OnDestinationChangedListener

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
        binding = FragmentWelcomeBinding.inflate(LayoutInflater.from(parentFragment?.context))
        binding.viewModel = viewModel
        listener =
            NavController.OnDestinationChangedListener { controller, destination, arguments ->
                if (controller.graph.startDestination == controller.currentDestination?.id) {
                    viewModel.clearCardListFromCache()
                    binding.buttonFarther.visibility = View.VISIBLE
                }
            }
    }

    private fun injectDependencies() {
        component =
            (requireContext().applicationContext as MakeChoiceApplication).appComponent.welcomeComponent()
                .create()
        component.provideDependenciesFor(this)
        viewModel = getViewModel(factory, requireActivity())
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
        with(viewModel) {
            nextButtonClicked.observe(viewLifecycleOwner, {
                it.getContentIfNotHandled()?.let {
                    if (viewModel.getCardListFromCache().isNullOrEmpty()) {
                        Toast.makeText(
                            context,
                            "Пожалуйста, добавьте свои варианты",
                            Toast.LENGTH_LONG
                        ).show()
                        binding.buttonFarther.visibility = View.VISIBLE
                        binding.progressBar.visibility = ProgressBar.INVISIBLE
                    } else {
                        navController.navigate(R.id.action_welcomeFragment_to_makeChoiceFragment)

                    }
                }
            })
        }
    }


}
