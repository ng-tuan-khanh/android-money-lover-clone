package com.narzarech.android.moneyloverclone.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CategoryInfo::class], version = 1, exportSchema = true)
abstract class CategoryDatabase : RoomDatabase() {
    abstract val categoryDao: CategoryDao

    companion object {

        @Volatile
        private var INSTANCE: CategoryDatabase? = null

        fun getInstance(context: Context): CategoryDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CategoryDatabase::class.java,
                        "category_database"
                    )
                        .createFromAsset("database/category_database.db")
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}