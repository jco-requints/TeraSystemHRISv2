package com.example.bottomnavigation.helper

import android.os.Bundle
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

fun BottomNavigationPosition.createFragment(bundle: Bundle): Fragment = when (this) {
    BottomNavigationPosition.LOGS -> LogsFragment.newInstance(bundle)
    BottomNavigationPosition.LEAVES -> LeavesFragment.newInstance(bundle)
    BottomNavigationPosition.CLIENTS -> ClientsFragment.newInstance(bundle)
    BottomNavigationPosition.PROFILE -> ProfileFragment.newInstance(bundle)
}

fun BottomNavigationPosition.getTag(): String = when (this) {
    BottomNavigationPosition.LOGS -> LogsFragment.TAG
    BottomNavigationPosition.LEAVES -> LeavesFragment.TAG
    BottomNavigationPosition.CLIENTS -> ClientsFragment.TAG
    BottomNavigationPosition.PROFILE -> ProfileFragment.TAG
}

