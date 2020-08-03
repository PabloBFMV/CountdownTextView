package com.pablochp.countdowntextview

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.view_countdowntextview.view.*

class CountdownTextView(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet), CountdownEventHandler {

    //region Variables
    //TextViews
    var textDays: TextView? = null
    var textHours: TextView? = null
    var textMinutes: TextView? = null
    var textSeconds: TextView? = null
    var textColon1: TextView? = null
    var textColon2: TextView? = null
    var textColon3: TextView? = null

    //Time Values
    var days = 0
    var hours = 0
    var minutes = 0
    var seconds = 0

    //Event Handler
    var eh: CountdownEventHandler? = null

    //Countdown logic
    var countdown: Countdown? = null
    //endregion

    //region Initialization
    init {
        //We inflate the layout
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.view_countdowntextview, this)

        //We get every single textview from the layout
        textDays = view.tvDays
        textHours = view.tvHours
        textMinutes = view.tvMinutes
        textSeconds = view.tvSeconds
        textColon1 = view.tvColon1
        textColon2 = view.tvColon2
        textColon3 = view.tvColon3

        //Get the attributes object
        val attr =
            context.obtainStyledAttributes(attributeSet, R.styleable.CountdownTextView, 0, 0)

        //Get the numerical values of time
        val days = attr.getInteger(R.styleable.CountdownTextView_days, 0)
        val hours = attr.getInteger(R.styleable.CountdownTextView_days, 0)
        val minutes = attr.getInteger(R.styleable.CountdownTextView_days, 0)
        val seconds = attr.getInteger(R.styleable.CountdownTextView_days, 0)

        //Get style attributes
        val color =
            attr.getColor(R.styleable.CountdownTextView_fontColor, Color.parseColor("#000000"))
        val size = attr.getDimensionPixelSize(R.styleable.CountdownTextView_fontSize, 0)

        //Get the value of what is going to be shown if needed
        val showOnly = attr.getInteger(R.styleable.CountdownTextView_showOnly, 0)

        //Hide the textviews depending on the selected attribute
        when (showOnly) {
            1 -> {
                textHours!!.visibility = View.GONE
                textMinutes!!.visibility = View.GONE
                textSeconds!!.visibility = View.GONE
                textColon1!!.visibility = View.GONE
                textColon2!!.visibility = View.GONE
                textColon3!!.visibility = View.GONE
            }
            2 -> {
                textDays!!.visibility = View.GONE
                textMinutes!!.visibility = View.GONE
                textSeconds!!.visibility = View.GONE
                textColon1!!.visibility = View.GONE
                textColon2!!.visibility = View.GONE
                textColon3!!.visibility = View.GONE
            }
            3 -> {
                textDays!!.visibility = View.GONE
                textHours!!.visibility = View.GONE
                textSeconds!!.visibility = View.GONE
                textColon1!!.visibility = View.GONE
                textColon2!!.visibility = View.GONE
                textColon3!!.visibility = View.GONE
            }
            4 -> {
                textDays!!.visibility = View.GONE
                textHours!!.visibility = View.GONE
                textMinutes!!.visibility = View.GONE
                textColon1!!.visibility = View.GONE
                textColon2!!.visibility = View.GONE
                textColon3!!.visibility = View.GONE
            }
        }

        //Change the font size if necessary
        if (size > 0) {
            setTextSize(size)
        }

        //Change the color of the textviews
        setColor(color)
        //Set the selected time
        setTime(days, hours, minutes, seconds)
    }

    //Function to set the selected values and print them
    fun setTime(days: Int, hours: Int, minutes: Int, seconds: Int) {
        //Assing the selected values
        this.days = days
        this.hours = hours
        this.minutes = minutes
        this.seconds = seconds

        //Add a 0 the left if needed and print values
        textDays!!.text = if (days.toString().length == 1) "0$days" else days.toString()
        textHours!!.text = if (hours.toString().length == 1) "0$hours" else hours.toString()
        textMinutes!!.text = if (minutes.toString().length == 1) "0$minutes" else minutes.toString()
        textSeconds!!.text = if (seconds.toString().length == 1) "0$seconds" else seconds.toString()
    }
    //endregion

    //region Customization
    //Function to change the color of the font
    fun setColor(color: Int) {
        textDays!!.setTextColor(color)
        textHours!!.setTextColor(color)
        textMinutes!!.setTextColor(color)
        textSeconds!!.setTextColor(color)
        textColon1!!.setTextColor(color)
        textColon2!!.setTextColor(color)
        textColon3!!.setTextColor(color)
    }

    //Function to change the size of the text
    fun setTextSize(size: Int) {
        textDays!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, size.toFloat())
        textHours!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, size.toFloat())
        textMinutes!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, size.toFloat())
        textSeconds!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, size.toFloat())
        textColon1!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, size.toFloat())
        textColon2!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, size.toFloat())
        textColon3!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, size.toFloat())
    }

    //Function to set a typeface
    fun setTypeFace(typeFace: Typeface) {
        textDays!!.typeface = typeFace
        textHours!!.typeface = typeFace
        textMinutes!!.typeface = typeFace
        textSeconds!!.typeface = typeFace
        textColon1!!.typeface = typeFace
        textColon2!!.typeface = typeFace
        textColon3!!.typeface = typeFace
    }
    //endregion

    //region Countdown
    //Function to start the countdown
    fun startCountdown() {
        //We stop the countdown in case it was going
        stopCountdown()

        //We set the time to the current values
        setTime(days, hours, minutes, seconds)

        //We get tha vlues of each time unit in milliseconds
        val secMil: Long = 1000
        val minMil = 60 * secMil
        val hourMil = 60 * minMil
        val dayMil = 24 * hourMil

        //We set the values to the maximum posible setting
        if (minutes > 59) minutes = 59
        if (seconds > 59) seconds = 59
        if (hours > 23) hours = 23

        //We transform everything to milliseconds
        val milliseconds =
            days * dayMil + hours * hourMil + minutes * minMil + seconds * secMil

        //Check if there is an event handler if not we set an empty one
        var eventHandler: CountdownEventHandler? = null
        if (eh != null)
            eventHandler = eh
        else
            eh = this

        //Check if we can actually start the timer
        if (milliseconds > 0) {
            //We start the timer
            countdown = Countdown(
                milliseconds, 1000,
                textDays!!, textHours!!, textMinutes!!, textSeconds!!,
                eventHandler!!
            )
            countdown!!.start()
        }
    }

    //Function to stop the countdown
    fun stopCountdown() {
        if (countdown != null) {
            countdown!!.cancel()
            countdown = null
        }
    }
    //endregion

    //region Default Event Handler
    override fun onTick(time: Long) {
    }

    override fun onFinish() {
    }
    //endregion
}