package com.narzarech.android.moneyloverclone.database

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

data class Category(
    var name: String? = null
)

@IgnoreExtraProperties
data class Transaction(
    var amount: Double? = null,
    //var category: Category? = null,
    var note: String? = null,
    var date: String? = null
)

class FirebaseDatabase {
    companion object {
        private lateinit var dbReference: DatabaseReference

        init {
            val database =
                Firebase.database("https://money-lover-clone-1d481-default-rtdb.asia-southeast1.firebasedatabase.app/")
            database.setPersistenceEnabled(true)
            dbReference = database.reference
        }

        fun writeTransaction(
            amount: Double,
            note: String,
            date: String
        ) {
            val newTransaction = Transaction(amount, note, date)
            val key = dbReference.child("transactions").push().key
            if (key == null) {
                Log.w(
                    "Firebase Realtime Database::",
                    "Could not get push key for new transactions"
                )
                return
            }
            dbReference.child("transactions").child(key).setValue(newTransaction)
        }

        // TODO: Think of a way to optimize this piece of code
        fun readAllTransactions(): MutableLiveData<List<Transaction?>> {
            val listTransactions: MutableList<Transaction?> = mutableListOf()
            val listener = object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val transaction = snapshot.getValue<Transaction?>()
                    listTransactions.add(transaction)
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    val transaction = snapshot.getValue<Transaction?>()
                    listTransactions.add(transaction)
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    val transaction = snapshot.getValue<Transaction?>()
                    listTransactions.add(transaction)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(
                        "Firebase Realtime Database::",
                        "loadPost:onCancelled",
                        error.toException()
                    )
                }
            }

            dbReference.child("transactions").addChildEventListener(listener)

            return MutableLiveData<List<Transaction?>>(listTransactions)
        }

        fun writeCategory(name: String) {
            val key = dbReference.child("categories").push().key
            if (key == null) {
                Log.w(
                    "Firebase Realtime Database::",
                    "Could not get push key for new categories"
                )
                return
            }
            dbReference.child("categories").child(key).setValue(Category(name))
        }

        // TODO: Think of a way to optimize this piece of code
        fun readAllCategories(): MutableLiveData<List<Category?>> {
            val listCategories: MutableList<Category?> = mutableListOf()
            val listener = object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val category = snapshot.getValue<Category?>()
                    listCategories.add(category)
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    val category = snapshot.getValue<Category?>()
                    listCategories.add(category)
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    val category = snapshot.getValue<Category?>()
                    listCategories.add(category)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(
                        "Firebase Realtime Database::",
                        "loadPost:onCancelled",
                        error.toException()
                    )
                }
            }

            dbReference.child("categories").addChildEventListener(listener)

            return MutableLiveData<List<Category?>>(listCategories)
        }
    }
}