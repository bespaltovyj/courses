package com.epam.training.task6.traversing;

import com.epam.training.task6.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BreadthFirstTraversing extends TraversingTree {

    @Override
    public List<Node> getListNode(Node parentNode) {
        return getAllDescendants(Collections.singletonList(parentNode));
    }

    private List<Node> getAllDescendants(List<Node> currentNodes) {
        List<Node> allDescendants = new ArrayList<>();
        allDescendants.addAll(currentNodes);
        List<Node> descendantsOnNextLevel = currentNodes
                .stream()
                .flatMap(x -> x.getDescendants().stream())
                .collect(Collectors.toList());
        if (!descendantsOnNextLevel.isEmpty()) {
            allDescendants.addAll(getAllDescendants(descendantsOnNextLevel));
        }
        return allDescendants;
    }
}
