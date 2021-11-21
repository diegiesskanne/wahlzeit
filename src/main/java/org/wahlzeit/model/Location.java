package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Location extends DataObject {

    // Coordinate has to be final, because this way it is insured that there is exactly one Coordinate
    private final CartesianCoordinate cartesianCoordinate;

    public Location(CartesianCoordinate cartesianCoordinate) {
        this.cartesianCoordinate = cartesianCoordinate;
    }

    public CartesianCoordinate getCoordinate() {
        return cartesianCoordinate;
    }

    @Override
    public String getIdAsString() {
        return null;
    }

    @Override
    public void readFrom(ResultSet resultSet) throws SQLException {
        this.cartesianCoordinate.readFrom(resultSet);
    }

    @Override
    public void writeOn(ResultSet resultSet) throws SQLException {
        this.cartesianCoordinate.writeOn(resultSet);
    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) throws SQLException {

    }
}
