package ru.kesva.makechoice.ui.customlayout

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.core.view.marginLeft
import com.bumptech.glide.Glide
import ru.kesva.makechoice.R
import ru.kesva.makechoice.domain.model.Card
import kotlin.random.Random
import kotlinx.android.synthetic.main.layout_for_card.view.*

class AnimatedGridLayout : GridLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    val childViewList = mutableListOf<View>()
    var oneAnimationDuration = 400L

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
        result = chooseCard()
        refresh()
    }

    fun chooseCard(): Int {
        return Random.nextInt(0, cardList.size)
    }

    private fun refresh() {
        Log.d("Test", "refresh: ")
        removeAllViews()
        createChildren()
        calculateGrid()
        centerLastChildrenSizeIfNeeded()
        setPaddingIfCardListTooSmall()
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

    private fun setPaddingIfCardListTooSmall() {
        val isSmall = cardList.size == 2 || cardList.size == 3 || cardList.size == 4
        if (isSmall) {
            Log.d("padd", "setPaddingIfCardListTooSmall: ${cardList.size}")
            val container = parent as ViewGroup
            container.setPadding(
                dpToPx(8).toInt(),
                dpToPx(42).toInt(),
                dpToPx(8).toInt(),
                dpToPx(8).toInt()
            )
        }

    }


    private fun dpToPx(dp: Int): Float {
        return dp * Resources.getSystem().displayMetrics.density
    }


}

class NoItemsException(msg: String) : Exception(msg)