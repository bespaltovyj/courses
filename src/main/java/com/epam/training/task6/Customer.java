package com.epam.training.task6;

public class Customer {

    public void printTree(Node tree) {
        for (Node node : tree) {
            if (node.isLeaf()) {
                System.out.println("It is leaf");
                continue;
            }
            System.out.println("It is branch");
        }
    }
}
