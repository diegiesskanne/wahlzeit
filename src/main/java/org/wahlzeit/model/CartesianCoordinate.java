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

        // precondition
        assert sphericCoordinate.assertClassInvariants();

        x = sphericCoordinate.getRadius() * Math.sin(sphericCoordinate.getTheta()) * Math.cos(sphericCoordinate.getPhi());

        y = sphericCoordinate.getRadius() * Math.sin(sphericCoordinate.getTheta()) * Math.sin(sphericCoordinate.getPhi());

        z = sphericCoordinate.getRadius() * Math.cos(sphericCoordinate.getTheta());

        // postcondition
        assert assertClassInvariants();

    }

    public double getX() {
        assert x <= Double.MAX_VALUE;
        return x;
    }

    public double getY() {
        assert y <= Double.MAX_VALUE;
        return y;
    }

    public double getZ() {
        assert z <= Double.MAX_VALUE;
        return z;
    }

    public void setX(double x) {
        assert x <= Double.MAX_VALUE;
        this.x = x;
    }

    public void setY(double y) {
        assert y <= Double.MAX_VALUE;
        this.y = y;
    }

    public void setZ(double z) {
        assert z <= Double.MAX_VALUE;
        this.z = z;
    }

    public Location getLocation() { return location; }

    public void setLocation(Location location) { this.location = location; }

    public int cartesianHash(){

        // precondition
        assert assertClassInvariants();

        return Objects.hash(
                this.getX(), this.getY(), this.getZ(),
                this.getLocation());
    }

    public boolean checkEquality(CartesianCoordinate cartesianCoordinate){

        // preconditions
        assert cartesianCoordinate != null;
        assert assertClassInvariants();

        double max_delta = 0.000001;
        double delta_x = this.getX() - cartesianCoordinate.getX();
        double delta_y = this.getY() - cartesianCoordinate.getY();
        double delta_z = this.getZ() - cartesianCoordinate.getZ();

        return Math.abs(delta_x) < max_delta && Math.abs(delta_y) < max_delta && Math.abs(delta_z) < max_delta;
    }

    public double getDistance(CartesianCoordinate cartesian_coordinate){

        // preconditions
        assert cartesian_coordinate != null;
        assert assertClassInvariants();

        double squared_x = Math.pow((cartesian_coordinate.x - this.x), 2);
        double squared_y = Math.pow((cartesian_coordinate.y - this.y), 2);
        double squared_z = Math.pow((cartesian_coordinate.z - this.z), 2);

        return Math.sqrt(squared_x + squared_y + squared_z);
    }

    protected boolean assertClassInvariants() {
        return this.x < Double.MAX_VALUE && this.y < Double.MAX_VALUE && this.z < Double.MAX_VALUE;
    }

    @Override
    public void readFrom(ResultSet resultSet) throws SQLException {

        // preconditions
        assert resultSet != null;
        assert assertClassInvariants();

        resultSet.getDouble("coordinate_x");
        resultSet.getDouble("coordinate_y");
        resultSet.getDouble("coordinate_x");

    }

    @Override
    public void writeOn(ResultSet resultSet) throws SQLException {

        // preconditions
        assert resultSet != null;
        assert assertClassInvariants();

        resultSet.updateDouble("coordinate_x", this.getX());
        resultSet.updateDouble("coordinate_y", this.getY());
        resultSet.updateDouble("coordinate_z", this.getZ());
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

