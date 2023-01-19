package com.narzarech.android.moneyloverclone.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CategoryDao {
    @Insert
    suspend fun insert(category: CategoryInfo)

    @Update
    suspend fun update(category: CategoryInfo)

    @Query("DELETE FROM category_info_table WHERE id = :categoryId")
    suspend fun remove(categoryId: Long)

    @Query("SELECT * FROM category_info_table WHERE id = :categoryId")
    suspend fun get(categoryId: Long): CategoryInfo?

    @Query("SELECT * FROM category_info_table ORDER BY id DESC")
    fun getListCategories(): LiveData<List<CategoryInfo>>

    @Query("DELETE FROM transaction_info_table")
    suspend fun clear()
}