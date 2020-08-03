package com.pablochp.countdowntextviewtest

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.pablochp.countdowntextview.CountdownEventHandler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CountdownEventHandler {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Set initial time
        tvCount.setTime(10,9,1,10)

        //Set the event handler
        tvCount.eh = this

        //Set a custom typeface
        tvCount.setTypeFace(Typeface.createFromAsset(assets, "fonts/ComicNeueBold.ttf"))

        //We start the countdown
        tvCount.startCountdown()
    }

    override fun onTick(time: Long) {
        //Do something...
    }

    override fun onFinish() {
        //do something...
    }
}