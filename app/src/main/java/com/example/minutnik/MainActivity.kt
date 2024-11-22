package com.example.minutnik

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    private lateinit var buttonBack: Button
    private lateinit var buttonNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        buttonBack=findViewById(R.id.btnConfig)
        buttonNext=findViewById(R.id.btnTimer)

        if (savedInstanceState != null) {

            val currentFragmentTag = savedInstanceState.getString("currentFragment")

            when (currentFragmentTag) {
                "FragmentConfig" -> {

                    val minutes = savedInstanceState.getString("minutes")
                    val seconds = savedInstanceState.getString("seconds")

                    val fragment = FragmentConfig()
                    val bundle = Bundle()
                    bundle.putString("minutes", minutes)
                    bundle.putString("seconds", seconds)
                    fragment.arguments = bundle
                    replaceFragment(fragment)
                    buttonBack.isEnabled=false
                }
                "FragmentTimer" -> {
                    val fragment=FragmentTimer()

                    val minutes = savedInstanceState.getString("minutes")
                    val seconds = savedInstanceState.getString("seconds")

                    val bundle = Bundle()
                    minutes?.let {bundle.putInt("mins", it.toInt())}
                    seconds?.let { bundle.putInt("secs", it.toInt()) }
                    fragment.arguments = bundle

                    replaceFragment(fragment)
                    buttonNext.isEnabled=false
                }
            }
        }
        else {
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, FragmentConfig()).commit()
            buttonBack.isEnabled=false
        }





        buttonBack.setOnClickListener{
            replaceFragment(FragmentConfig())
            buttonBack.isEnabled=false
            buttonNext.isEnabled=true
        }

        buttonNext.setOnClickListener{
            val f = supportFragmentManager.findFragmentById(R.id.fragment_container) as? FragmentConfig
            val minutes = f?.view?.findViewById<EditText>(R.id.minutes)?.text.toString().toIntOrNull() ?: 0
            val seconds = f?.view?.findViewById<EditText>(R.id.seconds)?.text.toString().toIntOrNull() ?: 0

            val fragment = FragmentTimer()
            val bundle = Bundle()
            bundle.putInt("mins", minutes)
            bundle.putInt("secs", seconds)
            fragment.arguments = bundle

            replaceFragment(fragment)

            buttonBack.isEnabled=true
            buttonNext.isEnabled=false

        }
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment is FragmentConfig) {
            outState.putString("currentFragment", "FragmentConfig")
            outState.putString("minutes", currentFragment.minutesField.text.toString())
            outState.putString("seconds", currentFragment.secondsField.text.toString())


        } else if (currentFragment is FragmentTimer) {
            outState.putString("currentFragment", "FragmentTimer")
            outState.putString("minutes", currentFragment.minuteTens.text.toString()+currentFragment.minuteUnits.text.toString())
            outState.putString("seconds", currentFragment.secondTens.text.toString()+currentFragment.secondUnits.text.toString())
        }

    }

}