package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

public class CoordinateTest {

    private CartesianCoordinate cartesianCoordinate1;

    private CartesianCoordinate cartesianCoordinate2;

    private CartesianCoordinate cartesianCoordinate3;

    private CartesianCoordinate cartesianCoordinate4;

    @Before
    public void initCoordinates() throws CoordinateException{
        cartesianCoordinate1 = CartesianCoordinate.getCartesianCoordinateObject(0.0, 0.0, 3.0);
        cartesianCoordinate2 = CartesianCoordinate.getCartesianCoordinateObject(0.3333, (2.0 / 3.0), 24);
        cartesianCoordinate3 = CartesianCoordinate.getCartesianCoordinateObject(0.0, 1243.0, 42.0);
        cartesianCoordinate4 = CartesianCoordinate.getCartesianCoordinateObject(3.0, 4.0, 5.0);
    }

    @Test
    public void testConstructor() {

        // check if coordinates have been initialized
        assertNotNull(cartesianCoordinate1);
        assertNotNull(cartesianCoordinate2);
        assertNotNull(cartesianCoordinate3);
        assertNotNull(cartesianCoordinate4);
    }

    @Test
    public void testGetter() {

        // test X
        assertEquals(0.0, cartesianCoordinate1.getX(), 0.0);

        // test zero + Y
        assertEquals(0.0, cartesianCoordinate1.getY(), 0.0);

        // test positive + Z
        assertEquals(42.0, cartesianCoordinate3.getZ(), 0.0);

    }

    @Test
    public void testSet_and_Equal() throws CoordinateException{

        // create new coordinate and change its values
        CartesianCoordinate test_Cartesian_coordinate = CartesianCoordinate.getCartesianCoordinateObject(3.0, 2.0, 1.0);

        CartesianCoordinate c1 = test_Cartesian_coordinate.setX(42.0);

        // test if u get the right value for a specific key
        assertTrue(CartesianCoordinate.cartesianCoordinateMap.get(test_Cartesian_coordinate) == test_Cartesian_coordinate);

        // object is already stored in map
        assertTrue(CartesianCoordinate.getCartesianCoordinateObject(3.0, 2.0, 1.0) == CartesianCoordinate.cartesianCoordinateMap.get(test_Cartesian_coordinate));

        // check if new object is also stored
        assertTrue(CartesianCoordinate.cartesianCoordinateMap.get(c1) == CartesianCoordinate.getCartesianCoordinateObject(42.0, 2.0, 1.0));

        // test isEqual + equals
        assertTrue(cartesianCoordinate1.isEqual(CartesianCoordinate.getCartesianCoordinateObject(0.0, 0.0, 3.0)));
        assertTrue(cartesianCoordinate1.equals(CartesianCoordinate.getCartesianCoordinateObject(0.0, 0.0, 3.0)));

    }

