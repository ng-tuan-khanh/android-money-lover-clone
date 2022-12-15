package com.narzarech.android.moneyloverclone.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TransactionInfo::class], version = 1, exportSchema = false)
abstract class TransactionDatabase : RoomDatabase() {
    abstract val transactionDao: TransactionDao

    companion object {

        @Volatile
        private var INSTANCE: TransactionDatabase? = null

        fun getInstance(context: Context): TransactionDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TransactionDatabase::class.java,
                        "transaction_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}