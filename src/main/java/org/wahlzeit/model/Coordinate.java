package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Coordinate {

    CartesianCoordinate asCartesianCoordinate();

    double getCartesianDistance(Coordinate coordinate) throws IllegalArgumentException;

    SphericCoordinate asSphericCoordinate();

    double getCentralAngle(Coordinate coordinate) throws IllegalArgumentException;

    boolean isEqual(Coordinate coordinate);

    void writeOn(ResultSet resultSet) throws SQLException;

    void readFrom(ResultSet resultSet) throws SQLException;

}
