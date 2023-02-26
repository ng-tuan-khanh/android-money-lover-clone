package com.narzarech.android.moneyloverclone.database

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

data class Category(
    var id: Long? = null,
    var name: String? = null
)

data class Transaction(
    var id: Long? = null,
    var amount: Double? = null,
    var category: Category? = null,
    var note: String? = null,
    var date: String? = null
)

class FirebaseDatabase {
    companion object {
        private val dbReference =
            Firebase.database("https://money-lover-clone-1d481-default-rtdb.asia-southeast1.firebasedatabase.app/").reference

        fun writeTransaction(
            id: Long,
            amount: Double,
            category: Category,
            note: String,
            date: String
        ) {
            val newTransaction = Transaction(id, amount, category, note, date)
            dbReference.child("transactions").child(id.toString()).setValue(newTransaction)
        }

        fun readTransaction(id: Long): MutableLiveData<Transaction?> {
            val transaction = MutableLiveData<Transaction?>()
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val newTransaction = snapshot.getValue<Transaction>()
                    transaction.value = newTransaction
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(
                        "Firebase Realtime Database::",
                        "loadPost:onCancelled",
                        error.toException()
                    )
                }
            }

            dbReference.child("transactions").child(id.toString()).addValueEventListener(listener)

            return transaction
        }

        fun writeCategory(id: Long, name: String) {
            val newCategory = Category(id, name)
            dbReference.child("categories").child(id.toString()).setValue(newCategory)
        }

        fun readCategory(id: Long): MutableLiveData<Category?> {
            val category = MutableLiveData<Category?>()
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val newCategory = snapshot.getValue<Category>()
                    category.value = newCategory
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(
                        "Firebase Realtime Database::",
                        "loadPost:onCancelled",
                        error.toException()
                    )
                }
            }

            dbReference.child("categories").child(id.toString()).addValueEventListener(listener)

            return category
        }
    }
}