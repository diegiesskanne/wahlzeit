package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public abstract class AbstractCoordinate extends DataObject implements Coordinate {

    @Override
    public double getCentralAngle(Coordinate coordinate){

        double theta = coordinate.asSphericCoordinate().getTheta();

        double phi = coordinate.asSphericCoordinate().getPhi();

        return Math.acos(
                Math.sin(phi) * Math.sin(this.asSphericCoordinate().getPhi())
                        + Math.cos(phi) * Math.cos(this.asSphericCoordinate().getPhi())
                        * Math.cos(Math.abs(theta-this.asSphericCoordinate().getTheta()))
        );
    }

    @Override
    public boolean isEqual(Coordinate another_coordinate){

        if(another_coordinate == null) {
            return false;
        } else {

            CartesianCoordinate c = another_coordinate.asCartesianCoordinate();
            double max_delta = 0.000001;
            double delta_x = this.asCartesianCoordinate().getX() - c.getX();
            double delta_y = this.asCartesianCoordinate().getY() - c.getY();
            double delta_z = this.asCartesianCoordinate().getZ() - c.getZ();

            return Math.abs(delta_x) < max_delta && Math.abs(delta_y) < max_delta && Math.abs(delta_z) < max_delta;

        }
    }

    @Override
    public double getCartesianDistance(Coordinate coordinate) {
        return this.asCartesianCoordinate().getDistance(coordinate.asCartesianCoordinate());
    }

    @Override
    public int hashCode() {
        CartesianCoordinate this_coordinate = this.asCartesianCoordinate();
        return Objects.hash(
                this_coordinate.getX(), this_coordinate.getY(), this_coordinate.getZ(),
                this_coordinate.getLocation());
    }

    @Override
    public boolean equals(Object o){
        if ((o instanceof Coordinate)){
            return isEqual((Coordinate) o);
        }else{
            return false;
        }
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
