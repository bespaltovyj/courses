package com.epam.training.task6.traversing;

import com.epam.training.task6.Node;

import java.util.ArrayList;
import java.util.List;

public class DepthFirstTraversing extends TraversingTree {

    public DepthFirstTraversing() {
        super();
    }

    @Override
    public List<Node> getListNode(Node parentNode) {
        List<Node> nodes = new ArrayList<>();
        nodes.add(parentNode);
        if (parentNode.getDescendants().isEmpty()) {
            return nodes;
        }
        for (Node descendant : parentNode.getDescendants()) {
            nodes.addAll(getListNode(descendant));
        }
        return nodes;
    }


}
