package org.wahlzeit.model;

import org.wahlzeit.annotations.PatternInstance;
import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

@PatternInstance(
        patternName = "Template Pattern",
        participants = { "Concrete Class" }
)
public class CartesianCoordinate extends AbstractCoordinate {

    private final double x;

    private final double y;

    private final double z;

    // may be discarded
    private Location location;

    public static final HashMap<CartesianCoordinate, CartesianCoordinate> cartesianCoordinateMap = new HashMap<>();

    public static CartesianCoordinate getCartesianCoordinateObject(double x, double y, double z) throws CoordinateException {
        CartesianCoordinate coordinate = new CartesianCoordinate(x, y, z);

        CartesianCoordinate rcoordinate = cartesianCoordinateMap.get(coordinate);
        synchronized (Coordinate.class) {
            if (rcoordinate == null) {
                cartesianCoordinateMap.put(coordinate, coordinate);
                return coordinate;
            } else {
                return rcoordinate;
            }
        }
    }

    public CartesianCoordinate(double x, double y, double z) throws CoordinateException {

        if (Double.isNaN(x) || Double.isNaN(y) || Double.isNaN(z)) throw new CoordinateException("A value is not a number");
        this.x = x;
        this.y = y;
        this.z = z;

        assertClassInvariants();
    }

    public CartesianCoordinate(SphericCoordinate sphericCoordinate) throws CoordinateException {

        try {
            // precondition
            sphericCoordinate.assertClassInvariants();

            this.x = sphericCoordinate.getRadius() * Math.sin(sphericCoordinate.getTheta()) * Math.cos(sphericCoordinate.getPhi());

            this.y = sphericCoordinate.getRadius() * Math.sin(sphericCoordinate.getTheta()) * Math.sin(sphericCoordinate.getPhi());

            this.z = sphericCoordinate.getRadius() * Math.cos(sphericCoordinate.getTheta());

            getCartesianCoordinateObject(this.x, this.y, this.z);
            // postcondition
            assertClassInvariants();
        }catch (CoordinateException ex){
            throw new CoordinateException("There is something wrong, when creating this Coordinate!", ex);
        }

    }

    public double getX() {
        assert this.x <= Double.MAX_VALUE;
        return this.x;
    }

    public double getY() {
        assert this.y <= Double.MAX_VALUE;
        return this.y;
    }

    public double getZ() {
        assert this.z <= Double.MAX_VALUE;
        return this.z;
    }

    public CartesianCoordinate setX(double x) throws CoordinateException {
        assert x <= Double.MAX_VALUE;
        return getCartesianCoordinateObject(x, this.y, this.z);
    }

    public CartesianCoordinate setY(double y) throws CoordinateException {
        assert y <= Double.MAX_VALUE;
        return getCartesianCoordinateObject(this.x, y, this.z);
    }

    public CartesianCoordinate setZ(double z) throws CoordinateException {
        assert z <= Double.MAX_VALUE;
        return getCartesianCoordinateObject(this.x, this.y, z);
    }

    public Location getLocation() { return location; }

    public void setLocation(Location location) { this.location = location; }

    public int cartesianHash() throws CoordinateException {

        // precondition
        assertClassInvariants();

        return Objects.hash(
                this.getX(), this.getY(), this.getZ(),
                this.getLocation());
    }

    public boolean checkEquality(CartesianCoordinate cartesianCoordinate) throws CoordinateException{

        // preconditions
        assert cartesianCoordinate != null;
        try {

            assertClassInvariants();

            double max_delta = 0.001;
            double delta_x = this.getX() - cartesianCoordinate.getX();
            double delta_y = this.getY() - cartesianCoordinate.getY();
            double delta_z = this.getZ() - cartesianCoordinate.getZ();

            return Math.abs(delta_x) < max_delta && Math.abs(delta_y) < max_delta && Math.abs(delta_z) < max_delta;
        }catch (CoordinateException ex){
            throw new CoordinateException("There is something wrong with the Coordinates!", ex);
        }
    }

    public double getDistance(CartesianCoordinate cartesian_coordinate) throws CoordinateException{


        // preconditions
        assert cartesian_coordinate != null;
        try {

            assertClassInvariants();

            double squared_x = Math.pow((cartesian_coordinate.x - this.x), 2);
            double squared_y = Math.pow((cartesian_coordinate.y - this.y), 2);
            double squared_z = Math.pow((cartesian_coordinate.z - this.z), 2);

            return Math.sqrt(squared_x + squared_y + squared_z);
        }catch (CoordinateException ex){
            throw new CoordinateException("There is something wrong with the Coordinates!", ex);
        }
    }

    protected void assertClassInvariants() throws CoordinateException {
        if(this.x > Double.MAX_VALUE || this.y > Double.MAX_VALUE || this.z > Double.MAX_VALUE){
            throw new CoordinateException("The values are not admissible!");
        }
        if (Double.isNaN(x) || Double.isNaN(y) || Double.isNaN(z)) throw new CoordinateException("A value is not a number");
    }

    @Override
    public void readFrom(ResultSet resultSet) throws SQLException {

        // preconditions
        assert resultSet != null;

        resultSet.getDouble("coordinate_x");
        resultSet.getDouble("coordinate_y");
        resultSet.getDouble("coordinate_x");

    }

    @Override
    public void writeOn(ResultSet resultSet) throws SQLException {

        // preconditions
        assert resultSet != null;

        resultSet.updateDouble("coordinate_x", this.getX());
        resultSet.updateDouble("coordinate_y", this.getY());
        resultSet.updateDouble("coordinate_z", this.getZ());
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() throws CoordinateException {
        return this;
    }


    @Override
    public SphericCoordinate asSphericCoordinate() throws CoordinateException {
        return new SphericCoordinate(this);
    }


}

