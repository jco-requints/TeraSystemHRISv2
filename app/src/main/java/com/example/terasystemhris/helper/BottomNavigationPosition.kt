package com.example.bottomnavigation.helper

import androidx.fragment.app.Fragment
import com.example.bottomnavigation.ui.ClientsFragment
import com.example.bottomnavigation.ui.LeavesFragment
import com.example.bottomnavigation.ui.ProfileFragment
import com.example.terasystemhris.LogsFragment
import com.example.terasystemhris.R

enum class BottomNavigationPosition(val position: Int, val id: Int) {
    LOGS(0, R.id.logs),
    LEAVES(1, R.id.leaves),
    CLIENTS(2, R.id.clients),
    PROFILE(3, R.id.profile);
}

fun findNavigationPositionById(id: Int): BottomNavigationPosition = when (id) {
    BottomNavigationPosition.LOGS.id -> BottomNavigationPosition.LOGS
    BottomNavigationPosition.LEAVES.id -> BottomNavigationPosition.LEAVES
    BottomNavigationPosition.CLIENTS.id -> BottomNavigationPosition.CLIENTS
    BottomNavigationPosition.PROFILE.id -> BottomNavigationPosition.PROFILE
    else -> BottomNavigationPosition.LOGS
}

fun BottomNavigationPosition.createFragment(): Fragment = when (this) {
    BottomNavigationPosition.LOGS -> LogsFragment.newInstance()
    BottomNavigationPosition.LEAVES -> LeavesFragment.newInstance()
    BottomNavigationPosition.CLIENTS -> ClientsFragment.newInstance()
    BottomNavigationPosition.PROFILE -> ProfileFragment.newInstance()
}

fun BottomNavigationPosition.getTag(): String = when (this) {
    BottomNavigationPosition.LOGS -> LogsFragment.TAG
    BottomNavigationPosition.LEAVES -> LeavesFragment.TAG
    BottomNavigationPosition.CLIENTS -> ClientsFragment.TAG
    BottomNavigationPosition.PROFILE -> ProfileFragment.TAG
}

