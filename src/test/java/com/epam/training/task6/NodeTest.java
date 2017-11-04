package com.epam.training.task6;

import com.epam.training.task6.traversing.BreadthFirstTraversing;
import com.epam.training.task6.traversing.DepthFirstTraversing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {
    Tree tree;

    /**
     * This method creates such a tree:
     * 1
     * 2       3     4     8
     * 5    6   7
     * BFS: 1,2,3,4,8,5,6,7
     * DFT: 1,2,5,6,3,7,4,8
     */
    @BeforeEach
    void setUp() {
        Node parentNode = new Node(1);

        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node8 = new Node(8);

        parentNode.addDescendants(node2);
        parentNode.addDescendants(node3);
        parentNode.addDescendants(node4);
        parentNode.addDescendants(node8);

        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);

        node2.addDescendants(node5);
        node2.addDescendants(node6);

        node3.addDescendants(node7);
        tree = new Tree(parentNode);
    }


    @Test
    void testDepthFirstTraversing() {
        tree.setTraversingTree(new DepthFirstTraversing());
        List<Integer> correctResult = Arrays.asList(1, 2, 5, 6, 3, 7, 4, 8);
        List<Integer> currentResult = new ArrayList<>();
        for (Node node : tree) {
            currentResult.add(node.getValue());
        }
        assertEquals(correctResult, currentResult);
    }

    @Test
    void testBreadthFirstTraversing() {
        tree.setTraversingTree(new BreadthFirstTraversing());
        List<Integer> correctResult = Arrays.asList(1, 2, 3, 4, 8, 5, 6, 7);
        List<Integer> currentResult = new ArrayList<>();
        for (Node node : tree) {
            currentResult.add(node.getValue());
        }
        assertEquals(correctResult, currentResult);
    }

}