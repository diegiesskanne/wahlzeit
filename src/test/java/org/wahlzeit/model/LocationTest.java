package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class LocationTest {

    private Location location;

    @Before
    public void initLocation() {
        location = new Location(new Coordinate(42.0, 420.0, -42.0));
    }

    @Test
    public void testConstructor() {

        // check if coordinate has been initialized
        assertNotNull(location);

    }

    @Test
    public void testGetter() {

        Coordinate coordinate = new Coordinate(42.0, 420.0, -42.0);

        // check getter
        assertTrue(location.getCoordinate().isEqual(coordinate));
    }
}
