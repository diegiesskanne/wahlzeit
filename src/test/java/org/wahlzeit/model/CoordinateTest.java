package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

public class CoordinateTest {

    private Coordinate coordinate1;

    private Coordinate coordinate2;

    private Coordinate coordinate3;

    @Before
    public void initCoordinates() {
        coordinate1 = new Coordinate(-2.0, 0.0, 3.0);
        coordinate2 = new Coordinate(0.3333, -(2.0 / 3.0), 24);
        coordinate3 = new Coordinate(0.0, 1243.0, 42.0);
    }

    @Test
    public void testConstructor() {

        // check if coordinates have been initialized
        assertNotNull(coordinate1);
        assertNotNull(coordinate2);
        assertNotNull(coordinate3);
    }

    @Test
    public void testGetter() {

        // test negative + X
        assertEquals(-2.0, coordinate1.getX(), 0.0);

        // test zero + Y
        assertEquals(0.0, coordinate1.getY(), 0.0);

        // test positive + Z
        assertEquals(42.0, coordinate3.getZ(), 0.0);

    }

    @Test
    public void testSet_and_Equal() {

        // create new coordinate and change its values
        Coordinate test_coordinate = new Coordinate(3.0, 2.0, 1.0);
        test_coordinate.setX(42.0);
        test_coordinate.setY(42.0);
        test_coordinate.setZ(42.0);
        assertEquals(test_coordinate.getX(), test_coordinate.getY(), 0.0);
        assertEquals(test_coordinate.getY(), test_coordinate.getZ(), 0.0);

        // test setX with taking the old value into account
        coordinate1.setX(coordinate1.getX() + 3);

        // test setY
        coordinate1.setY(2.0);

        // test previous set operation and isEqual + equals at the same time
        assertTrue(coordinate1.isEqual(new Coordinate(1.0, 2.0, 3.0)));
        assertTrue(coordinate1.equals(new Coordinate(1.0, 2.0, 3.0)));
        assertFalse(coordinate1.isEqual(null));

    }

    @Test
    public void testGetDistance() {

        // test with positive and negative x,y,z values
        Coordinate test_coordinate_1 = new Coordinate(1.0, 3.0, 42.0);
        Coordinate test_coordinate_2 = new Coordinate(4.0, -1.0, -42.0);

        double distance = test_coordinate_1.getDistance(test_coordinate_2);
        double distance_the_other_way = test_coordinate_2.getDistance(test_coordinate_1);
        assertEquals(distance, 84.1, 0.1);

        // the other way around should be the same distance
        assertEquals(distance, distance_the_other_way, 0.0);

        // test edge cases
        Coordinate test_coordinate_3 = new Coordinate(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
        Coordinate test_coordinate_4 = new Coordinate(Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE);
        Coordinate test_coordinate_5 = new Coordinate(0.0, 0.0, 0.0);

        assertEquals(test_coordinate_3.getDistance(test_coordinate_5), Double.POSITIVE_INFINITY, 0.0);
        assertEquals(test_coordinate_4.getDistance(test_coordinate_5), 0.0, 0.0);
    }

    @Test
    public void testDatabase() throws SQLException {

        Coordinate coordinate = new Coordinate(0.0, 0.0, 7.0);
        ResultSet testset = Mockito.mock(ResultSet.class);

        coordinate.writeOn(testset);

        verify(testset, Mockito.times(1)).updateDouble("coordinate_x", coordinate.getX());
        verify(testset, Mockito.times(1)).updateDouble("coordinate_y", coordinate.getY());
        verify(testset, Mockito.times(1)).updateDouble("coordinate_z", coordinate.getZ());
    }

}
