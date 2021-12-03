package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractCoordinate extends DataObject implements Coordinate {

    @Override
    public double getCentralAngle(Coordinate coordinate){

        // preconditions
        if (coordinate == null) throw new IllegalArgumentException("coordinate is null");
        this.assertClassInvariants();

        // convert to spheric and call implementation method
        double centralAngle = this.asSphericCoordinate().calculateCentralAngle(coordinate.asSphericCoordinate());

        // postcondition
        assert 0 <= centralAngle;
        assert centralAngle <= (2 * Math.PI);

        return centralAngle;

    }

    @Override
    public boolean isEqual(Coordinate another_coordinate){

        // precondition
        this.assertClassInvariants();

        if(another_coordinate == null) {
            return false;
        } else {
            return this.asCartesianCoordinate().checkEquality(another_coordinate.asCartesianCoordinate());
        }
    }

    @Override
    public double getCartesianDistance(Coordinate coordinate) {

        // precondition
        if (coordinate == null) throw new IllegalArgumentException("coordinate is null");
        this.assertClassInvariants();

        // convert to cartesian and call implementation method
        double cartesianDistance = this.asCartesianCoordinate().getDistance(coordinate.asCartesianCoordinate());

        // postcondition
        assert 0 <= cartesianDistance;

        return cartesianDistance;
    }

    @Override
    public int hashCode() {
        return this.asCartesianCoordinate().cartesianHash();
    }

    @Override
    public boolean equals(Object o){

        // precondition
        assertClassInvariants();

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

    protected abstract boolean assertClassInvariants();
}
