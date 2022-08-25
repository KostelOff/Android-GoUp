package ru.kosteloff.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.kosteloff.R

object FragmentManager {
    var currentFragment: Fragment? = null

    fun setFragment(newFragment: Fragment, act: AppCompatActivity) {
        val transaction = act.supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        transaction.replace(R.id.placeHolder, newFragment).commit()

        currentFragment = newFragment
    }
}