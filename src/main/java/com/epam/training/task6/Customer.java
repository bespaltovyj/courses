package com.epam.training.task6;

import com.epam.training.task6.visitor.PrinterNode;

public class Customer {

    public void printTree(Tree tree) {
        for (Node node : tree) {
            node.accept(new PrinterNode());
        }
    }


}
