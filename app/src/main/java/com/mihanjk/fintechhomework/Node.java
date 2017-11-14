package com.mihanjk.fintechhomework;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "nodes")
public class Node {
    @PrimaryKey(autoGenerate = true)
    long id;
    int value;
    @Ignore
    List<Node> children;

    public Node() {
    }

    public Node(int value, List<Node> children) {
        this.value = value;
        this.children = children;
    }

    public Node(long id, int value, List<Node> children) {
        this.id = id;
        this.value = value;
        this.children = children;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }
}
