package com.narzarech.android.moneyloverclone.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Entity(tableName = "transaction_info_table")
data class TransactionInfo(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var amount: Double = 0.0,
    // TODO: Temporary approach, research Parcelable
    var category: String,
    // Default value is the current date string
    var date: String = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
)