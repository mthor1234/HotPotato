package com.sync.line.hook.hotpotato

import android.animation.ArgbEvaluator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import android.view.animation.Animation
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.support.constraint.ConstraintLayout


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timer = MyCounter(16000, 250)

        var btn_start = findViewById(R.id.btn_start) as ImageButton
        var tv_countdown = findViewById(R.id.tv_countdown) as TextView
        var cl_container = findViewById(R.id.cl_container) as ConstraintLayout

        btn_start.setOnClickListener { view ->
            timer.start()

            tv_countdown.visibility = VISIBLE
            btn_start.setEnabled(false)
        }

    }

    // Blink red and white on each countdown increment
    @SuppressLint("WrongConstant")
    fun manageBlinkEffect(duration: Long, start : Boolean){
        val anim = ObjectAnimator.ofInt(cl_container, "backgroundColor", Color.WHITE, Color.RED,
                Color.WHITE)
        if(start == true) {
            anim.duration = duration
            anim.setEvaluator(ArgbEvaluator())
            anim.repeatMode = Animation.REVERSE
            anim.repeatCount = Animation.INFINITE
            anim.start()
        }else{
            anim.end()
        }
    }

    inner class MyCounter(millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {

        // This the the start timer, once the user clicks the "Go Button"
        // 3..2..1..Go!
        var timeLeft : Long = millisInFuture - 3000

        var blinkAnimation_Duration : Long = 1500



        override fun onTick(millisUntilFinished: Long) {

            if(millisUntilFinished > timeLeft){
                tv_countdown.text = ((millisUntilFinished - timeLeft + 1000) / 1000).toString()
            }else{

                if(millisUntilFinished <= timeLeft && millisUntilFinished > timeLeft - 250 ) {
                    tv_countdown.text = ("GO!")
                    println("GO!")
                    val bounceAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.bounce)
                    btn_start.startAnimation(bounceAnimation)
                    manageBlinkEffect(blinkAnimation_Duration, true)

                }else{
                    blinkAnimation_Duration = millisUntilFinished / 2
                    println("Start Countdown done!")
                    tv_countdown.visibility = INVISIBLE
                    manageBlinkEffect(blinkAnimation_Duration, true)

                    }
            }

            println("Timer : " + millisUntilFinished / 1000 + " Time Left: $timeLeft")
        }

        override fun onFinish() {
            println("Timer complete")
            manageBlinkEffect(blinkAnimation_Duration, false)
        }



    }

}
