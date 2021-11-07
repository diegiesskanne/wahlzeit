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

    /*@Test
    public void insertImageTest() throws Exception {
        PhotoManager pm = PhotoManager.getInstance();
        String pathname = System.getProperty("user.dir");
        File f = new File(pathname + "/src/data/2018-Audi-E-Tron-GT-Concept-005-1080.jpeg"); //Get test picture
        SysLog.logSysInfo("aa");
        SysLog.logSysInfo(f.getPath());
        assertNotNull(f);
        Photo p = pm.createPhoto(f); //Create Photo from picture
        Location l = new Location(new Coordinate(5.0,6.0,7.0));

        p.setOwnerName("TEST");
        p.setOwnerLanguage(Language.GERMAN);
        p.setOwnerHomePage(new URL("https:\\www.test.com"));
        p.setTags(new Tags("tTag"));
        p.setOwnerId(1);
        p.setOwnerEmailAddress(EmailAddress.getFromString("test@gmail.com"));
        p.location_string = p.format_location(l);

        pm.savePhoto(p);
        PhotoId testPhotoId = p.getId();
        pm.photoCache.clear();
        Photo photo = pm.getPhotoFromId(testPhotoId);

        assertEquals(photo.location_string, "X=5.0 Y=6.0, Z=7.0");
    }*/
}
