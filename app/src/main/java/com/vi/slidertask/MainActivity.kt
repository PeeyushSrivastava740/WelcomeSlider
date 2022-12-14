package com.vi.slidertask

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {

    var viewPager: ViewPager2? = null

    var progress:ProgressBar?=null
    var myCustomPagerAdapter: ViewPagerAdapter? = null

    private lateinit var cardView:CardView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewPager = findViewById(R.id.idViewPager)
        cardView = findViewById(R.id.cardView)
        progress = findViewById(R.id.progress)
        myCustomPagerAdapter = ViewPagerAdapter(this@MainActivity)
        viewPager!!.adapter = myCustomPagerAdapter

        cardView.setOnClickListener {

            if (viewPager!!.adapter!!.itemCount > 2){
                if (progress!!.visibility == View.VISIBLE){
                    fakeDrag(viewPager!!,true,1200,2)
                    progress!!.progress =100
                }else{
                    fakeDrag(viewPager!!,true,1200,2)
                    progress!!.visibility = View.VISIBLE
                }
            }


        }

    }

    fun fakeDrag(viewPager: ViewPager2, leftToRight: Boolean, duration: Long, numberOfPages: Int) {
        val pxToDrag: Int = viewPager.width
        val animator = ValueAnimator.ofInt(0, pxToDrag)
        var previousValue = 0
        animator.addUpdateListener { valueAnimator ->
            val currentValue = valueAnimator.animatedValue as Int
            var currentPxToDrag: Float = (currentValue - previousValue).toFloat() * numberOfPages
            when {
                leftToRight -> {
                    currentPxToDrag *= -1
                }
            }
            viewPager.fakeDragBy(currentPxToDrag)
            previousValue = currentValue
        }
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {
                         viewPager.beginFakeDrag()
            }

            override fun onAnimationEnd(p0: Animator) {
                viewPager.endFakeDrag()
            }

            override fun onAnimationCancel(p0: Animator) {
                /* Ignored */
            }

            override fun onAnimationRepeat(p0: Animator) {
                /* Ignored */
            }
        })
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.duration = duration
        animator.start()
    }
}