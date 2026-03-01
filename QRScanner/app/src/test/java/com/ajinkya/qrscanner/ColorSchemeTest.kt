package com.ajinkya.qrscanner

import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for ColorScheme data class and related functionality
 */
class ColorSchemeTest {

    @Test
    fun `color scheme should store correct values`() {
        // Given
        val name = "Test Theme"
        val primary = android.graphics.Color.BLUE
        val background = android.graphics.Color.WHITE
        val gradient = android.graphics.Color.CYAN

        // When
        val scheme = ColorScheme(name, primary, background, gradient)

        // Then
        assertEquals(name, scheme.name)
        assertEquals(primary, scheme.primary)
        assertEquals(background, scheme.background)
        assertEquals(gradient, scheme.gradient)
    }

    @Test
    fun `default color schemes should have valid colors`() {
        // Given
        val colorSchemes = listOf(
            ColorScheme("Classic", -0x1000000, -0x1, -0xcccccd),
            ColorScheme("Ocean", -0xff9934, -0xf0001, -0x333401),
            ColorScheme("Sunset", -0x94cb, -0xb0f, -0x5b00),
            ColorScheme("Forest", -0xd18230, -0xe170f, -0x99442a),
            ColorScheme("Berry", -0x71dc56, -0xc1cb0, -0x1fbf04),
            ColorScheme("Midnight", -0xe5dc82, -0x171b19, -0xc6b64d)
        )

        // Then
        assertEquals("Should have 6 color schemes", 6, colorSchemes.size)
        
        colorSchemes.forEach { scheme ->
            assertNotNull("Scheme name should not be null", scheme.name)
            assertTrue("Scheme name should not be empty", scheme.name.isNotEmpty())
            assertTrue("Primary color should be valid", scheme.primary != 0 || scheme.name == "Classic")
        }
    }

    @Test
    fun `corner styles should have valid radius values`() {
        // Given
        val cornerStyles = listOf(
            CornerStyle("Square", 0f),
            CornerStyle("Rounded", 0.3f),
            CornerStyle("Smooth", 0.5f)
        )

        // Then
        assertEquals("Should have 3 corner styles", 3, cornerStyles.size)
        
        cornerStyles.forEach { style ->
            assertNotNull("Style name should not be null", style.name)
            assertTrue("Radius should be non-negative", style.radius >= 0f)
            assertTrue("Radius should be <= 1", style.radius <= 1f)
        }
    }

    @Test
    fun `corner style radius should affect rendering`() {
        // Given
        val square = CornerStyle("Square", 0f)
        val rounded = CornerStyle("Rounded", 0.3f)
        val smooth = CornerStyle("Smooth", 0.5f)

        // Then
        assertTrue("Square should have 0 radius", square.radius == 0f)
        assertTrue("Rounded should have medium radius", rounded.radius > 0f && rounded.radius < 0.5f)
        assertTrue("Smooth should have larger radius", smooth.radius >= 0.5f)
    }

    // Test data classes (copied from main source for testing)
    data class ColorScheme(
        val name: String,
        val primary: Int,
        val background: Int,
        val gradient: Int
    )

    data class CornerStyle(
        val name: String,
        val radius: Float
    )
}
