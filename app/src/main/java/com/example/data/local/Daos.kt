package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages ORDER BY timestamp ASC")
    fun getAllMessages(): Flow<List<MessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity): Long

    @Update
    suspend fun updateMessage(message: MessageEntity)

    @Query("DELETE FROM messages")
    suspend fun clearAll()
}

@Dao
interface TerminalDao {
    @Query("SELECT * FROM terminal_logs ORDER BY timestamp ASC")
    fun getAllLogs(): Flow<List<TerminalLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: TerminalLogEntity): Long

    @Query("DELETE FROM terminal_logs")
    suspend fun clearLogs()
}

@Dao
interface WorkspaceFileDao {
    @Query("SELECT * FROM workspace_files ORDER BY path ASC")
    fun getAllFiles(): Flow<List<WorkspaceFileEntity>>

    @Query("SELECT * FROM workspace_files WHERE path = :path LIMIT 1")
    suspend fun getFileByPath(path: String): WorkspaceFileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFile(file: WorkspaceFileEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(files: List<WorkspaceFileEntity>)

    @Query("DELETE FROM workspace_files WHERE path = :path")
    suspend fun deleteFile(path: String)
}
