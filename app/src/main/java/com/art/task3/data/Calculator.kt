package com.art.task3.data

import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.math.sign

class Calculator {
    private var operators: MutableList<Operator> = mutableListOf()
    private var operands: MutableList<Float> = mutableListOf()

    fun calculate(expression: String): Float {
        var lastChar = '\u0000'
        var digitAfterDot = 1
        loop@ for (current in expression) {
            when {
                current == '(' -> operators.add(LeftParentheses)
                current == ')' -> operateRightParenthesis()
                current.isMinusSign() -> operands.add(-1.0f)
                current.isDigit() && lastChar.isMinusSign() -> {
                    if (operands.size > 1) {
                        operators.add(AddOperator)
                    }
                    operands.replace(operands.last() * current.charToFloat())
                }
                current.isOperator() -> addAndOperateIfNeeded(current)
                current != '.' && lastChar == '.' -> {
                    operands.replace(operands.last() + current.charToFloat() / 10f.pow(digitAfterDot))
                    digitAfterDot++
                    continue@loop
                }
                lastChar.isDigit() && current.isDigit() ->
                    operands.replace(operands.last().sign * (operands.last().absoluteValue * 10f + current.charToFloat()))
                current.isDigit() -> operands.add(current.charToFloat())
            }
            lastChar = current
            digitAfterDot = 1
        }

        if (operands.isEmpty() || operators.isEmpty() || operands.size - operators.size != 1) {
            operators.clear()
            operands.clear()
            return Float.NaN
        }

        while (!operators.isEmpty()) {
            operateAndRemove()
        }
        return operands.getLastAndRemove()
    }

    private fun addAndOperateIfNeeded(current: Char) {
        when (current) {
            '+' -> checkPriorityAndOperate(AddOperator)
            '*' -> checkPriorityAndOperate(MultiplyOperator)
            '/' -> operators.add(DivideOperator)
        }
    }

    private fun checkPriorityAndOperate(operator: Operator) {
        if (!operators.isEmpty() && operator <= operators.last()) {
            while (!operators.isEmpty() && operator < operators.last()) {
                operateAndRemove()
            }
        }
        operators.add(operator)
    }

    private fun operateRightParenthesis() {
        while (!operators.isEmpty() && operators.last() != LeftParentheses) {
            operateAndRemove()
        }
        operators.removeAt(operators.lastIndex)
    }

    private fun operateAndRemove() {
        val secondOperand = operands.getLastAndRemove()
        val firstOperand = operands.getLastAndRemove()
        operands.add(operators.getLastAndRemove().operate(firstOperand, secondOperand))
    }

    private fun <E> MutableList<E>.replace(element: E) {
        removeAt(lastIndex)
        add(element)
    }


    private fun <E> MutableList<E>.getLastAndRemove(): E {
        val lastElement = last()
        removeAt(lastIndex)
        return lastElement
    }

    private fun Char.isOperator() = this == '-' || this == '+' || this == '/' || this == '*'

    private fun Char.isMinusSign(): Boolean = this == '-'

    private fun Char.charToFloat(): Float = this.toFloat() - 48
}
