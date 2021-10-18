package com.sunday.shiftsapplication.data.factory

class RoleFactory {
    companion object {
        fun list(): List<String> = listOf("Waitress", "Prep", "Cook", "Front of House")
    }
}