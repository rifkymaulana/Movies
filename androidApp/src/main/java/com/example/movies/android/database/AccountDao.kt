package com.example.movies.android.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AccountDao {
    @Insert
    suspend fun insertAccount(account: AccountEntity)

    @Query("SELECT * FROM accounts WHERE email = :email")
    suspend fun getAccountByEmail(email: String): AccountEntity?

    @Query("SELECT * FROM accounts WHERE email = :email AND password = :password")
    suspend fun getAccountByEmailAndPassword(email: String, password: String): AccountEntity?

    // update isLogin to true when user login
    @Query("UPDATE accounts SET is_login = :isLogin WHERE id = :id")
    suspend fun updateAccount(id: Int, isLogin: Boolean)

    // check in database if user is login
    @Query("SELECT * FROM accounts WHERE is_login = :isLogin")
    suspend fun getAccountByIsLogin(isLogin: Boolean): AccountEntity?

}