package com.ahead.assingment.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ahead.assingment.R
import com.ahead.assingment.fragment.MenuFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, MenuFragment())
                .commit()
        }
    }
}

