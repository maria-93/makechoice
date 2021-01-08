package ru.kesva.makechoice.ui.makechoicefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.kesva.makechoice.MakeChoiceApplication
import ru.kesva.makechoice.databinding.FragmentMakeChoiceBinding
import ru.kesva.makechoice.di.ViewModelFactory
import ru.kesva.makechoice.di.subcomponents.MakeChoiceComponent
import ru.kesva.makechoice.extensions.getViewModel
import ru.kesva.makechoice.ui.viewmodel.SharedViewModel
import javax.inject.Inject

class MakeChoiceFragment : Fragment() {
    private lateinit var component: MakeChoiceComponent
    private lateinit var binding: FragmentMakeChoiceBinding
    private lateinit var viewModel: SharedViewModel

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
        binding = FragmentMakeChoiceBinding.inflate(LayoutInflater.from(parentFragment?.context))
        binding.viewModel = viewModel
        binding.isStartAnimationButtonVisible = viewModel.isStartAnimationButtonVisible
    }

    private fun injectDependencies() {
        component =
            (requireContext().applicationContext as MakeChoiceApplication)
                .appComponent.makeChoiceComponent().create()
        component.provideDependenciesFor(this)
        viewModel = getViewModel(factory, requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }


}