package com.peruapps.icnclient.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.peruapps.icnclient.R
import com.peruapps.icnclient.extensions.onItemSelected
import com.peruapps.icnclient.extensions.safeValue
import com.peruapps.icnclient.extensions.toDate
import java.text.DateFormatSymbols
import java.util.*

class CustomCalendarView @JvmOverloads constructor(context: Context,
                                                   attrs: AttributeSet? = null,
                                                   defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr) {

    private val DAY_OF_THE_WEEK_TEXT = "dayOfTheWeekText"
    private val DAY_OF_THE_WEEK_LAYOUT = "dayOfTheWeekLayout"
    private val DAY_OF_THE_MONTH_LAYOUT = "dayOfTheMonthLayout"
    private val DAY_OF_THE_MONTH_TEXT = "dayOfTheMonthText"
    private val DAY_OF_THE_MONTH_BACKGROUND = "dayOfTheMonthBackground"
    private lateinit var spMonths: Spinner
    private lateinit var _rootView: View
    private var currentCalendar = Calendar.getInstance()
    private var calendarListener: CustomCalendarListener? = null
    /**
     * Temporal list for save the selected dates
     * Is public because you are going to ask for it on the view
     * when is need.
     * Is initialized as null because we don´t want to use memory if no date is selected.
     */
    var selectedDatesList : ArrayList<String>? = null

    private val onDayOfMonthClickListener = OnClickListener { dayView ->

        // Extract day selected
        val dayOfTheMonthContainer = dayView as ViewGroup
        var tagId = dayOfTheMonthContainer.tag as String
        tagId = tagId.substring(DAY_OF_THE_MONTH_LAYOUT.length, tagId.length)
        val dayOfTheMonthText: TextView = dayView.findViewWithTag(DAY_OF_THE_MONTH_TEXT + tagId)

        // Extract the day from the text
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, currentCalendar.get(Calendar.YEAR))
        calendar.set(Calendar.MONTH, currentCalendar.get(Calendar.MONTH))
        calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(dayOfTheMonthText.text.toString()))

        // Depending on your needs, you would have to set
        // a background instead of backgroundColor.
        val color = if (shouldPaintDateView(calendar.time.time.toDate())) Color.parseColor("#37d1dc") else Color.parseColor("#ffffff")
        dayOfTheMonthText.setBackgroundColor(color)

        // Fire event
        checkNotNull(calendarListener) { "You must assign a valid CustomCalendarListener first!" }
        calendarListener?.onDayClicked(calendar.time)
    }

    init {
        if(!isInEditMode) {
            val inflate = getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            _rootView = inflate.inflate(R.layout.calendar_view_layout, this, true)
            createViews(_rootView)
            handleMonthClicked()
            currentCalendar = Calendar.getInstance()
            updateView()
        }
    }

    /**
     * Verify if we have to paint the background view of the date.
     * First, search if [selectedDate] is already stored in [selectedDatesList]
     * If it is, it get´s removed from [selectedDatesList] and return false.
     * If is not stored, then [selectedDatesList] add [selectedDate] and return true.
     *
     * @param selectedDate the date selected from the calendar.
     * @return [Boolean] true for paint the view, false for "clear" the background.
     */
    private fun shouldPaintDateView(selectedDate: String) : Boolean {
        // Initialize selected dates if null.
        if (selectedDatesList == null) selectedDatesList = ArrayList()
        // Find if the selectedDate is already in our temporal list of dates.
        // If is not in the list it will return null. If there is a date that match the selected date
        // will return the date object.
        val dateToAdd = selectedDatesList?.find { it == selectedDate }
        return if (dateToAdd != null){
            // If dateToAdd is already on the list. It means the client want´s to
            // Unselected so we have to remove it from our temporal list.
            selectedDatesList?.remove(dateToAdd)
            false
        } else {
            // if dateToAdd is null means that there is no matching dates on our temp array
            // so we need to add it and pint the view with a "selected" date color.
            selectedDatesList?.add(selectedDate)
            true
        }
    }

    fun setDayClickedListener(customCalendarListener: CustomCalendarListener) {
        calendarListener = customCalendarListener
    }

    private fun updateView() {
        setUpWeekDaysLayout()
        setUpDaysOfMonthLayout()
        setUpDaysInCalendar()
    }

    private fun getWeekIndex(weekIndex: Int, currentCalendar: Calendar) : Int {
        return if (currentCalendar.firstDayOfWeek == 1){
            weekIndex
        } else {
            if (weekIndex == 1){
                7
            } else {
                weekIndex - 1
            }
        }
    }

    private fun createViews(view: View) {
        spMonths = view.findViewById(R.id.spMonths)
        val monthAdapter = ArrayAdapter.createFromResource(_rootView.context, R.array.months, R.layout.item_simple_spinner)
            .also {
                it.setDropDownViewResource(R.layout.item_simple_spinner)
            }
        spMonths.adapter = monthAdapter

        val inflate = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        for (i in 0..41) {
            val weekIndex = i % 7 + 1
            val dayOfTheWeekLayout = view.findViewWithTag<ViewGroup>(DAY_OF_THE_WEEK_LAYOUT + weekIndex)

            // Create day of the month
            val dayOfTheMonthLayout: View = inflate.inflate(R.layout.calendar_day_of_the_month_layout, dayOfTheWeekLayout, false)
            val dayOfTheMonthText : View = dayOfTheMonthLayout.findViewWithTag(DAY_OF_THE_MONTH_TEXT)
            val dayOfTheMonthBackground : View = dayOfTheMonthLayout.findViewWithTag(DAY_OF_THE_MONTH_BACKGROUND)

            // Set tags to identify them
            val viewIndex = i + 1
            dayOfTheMonthLayout.tag = DAY_OF_THE_MONTH_LAYOUT + viewIndex
            dayOfTheMonthText.tag = DAY_OF_THE_MONTH_TEXT + viewIndex
            dayOfTheMonthBackground.tag = DAY_OF_THE_MONTH_BACKGROUND + viewIndex
            dayOfTheWeekLayout.addView(dayOfTheMonthLayout)
        }
    }

    private fun handleMonthClicked() {
        spMonths.safeValue {
            it.onItemSelected { monthPosition ->
                currentCalendar.set(Calendar.MONTH, monthPosition)
                updateView()
            }
            it.setSelection(currentCalendar.get(Calendar.MONTH))
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUpWeekDaysLayout() {
        var tvDayOfWeek: TextView?
        var dayOfTheWeekString: String
        val weekDaysArray = DateFormatSymbols(Locale.getDefault()).weekdays
        for (i in 1 until weekDaysArray.size) {
            tvDayOfWeek =_rootView.findViewWithTag(DAY_OF_THE_WEEK_TEXT + getWeekIndex(i, currentCalendar))
            dayOfTheWeekString = weekDaysArray[i]
            //result = Lun/ Mar/ Mie/ Jue/ Vie...
            tvDayOfWeek?.text = "${dayOfTheWeekString.substring(0, 1).toUpperCase()}${dayOfTheWeekString.substring(1, 3)}"
        }
    }

    private fun setUpDaysOfMonthLayout() {
        var dayOfTheMonthText: TextView?
        var dayOfTheMonthContainer: ViewGroup?
        var dayOfTheMonthBackground: ViewGroup?

        //Include the 42 in the loop.
        for (i in 1..42) {
            dayOfTheMonthContainer =_rootView.findViewWithTag(DAY_OF_THE_MONTH_LAYOUT + i)
            dayOfTheMonthBackground =_rootView.findViewWithTag(DAY_OF_THE_MONTH_BACKGROUND + i)
            dayOfTheMonthText =_rootView.findViewWithTag(DAY_OF_THE_MONTH_TEXT + i)
            dayOfTheMonthText.isInvisible = true

            // Apply styles
            dayOfTheMonthText?.setBackgroundResource(android.R.color.transparent)
            dayOfTheMonthText?.setTypeface(null, Typeface.NORMAL)
            dayOfTheMonthContainer?.setBackgroundResource(android.R.color.transparent)
            dayOfTheMonthContainer?.setOnClickListener(null)
            dayOfTheMonthBackground?.setBackgroundResource(android.R.color.transparent)
        }
    }

    private fun setUpDaysInCalendar() {
        val auxCalendar = Calendar.getInstance(Locale.getDefault())
        auxCalendar.time = currentCalendar.time
        auxCalendar.set(Calendar.DAY_OF_MONTH, 1)
        val firstDayOfMonth = auxCalendar.get(Calendar.DAY_OF_WEEK)
        var dayOfTheMonthText: TextView?
        var dayOfTheMonthContainer: ViewGroup?
        var dayOfTheMonthLayout: ViewGroup?

        // Calculate dayOfTheMonthIndex
        var dayOfTheMonthIndex = getWeekIndex(firstDayOfMonth, auxCalendar)

        run {
            var i = 1
            while (i <= auxCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                dayOfTheMonthContainer = rootView.findViewWithTag(DAY_OF_THE_MONTH_LAYOUT + dayOfTheMonthIndex)
                dayOfTheMonthText = rootView.findViewWithTag(DAY_OF_THE_MONTH_TEXT + dayOfTheMonthIndex)
                if (dayOfTheMonthText == null) {
                    break
                }
                dayOfTheMonthContainer?.setOnClickListener(onDayOfMonthClickListener)
                dayOfTheMonthText?.visibility = View.VISIBLE
                dayOfTheMonthText?.text = "$i"
                i++
                dayOfTheMonthIndex++
            }
        }

        for (i in 36..42) {
            dayOfTheMonthText =_rootView.findViewWithTag(DAY_OF_THE_MONTH_TEXT + i)
            dayOfTheMonthLayout =_rootView.findViewWithTag(DAY_OF_THE_MONTH_LAYOUT + i)
            if (dayOfTheMonthText != null && dayOfTheMonthLayout != null)
                if (dayOfTheMonthText!!.isInvisible) {
                    dayOfTheMonthLayout.isGone = true
                } else {
                    dayOfTheMonthLayout.isVisible = true
                }
        }
    }

    /**
     * This interface communicates the parent container (activity/fragment) that fragment
     * has been clicked.
     */
    interface CustomCalendarListener {
        fun onDayClicked(date: Date)
    }
}