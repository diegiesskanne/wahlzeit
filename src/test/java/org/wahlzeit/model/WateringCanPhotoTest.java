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

    private WateringCanPhoto wcphoto1;
    private WateringCanPhoto wcphoto2;

    @Mock
    private ResultSet resultSetMock;

    @Before
    public void initPhoto() throws SQLException {

        resultSetMock = Mockito.mock(ResultSet.class);
        PhotoId testid = new PhotoId(1);
        wcphoto1 = WateringCanPhotoFactory.getInstance().createWateringCanPhoto();
        wcphoto2 = WateringCanPhotoFactory.getInstance().createWateringCanPhoto(testid);

    }

    private WateringCanPhoto initPhotoMock(final ResultSet rset) throws SQLException {

        when(rset.getString("owner_email_address")).thenReturn("max.mustermann@gmail.com");
        when(rset.getString("owner_home_page")).thenReturn("http://wahlzeit.org/yx32ebyn");
        when(rset.getString(eq("color"))).thenReturn("green");

        return new WateringCanPhoto(rset);
    }

    @Test
    public void testConstructors() {
        assertNotNull(wcphoto1);
        assertNotNull(wcphoto2);
    }

    @Test
    public void testWateringCanPhoto() throws SQLException, CoordinateException{

        WateringCanPhoto wcphoto3 = initPhotoMock(resultSetMock);
        CartesianCoordinate cartesianCoordinate = CartesianCoordinate.getCartesianCoordinateObject(4.0, 2.0, 0.0);
        wcphoto3.location = new Location(cartesianCoordinate);

        wcphoto3.location.writeOn(resultSetMock);

        verify(resultSetMock, Mockito.times(1)).updateDouble("coordinate_x", wcphoto3.location.getCoordinate().asCartesianCoordinate().getX());
        verify(resultSetMock, Mockito.times(1)).updateDouble("coordinate_y", wcphoto3.location.getCoordinate().asCartesianCoordinate().getY());
        verify(resultSetMock, Mockito.times(1)).updateDouble("coordinate_z", wcphoto3.location.getCoordinate().asCartesianCoordinate().getZ());
    }

}
