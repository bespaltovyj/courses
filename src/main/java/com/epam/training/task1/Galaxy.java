package com.epam.training.task1;

import java.util.ArrayList;
import java.util.List;

public class Galaxy {

    private String name;
    private List<Planet> planets = new ArrayList<>();

    public Galaxy(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Planet> getPlanets() {
        return planets;
    }

    public void addPlanet(Planet planet) {
        planets.add(planet);
    }

}
