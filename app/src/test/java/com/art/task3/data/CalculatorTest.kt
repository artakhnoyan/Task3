package com.art.task3.data

import org.junit.Assert.assertEquals
import org.junit.Test

internal class CalculatorTest {
    private val calc = Calculator()

    @Test
    fun `number test`() {
        val input = "6"
        val output = Float.NaN
        assertEquals(output, calc.calculate(input))
    }

    @Test
    fun `two digit number test`() {
        val input = "67"
        val output = Float.NaN
        assertEquals(output, calc.calculate(input))
    }

    @Test
    fun `float number test`() {
        val input = "67.5"
        val output = Float.NaN
        assertEquals(output, calc.calculate(input))
    }

    @Test
    fun `invalid expression test`() {
        val input = "67.5+"
        val output = Float.NaN
        assertEquals(output, calc.calculate(input))
    }

    @Test
    fun `simple expression test`() {
        val input = "67.5+3"
        val output = 70.5f
        assertEquals(output, calc.calculate(input))
    }

    @Test
    fun `simple expression with priority test`() {
        val input = "67.5+3*2"
        val output = 73.5f
        assertEquals(output, calc.calculate(input))
    }

    @Test
    fun `expression with priority test`() {
        val input = "67.5+2/2*2"
        val output = 69.5f
        assertEquals(output, calc.calculate(input))
    }

    @Test
    fun `negative number at first expression test`() {
        val input = "-2+4"
        val output = 2.0f
        assertEquals(output, calc.calculate(input))
    }

    @Test
    fun `negative number in expression test`() {
        val input = "-2-2-2"
        val output = -6.0f
        assertEquals(output, calc.calculate(input))
    }

    @Test
    fun `negative number in expression test 2`() {
        val input = "66*66-2"
        val output = 4354.0f
        assertEquals(output, calc.calculate(input))
    }

    @Test
    fun `negative number in expression test 3`() {
        val input = "22*66-3"
        val output = 1449.0f
        assertEquals(calc.calculate(input), output)
    }

    @Test
    fun `some big expression test`() {
        val input = "-2-2-2/4*5+4*4+3+2"
        val output = 14.5f
        assertEquals(output, calc.calculate(input))
    }

    @Test
    fun `some big expression expression with parentheses test`() {
        val input = "(-2-2-2)/4*5+4*4+3+2"
        val output = 13.5f
        assertEquals(output, calc.calculate(input))
    }
}
