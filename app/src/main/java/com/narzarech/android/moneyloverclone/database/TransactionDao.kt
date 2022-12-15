package com.narzarech.android.moneyloverclone.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TransactionDao {
    @Insert
    suspend fun insert(transaction: TransactionInfo)

    @Update
    suspend fun update(transaction: TransactionInfo)

    @Query("DELETE FROM transaction_info_table WHERE id = :transactionId")
    suspend fun remove(transactionId: Long)

    @Query("SELECT * FROM transaction_info_table WHERE id = :transactionId")
    suspend fun get(transactionId: Long): TransactionInfo?

    @Query("SELECT * FROM transaction_info_table ORDER BY id DESC LIMIT 1")
    suspend fun getLatestTransaction(): TransactionInfo?

    @Query("SELECT * FROM transaction_info_table ORDER BY id DESC")
    fun getAllTransactions(): LiveData<List<TransactionInfo>>

    @Query("DELETE FROM transaction_info_table")
    suspend fun clear()
}