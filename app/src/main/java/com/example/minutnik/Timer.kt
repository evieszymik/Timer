package com.example.minutnik

import android.os.CountDownTimer
import android.widget.Toast

class Timer(private val fragment: FragmentTimer,private var minuteTens: Int=0, private var minuteUnits: Int=0,
            private var secondTens: Int=0, private var secondUnits: Int=0) {
    
    private val maxVal: Int=9
    private val maxVal2: Int=5
    private val minVal: Int =0
    private lateinit var counter: CountDownTimer

    fun increaseMinuteTens(){
        setMinuteTens(this.minuteTens +1)
    }

    fun increaseMinuteUnits(){
        setMinuteUnits(this.minuteUnits +1)
    }

    fun increaseSecondTens(){
        setSecondTens(this.secondTens +1)
    }

    fun increaseSecondUnits(){
        setSecondUnits(this.secondUnits +1)
    }

    fun decreaseMinuteTens(){
        setMinuteTens(this.minuteTens -1)
    }

    fun decreaseMinuteUnits(){
        setMinuteUnits(this.minuteUnits -1)
    }

    fun decreaseSecondTens(){
        setSecondTens(this.secondTens -1)
    }

    fun decreaseSecondUnits(){
        setSecondUnits(this.secondUnits -1)
    }

    fun countDown(){
        if(secondUnits>minVal)
            setSecondUnits(secondUnits-1)
        else if(secondTens>minVal){
            setSecondTens(secondTens-1)
            setSecondUnits(maxVal)
        }
        else if(minuteUnits>minVal){
            setMinuteUnits(minuteUnits-1)
            setSecondTens(5)
            setSecondUnits(maxVal)
        }
        else{
            setMinuteTens(minuteTens-1)
            setMinuteUnits(maxVal)
            setSecondTens(5)
            setSecondUnits(maxVal)
        }
    }

    fun startTime(timeLeftInMillis: Long){
        counter = object : CountDownTimer(timeLeftInMillis, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                countDown()
                fragment.activity?.runOnUiThread {
                        fragment.setFields()
                    }
            }

            override fun onFinish() {
                fragment.activity?.runOnUiThread {
                    Toast.makeText(fragment.requireContext(), "Timer finished!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        counter.start()
    }
    fun endTime(){
        counter.cancel()
    }
    
    fun zeroAll(){
        setMinuteTens(minVal)
        setMinuteUnits(minVal)
        setSecondTens(minVal)
        setSecondUnits(minVal)
    }

    fun getMinuteTens(): Int{
        return minuteTens
    }
    fun getMinuteUnits(): Int{
        return minuteUnits
    }
    fun getSecondTens(): Int{
        return secondTens
    }
    fun getSecondUnits(): Int{
        return secondUnits
    }

    private fun setMinuteTens(x: Int){
        if(x in minVal..maxVal)
            this.minuteTens=x
    }
    private fun setMinuteUnits(x: Int){
        if(x in minVal..maxVal)
            this.minuteUnits=x
    }
    private fun setSecondTens(x: Int){
        if(x in minVal..maxVal2)
            this.secondTens=x
    }
    private fun setSecondUnits(x: Int){
        if(x in minVal..maxVal)
            this.secondUnits=x
    }
}