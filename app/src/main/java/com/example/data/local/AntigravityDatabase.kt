package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        MessageEntity::class,
        TerminalLogEntity::class,
        WorkspaceFileEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AntigravityDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
    abstract fun terminalDao(): TerminalDao
    abstract fun workspaceFileDao(): WorkspaceFileDao

    companion object {
        @Volatile
        private var INSTANCE: AntigravityDatabase? = null

        fun getInstance(context: Context): AntigravityDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AntigravityDatabase::class.java,
                    "antigravity_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
