package com.epam.training.task6.traversing;

import com.epam.training.task6.Node;

import java.util.Iterator;
import java.util.List;

public abstract class TraversingTree implements Iterable<Node> {

    private Node parentNode;

    public abstract List<Node> getListNode(Node parentNode);

    @Override
    public Iterator<Node> iterator() {
        return getListNode(getParentNode()).iterator();
    }

    public TraversingTree() {
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }

    public Node getParentNode() {
        return parentNode;
    }
}
