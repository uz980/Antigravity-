package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.AntigravityHeader
import com.example.ui.components.CodeEditorView
import com.example.ui.components.GoogleAuthDialog
import com.example.ui.components.TerminalConsole
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.ObsidianBackground
import com.example.ui.theme.ObsidianBorder
import com.example.ui.theme.ObsidianSurface
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.AntigravityTab
import com.example.ui.viewmodel.AntigravityViewModel

@Composable
fun AntigravityMainScreen(
    viewModel: AntigravityViewModel = viewModel()
) {
    val context = LocalContext.current
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val messages by viewModel.messages.collectAsStateWithLifecycle()
    val terminalLogs by viewModel.terminalLogs.collectAsStateWithLifecycle()
    val workspaceFiles by viewModel.workspaceFiles.collectAsStateWithLifecycle()
    val selectedFile by viewModel.selectedFile.collectAsStateWithLifecycle()
    val isAgentBusy by viewModel.isAgentBusy.collectAsStateWithLifecycle()
    val agentTasks by viewModel.agentTasks.collectAsStateWithLifecycle()
    val showAuthDialog by viewModel.showAuthDialog.collectAsStateWithLifecycle()
    val authLink by viewModel.authLinkGenerated.collectAsStateWithLifecycle()
    val authCode by viewModel.authCodeGenerated.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            AntigravityHeader(
                user = currentUser,
                onAuthClick = { viewModel.openAuthDialog() }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = ObsidianSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(0.8.dp, ObsidianBorder)
                    .testTag("antigravity_bottom_nav")
            ) {
                NavigationBarItem(
                    selected = currentTab == AntigravityTab.AGENT,
                    onClick = { viewModel.selectTab(AntigravityTab.AGENT) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.SmartToy,
                            contentDescription = "Agent",
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    label = {
                        Text(
                            text = "Agent",
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = if (currentTab == AntigravityTab.AGENT) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CyanAccent,
                        selectedTextColor = CyanAccent,
                        unselectedIconColor = TextMuted,
                        unselectedTextColor = TextMuted,
                        indicatorColor = CyanAccent.copy(alpha = 0.15f)
                    ),
                    modifier = Modifier.testTag("nav_item_agent")
                )

                NavigationBarItem(
                    selected = currentTab == AntigravityTab.TERMINAL,
                    onClick = { viewModel.selectTab(AntigravityTab.TERMINAL) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Terminal,
                            contentDescription = "Terminal",
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    label = {
                        Text(
                            text = "Terminal",
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = if (currentTab == AntigravityTab.TERMINAL) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CyanAccent,
                        selectedTextColor = CyanAccent,
                        unselectedIconColor = TextMuted,
                        unselectedTextColor = TextMuted,
                        indicatorColor = CyanAccent.copy(alpha = 0.15f)
                    ),
                    modifier = Modifier.testTag("nav_item_terminal")
                )

                NavigationBarItem(
                    selected = currentTab == AntigravityTab.FILES,
                    onClick = { viewModel.selectTab(AntigravityTab.FILES) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Code,
                            contentDescription = "Files",
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    label = {
                        Text(
                            text = "Fayllar",
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = if (currentTab == AntigravityTab.FILES) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CyanAccent,
                        selectedTextColor = CyanAccent,
                        unselectedIconColor = TextMuted,
                        unselectedTextColor = TextMuted,
                        indicatorColor = CyanAccent.copy(alpha = 0.15f)
                    ),
                    modifier = Modifier.testTag("nav_item_files")
                )

                NavigationBarItem(
                    selected = currentTab == AntigravityTab.TASKS,
                    onClick = { viewModel.selectTab(AntigravityTab.TASKS) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.TaskAlt,
                            contentDescription = "Tasks",
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    label = {
                        Text(
                            text = "Reja",
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = if (currentTab == AntigravityTab.TASKS) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CyanAccent,
                        selectedTextColor = CyanAccent,
                        unselectedIconColor = TextMuted,
                        unselectedTextColor = TextMuted,
                        indicatorColor = CyanAccent.copy(alpha = 0.15f)
                    ),
                    modifier = Modifier.testTag("nav_item_tasks")
                )

                NavigationBarItem(
                    selected = currentTab == AntigravityTab.ACCOUNT,
                    onClick = { viewModel.selectTab(AntigravityTab.ACCOUNT) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Google",
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    label = {
                        Text(
                            text = "Google",
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = if (currentTab == AntigravityTab.ACCOUNT) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CyanAccent,
                        selectedTextColor = CyanAccent,
                        unselectedIconColor = TextMuted,
                        unselectedTextColor = TextMuted,
                        indicatorColor = CyanAccent.copy(alpha = 0.15f)
                    ),
                    modifier = Modifier.testTag("nav_item_account")
                )
            }
        },
        containerColor = ObsidianBackground
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                AntigravityTab.AGENT -> {
                    AgentScreen(
                        messages = messages,
                        isAgentBusy = isAgentBusy,
                        onSendPrompt = { prompt -> viewModel.sendPrompt(prompt) },
                        onClearChat = { viewModel.clearChat() }
                    )
                }

                AntigravityTab.TERMINAL -> {
                    TerminalConsole(
                        logs = terminalLogs,
                        onExecuteCommand = { cmd -> viewModel.runTerminalCommand(cmd) }
                    )
                }

                AntigravityTab.FILES -> {
                    CodeEditorView(
                        files = workspaceFiles,
                        selectedFile = selectedFile,
                        onSelectFile = { file -> viewModel.selectFile(file) },
                        onSaveFile = { path, content -> viewModel.saveFile(path, content) },
                        onRunInAgent = { prompt ->
                            viewModel.selectTab(AntigravityTab.AGENT)
                            viewModel.sendPrompt(prompt)
                        }
                    )
                }

                AntigravityTab.TASKS -> {
                    TasksScreen(tasks = agentTasks)
                }

                AntigravityTab.ACCOUNT -> {
                    AccountScreen(
                        user = currentUser,
                        onOpenAuthDialog = { viewModel.openAuthDialog() },
                        onRunDiagnostics = {
                            viewModel.selectTab(AntigravityTab.TERMINAL)
                            viewModel.runTerminalCommand("antigravity doctor")
                        }
                    )
                }
            }

            // Google OAuth Dialog
            if (showAuthDialog) {
                GoogleAuthDialog(
                    authUrl = authLink,
                    deviceCode = authCode,
                    currentEmail = currentUser.email,
                    onDismiss = { viewModel.dismissAuthDialog() },
                    onConfirmLogin = { email, name ->
                        viewModel.completeGoogleLogin(email, name)
                    }
                )
            }
        }
    }
}
