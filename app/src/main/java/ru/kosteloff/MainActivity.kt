package ru.kosteloff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.kosteloff.fragments.DaysFragment
import ru.kosteloff.utils.FragmentManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FragmentManager.setFragment(DaysFragment.newInstance(), this)
    }
}