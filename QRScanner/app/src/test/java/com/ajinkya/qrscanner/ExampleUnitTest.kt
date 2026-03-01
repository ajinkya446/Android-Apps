package com.ajinkya.qrscanner

import org.junit.Test
import org.junit.Assert.*

/**
 * Example local unit tests
 */
class ExampleUnitTest {

    @Test
    fun `addition is correct`() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `string concatenation works`() {
        val result = "Hello" + " " + "World"
        assertEquals("Hello World", result)
    }

    @Test
    fun `list operations work`() {
        val list = listOf(1, 2, 3)
        assertEquals(3, list.size)
        assertTrue(list.contains(2))
    }
}
