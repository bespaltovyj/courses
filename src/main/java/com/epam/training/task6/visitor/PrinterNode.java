package com.epam.training.task6.visitor;

import com.epam.training.task6.Node;

public class PrinterNode implements Visitor {

    @Override
    public void visit(Node node) {
        if (node.isLeaf()) {
            System.out.println("It is leaf");
            return;
        }
        System.out.println("It is branch");
    }
}
