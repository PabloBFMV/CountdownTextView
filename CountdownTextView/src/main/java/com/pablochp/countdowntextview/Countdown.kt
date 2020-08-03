package com.pablochp.countdowntextview

import android.os.CountDownTimer
import android.widget.TextView

class Countdown(
    millisInFuture: Long,
    countDownInterval: Long,
    var daysText: TextView,
    var hoursText: TextView,
    var minutesText: TextView,
    var secondsText: TextView,
    var eh: CountdownEventHandler
) :
    CountDownTimer(millisInFuture, countDownInterval) {

    //region Countdown Overrides
    override fun onFinish() {
        //Set al the values to 0
        daysText.text = "00"
        hoursText.text = "00"
        minutesText.text = "00"
        secondsText.text = "00"
        eh.onFinish()
    }

    override fun onTick(millisUntilFinished: Long) {
        getReminder(millisUntilFinished)
        eh.onTick(millisUntilFinished)
    }
    //endregion

    //Function to get the reminder of the time in the countdown
    private fun getReminder(time: Long) {
        //Values of the new measurements
        var days = 0
        var hours = 0
        var minutes = 0
        val seconds: Int
        //Value of each time measurement in milliseconds
        val secMil = 1000
        val minMil = 60 * secMil
        val hourMil = 60 * minMil
        val dayMil = 24 * hourMil
        //Value of the measurement currently displayed
        val currentDays: Int = daysText.text.toString().trim { it <= ' ' }.toInt()
        val currentHours: Int = hoursText.text.toString().trim { it <= ' ' }.toInt()
        val currentMinutes: Int = minutesText.text.toString().trim { it <= ' ' }.toInt()

        //Convert the new time to int
        if (time >= dayMil) {
            days = (time / dayMil).toInt()
            hours = (time % dayMil / hourMil).toInt()
            minutes = (time % dayMil % hourMil / minMil).toInt()
            seconds = (time % dayMil % hourMil % minMil / secMil).toInt()
        } else if (time >= hourMil) {
            hours = (time / hourMil).toInt()
            minutes = (time % hourMil / minMil).toInt()
            seconds = (time % hourMil % minMil / secMil).toInt()
        } else if (time >= minMil) {
            minutes = (time / minMil).toInt()
            seconds = (time % minMil / secMil).toInt()
        } else {
            seconds = (time / secMil).toInt()
        }

        //Check which values changed
        if (currentDays != days) {
            daysText.text = if (days.toString().length == 1) "0$days" else days.toString()
        }
        if (currentHours != hours) {
            hoursText.text = if (hours.toString().length == 1) "0$hours" else hours.toString()
        }
        if (currentMinutes != minutes) {
            minutesText.text =
                if (minutes.toString().length == 1) "0$minutes" else minutes.toString()
        }

        //Change the seconds because they obviously changed
        secondsText.text = if (seconds.toString().length == 1) "0$seconds" else seconds.toString()
    }
}