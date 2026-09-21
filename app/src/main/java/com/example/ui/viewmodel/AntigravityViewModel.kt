package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AntigravityDatabase
import com.example.data.model.AgentMessage
import com.example.data.model.AgentTaskItem
import com.example.data.model.TerminalEntry
import com.example.data.model.UserAccount
import com.example.data.model.WorkspaceFile
import com.example.data.repository.AntigravityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AntigravityTab {
    AGENT, TERMINAL, FILES, TASKS, ACCOUNT
}

class AntigravityViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AntigravityRepository(AntigravityDatabase.getInstance(application))

    val currentUser: StateFlow<UserAccount> = repository.currentUser
    val agentTasks: StateFlow<List<AgentTaskItem>> = repository.agentTasks

    val messages: StateFlow<List<AgentMessage>> = repository.messages
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val terminalLogs: StateFlow<List<TerminalEntry>> = repository.terminalLogs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val workspaceFiles: StateFlow<List<WorkspaceFile>> = repository.workspaceFiles
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _currentTab = MutableStateFlow(AntigravityTab.AGENT)
    val currentTab: StateFlow<AntigravityTab> = _currentTab.asStateFlow()

    private val _selectedFile = MutableStateFlow<WorkspaceFile?>(null)
    val selectedFile: StateFlow<WorkspaceFile?> = _selectedFile.asStateFlow()

    private val _isAgentBusy = MutableStateFlow(false)
    val isAgentBusy: StateFlow<Boolean> = _isAgentBusy.asStateFlow()

    private val _showAuthDialog = MutableStateFlow(false)
    val showAuthDialog: StateFlow<Boolean> = _showAuthDialog.asStateFlow()

    private val _authLinkGenerated = MutableStateFlow(
        "https://accounts.google.com/o/oauth2/v2/auth?client_id=antigravity-agent.apps.googleusercontent.com&scope=openid%20profile%20email%20cloud-platform&response_type=code&redirect_uri=urn:ietf:wg:oauth:2.0:oob"
    )
    val authLinkGenerated: StateFlow<String> = _authLinkGenerated.asStateFlow()

    private val _authCodeGenerated = MutableStateFlow("ANTI-8492-GRAV")
    val authCodeGenerated: StateFlow<String> = _authCodeGenerated.asStateFlow()

    init {
        viewModelScope.launch {
            repository.initializeWorkspaceIfNeeded()
        }
    }

    fun selectTab(tab: AntigravityTab) {
        _currentTab.value = tab
    }

    fun selectFile(file: WorkspaceFile) {
        _selectedFile.value = file
    }

    fun sendPrompt(prompt: String) {
        if (prompt.isBlank() || _isAgentBusy.value) return
        viewModelScope.launch {
            _isAgentBusy.value = true
            try {
                repository.sendUserPrompt(prompt)
            } finally {
                _isAgentBusy.value = false
            }
        }
    }

    fun runTerminalCommand(command: String) {
        if (command.isBlank()) return
        viewModelScope.launch {
            repository.executeTerminalCommand(command)
        }
    }

    fun saveFile(path: String, newContent: String) {
        viewModelScope.launch {
            repository.saveWorkspaceFile(path, newContent)
            _selectedFile.value = _selectedFile.value?.copy(content = newContent, isModified = true)
        }
    }

    fun clearChat() {
        viewModelScope.launch {
            repository.clearHistory()
        }
    }

    fun openAuthDialog() {
        val code = "ANTI-" + (1000..9999).random() + "-GRAV"
        _authCodeGenerated.value = code
        _showAuthDialog.value = true
    }

    fun dismissAuthDialog() {
        _showAuthDialog.value = false
    }

    fun completeGoogleLogin(email: String = "shaxriyordoliyev4@gmail.com", name: String = "Shaxriyor Doliyev") {
        repository.updateAccount(email, name)
        _showAuthDialog.value = false
        viewModelScope.launch {
            repository.executeTerminalCommand("echo '✓ Google Account OAuth authorization confirmed: $email'")
        }
    }
}
