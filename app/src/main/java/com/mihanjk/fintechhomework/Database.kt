package com.mihanjk.fintechhomework

import android.arch.persistence.room.*
import android.content.Context
import io.reactivex.Flowable
import io.reactivex.Observable

@Entity(tableName = "node_relations",
        primaryKeys = arrayOf("parent_id", "child_id"),
        foreignKeys = arrayOf(ForeignKey(
                entity = Node::class, parentColumns = arrayOf("id"),
                childColumns = arrayOf("parent_id"),
                onDelete = ForeignKey.CASCADE)))
data class NodeChildren(@ColumnInfo(name = "parent_id")
                        val parentId: Long,
                        @ColumnInfo(name = "child_id")
                        val childId: Long)

@Dao
abstract class NodeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertNode(node: Node)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRelations(children: NodeChildren)

//    @Transaction
//    fun insertNode(node: Node, parentId: Long? = null) {
//        insertNode(node)
//        if (parentId != null) insertRelations(NodeChildren(parentId, node.id))
//
//        node.children.apply {
//            if (isNotEmpty()) forEach {
//                insertNode(it, node.id)
//            }
//        }
//    }

    @Query("SELECT * FROM nodes")
    abstract fun getNodes(): Flowable<List<Node>>

    @Transaction
    open fun getNode() = Observable.just(
            getNodes().blockingSingle()
                    .apply { forEach { it.children = getChildrenNodes(it.id).blockingSingle() } })

    @Query("SELECT nodes.* FROM nodes INNER JOIN node_relations ON nodes.id=node_relations.parent_id\n" +
            "WHERE node_relations.child_id=:arg0")
    abstract fun getParentsNodes(childId: Long): Flowable<List<Node>>

    @Query("SELECT nodes.* FROM nodes INNER JOIN node_relations ON nodes.id=node_relations.child_id\n" +
            "WHERE node_relations.parent_id=:arg0")
    abstract fun getChildrenNodes(parentId: Long): Flowable<List<Node>>
}

@Database(entities = arrayOf(Node::class, NodeChildren::class), version = 1)
abstract class NodeDatabase : RoomDatabase() {
    abstract fun getNodeDao(): NodeDao

    companion object {

        @Volatile private var INSTANCE: NodeDatabase? = null

        fun getInstance(context: Context): NodeDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        NodeDatabase::class.java, "node.db")
                        .build()
    }
}

object Injection {

    fun provideUserDataSource(context: Context): NodeDao {
        val database = NodeDatabase.getInstance(context)
        return database.getNodeDao()
    }
}
