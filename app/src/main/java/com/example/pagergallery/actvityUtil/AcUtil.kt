package com.example.pagergallery.actvityUtil

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

/**
 * 装载fragment
 *
 *
 * */
class AcUtil {

    private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commit()
    }

    fun FragmentActivity.addFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction { add(frameId, fragment) }
    }


    fun FragmentActivity.replaceFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction { replace(frameId, fragment) }
    }
}