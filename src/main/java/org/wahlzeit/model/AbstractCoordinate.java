package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public abstract class AbstractCoordinate extends DataObject implements Coordinate {

    @Override
    public double getCentralAngle(Coordinate coordinate){

        return this.asSphericCoordinate().calculateCentralAngle(coordinate.asSphericCoordinate());

    }

    @Override
    public boolean isEqual(Coordinate another_coordinate){

        if(another_coordinate == null) {
            return false;
        } else {
            return this.asCartesianCoordinate().checkEquality(another_coordinate.asCartesianCoordinate());
        }
    }

    @Override
    public double getCartesianDistance(Coordinate coordinate) {
        return this.asCartesianCoordinate().getDistance(coordinate.asCartesianCoordinate());
    }

    @Override
    public int hashCode() {
        return this.asCartesianCoordinate().cartesianHash();
    }

    @Override
    public boolean equals(Object o){
        if ((o instanceof Coordinate)){
            return isEqual((Coordinate) o);
        }else{
            return false;
        }
    }

    @Override
    public String getIdAsString() {
        throw new UnsupportedOperationException("This Class has no ID!");
    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) throws SQLException {
        throw new UnsupportedOperationException("This Class has no ID!");
    }

    // Abstract Methods to be implemented in the subclasses
    @Override
    public abstract SphericCoordinate asSphericCoordinate();

    @Override
    public abstract CartesianCoordinate asCartesianCoordinate();

    @Override
    public abstract void readFrom(ResultSet resultSet) throws SQLException;

    @Override
    public abstract void writeOn(ResultSet resultSet) throws SQLException;
}
