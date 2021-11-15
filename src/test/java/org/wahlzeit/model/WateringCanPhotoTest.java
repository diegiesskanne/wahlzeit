package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Mock;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class WateringCanPhotoTest {

    private Watering_Can_Photo wcphoto1;
    private Watering_Can_Photo wcphoto2;

    @Mock
    private ResultSet resultSetMock;

    @Before
    public void initPhoto() throws SQLException {

        resultSetMock = Mockito.mock(ResultSet.class);
        PhotoId testid = new PhotoId(1);
        wcphoto1 = Watering_Can_Photo_Factory.getInstance().createWateringCanPhoto();
        wcphoto2 = Watering_Can_Photo_Factory.getInstance().createWateringCanPhoto(testid);

    }

    private Watering_Can_Photo initPhotoMock(final ResultSet rset) throws SQLException {

        when(rset.getString("owner_email_address")).thenReturn("max.mustermann@gmail.com");
        when(rset.getString("owner_home_page")).thenReturn("http://wahlzeit.org/yx32ebyn");
        when(rset.getString(eq("color"))).thenReturn("green");

        return new Watering_Can_Photo(rset);
    }

    @Test
    public void testConstructors() {
        assertNotNull(wcphoto1);
        assertNotNull(wcphoto2);
    }

    @Test
    public void testWateringCanPhoto() throws SQLException {

        Watering_Can_Photo wcphoto3 = initPhotoMock(resultSetMock);
        Coordinate coordinate = new Coordinate(4.0, 2.0, 0.0);
        wcphoto3.location = new Location(coordinate);

        String testcolor = "blue";
        wcphoto3.setColor(testcolor);

        wcphoto3.location.writeOn(resultSetMock);

        assertEquals(wcphoto3.getColor(), Color.blue);
        verify(resultSetMock, Mockito.times(1)).updateDouble("coordinate_x", wcphoto3.location.getCoordinate().getX());
        verify(resultSetMock, Mockito.times(1)).updateDouble("coordinate_y", wcphoto3.location.getCoordinate().getY());
        verify(resultSetMock, Mockito.times(1)).updateDouble("coordinate_z", wcphoto3.location.getCoordinate().getZ());
    }

}
