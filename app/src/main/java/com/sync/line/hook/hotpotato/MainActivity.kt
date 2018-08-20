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

        val timer = MyCounter(4000, 250)

        var btn_start = findViewById(R.id.btn_start) as ImageButton
        var tv_countdown = findViewById(R.id.tv_countdown) as TextView
        var cl_container = findViewById(R.id.cl_container) as ConstraintLayout

        btn_start.setOnClickListener { view ->
            timer.start()
            tv_countdown.visibility = VISIBLE

            val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce)
            view.startAnimation(bounceAnimation)
            manageBlinkEffect()

            btn_start.setEnabled(false)
        }

    }

    @SuppressLint("WrongConstant")
    fun manageBlinkEffect(){
        val anim = ObjectAnimator.ofInt(cl_container, "backgroundColor", Color.WHITE, Color.RED,
                Color.WHITE)
        anim.duration = 1500
        anim.setEvaluator(ArgbEvaluator())
        anim.repeatMode = Animation.REVERSE
        anim.repeatCount = Animation.INFINITE
        anim.start()
    }

    inner class MyCounter(millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {

        override fun onFinish() {
            println("Timer complete")
            tv_countdown.visibility = INVISIBLE;
        }

        override fun onTick(millisUntilFinished: Long) {
            if(millisUntilFinished.compareTo(1000) == 1){
                tv_countdown.text = (millisUntilFinished / 1000).toString()
            }else{
                tv_countdown.text = ("GO!")
            }

            println("Timer : " + millisUntilFinished / 1000)
        }
    }

}
