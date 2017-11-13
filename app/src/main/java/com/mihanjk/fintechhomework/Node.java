package com.mihanjk.fintechhomework;

import java.util.List;

public class Node {
    int value;
    List<Node> children;

    public Node(int value, List<Node> children) {
        this.value = value;
        this.children = children;
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
