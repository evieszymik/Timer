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

        supportFragmentManager.beginTransaction().add(R.id.fragment_container, FragmentConfig()).commit()

        buttonBack.setOnClickListener{
            replaceFragment(FragmentConfig())
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
        }
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }
}