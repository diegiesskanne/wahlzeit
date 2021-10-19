package org.wahlzeit.model;

public class Location {

    // Coordinate has to be final, because this way it is insured that there is exactly one Coordinate
    private final Coordinate coordinate;

    public Location(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

}