    @Test
    public void testGetDistance() throws CoordinateException{

        // test with positive and negative x,y,z values
        CartesianCoordinate test_Cartesian_coordinate_1 = CartesianCoordinate.getCartesianCoordinateObject(1.0, 3.0, 42.0);
        CartesianCoordinate test_Cartesian_coordinate_2 = CartesianCoordinate.getCartesianCoordinateObject(4.0, 1.0, 42.0);

        double distance = test_Cartesian_coordinate_1.getDistance(test_Cartesian_coordinate_2);
        double distance_the_other_way = test_Cartesian_coordinate_2.getDistance(test_Cartesian_coordinate_1);
        assertEquals(distance, 3.6, 0.1);

        // the other way around should be the same distance
        assertEquals(distance, distance_the_other_way, 0.0);

        // test edge cases
        CartesianCoordinate test_Cartesian_coordinate_3 = CartesianCoordinate.getCartesianCoordinateObject(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
        CartesianCoordinate test_Cartesian_coordinate_4 = CartesianCoordinate.getCartesianCoordinateObject(Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE);
        CartesianCoordinate test_Cartesian_coordinate_5 = CartesianCoordinate.getCartesianCoordinateObject(0.0, 0.0, 0.0);

        assertEquals(test_Cartesian_coordinate_3.getDistance(test_Cartesian_coordinate_5), Double.POSITIVE_INFINITY, 0.0);
        assertEquals(test_Cartesian_coordinate_4.getDistance(test_Cartesian_coordinate_5), 0.0, 0.0);
    }

    @Test
    public void testPersistence() throws SQLException, CoordinateException {

        CartesianCoordinate cartesianCoordinate = CartesianCoordinate.getCartesianCoordinateObject(0.0, 0.0, 7.0);
        SphericCoordinate sphericCoordinate = SphericCoordinate.getSphericCoordinateObject(0.10, 0.20, 3.0);
        ResultSet testset = Mockito.mock(ResultSet.class);

        cartesianCoordinate.writeOn(testset);
        sphericCoordinate.writeOn(testset);

        // test persistence cartesian
        verify(testset, Mockito.times(1)).updateDouble("coordinate_x", cartesianCoordinate.getX());
        verify(testset, Mockito.times(1)).updateDouble("coordinate_y", cartesianCoordinate.getY());
        verify(testset, Mockito.times(1)).updateDouble("coordinate_z", cartesianCoordinate.getZ());

        // test persistence spheric
        verify(testset, Mockito.times(1)).updateDouble("coordinate_theta", sphericCoordinate.getTheta());
        verify(testset, Mockito.times(1)).updateDouble("coordinate_phi", sphericCoordinate.getPhi());
        verify(testset, Mockito.times(1)).updateDouble("coordinate_radius", sphericCoordinate.getRadius());

    }

    @Test
    public void testConversion() throws CoordinateException{

        SphericCoordinate sphericCoordinate1 = new SphericCoordinate(cartesianCoordinate4);
        SphericCoordinate sphericCoordinate2 = SphericCoordinate.getSphericCoordinateObject(0.93, 0.79, 7.07);
        CartesianCoordinate cartesianCoordinate3 = new CartesianCoordinate(sphericCoordinate1);

        assertEquals(sphericCoordinate1.getPhi(), sphericCoordinate2.getPhi(), 0.01);
        assertEquals(sphericCoordinate1.getTheta(), sphericCoordinate2.getTheta(), 0.01);
        assertEquals(sphericCoordinate1.getRadius(), sphericCoordinate2.getRadius(), 0.01);

        assertEquals(cartesianCoordinate4.getX(), cartesianCoordinate3.getX(), 0.01);
        assertEquals(cartesianCoordinate4.getY(), cartesianCoordinate3.getY(), 0.01);
        assertEquals(cartesianCoordinate4.getZ(), cartesianCoordinate3.getZ(), 0.01);


        // if the two coordinate types are interchangeable, these isEqual tests should return true
        assertTrue(sphericCoordinate1.isEqual(cartesianCoordinate3));
        assertTrue(cartesianCoordinate3.isEqual(sphericCoordinate1));
    }

    @Test
    public void testDistance() throws CoordinateException{

        SphericCoordinate sphericCoordinate2 = SphericCoordinate.getSphericCoordinateObject(0.93, 0.79, 7.07);
        SphericCoordinate sphericCoordinate3 = SphericCoordinate.getSphericCoordinateObject(0.23, 0.41, 15.02);

        double distance = sphericCoordinate2.getCartesianDistance(sphericCoordinate3);
        double self_distance = sphericCoordinate2.getCartesianDistance(sphericCoordinate2);

        // simple distance tests
        assertEquals(distance, 9.62, 0.01);
        assertEquals(self_distance, 0.0, 0.01);

    }

    @Test
    public void testCentralAngle() throws CoordinateException{

        SphericCoordinate sphericCoordinate1 = SphericCoordinate.getSphericCoordinateObject(1.2, 0.45, 3.14);
        SphericCoordinate sphericCoordinate2 = SphericCoordinate.getSphericCoordinateObject(0.6, 0.45, 3.14);
        SphericCoordinate sphericCoordinate3 = SphericCoordinate.getSphericCoordinateObject(0.0, 0.0, 0.0);
        SphericCoordinate sphericCoordinate4 = SphericCoordinate.getSphericCoordinateObject(0.0, 0.0, 0.0);

        double a = sphericCoordinate1.getCentralAngle(sphericCoordinate2);
        double b = sphericCoordinate2.getCentralAngle(sphericCoordinate1);
        double c = sphericCoordinate3.getCentralAngle(sphericCoordinate4);
        double d = sphericCoordinate1.getCentralAngle(sphericCoordinate1);

        // simple angle
        assertEquals(a, 0.6, 0.01);

        // the other way around
        assertEquals(b, 0.6, 0.01);

        //two equal coordinates
        assertEquals(c, 0.0, 0.01);

        // self angle
        assertEquals(d, 0.0, 0.01);

    }

    @Test
    public void testIsEqual() throws CoordinateException{
        SphericCoordinate sphericCoordinate1 = SphericCoordinate.getSphericCoordinateObject(2*Math.PI, 0.6, 3.14);
        SphericCoordinate sphericCoordinate2 = SphericCoordinate.getSphericCoordinateObject(6*Math.PI, 0.6, 3.14);

        SphericCoordinate sphericCoordinate3 = SphericCoordinate.getSphericCoordinateObject(0.2, 0.0, 420.0);
        SphericCoordinate sphericCoordinate4 = SphericCoordinate.getSphericCoordinateObject(0.2, 2*Math.PI, 420.0);

        SphericCoordinate coordinate5 = SphericCoordinate.getSphericCoordinateObject(0, 0, 0);
        SphericCoordinate coordinate6 = SphericCoordinate.getSphericCoordinateObject(0, 0, 0);

        Coordinate coordinate1 = SphericCoordinate.getSphericCoordinateObject(32.0 % (2 * Math.PI), 34.0 % (2 * Math.PI), 13.0);
        Coordinate coordinate2 = SphericCoordinate.getSphericCoordinateObject(0.584, 2.584, 13.0);

        // check constructor functionality
        assertTrue(coordinate1.isEqual(coordinate2));

        // phi is a manifold of 2 PI
        assertTrue(sphericCoordinate1.isEqual(sphericCoordinate2));

        // theta is a manifold of 2 PI
        assertTrue(sphericCoordinate3.isEqual(sphericCoordinate4));

        // small values should yield the same coordinate object
        assertTrue(coordinate5 == coordinate6);

    }

    @Test (expected = IllegalArgumentException.class)
    public void testNullSafetyGCA() throws CoordinateException {
        SphericCoordinate coordinate = new SphericCoordinate(32.0, 34.0, 13.0);

        coordinate.getCentralAngle(null);

    }

    @Test (expected = IllegalArgumentException.class)
    public void testNullSafetyGCD() throws CoordinateException {
        CartesianCoordinate coordinate = CartesianCoordinate.getCartesianCoordinateObject(2.0, 24.0, 13.0);

        coordinate.getCartesianDistance(null);
    }

    @Test (expected = CoordinateException.class)
    public void testNegativeSC() throws CoordinateException {
        SphericCoordinate coordinate = new SphericCoordinate(-2.0, -24.0, -13.0);

    }

    @Test (expected = CoordinateException.class)
    public void testNaNArgument() throws CoordinateException {
        CartesianCoordinate coordinate = CartesianCoordinate.getCartesianCoordinateObject(Double.NaN, 2.0, 3.0);
    }

    @Test (expected = NullPointerException.class)
    public void testNullCoordinate() throws NullPointerException, CoordinateException {
        SphericCoordinate coordinate1 = null;
        SphericCoordinate coordinate2 = new SphericCoordinate(1.0, 2.0, 3.0);

        coordinate1.getCentralAngle(coordinate2);
    }

    @Test (expected = NullPointerException.class)
    public void testNullArgument() throws CoordinateException {
        CartesianCoordinate coordinate = new CartesianCoordinate(null);
    }

}
