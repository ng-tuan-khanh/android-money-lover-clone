package com.narzarech.android.moneyloverclone.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_info_table")
data class CategoryInfo(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var category: String
)
