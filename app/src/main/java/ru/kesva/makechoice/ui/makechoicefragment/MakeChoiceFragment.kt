package ru.kesva.makechoice.ui.makechoicefragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Annotation
import android.text.SpannableString
import android.text.Spanned
import android.text.SpannedString
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import ru.kesva.makechoice.MainActivity
import ru.kesva.makechoice.MakeChoiceApplication
import ru.kesva.makechoice.R
import ru.kesva.makechoice.databinding.FragmentMakeChoiceBinding
import ru.kesva.makechoice.di.ViewModelFactory
import ru.kesva.makechoice.di.subcomponents.MakeChoiceComponent
import ru.kesva.makechoice.extensions.fadeIn
import ru.kesva.makechoice.extensions.fadeOut
import ru.kesva.makechoice.extensions.getViewModel
import ru.kesva.makechoice.ui.viewmodel.MakeChoiceViewModel
import javax.inject.Inject

class MakeChoiceFragment : Fragment() {
    private lateinit var component: MakeChoiceComponent
    private lateinit var binding: FragmentMakeChoiceBinding
    private lateinit var makeChoiceViewModel: MakeChoiceViewModel
    private lateinit var navController: NavController

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectDependencies()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //если activity или application были уничтожены системой Android из-за нехватки памяти,
        // а пользователь находился на этом экране, то пересоздаем activity, чтобы приложение
        //не упало из-за NPE, т.к. список карточек пользователя будет пустым
        if (makeChoiceViewModel.getCardListFromCache().isNullOrEmpty()) {
            createNewActivity()
            requireActivity().finish()
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
        binding = FragmentMakeChoiceBinding.inflate(LayoutInflater.from(parentFragment?.context))
        binding.viewModel = makeChoiceViewModel
        binding.animatedGridLayout.onAnimationEndAction = ::onAnimationFinished
        return binding.root
    }

    private fun onAnimationFinished() {
        binding.newListButton.fadeIn()
        binding.newListButton.isEnabled = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = NavHostFragment.findNavController(this)
        subscribeToEvents()
        binding.lifecycleOwner = viewLifecycleOwner
        binding.toolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }
        val fullText = getText(R.string.photos_by_pexels) as SpannedString
        val spannableString = SpannableString(fullText)
        val annotations = fullText.getSpans(0, fullText.length, Annotation::class.java)
        annotations?.find { it.value =="pexels_link" }?.let {
            spannableString.apply {
                setSpan(
                    ForegroundColorSpan(
                    ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark)),
                    fullText.getSpanStart(it),
                    fullText.getSpanEnd(it),
                    0
                )
            }
        }

        binding.pexelsLink.apply {
            text = spannableString
            movementMethod = LinkMovementMethod.getInstance()
        }

    }

    private fun subscribeToEvents() {
        with(makeChoiceViewModel) {
            playAgainButtonClicked.observe(viewLifecycleOwner, {
                it.getContentIfNotHandled()?.let {
                    navController.popBackStack()
                }
            })

            startOverButtonClicked.observe(viewLifecycleOwner, {
                it.getContentIfNotHandled()?.let {
                    createNewActivity()
                }
            })

            animationStartedEvent.observe(viewLifecycleOwner, {
                it.getContentIfNotHandled()?.let {
                    binding.startAnimationButton.fadeOut()
                    binding.startAnimationButton.isEnabled = false
                }
            })

        }
    }

    private fun createNewActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        requireActivity().overridePendingTransition(R.anim.slide_in_right_animation, R.anim.slide_out_left_animation)
    }


}