package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Coordinate extends DataObject {

    private double x;

    private double y;

    private double z;

    // may be discarded
    private Location location;

    public Coordinate(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
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

    public double getDistance(Coordinate another_coordinate){

        double squared_x = Math.pow((another_coordinate.x - this.x), 2);
        double squared_y = Math.pow((another_coordinate.y - this.y), 2);
        double squared_z = Math.pow((another_coordinate.z - this.z), 2);

        return Math.sqrt(squared_x + squared_y + squared_z);
    }

    public boolean isEqual(Coordinate another_coordinate){
        if(another_coordinate == null) {
            return false;
        }else {
            double max_delta = 0.000001;
            double delta_x = this.x - another_coordinate.x;
            double delta_y = this.y - another_coordinate.y;
            double delta_z = this.z - another_coordinate.z;

            return Math.abs(delta_x) < max_delta && Math.abs(delta_y) < max_delta && Math.abs(delta_z) < max_delta;
        }
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
    public int hashCode() {
        return Objects.hash(x, y, z, location);
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
}

