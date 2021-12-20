package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class SphericCoordinate extends AbstractCoordinate {
    
    private final double phi;
    
    private final double theta;
    
    private final double radius;

    // may be discarded
    private Location location;

    public static final HashMap<SphericCoordinate, SphericCoordinate> sphericCoordinateMap = new HashMap<>();

    public static SphericCoordinate getSphericCoordinateObject(double phi, double theta, double radius) throws CoordinateException {
        SphericCoordinate coordinate = new SphericCoordinate(phi, theta, radius);

        SphericCoordinate rcoordinate = sphericCoordinateMap.get(coordinate);
        synchronized (Coordinate.class) {
            if (rcoordinate == null) {
                sphericCoordinateMap.put(coordinate, coordinate);
                return coordinate;
            } else {
                return rcoordinate;
            }
        }
    }

    public SphericCoordinate(double phi, double theta, double radius) throws CoordinateException {

        this.phi = phi % (2 * Math.PI);
        this.theta = theta % (2 * Math.PI);
        this.radius = radius;
    }

    public SphericCoordinate(CartesianCoordinate c) throws CoordinateException {

        // precondition
        assert c.assertClassInvariants();

        this.radius = Math.sqrt(Math.pow(c.getX(), 2) + Math.pow(c.getY(), 2) + Math.pow(c.getZ(), 2));

        if(c.getZ() != 0) {
            this.theta = Math.atan(Math.sqrt(Math.pow(c.getX(), 2) + Math.pow(c.getY(), 2)) / c.getZ());
        }else {
            this.theta = 0;
        }

        if (c.getX() > 0) {
            this.phi = Math.atan(c.getY() / c.getX());
        }else if (c.getX() < 0) {
            this.phi = Math.atan(c.getY() / c.getX()) + Math.PI;
        }else {
            this.phi = Math.PI / 2;
        }

        // postcondition
        assert assertClassInvariants();

    }

    public double getPhi() {
        assert 0 <= this.phi;
        assert this.phi <= (2 * Math.PI);
        return this.phi;
    }

    public double getTheta() {
        assert 0 <= this.theta;
        assert this.theta <= (2 * Math.PI);
        return this.theta;
    }

    public double getRadius() { return this.radius; }

    public SphericCoordinate setPhi(double phi) throws CoordinateException {
        assert 0 <= phi;
        assert phi <= (2 * Math.PI);
        return getSphericCoordinateObject(phi, this.theta, this.radius);
    }

    public SphericCoordinate setTheta(double theta) throws CoordinateException {
        assert 0 <= theta;
        assert theta <= (2 * Math.PI);
        return getSphericCoordinateObject(this.phi, theta, this.radius);
    }

    public SphericCoordinate setRadius(double radius) throws CoordinateException {
        return getSphericCoordinateObject(this.phi, this.theta, radius);
    }

    public Location getLocation() { return location; }

    public void setLocation(Location location) { this.location = location; }

    public double calculateCentralAngle(SphericCoordinate other_sphericCoordinate) throws CoordinateException{

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

    @Override
    protected boolean assertClassInvariants() {
        return 0 <= this.theta && this.theta <= (2 * Math.PI)
                && 0 <= this.phi && this.phi <= (2 * Math.PI)
                && 0 <= this.radius && this.radius <= Double.MAX_VALUE;
    }
    
    @Override
    public CartesianCoordinate asCartesianCoordinate() throws CoordinateException {

        return new CartesianCoordinate(this);
    }

    @Override
    public SphericCoordinate asSphericCoordinate() throws CoordinateException {
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
