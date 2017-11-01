package com.epam.training.task6;

import com.epam.training.task6.traversing.TraversingTree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Node implements Iterable<Node> {

    private int value;
    private List<Node> descendants;
    private TraversingTree traversingTree;

    public Node(int value) {
        this(value, new ArrayList<>(), null);
    }

    public Node(int value, TraversingTree TraversingTree) {
        this(value, new ArrayList<>(), TraversingTree);
    }

    public Node(int value, List<Node> descendants, TraversingTree traversingTree) {
        this.value = value;
        this.descendants = descendants;
        this.traversingTree = traversingTree;
        if (traversingTree != null) {
            traversingTree.setParentNode(this);
        }
    }

    @Override
    public Iterator<Node> iterator() {
        return traversingTree.iterator();
    }

    public int getValue() {
        return value;
    }

    public List<Node> getDescendants() {
        return descendants;
    }

    public TraversingTree getTraversingTree() {
        return traversingTree;
    }

    public void setTraversingTree(TraversingTree traversingTree) {
        this.traversingTree = traversingTree;
        traversingTree.setParentNode(this);
    }

    public void addDescendants(Node descendant) {
        descendants.add(descendant);
    }

    public boolean isLeaf() {
        return descendants.isEmpty();
    }
}
