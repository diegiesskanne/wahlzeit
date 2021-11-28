package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class CartesianCoordinate extends AbstractCoordinate {

    private double x;

    private double y;

    private double z;

    // may be discarded
    private Location location;

    public CartesianCoordinate(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public CartesianCoordinate(SphericCoordinate sphericCoordinate) {
        x = sphericCoordinate.getRadius() * Math.sin(sphericCoordinate.getTheta()) * Math.cos(sphericCoordinate.getPhi());

        y = sphericCoordinate.getRadius() * Math.sin(sphericCoordinate.getTheta()) * Math.sin(sphericCoordinate.getPhi());

        z = sphericCoordinate.getRadius() * Math.cos(sphericCoordinate.getTheta());
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public Location getLocation() { return location; }

    public void setLocation(Location location) { this.location = location; }

    public double getDistance(CartesianCoordinate another_Cartesian_coordinate){

        double squared_x = Math.pow((another_Cartesian_coordinate.x - this.x), 2);
        double squared_y = Math.pow((another_Cartesian_coordinate.y - this.y), 2);
        double squared_z = Math.pow((another_Cartesian_coordinate.z - this.z), 2);

        return Math.sqrt(squared_x + squared_y + squared_z);
    }

    @Override
    public String getIdAsString() {
        return null;
    }

    @Override
    public void readFrom(ResultSet resultSet) throws SQLException {
        resultSet.getDouble("coordinate_x");
        resultSet.getDouble("coordinate_y");
        resultSet.getDouble("coordinate_x");

    }

    @Override
    public void writeOn(ResultSet resultSet) throws SQLException {
        resultSet.updateDouble("coordinate_x", this.getX());
        resultSet.updateDouble("coordinate_y", this.getY());
        resultSet.updateDouble("coordinate_z", this.getZ());
    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) throws SQLException {

    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        return this;
    }


    @Override
    public SphericCoordinate asSphericCoordinate() {
        return new SphericCoordinate(this);
    }


}

