package com.epam.training.task1;

import com.epam.training.task1.enums.Rang;

public class Captain extends Staff {

    private Rang rang;

    public Captain(String name, int age, Rang rang) {
        super(name, age);
        this.rang = rang;
    }

    public Rang getRang() {
        return rang;
    }

    public void setRang(Rang rang) {
        this.rang = rang;
    }
}
