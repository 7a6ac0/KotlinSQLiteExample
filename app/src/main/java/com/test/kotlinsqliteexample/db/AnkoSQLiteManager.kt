package com.test.kotlinsqliteexample.db

import android.content.Context
import com.test.kotlinsqliteexample.MainApplication
import com.test.kotlinsqliteexample.model.model.User
import org.jetbrains.anko.db.*

/**
 * Created by admin on 2017/11/21.
 */
class AnkoSQLiteManager(var ctx: Context = MainApplication.instance) {
    fun selectUserByID(user_id: String) : User {
        var user: User? = null
        ctx.database.use {
            select(UserTable.TABLE_NAME)
                    .whereSimple("${UserTable.USER_ID} = ?", user_id)
                    .parseOpt(object : RowParser<User>{
                        override fun parseRow(columns: Array<Any?>): User {
                            val id = columns.get(1)
                            val name = columns.get(2)
                            val email = columns.get(3)
                            user = User(user_id = id.toString(), name = name.toString(), email = email.toString())
                            return user!!
                        }
                    })
        }
        return user!!
    }

    fun deleteUser(user: User) : Boolean {
        var isDelete: Boolean = false
        ctx.database.use {
            try {
                beginTransaction()
                val result = delete(UserTable.TABLE_NAME, "${UserTable.USER_ID} = {user_id}", "user_id" to user.user_id!!) > 0
                if (result) {
                    setTransactionSuccessful()
                    isDelete = true
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            finally {
                endTransaction()
            }
        }
        return isDelete
    }

    fun selectAllUsers() {
        var listUsers = ArrayList<User>()
        ctx.database.use {
            select(UserTable.TABLE_NAME)
                    .parseList(object : MapRowParser<List<User>> {
                        override fun parseRow(columns: Map<String, Any?>): ArrayList<User> {
                            val id = columns.getValue("user_id")
                            val name = columns.getValue("name")
                            val email = columns.getValue("email")
                            val user = User(user_id = id.toString(), name = name.toString(), email = email.toString())
                            listUsers.add(user)

                            return listUsers
                        }
                    })
        }
        listUsers.forEach {
            println("user_id: ${it.user_id}")
            println("name: ${it.name}")
            println("email: ${it.email}")
        }
    }

    fun insertUser(user: User) : Boolean = ctx.database.use {
        try {
            beginTransaction()
            val insertedId = insert(UserTable.TABLE_NAME,
                    UserTable.USER_ID to user.user_id,
                    UserTable.NAME to user.name,
                    UserTable.EMAIL to user.email)

            if (insertedId != -1L) {
                setTransactionSuccessful()
                true
            } else {
                false
                throw RuntimeException("Fail to insert")
            }

        } finally {
            endTransaction()
        }
    }
}