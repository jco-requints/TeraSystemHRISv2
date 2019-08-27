package com.example.bottomnavigation.helper

import androidx.fragment.app.Fragment
import com.example.bottomnavigation.ui.HomeFragment
import com.example.bottomnavigation.ui.NotificationsFragment
import com.example.bottomnavigation.ui.ProfileFragment
import com.example.terasystemhris.R

enum class BottomNavigationPosition(val position: Int, val id: Int) {
    HOME(0, R.id.home),
    LEAVES(1, R.id.dashboard),
    CLIENTS(2, R.id.notifications),
    PROFILE(3, R.id.profile);
}

fun findNavigationPositionById(id: Int): BottomNavigationPosition = when (id) {
    BottomNavigationPosition.HOME.id -> BottomNavigationPosition.HOME
    BottomNavigationPosition.LEAVES.id -> BottomNavigationPosition.LEAVES
    BottomNavigationPosition.CLIENTS.id -> BottomNavigationPosition.CLIENTS
    BottomNavigationPosition.PROFILE.id -> BottomNavigationPosition.PROFILE
    else -> BottomNavigationPosition.HOME
}

fun BottomNavigationPosition.createFragment(): Fragment = when (this) {
    BottomNavigationPosition.HOME -> HomeFragment.newInstance()
    BottomNavigationPosition.LEAVES -> DashboardFragment.newInstance()
    BottomNavigationPosition.CLIENTS -> NotificationsFragment.newInstance()
    BottomNavigationPosition.PROFILE -> ProfileFragment.newInstance()
}

fun BottomNavigationPosition.getTag(): String = when (this) {
    BottomNavigationPosition.HOME -> HomeFragment.TAG
    BottomNavigationPosition.LEAVES -> DashboardFragment.TAG
    BottomNavigationPosition.CLIENTS -> NotificationsFragment.TAG
    BottomNavigationPosition.PROFILE -> ProfileFragment.TAG
}

