package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class SphericCoordinate extends DataObject implements Coordinate {
    
    private double phi;
    
    private double theta;
    
    private double radius;

    // may be discarded
    private Location location;

    public SphericCoordinate(double phi, double theta, double radius){
        this.phi = phi;
        this.theta = theta;
        this.radius = radius;
    }

    public SphericCoordinate(CartesianCoordinate c) {
        radius = Math.sqrt(Math.pow(c.getX(), 2) + Math.pow(c.getY(), 2) + Math.pow(c.getZ(), 2));

        theta = Math.atan( Math.sqrt(Math.pow(c.getX(), 2) + Math.pow(c.getY(), 2)) / c.getZ());

        if (c.getX() > 0) {
            phi = Math.atan(c.getY() / c.getX());
        }else if (c.getX() < 0) {
            phi = Math.atan(c.getY() / c.getX()) + Math.PI;
        }else {
            phi = Math.PI / 2;
        }

    }

    public double getPhi() { return phi; }

    public double getTheta() { return theta; }

    public double getRadius() { return radius; }

    public void setPhi(double phi){ this.phi = phi; }

    public void setTheta(double theta){ this.theta = theta; }

    public void setRadius(double radius){ this.radius = radius; }

    public Location getLocation() { return location; }

    public void setLocation(Location location) { this.location = location; }

    
    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        return new CartesianCoordinate(this);
    }

    @Override
    public double getCartesianDistance(Coordinate coordinate) {
        return asCartesianCoordinate().getCartesianDistance(coordinate);
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        return this;
    }

    @Override
    public double getCentralAngle(Coordinate coordinate) {

        double theta = coordinate.asSphericCoordinate().getTheta();

        double phi = coordinate.asSphericCoordinate().getPhi();

        return Math.acos(
                Math.sin(phi) * Math.sin(this.phi)
                + Math.cos(phi) * Math.cos(this.phi) * Math.cos(Math.abs(theta-this.theta))
        );
    }


    @Override
    public boolean isEqual(Coordinate coordinate) {

        if (coordinate == null) {
            return false;
        }else {
            return asCartesianCoordinate().isEqual(coordinate);
        }

    }

    @Override
    public int hashCode() {
        return Objects.hash(phi, theta, radius);
    }

    @Override
    public String getIdAsString() {
        return null;
    }

    @Override
    public void readFrom(ResultSet resultSet) throws SQLException {
        resultSet.getDouble("coordinate_theta");
        resultSet.getDouble("coordinate_phi");
        resultSet.getDouble("coordinate_radius");
    }

    @Override
    public void writeOn(ResultSet resultSet) throws SQLException {
        resultSet.updateDouble("coordinate_theta", this.getTheta());
        resultSet.updateDouble("coordinate_phi", this.getPhi());
        resultSet.updateDouble("coordinate_radius", this.getRadius());
    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) throws SQLException {

    }
}
