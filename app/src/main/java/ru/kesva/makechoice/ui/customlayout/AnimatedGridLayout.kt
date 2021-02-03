package ru.kesva.makechoice.ui.customlayout

import android.animation.*
import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.Log
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.GridLayout
import androidx.core.animation.doOnEnd
import androidx.core.view.marginLeft
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_for_card.view.*
import ru.kesva.makechoice.R
import ru.kesva.makechoice.domain.model.Card
import kotlin.random.Random

class AnimatedGridLayout : GridLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    var onAnimationEndAction: (() -> Unit)? = null

    val childViewList = mutableListOf<View>()
    private var oneAnimationDuration = 400L

    private var _cardList: List<Card>? = null
    private val cardList: List<Card>
        get() {
            _cardList?.let {
                return it
            }
            throw NoItemsException("Cardlist is null")
        }

    private var result: Int = 0

    fun setCardList(list: List<Card>) {
        Log.d("Test", "setCardList: ")
        if (list.isEmpty()) {
            throw NoItemsException("The list should't be empty")
        }
        if (list.size > 9) {
            throw IllegalArgumentException("The list size should be less than 9")
        }
        _cardList = list
        result = getWinningCard()
        refresh()
    }

    private fun getWinningCard(): Int {
        return Random.nextInt(0, cardList.size)
    }

    private fun refresh() {
        Log.d("Test", "refresh: ")
        removeAllViews()
        createChildren()
        calculateGrid()
        centerLastChildrenSizeIfNeeded()
    }

    private fun createChildren() {
        Log.d("Test", "createChildren: ")
        cardList.forEach {
            val view = inflateChild(it)
            addView(view)
            childViewList.add(view)
        }
    }

    private fun inflateChild(card: Card): View {
        Log.d("Test", "inflateChild: ")
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.layout_for_card, this, false)
        val rowSpec = spec(UNDEFINED, 1f)
        val columnSpec = spec(UNDEFINED, 1f)
        val params = LayoutParams(rowSpec, columnSpec)
        params.width = 0
        params.height = LayoutParams.WRAP_CONTENT
        params.setMargins(
            dpToPx(4).toInt(),
            dpToPx(4).toInt(),
            dpToPx(4).toInt(),
            dpToPx(4).toInt()
        )
        view.textForCard.text = card.query
        Glide
            .with(this)
            .load(card.uri)
            .into(view.imageForCard)
        view.layoutParams = params
        return view
    }

    private fun calculateGrid() {
        rowCount = 3
        columnCount = if (cardList.size <= 4) {
            2
        } else {
            3
        }
    }

    private fun centerLastChildrenSizeIfNeeded() {
        val size = cardList.size
        val isOdd = size % 2 != 0

        val isNeeded = isOdd || size == 8
        if (isNeeded) {
            post(::centerLastChildren)
        }
    }

    private fun centerLastChildren() {
        when (cardList.size) {
            3 -> {
                val lastView = getChildAt(2)
                val translation = (lastView.width / 2).toFloat()
                lastView.translationX = translation

            }
            5 -> {
                val thirdView = getChildAt(3)
                val fourthView = getChildAt(4)
                val translationForThird = (thirdView.width / 2).toFloat()
                thirdView.translationX = translationForThird
                val translationForFourth = (fourthView.width / 2).toFloat()
                fourthView.translationX = translationForFourth
            }
            7 -> {
                val sixthView = getChildAt(6)
                val translationForSixth = (sixthView.width + sixthView.marginLeft * 2).toFloat()
                sixthView.translationX = translationForSixth
            }
            8 -> {
                val sixthView = getChildAt(6)
                val translationForSixth = (sixthView.width / 2).toFloat()
                sixthView.translationX = translationForSixth
                val seventhView = getChildAt(7)
                val translationForSeventh = (seventhView.width / 2).toFloat()
                seventhView.translationX = translationForSeventh
            }
        }
    }

    private fun dpToPx(dp: Int): Float {
        return dp * Resources.getSystem().displayMetrics.density
    }

    var round = 1
    var cardIndex = 0

    fun startViewAnimation(index: Int) {
        Log.d("asdf", "startViewAnimation: cardIndex=$cardIndex round=$round")
        val childView = getChildAt(index) as View
        Log.d("asdf", "startViewAnimation before setUpAnimators: cardIndex increased to=$cardIndex")
        setUpAnimatorsForView(childView)

    }

    private fun setUpAnimatorsForView(view: View) {
        Log.d("asdf", "setUpAnimatorForView: ")
        // 1. создать анимацию поднятия
        val animatorUp = createCardAnimationUp(view)

        // 3. создать анимацию опускания
        val animatorDown = createCardAnimationDown(view)

        // 2. прилепить на ее окончание листенер с вызовом рекурсивного метода startViewAnimation
        val shouldStop = round == 6 && cardIndex == result
        Log.d("asdf", "setUpAnimatorsForView: after shouldStop==true $cardIndex")
        if (cardIndex >= cardList.size - 1) {
            round++
            cardIndex = -1
            if (round < 4 && oneAnimationDuration > 200) {
                oneAnimationDuration -= 100L
            }
            if (round >= 4) {
                oneAnimationDuration += 100L
            }
        }
        cardIndex++
        if (!shouldStop) {
            Log.d(
                "asdf",
                "before starting animatorDown in animatorUpListener: cardIndex=$cardIndex result=$result"
            )
            animatorUp.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    Log.d("asdf", "onAnimationEnd: before startViewAnimation round $round")
                    startViewAnimation(cardIndex)
                    animatorDown.start()
                }
            })
        } else {
            showWinningCard(view)
        }
        animatorUp.start()
    }

    private fun showWinningCard(view: View) {
        val container = view.parent as ViewGroup
        val parentCenterWidth = (container.width / 2).toFloat()
        val parentCenterHeight = (container.height / 2).toFloat()
        val childCenterHeight = (view.height / 2).toFloat()
        val childCenterWidth = (view.width / 2).toFloat()
        val x = PropertyValuesHolder.ofFloat(View.X, parentCenterWidth - childCenterWidth)
        val y = PropertyValuesHolder.ofFloat(View.Y, parentCenterHeight - childCenterHeight)
        val animator = ObjectAnimator.ofPropertyValuesHolder(view, x, y)
        animator.duration = 1200
        animator.startDelay = 1200
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                for (item in childViewList) {
                    if (item != view) {
                        val itemAnimator = ObjectAnimator.ofFloat(item, View.ALPHA, 0f)
                        itemAnimator.duration = 400
                        itemAnimator.start()
                    } else {
                       val displayWidth = (resources.displayMetrics.widthPixels).toFloat() * 0.6f
                        Log.d("TAG", "onAnimationEnd: displayWidth $displayWidth")
                        val diffX = displayWidth / view.width
                        Log.d("TAG", "onAnimationEnd: diffX $diffX")
                        view.animate().scaleX(diffX).scaleY(diffX)

                    }
                }
            }
        })
        animator.start()
        animator.doOnEnd {
            onAnimationEndAction?.invoke()
        }
    }

    private fun startAnimationWinningCard(view: View) {
        val displayHeight = resources.displayMetrics.heightPixels * 0.4
        val  displayWidth = resources.displayMetrics.widthPixels * 0.4
        val startingSize = Size(view.width, view.height)
        val targetSize = Size(displayWidth.toInt(), displayHeight.toInt())
        // возможно проблема в pivotX и pivotY. Нужно поставить их на центр экрана.
        val evaluator =
            TypeEvaluator<Size> { fraction, startValue, endValue ->
                val widthValue = (startValue.width + (endValue.width - startValue.width) * fraction).toInt()
                val heightValue = (startValue.height + (endValue.height - startValue.height) * fraction).toInt()
                Size(widthValue, heightValue)
            }

        ValueAnimator.ofObject(evaluator, startingSize, targetSize).apply {
            addUpdateListener { valueAnimator ->
                val animObject = valueAnimator.animatedValue as Size
                view.x -= ((animObject.width - view.width).toFloat() / 2)
                view.y -= ((animObject.height - view.height).toFloat() / 2)
                view.layoutParams.width = animObject.width
                view.layoutParams.height = animObject.height
                view.requestLayout()

            }
            duration = 20000
        }.start()
    }

    /*     val width = container.width / 2
                             val height = LayoutParams.WRAP_CONTENT
                             val params = LayoutParams()
                             params.width = width
                             params.height = height
                             view.layoutParams = params*/

    /* val anim = ValueAnimator.ofInt(view.measuredWidth, container.width / 4)
     anim.addUpdateListener { valueAnimator ->
         val animValue = valueAnimator?.animatedValue as Int
         val layoutParams = view.layoutParams
         layoutParams.width = animValue
         layoutParams.height = LayoutParams.WRAP_CONTENT
         view.layoutParams = layoutParams
     }
     anim.duration = 1000
     anim.start()*/

    private fun scaleView(view: View, startScale: Float, endScale: Float) {
        val animation: Animation = ScaleAnimation(
            1f, 1f,
            startScale, endScale,
            Animation.ABSOLUTE, 2f,
            Animation.ABSOLUTE, 2f
        )
        animation.fillAfter = true
        animation.duration = 1000
        view.startAnimation(animation)

    }

    private fun createCardAnimationUp(view: View): Animator {
        val scaleXUp = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.4f)
        val scaleYUp = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.4f)
        val scaleZUp = PropertyValuesHolder.ofFloat(View.TRANSLATION_Z, 30f)
        val animatorUp = ObjectAnimator.ofPropertyValuesHolder(view, scaleXUp, scaleYUp, scaleZUp)
        animatorUp.duration = oneAnimationDuration
        animatorUp.interpolator = AccelerateDecelerateInterpolator()
        return animatorUp
    }

    private fun createCardAnimationDown(view: View): Animator {
        val scaleXDown = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.0f)
        val scaleYDown = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.0f)
        val scaleZDown = PropertyValuesHolder.ofFloat(View.TRANSLATION_Z, -30f)
        val animatorDown =
            ObjectAnimator.ofPropertyValuesHolder(view, scaleXDown, scaleYDown, scaleZDown)
        animatorDown.duration = oneAnimationDuration
        animatorDown.interpolator = AccelerateDecelerateInterpolator()
        return animatorDown
    }

}

class NoItemsException(msg: String) : Exception(msg)