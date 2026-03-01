package com.ajinkya.qrscanner

import android.content.Context
import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream
import java.util.Date
import java.util.UUID

/**
 * This file contains intentionally unused code for testing code review automation.
 * DO NOT USE THESE FUNCTIONS - they are only for testing Detekt and Lint checks.
 * 
 * TODO: Delete this file after verifying code review checks work properly.
 */

class UnusedCodeForTesting {

    // Unused property
    private val unusedProperty = "This is never used"
    
    // Unused companion object property
    companion object {
        private const val UNUSED_CONSTANT = "Unused constant value"
        private val unusedCompanionProperty = 12345
    }
    
    /**
     * Unused function with unused parameters
     */
    fun unusedFunction(
        param1: String,  // Never used
        param2: Int,     // Never used
        param3: Bitmap? // Never used
    ): Boolean {
        // Unused local variable
        val unusedLocalVar = "Never referenced"
        
        // Unused date
        val unusedDate = Date()
        
        // Function always returns same value, making parameters useless
        return true
    }
    
    /**
     * Another unused function
     */
    fun anotherUnusedFunction(context: Context): String {
        // Unused context parameter
        val unusedUUID = UUID.randomUUID().toString()
        val unusedString = "Generated but never used: $unusedUUID"
        
        // Create unused bitmap
        val unusedBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        
        return "unused"
    }
    
    /**
     * Function with many unused things
     */
    fun functionWithUnusedThings(
        firstUnused: String,
        secondUnused: Int,
        thirdUnused: Boolean
    ) {
        // Unused imports at top (Date, File, FileOutputStream) will also be flagged
        val unusedFile = File("/tmp/unused.txt")
        val unusedStream: FileOutputStream? = null
        
        // Complex unused calculation
        val unusedCalculation = (secondUnused * 2) + (if (thirdUnused) 1 else 0)
        
        // Unused loop
        for (i in 0..10) {
            val unusedLoopVar = i * 2
        }
        
        // Unused when expression
        val unusedWhen = when (firstUnused) {
            "a" -> 1
            "b" -> 2
            else -> 0
        }
    }
    
    /**
     * Unused private helper function
     */
    private fun unusedPrivateHelper(): String {
        return "Helper result that is never used"
    }
    
    /**
     * Function that creates unused objects
     */
    fun createUnusedObjects() {
        // Creating objects that are never used
        val unusedList = listOf(1, 2, 3, 4, 5)
        val unusedMap = mapOf("key" to "value")
        val unusedSet = setOf("a", "b", "c")
        
        // Unused lambda
        val unusedLambda = { x: Int -> x * 2 }
        
        // Unused collections operations
        unusedList.filter { it > 2 }
        unusedMap.map { it.value }
    }
}
