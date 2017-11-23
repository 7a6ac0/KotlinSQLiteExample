package com.test.kotlinsqliteexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.test.kotlinsqliteexample.db.AnkoSQLiteManager
import com.test.kotlinsqliteexample.model.model.User
import org.jetbrains.anko.doAsync

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val user1 = User(user_id = "1", name = "mike", email = "test1@test.com")
        val user2 = User(user_id = "2", name = "jack", email = "test2@test.com")
        val user3 = User(user_id = "3", name = "bear", email = "test3@test.com")
        doAsync {
            AnkoSQLiteManager().insertUser(user1)
            AnkoSQLiteManager().insertUser(user2)
            AnkoSQLiteManager().insertUser(user3)
            println("------ selectAllUsers ------")
            val userList = AnkoSQLiteManager().selectAllUsers()
            userList.forEach {
                println("ID: ${it.user_id}\nNAME: ${it.name}\nEMAIL: ${it.email}")
            }
            println("------ selectUserByID 2 ------")
            val getUser: User = AnkoSQLiteManager().selectUserByID(user2.user_id!!)
            println(getUser.toString())
            println("------ delete User3 ------")
            AnkoSQLiteManager().deleteUser(user3)

            println("------ selectAllUsers ------")
            AnkoSQLiteManager().selectAllUsers()
        }
    }
}
