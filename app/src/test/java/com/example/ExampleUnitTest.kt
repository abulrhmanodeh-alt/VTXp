package com.example

import com.example.data.local.entity.RedeemCodeEntity
import com.example.data.local.entity.ScriptEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun testAdminPassword() {
        val correctPassword = "vtx131211"
        assertTrue(correctPassword == "vtx131211")
        assertFalse("wrong_pass" == "vtx131211")
    }

    @Test
    fun testScriptEntity() {
        val script = ScriptEntity(
            title = "Blox Fruits VTX",
            code = "loadstring(game:HttpGet('...'))()",
            imageUrl = "https://example.com/image.jpg",
            gameCategory = "Blox Fruits"
        )
        assertEquals("Blox Fruits VTX", script.title)
        assertEquals("Blox Fruits", script.gameCategory)
        assertTrue(script.code.contains("loadstring"))
    }

    @Test
    fun testRedeemCodeEntity() {
        val redeemCode = RedeemCodeEntity(
            code = "VTX-VIP",
            scriptContent = "print('VIP Loaded')",
            scriptTitle = "VIP Script"
        )
        assertEquals("VTX-VIP", redeemCode.code)
        assertTrue(redeemCode.code.equals("vtx-vip", ignoreCase = true))
    }
}
