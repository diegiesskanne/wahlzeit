package org.wahlzeit.model;

import org.wahlzeit.main.ServiceMain;
import org.wahlzeit.services.SysLog;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Watering_Can_Photo_Manager extends PhotoManager{

    protected static final Watering_Can_Photo_Manager instance = new Watering_Can_Photo_Manager();

    /**
     * In-memory cache for photos
     */
    protected Map<PhotoId, Watering_Can_Photo> photoCache = new HashMap<PhotoId, Watering_Can_Photo>();

    public Watering_Can_Photo_Manager(){
        photoTagCollector = Watering_Can_Photo_Factory.getInstance().createPhotoTagCollector();
    }

    public static Watering_Can_Photo getWateringCanPhoto(String id){
        return getWateringCanPhoto(PhotoId.getIdFromString(id));
    }

    public static Watering_Can_Photo getWateringCanPhoto(PhotoId id){
        return instance.getPhotoFromId(id);
    }

    public Watering_Can_Photo getPhotoFromId(PhotoId id) {
        if (id.isNullId()) {
            return null;
        }

        Watering_Can_Photo result = doGetPhotoFromId(id);

        if (result == null) {
            try {
                PreparedStatement stmt = getReadingStatement("SELECT * FROM photos WHERE id = ?");
                result = (Watering_Can_Photo) readObject(stmt, id.asInt());
            } catch (SQLException sex) {
                SysLog.logThrowable(sex);
            }
            if (result != null) {
                doAddPhoto(result);
            }
        }

        return result;
    }

    protected Watering_Can_Photo doGetPhotoFromId(PhotoId id) {
        return photoCache.get(id);
    }

    protected Watering_Can_Photo createObject(ResultSet resultSet) throws SQLException{
        return Watering_Can_Photo_Factory.getInstance().createWateringCanPhoto(resultSet);
    }

    /**
     * @methodtype command
     *
     * Load all persisted photos. Executed when Wahlzeit is restarted.
     */
    public void addPhoto(Watering_Can_Photo photo) {
        PhotoId id = photo.getId();
        assertIsNewPhoto(id);
        doAddPhoto(photo);

        try {
            PreparedStatement stmt = getReadingStatement("INSERT INTO photos(id) VALUES(?)");
            createObject(photo, stmt, id.asInt());
            ServiceMain.getInstance().saveGlobals();
        } catch (SQLException sex) {
            SysLog.logThrowable(sex);
        }
    }

    /**
     * @methodtype command
     * @methodproperties primitive
     */
    protected void doAddPhoto(Watering_Can_Photo myPhoto) {
        photoCache.put(myPhoto.getId(), myPhoto);
    }

    /**
     *
     */
    public void savePhoto(Watering_Can_Photo photo) {
        try {
            PreparedStatement stmt = getUpdatingStatement("SELECT * FROM photos WHERE id = ?");
            updateObject(photo, stmt);
        } catch (SQLException sex) {
            SysLog.logThrowable(sex);
        }
    }

    /**
     *
     */
    public Watering_Can_Photo getVisiblePhoto(PhotoFilter filter) {
        Watering_Can_Photo result = getPhotoFromFilter(filter);

        if(result == null) {
            java.util.List<PhotoId> list = getFilteredPhotoIds(filter);
            filter.setDisplayablePhotoIds(list);
            result = getPhotoFromFilter(filter);
        }

        return result;
    }

    /**
     *
     */
    protected Watering_Can_Photo getPhotoFromFilter(PhotoFilter filter) {
        PhotoId id = filter.getRandomDisplayablePhotoId();
        Watering_Can_Photo result = getPhotoFromId(id);
        while((result != null) && !result.isVisible()) {
            id = filter.getRandomDisplayablePhotoId();
            result = getPhotoFromId(id);
            if ((result != null) && !result.isVisible()) {
                filter.addProcessedPhoto(result);
            }
        }

        return result;
    }

}
