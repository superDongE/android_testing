package com.example.layouttest

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.databinding.DataBindingUtil
import com.example.layouttest.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import java.lang.Math.abs

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    var scrollType: Boolean = false // scroll status
    var tabType : Boolean = false // tab click status

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        Log.d("superDOngE", "onCreate() run")

        binding.mainTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            @SuppressLint("ClickableViewAccessibility")
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // 탭 상태가 선택 상태로 변경
                Log.d("superDOngE", "position: ${tab?.position}")
                tabType = true
                when (tab?.position) {
                    0 -> {
//                        scrollToRow(bind.mainScroll, bind.mainLinear, bind.tv1)
                        binding.mianScroll.smoothScrollToView(binding.tv1)
                        tabType = false
                    }
                    1 -> {
//                        scrollToRow(bind.mainScroll, bind.mainLinear, bind.tv2)
                        binding.mianScroll.smoothScrollToView(binding.tv2)
                        tabType = false
                    }

                    2 -> {
//                        scrollToRow(bind.mainScroll, bind.mainLinear, bind.tv3)
                        binding.mianScroll.smoothScrollToView(binding.tv3)
                        tabType = false
                    }

                    3 -> {
//                        scrollToRow(bind.mainScroll, bind.mainLinear, bind.tv4)
                        binding.mianScroll.smoothScrollToView(binding.tv4)
                        tabType = false
                    }

                    4 -> {
//                        scrollToRow(bind.mainScroll, bind.mainLinear, bind.tv5)
                        binding.mianScroll.smoothScrollToView(binding.tv5)
                        tabType = false
                    }

                    5 -> {
//                        scrollToRow(bind.mainScroll, bind.mainLinear, bind.tv6)
                        binding.mianScroll.smoothScrollToView(binding.tv6)
                        tabType = false
                    }

                    else -> {
//                        scrollToRow(bind.mainScroll, bind.mainLinear, bind.tv7)
                        binding.mianScroll.smoothScrollToView(binding.tv7)
                        tabType = false
                    }
                }


                // touch listener
                binding.mianScroll.setOnTouchListener { _, event ->
                    when (event?.action) {
                        // Motion active false
                        // 모션 동작이 마무리되어 손가락이 사용자를 떠남
                        MotionEvent.ACTION_UP -> {
                            Log.d("superDOngE", "ACTION_UP")
                            val handler = Handler(mainLooper)
                            handler.postDelayed(
                                {scrollType = true}, 400
                            )
                            false
                        }
                        else -> {
                            Log.d("superDOngE", "else : ${event?.action}")
                            scrollType = false
                            false
                        }

                    }
                }


                // 스크롤 리스너를 달아준다.
                binding.mianScroll.viewTreeObserver.addOnScrollChangedListener {
                    val scrollY = binding.mianScroll.scrollY // Y value
                    Log.d("superDOngE", "scrollType : $scrollType, tabType: $tabType")

                    if (!scrollType && !tabType) {
                        when (scrollY) {
                            in binding.tv1.top..binding.tv1.bottom -> {
                                binding.mainTab.getTabAt(0)?.select()
//                                binding.mainTab.getTabAt(0)?.select()
                                //                        bind.mainTab.getTabAt(0)?.isSelected
                            }
                            in binding.tv2.top..binding.tv2.bottom -> {
                                binding.mainTab.getTabAt(1)?.select()
                                //                        bind.mainTab.getTabAt(1)?.isSelected
                            }
                            in binding.tv3.top..binding.tv3.bottom -> {
                                binding.mainTab.getTabAt(2)?.select()
                                //                        bind.mainTab.getTabAt(2)?.isSelected
                            }
                            in binding.tv4.top..binding.tv4.bottom -> {
                                binding.mainTab.getTabAt(3)?.select()
                                //                        bind.mainTab.getTabAt(3)?.isSelected
                            }
                            in binding.tv5.top..binding.tv5.bottom -> {
                                binding.mainTab.getTabAt(4)?.select()
                                //                        bind.mainTab.getTabAt(4)?.isSelected
                            }
                            in binding.tv6.top..binding.tv6.bottom -> {
                                binding.mainTab.getTabAt(5)?.select()
                                //                        bind.mainTab.getTabAt(5)?.isSelected
                            }
                            in binding.tv7.top..binding.tv7.bottom -> {
                                binding.mainTab.getTabAt(6)?.select()
                                //                        bind.mainTab.getTabAt(6)?.isSelected
                            }
                            else -> {
                                Log.d("t", "not Working")
                            }

                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Log.d("t", "test working")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Log.d("t", "test working")
            }

            private fun ScrollView.computeDistanceToView(view: View): Int {
                return abs(
                    calculateRectOnScreen(this).top - (this.scrollY + calculateRectOnScreen(
                        view
                    ).top)
                )
            }

            private fun calculateRectOnScreen(view: View): Rect {
                val location = IntArray(2)
                view.getLocationOnScreen(location)
                return Rect(
                    location[0],
                    location[1],
                    location[0] + view.measuredWidth,
                    location[1] + view.measuredHeight
                )
            }

            fun ScrollView.smoothScrollToView(
                view: View,
                marginTop: Int = 0,
                maxDuration: Long = 500L,
                onEnd: () -> Unit = {}
            ) {
                if (this.getChildAt(0).height <= this.height) { // 스크롤의 의미가 없다.
                    onEnd()
                    return
                }
                val y = computeDistanceToView(view) - marginTop
                val ratio =
                    abs(y - this.scrollY) / (this.getChildAt(0).height - this.height).toFloat()
                ObjectAnimator.ofInt(this, "scrollY", y).apply {
                    duration = (maxDuration * ratio).toLong()
                    interpolator = AccelerateDecelerateInterpolator()
                    doOnEnd {
                        onEnd()
                    }
                    start()
                }
            }
        })
    }


    private fun checkPostion(scrollY: Int) {

        when (scrollY) {
            in binding.tv1.top..binding.tv1.bottom -> {
                binding.mainTab.getTabAt(0)?.select()
//                                binding.mainTab.getTabAt(0)?.select()
                //                        bind.mainTab.getTabAt(0)?.isSelected
            }
            in binding.tv2.top..binding.tv2.bottom -> {
                binding.mainTab.getTabAt(1)?.select()
                //                        bind.mainTab.getTabAt(1)?.isSelected
            }
            in binding.tv3.top..binding.tv3.bottom -> {
                binding.mainTab.getTabAt(2)?.select()
                //                        bind.mainTab.getTabAt(2)?.isSelected
            }
            in binding.tv4.top..binding.tv4.bottom -> {
                binding.mainTab.getTabAt(3)?.select()
                //                        bind.mainTab.getTabAt(3)?.isSelected
            }
            in binding.tv5.top..binding.tv5.bottom -> {
                binding.mainTab.getTabAt(4)?.select()
                //                        bind.mainTab.getTabAt(4)?.isSelected
            }
            in binding.tv6.top..binding.tv6.bottom -> {
                binding.mainTab.getTabAt(5)?.select()
                //                        bind.mainTab.getTabAt(5)?.isSelected
            }
            in binding.tv7.top..binding.tv7.bottom -> {
                binding.mainTab.getTabAt(6)?.select()
                //                        bind.mainTab.getTabAt(6)?.isSelected
            }
            else -> {
                Log.d("t", "not Working")
            }
        }
    }
}