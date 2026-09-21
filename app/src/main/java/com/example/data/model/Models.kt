package com.example.data.model

data class UserAccount(
    val email: String = "shaxriyordoliyev4@gmail.com",
    val displayName: String = "Shaxriyor Doliyev",
    val photoUrl: String? = null,
    val isAuthenticated: Boolean = true,
    val cloudProjectId: String = "antigravity-prod-830893",
    val activeModel: String = "gemini-3.5-flash",
    val quotaLimit: String = "Unlimited (Pro Tier)",
    val authMethod: String = "Google OAuth2 Device Flow",
    val accessTokenMasked: String = "ya29.a0AfH6SM...4k8X",
    val expiresAt: String = "2026-12-31 23:59:59 UTC"
)

enum class MessageSender {
    USER, AGENT, SYSTEM
}

enum class AgentStatus {
    IDLE, THINKING, EXECUTING_TOOL, GENERATING_CODE, COMPLETED, ERROR
}

data class ToolCall(
    val id: String,
    val toolName: String, // e.g. "run_command", "edit_file", "view_file", "compile_applet", "search_web"
    val summary: String,
    val arguments: String,
    val result: String? = null,
    val isSuccess: Boolean = true,
    val isRunning: Boolean = false
)

data class AgentMessage(
    val id: Long = 0,
    val sender: MessageSender,
    val content: String,
    val thought: String? = null,
    val toolCalls: List<ToolCall> = emptyList(),
    val timestamp: Long = System.currentTimeMillis(),
    val status: AgentStatus = AgentStatus.COMPLETED
)

data class TerminalEntry(
    val id: Long = 0,
    val command: String,
    val output: String,
    val isCommand: Boolean = true,
    val exitCode: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)

data class AgentTaskItem(
    val id: Int,
    val title: String,
    val description: String,
    val isCompleted: Boolean = false,
    val isInProgress: Boolean = false
)

data class WorkspaceFile(
    val path: String,
    val name: String,
    val content: String,
    val language: String,
    val isModified: Boolean = false
)
