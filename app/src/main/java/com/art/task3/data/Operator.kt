package com.art.task3.data

sealed class Operator {
    abstract val priority: Int
    abstract fun operate(firstValue: Float, secondValue: Float): Float
}

operator fun Operator.compareTo(other: Operator): Int = this.priority - other.priority

object AddOperator : Operator() {
    override val priority = 1

    override fun operate(firstValue: Float, secondValue: Float): Float = firstValue + secondValue

}

object MultiplyOperator : Operator() {
    override val priority = 2

    override fun operate(firstValue: Float, secondValue: Float): Float = firstValue * secondValue

}

object DivideOperator : Operator() {
    override val priority = 3

    override fun operate(firstValue: Float, secondValue: Float): Float = firstValue / secondValue

}

object LeftParentheses : Operator() {
    override val priority = -1

    override fun operate(firstValue: Float, secondValue: Float): Float =
        throw IllegalAccessException("this operator cannot operate")
}