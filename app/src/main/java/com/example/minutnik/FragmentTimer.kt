package com.example.minutnik

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentTimer.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentTimer : Fragment(), TimerCallback {
    // TODO: Rename and change types of parameters
    private var minutes: Int=0
    private var seconds: Int=0

    private lateinit var timer:Timer

    lateinit var minuteTens: TextView
    lateinit var minuteUnits : TextView
    lateinit var secondTens : TextView
    lateinit var secondUnits : TextView
    private var timeLeftInMillis: Long = 0

    override fun onTimerCountdown() {
        activity?.runOnUiThread{
            setFields()
        }
    }

    override fun onTimerFinish() {
        Toast.makeText(this.requireContext(), "Timer finished!", Toast.LENGTH_SHORT).show()
    }

    private fun setFields(){
        minuteTens.text=String.format("%s",timer.getMinuteTens())
        minuteUnits.text=String.format("%s",timer.getMinuteUnits())
        secondTens.text=String.format("%s",timer.getSecondTens())
        secondUnits.text=String.format("%s",timer.getSecondUnits())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            minutes = it.getInt("mins")
            seconds = it.getInt("secs")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val minTens = minutes/10
        val minUnits = minutes % 10
        var secTens = seconds/10
        val secUnits = seconds%10

        if(secTens>5)
        {
            secTens=0
            Toast.makeText(requireContext(), "Incorrect second tens value", Toast.LENGTH_SHORT).show()
        }


        timer = Timer(this,minTens,minUnits,secTens,secUnits)

        val buttonAdd1 = view.findViewById<Button>(R.id.add1)
        val buttonAdd2 = view.findViewById<Button>(R.id.add2)
        val buttonAdd3 = view.findViewById<Button>(R.id.add3)
        val buttonAdd4 = view.findViewById<Button>(R.id.add4)

        val buttonSub1 = view.findViewById<Button>(R.id.sub1)
        val buttonSub2 = view.findViewById<Button>(R.id.sub2)
        val buttonSub3 = view.findViewById<Button>(R.id.sub3)
        val buttonSub4 = view.findViewById<Button>(R.id.sub4)

        minuteTens = view.findViewById(R.id.minTen)
        minuteUnits = view.findViewById(R.id.minUnit)
        secondTens = view.findViewById(R.id.secTen)
        secondUnits = view.findViewById(R.id.secUnit)

        val buttonStart = view.findViewById<Button>(R.id.buttonStart)
        val buttonStop = view.findViewById<Button>(R.id.buttonStop)
        val buttonPause = view.findViewById<Button>(R.id.buttonPause)

        setFields()
        
        buttonStop.isEnabled=false
        buttonPause.isEnabled=false


        buttonAdd1.setOnClickListener {
            timer.increaseMinuteTens()
            minuteTens.text = String.format("%s",timer.getMinuteTens())
        }

        buttonAdd2.setOnClickListener {
            timer.increaseMinuteUnits()
            minuteUnits.text = String.format("%s",timer.getMinuteUnits())
            }

        buttonAdd3.setOnClickListener {
            timer.increaseSecondTens()
            secondTens.text = String.format("%s",timer.getSecondTens())
             }

        buttonAdd4.setOnClickListener {
            timer.increaseSecondUnits()
            secondUnits.text = String.format("%s",timer.getSecondUnits())
             }

        buttonSub1.setOnClickListener {
            timer.decreaseMinuteTens()
            minuteTens.text = String.format("%s",timer.getMinuteTens())
             }

        buttonSub2.setOnClickListener {
            timer.decreaseMinuteUnits()
            minuteUnits.text = String.format("%s",timer.getMinuteUnits())
             }

        buttonSub3.setOnClickListener {
            timer.decreaseSecondTens()
            secondTens.text = String.format("%s",timer.getSecondTens())
             }

        buttonSub4.setOnClickListener {
            timer.decreaseSecondUnits()
            secondUnits.text = String.format("%s",timer.getSecondUnits())
             }

        buttonStart.setOnClickListener{
            timeLeftInMillis=(timer.getMinuteTens()*600000
                    +timer.getMinuteUnits()*60000
                    +timer.getSecondTens()*10000
                    +timer.getSecondUnits()*1000).toLong()
            
            timer.startTime(timeLeftInMillis)
            buttonStop.isEnabled=true
            buttonPause.isEnabled=true
        }

        buttonStop.setOnClickListener{
            timer.endTime()
            timer.zeroAll()
            setFields()
            timeLeftInMillis=0
            buttonStop.isEnabled=false
            buttonPause.isEnabled=false
        }

        buttonPause.setOnClickListener{
           timer.endTime()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentTimer.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentTimer().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }
}