package com.epam.training.task1;

import com.epam.training.task1.enums.Specialization;

public class Engineer extends Staff {

    private Specialization specialization;
    private int numberFlightHours;

    public Engineer(String name, int age, Specialization specialization, int numberFlightHours) {
        super(name, age);
        this.specialization = specialization;
        this.numberFlightHours = numberFlightHours;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public int getNumberFlightHours() {
        return numberFlightHours;
    }

    public void setNumberFlightHours(int numberFlightHours) {
        this.numberFlightHours = numberFlightHours;
    }
}
