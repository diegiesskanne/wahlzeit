package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class PhotoTest {

    private Photo photo1;
    private Photo photo2;

    @Before
    public void initPhoto() {
        photo1 = new Photo();
        photo2 = new Photo();
    }

    @Test
    public void testConstructor() {
        assertNotNull(photo1);
        assertNotNull(photo2);
    }

    @Test
    public void testGet() throws CoordinateException{

        photo1.location = new Location(CartesianCoordinate.getCartesianCoordinateObject(42.0, 420.0, -42.0));
        photo2.location = new Location(SphericCoordinate.getSphericCoordinateObject(0.2, 0.4, 33.0));
        assertEquals(photo1.location.getCoordinate().asCartesianCoordinate().getX(), 42.0, 0.0);
        assertEquals(photo2.location.getCoordinate().asSphericCoordinate().getRadius(), 33.0, 0.0);
    }

}
