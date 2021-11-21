package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.*;

public class LocationTest {

    private Location location;

    @Before
    public void initLocation() {
        location = new Location(new CartesianCoordinate(42.0, 420.0, -42.0));
    }

    @Test
    public void testConstructor() {

        // check if coordinate has been initialized
        assertNotNull(location);

    }

    @Test
    public void testGetter() {

        CartesianCoordinate cartesianCoordinate = new CartesianCoordinate(42.0, 420.0, -42.0);

        // check getter
        assertTrue(location.getCoordinate().isEqual(cartesianCoordinate));
    }

    @Test
    public void testSaveInDatabase() throws SQLException {

        CartesianCoordinate cartesianCoordinate = Mockito.mock(CartesianCoordinate.class);
        ResultSet testset = Mockito.mock(ResultSet.class);
        Location location = new Location(cartesianCoordinate);

        location.writeOn(testset);

        verify(cartesianCoordinate, Mockito.times(1)).writeOn(testset);

    }
}
