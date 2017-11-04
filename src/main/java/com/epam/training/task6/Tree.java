package com.epam.training.task6;

import com.epam.training.task6.traversing.TraversingTree;

import java.util.Iterator;

public class Tree implements Iterable<Node> {

    private Node parentNode;
    private TraversingTree traversingTree;

    @Override
    public Iterator<Node> iterator() {
        return traversingTree.iterator();
    }

    public Tree(Node parentNode) {
        this(parentNode, null);
    }

    public Tree(Node parentNode, TraversingTree traversingTree) {
        this.parentNode = parentNode;
        this.traversingTree = traversingTree;
        if (traversingTree != null) {
            traversingTree.setParentNode(parentNode);
        }
    }

    public Node getParentNode() {
        return parentNode;
    }

    public TraversingTree getTraversingTree() {
        return traversingTree;
    }

    public void setTraversingTree(TraversingTree traversingTree) {
        this.traversingTree = traversingTree;
        if (traversingTree != null) {
            traversingTree.setParentNode(parentNode);
        }
    }

}
