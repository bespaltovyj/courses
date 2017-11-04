package com.epam.training.task6;


import com.epam.training.task6.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

public class Node  {

    private int value;
    private List<Node> descendants;

    public Node(int value) {
        this(value, new ArrayList<>());
    }

    public Node(int value, List<Node> descendants) {
        this.value = value;
        this.descendants = descendants;
    }

    public int getValue() {
        return value;
    }

    public List<Node> getDescendants() {
        return descendants;
    }

    public void addDescendants(Node descendant) {
        descendants.add(descendant);
    }

    public boolean isLeaf() {
        return descendants.isEmpty();
    }

    public void accept(Visitor visitor){
        visitor.visit(this);
    }
}
