package ru.kesva.makechoice.ui.welcomefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import ru.kesva.makechoice.MainActivity
import ru.kesva.makechoice.MakeChoiceApplication
import ru.kesva.makechoice.R
import ru.kesva.makechoice.databinding.FragmentWelcomeBinding
import ru.kesva.makechoice.di.ViewModelFactory
import ru.kesva.makechoice.di.modules.ClickHandlersProvidesModule
import ru.kesva.makechoice.di.subcomponents.WelcomeComponent
import ru.kesva.makechoice.extensions.getViewModel
import ru.kesva.makechoice.ui.viewmodel.SharedViewModel
import javax.inject.Inject

class WelcomeFragment : Fragment() {
    private lateinit var binding: FragmentWelcomeBinding
    private lateinit var component: WelcomeComponent
    private lateinit var navController: NavController
    private lateinit var viewModel: SharedViewModel

    @Inject
    lateinit var welcomeAdapterClickHandler: WelcomeAdapterClickHandler

    @Inject
    lateinit var adapter: WelcomeAdapter

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
        binding = FragmentWelcomeBinding.inflate(LayoutInflater.from(parentFragment?.context))
        binding.recyclerView.adapter = adapter
        binding.adapter = adapter
        binding.welcomeAdapterClickHandler = welcomeAdapterClickHandler
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

    private fun subscribeToEvents() {
        with(viewModel) {
            nextButtonClicked.observe(viewLifecycleOwner, {
                it.getContentIfNotHandled()?.let {
                    if (viewModel.getCardListFromCache().isNotEmpty()) {
                        navController.navigate(R.id.action_welcomeFragment_to_makeChoiceFragment)
                        adapter.removeAllEditTexts()
                    } else {
                        Toast.makeText(
                            context,
                            "Пожалуйста, добавьте свои варианты",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            })
        }
    }

    private fun injectDependencies() {
        component =
            (requireContext().applicationContext as MakeChoiceApplication).appComponent.welcomeComponent()
                .create(
                    ClickHandlersProvidesModule(requireActivity() as MainActivity)
                )
        component.provideDependenciesFor(this)
        viewModel = getViewModel(factory, requireActivity())
    }
}