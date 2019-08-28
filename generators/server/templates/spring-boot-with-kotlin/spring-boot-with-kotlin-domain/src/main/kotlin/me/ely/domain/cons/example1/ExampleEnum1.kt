package me.ely.domain.cons.example1

enum class ExampleEnum1(val value: Int, val label: String) {
    ENUM1(1, "Label1"),
    ENUM2(2, "Label2");

    companion object {

        private val map = mutableMapOf<Int, ExampleEnum1>()

        init {
            values().forEach { map[it.value] = it }
        }

        fun parse(value: Int): ExampleEnum1? {
            return map[value]
        }
    }
}