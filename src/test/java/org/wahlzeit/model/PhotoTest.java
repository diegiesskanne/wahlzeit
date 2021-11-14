package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;
import org.wahlzeit.services.EmailAddress;
import org.wahlzeit.services.Language;
import org.wahlzeit.services.SysLog;

import java.io.File;
import java.net.URL;
import java.sql.ResultSet;

import static org.junit.Assert.*;


public class PhotoTest {

    private Photo photo;

    @Before
    public void initPhoto() {
        photo = new Photo();

    }

    @Test
    public void testConstructor() {
        assertNotNull(photo);
    }

    @Test
    public void testGet() {


        photo.location = new Location(new Coordinate(42.0, 420.0, -42.0));
        assertEquals(photo.location.getCoordinate().getX(), 42.0, 0.0);
    }

}
