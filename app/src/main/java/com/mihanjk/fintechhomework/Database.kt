package com.mihanjk.fintechhomework

import android.arch.persistence.room.*
import android.content.Context
import io.reactivex.Flowable
import java.util.*

@Entity(tableName = "nodes")
data class NodeEntity(@PrimaryKey(autoGenerate = true)
                      val id: String = UUID.randomUUID().toString(),
                      val value: Int)

@Entity(tableName = "node_relations",
        primaryKeys = arrayOf("parent_id", "child_id"),
        foreignKeys = arrayOf(ForeignKey(
                entity = NodeEntity::class, parentColumns = arrayOf("id"),
                childColumns = arrayOf("parent_id"),
                onDelete = ForeignKey.CASCADE)))
data class NodeChildren(@ColumnInfo(name = "parent_id")
                        val parentId: String,
                        @ColumnInfo(name = "child_id")
                        val childId: String)

@Dao
interface NodeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNode(nodeEntity: NodeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRelations(children: NodeChildren)

    @Transaction
    fun insertNode(node: Node, parentId: String? = null) {
        val nodeEntity = NodeEntity(value = node.value)
        insertNode(nodeEntity)
        if (parentId != null) insertRelations(NodeChildren(parentId, nodeEntity.id))

        node.children.apply {
            if (isNotEmpty()) forEach {
                insertNode(it, nodeEntity.id)
            }
        }
    }

    @Query("SELECT * ")
    fun getNode(): Flowable<List<Node>>


}

@Database(entities = arrayOf(NodeEntity::class, NodeChildren::class), version = 1)
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
                        NodeDatabase::class.java, "NodeEntity.db")
                        .build()
    }
}
