package me.ely.domain.cons

enum class UserStatus(val value: Int, val label: String) {
    Valid(1, "Valid"),
    Invalid(2, "Invalid");

    companion object {

        private val map = mutableMapOf<Int, UserStatus>()

        init {
            values().forEach { map[it.value] = it }
        }

        fun parse(value: Int): UserStatus? {
            return map[value]
        }
    }
}