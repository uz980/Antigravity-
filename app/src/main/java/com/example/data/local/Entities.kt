package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sender: String, // "USER", "AGENT", "SYSTEM"
    val content: String,
    val thought: String? = null,
    val toolCallsJson: String = "[]",
    val status: String = "COMPLETED",
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "terminal_logs")
data class TerminalLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val command: String,
    val output: String,
    val isCommand: Boolean = true,
    val exitCode: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "workspace_files")
data class WorkspaceFileEntity(
    @PrimaryKey val path: String,
    val name: String,
    val content: String,
    val language: String,
    val isModified: Boolean = false,
    val updatedAt: Long = System.currentTimeMillis()
)
