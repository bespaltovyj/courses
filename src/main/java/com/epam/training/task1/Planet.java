package com.epam.training.task1;

import com.epam.training.task1.enums.Atmosphere;

public class Planet {

    private String name;
    private Galaxy galaxy;
    private Atmosphere atmosphere;

    public Planet(String name, Galaxy galaxy, Atmosphere atmosphere) {
        this.name = name;
        this.galaxy = galaxy;
        this.atmosphere = atmosphere;
    }

    public String getName() {
        return name;
    }

    public Galaxy getGalaxy() {
        return galaxy;
    }

    public Atmosphere getAtmosphere() {
        return atmosphere;
    }
}
