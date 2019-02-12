package com.art.task3.presenter

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.art.task3.R
import com.art.task3.data.CalcRepositoryImpl
import com.art.task3.domain.CalcUseCase
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var calcViewModel: CalcViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calcFactory = CalcViewModelFactory(CalcUseCase(CalcRepositoryImpl()))
        calcViewModel = ViewModelProvider(this, calcFactory).get(CalcViewModel::class.java)

        calcViewModel.calcResult.observe(this, Observer { tv_result.setTextWithAnimation(it) })
        calcViewModel.previewText.observe(this, Observer { tv_expression.text = it })

        initDigitListeners()
        initOperatorListeners()

        button_equal.setOnClickListener { calcViewModel.setResultToExpression() }
        button_remove.setOnClickListener { calcViewModel.removeLastCharacter() }
        button_clear.setOnClickListener { calcViewModel.clearExpression() }
    }

    private fun initDigitListeners() {
        val digitClickListener =
            View.OnClickListener { calcViewModel.onDigitClick((it as TextView).text.toString()) }
        button_dot.setOnClickListener(digitClickListener)
        button_zero.setOnClickListener(digitClickListener)
        button_one.setOnClickListener(digitClickListener)
        button_two.setOnClickListener(digitClickListener)
        button_three.setOnClickListener(digitClickListener)
        button_four.setOnClickListener(digitClickListener)
        button_five.setOnClickListener(digitClickListener)
        button_six.setOnClickListener(digitClickListener)
        button_seven.setOnClickListener(digitClickListener)
        button_eight.setOnClickListener(digitClickListener)
        button_nine.setOnClickListener(digitClickListener)
    }

    private fun initOperatorListeners() {
        val operatorClickListener =
            View.OnClickListener { calcViewModel.onOperatorClick((it as TextView).text.toString()) }
        button_add.setOnClickListener(operatorClickListener)
        button_subtract.setOnClickListener(operatorClickListener)
        button_multiply.setOnClickListener(operatorClickListener)
        button_divide.setOnClickListener(operatorClickListener)
        button_left_parentheses.setOnClickListener(operatorClickListener)
        button_right_parentheses.setOnClickListener(operatorClickListener)
    }

    fun TextView.setTextWithAnimation(text: String?) {
        val animation = AnimationUtils.loadAnimation(this@MainActivity, R.anim.alpha_animation)
        animation.duration = 500
        animation.fillAfter = true
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
            }

            override fun onAnimationStart(animation: Animation?) {
                this@setTextWithAnimation.text = text
            }

        })
        startAnimation(animation)
    }
}
