package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class SphericCoordinate extends AbstractCoordinate {
    
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

        // precondition
        assert c.assertClassInvariants();

        radius = Math.sqrt(Math.pow(c.getX(), 2) + Math.pow(c.getY(), 2) + Math.pow(c.getZ(), 2));

        if(c.getZ() != 0) {
            theta = Math.atan(Math.sqrt(Math.pow(c.getX(), 2) + Math.pow(c.getY(), 2)) / c.getZ());
        }

        if (c.getX() > 0) {
            phi = Math.atan(c.getY() / c.getX());
        }else if (c.getX() < 0) {
            phi = Math.atan(c.getY() / c.getX()) + Math.PI;
        }else {
            phi = Math.PI / 2;
        }

        // postcondition
        assert assertClassInvariants();

    }

    public double getPhi() {
        assert 0 <= phi;
        assert phi <= (2 * Math.PI);
        return phi;
    }

    public double getTheta() {
        assert 0 <= theta;
        assert theta <= (2 * Math.PI);
        return theta;
    }

    public double getRadius() { return radius; }

    public void setPhi(double phi){
        assert 0 <= phi;
        assert phi <= (2 * Math.PI);
        this.phi = phi;
    }

    public void setTheta(double theta){
        assert 0 <= theta;
        assert theta <= (2 * Math.PI);
        this.theta = theta;
    }

    public void setRadius(double radius){ this.radius = radius; }

    public Location getLocation() { return location; }

    public void setLocation(Location location) { this.location = location; }

    public double calculateCentralAngle(SphericCoordinate other_sphericCoordinate){

        // preconditions
        assert other_sphericCoordinate != null;
        assert assertClassInvariants();

        double theta = this.getTheta();

        double phi = this.getPhi();

        return Math.acos(
                Math.sin(phi) * Math.sin(other_sphericCoordinate.getPhi())
                        + Math.cos(phi) * Math.cos(other_sphericCoordinate.getPhi())
                        * Math.cos(Math.abs(theta-other_sphericCoordinate.getTheta()))
        );
    }

    protected boolean assertClassInvariants () {
        return 0 <= this.theta && this.theta <= (2 * Math.PI) && 0 <= this.phi && this.phi <= (2 * Math.PI);
    }
    
    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        return new CartesianCoordinate(this);
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        return this;
    }


    @Override
    public void readFrom(ResultSet resultSet) throws SQLException {

        // preconditions
        assert resultSet != null;
        assertClassInvariants();

        resultSet.getDouble("coordinate_theta");
        resultSet.getDouble("coordinate_phi");
        resultSet.getDouble("coordinate_radius");
    }

    @Override
    public void writeOn(ResultSet resultSet) throws SQLException {

        // preconditions
        assert resultSet != null;
        assertClassInvariants();

        resultSet.updateDouble("coordinate_theta", this.getTheta());
        resultSet.updateDouble("coordinate_phi", this.getPhi());
        resultSet.updateDouble("coordinate_radius", this.getRadius());
    }
}
