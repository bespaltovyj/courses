package com.epam.training.task1;

import java.util.ArrayList;
import java.util.List;

public class Trip {

    private Planet sourcePlanet;
    private Planet destinationPlanet;
    private Ship ship;
    private Captain captain;
    private List<Engineer> engineers = new ArrayList<>();

    public Trip(Planet sourcePlanet, Planet destinationPlanet, Ship ship, Captain captain) {
        this.sourcePlanet = sourcePlanet;
        this.destinationPlanet = destinationPlanet;
        this.ship = ship;
        this.captain = captain;
    }

    public void addEngineer(Engineer engineer) {
        engineers.add(engineer);
    }

    public Planet getSourcePlanet() {
        return sourcePlanet;
    }

    public Planet getDestinationPlanet() {
        return destinationPlanet;
    }

    public Ship getShip() {
        return ship;
    }

    public Captain getCaptain() {
        return captain;
    }

    public List<Engineer> getEngineers() {
        return engineers;
    }
}
