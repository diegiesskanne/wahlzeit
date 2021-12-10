package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Coordinate {

    CartesianCoordinate asCartesianCoordinate() throws CoordinateException;

    double getCartesianDistance(Coordinate coordinate) throws IllegalArgumentException, CoordinateException;

    SphericCoordinate asSphericCoordinate() throws CoordinateException;

    double getCentralAngle(Coordinate coordinate) throws IllegalArgumentException, CoordinateException;

    boolean isEqual(Coordinate coordinate) throws CoordinateException;

    void writeOn(ResultSet resultSet) throws SQLException;

    void readFrom(ResultSet resultSet) throws SQLException;

}
