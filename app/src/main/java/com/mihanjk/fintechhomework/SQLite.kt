package com.mihanjk.fintechhomework

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class NodeDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        const val DB_NAME = "node"
        const val DB_VERSION = 1
        const val NODE_TABLE = "nodes"
        const val NODE_RELATIONS_TABLE = "node_relations"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE nodes (
            _id INTEGER PRIMARY KEY AUTOINCREMENT,
            value INTEGER NOT NULL
            )""")
        db.execSQL("""
            CREATE TABLE node_relations (
            parent_id INTEGER NOT NULL,
            child_id INTEGER NOT NULL,
            PRIMARY KEY (parent_id, child_id)
            )
            """)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun insertNode(value: Int): Long {
        val nodeValues = ContentValues().apply {
            put("value", value)
        }
        return writableDatabase.insert(NODE_TABLE, null, nodeValues).also { close() }
    }

    fun insertParentRelations(childId: Long, vararg parentsId: Long) {
        parentsId.forEach {
            writableDatabase.insert(NODE_RELATIONS_TABLE, null,
                    ContentValues().apply {
                        put("parent_id", it)
                        put("child_id", childId)
                    }).also { close() }
        }
    }

    fun insertChildRelations(parentId: Long, vararg childrenId: Long) {
        childrenId.forEach {
            writableDatabase.insert(NODE_RELATIONS_TABLE, null,
                    ContentValues().apply {
                        put("parent_id", parentId)
                        put("child_id", it)
                    }).also { close() }
        }
    }

    fun removeRelations(elementId: Long, vararg parentsId: Long) {
        parentsId.forEach {
            writableDatabase.delete(NODE_RELATIONS_TABLE, "child_id = ? AND parent_id = ?",
                    arrayOf(elementId.toString(), it.toString())).also { close() }
        }
    }

    fun getNode(select: Long? = null): ArrayList<Node> {
        val list = ArrayList<Node>()
        readableDatabase.rawQuery(if (select == null) "SELECT * FROM $NODE_TABLE"
        else "SELECT * FROM $NODE_TABLE WHERE _id = $select", emptyArray())
                .apply {
                    while (moveToNext()) {
                        list.add(Node(getInt(1), getChildren(getLong(0))))
                    }
                    close()
                }
        return list
    }

    fun getChildren(parentId: Long): ArrayList<Node> {
        val list = ArrayList<Node>()
        readableDatabase.rawQuery("""
            SELECT $NODE_TABLE.* FROM $NODE_TABLE INNER JOIN $NODE_RELATIONS_TABLE ON
            $NODE_TABLE._id=$NODE_RELATIONS_TABLE.child_id
            WHERE $NODE_RELATIONS_TABLE.parent_id=?
            """, arrayOf(parentId.toString())).run {
            while (moveToNext()) {
                list.add(Node(getInt(1), null))
            }
            close()
        }
        return list
    }

    fun getParents(childId: Long): ArrayList<Node> {
        val list = ArrayList<Node>()
        readableDatabase.rawQuery("""
            SELECT $NODE_TABLE.* FROM $NODE_TABLE INNER JOIN $NODE_RELATIONS_TABLE ON
            $NODE_TABLE._id=$NODE_RELATIONS_TABLE.parent_id
            WHERE $NODE_RELATIONS_TABLE.child_id=?
            """, arrayOf(childId.toString())).run {
            while (moveToNext()) {
                list.add(Node(getInt(1), null))
            }
            close()
        }
        return list
    }
}