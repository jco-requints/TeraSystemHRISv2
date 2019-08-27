package com.example.terasystemhris

interface NetworkRequestInterface
{
    fun beforeNetworkCall()
    fun afterNetworkCall(result: String?)
}