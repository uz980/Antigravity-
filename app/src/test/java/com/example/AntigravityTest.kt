package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.AntigravityDatabase
import com.example.data.local.TerminalLogEntity
import com.example.data.local.WorkspaceFileEntity
import com.example.data.model.UserAccount
import com.example.data.repository.AntigravityRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class AntigravityTest {

    private lateinit var db: AntigravityDatabase
    private lateinit var repository: AntigravityRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = AntigravityDatabase.getInstance(context)
        repository = AntigravityRepository(db)
    }

    @Test
    fun testUserAccountDefaults() = runBlocking {
        val user = repository.currentUser.value
        assertEquals("shaxriyordoliyev4@gmail.com", user.email)
        assertEquals("Shaxriyor Doliyev", user.displayName)
        assertTrue(user.isAuthenticated)
        assertEquals("gemini-3.5-flash", user.activeModel)
    }

    @Test
    fun testTerminalAuthLoginCommand() = runBlocking {
        val output = repository.executeTerminalCommand("antigravity auth login")
        assertTrue(output.contains("ANTIGRAVITY AUTHENTICATION"))
        assertTrue(output.contains("accounts.google.com/o/oauth2"))
        assertTrue(output.contains("Verification Code"))
    }

    @Test
    fun testTerminalDoctorCommand() = runBlocking {
        val output = repository.executeTerminalCommand("antigravity doctor")
        assertTrue(output.contains("ANTIGRAVITY DIAGNOSTICS"))
        assertTrue(output.contains("Google Auth Status: Connected"))
    }

    @Test
    fun testWorkspaceInitialization() = runBlocking {
        repository.initializeWorkspaceIfNeeded()
        val files = repository.workspaceFiles.first()
        assertTrue(files.isNotEmpty())
        assertNotNull(files.find { it.name == "MainActivity.kt" })
    }
}
