package org.wahlzeit.model;

import org.wahlzeit.annotations.PatternInstance;
import org.wahlzeit.main.ServiceMain;
import org.wahlzeit.services.SysLog;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@PatternInstance(
        patternName = "Mediator",
        participants = { "Mediator" }
)
public class WateringCanPhotoManager extends PhotoManager{

    protected static final WateringCanPhotoManager instance = new WateringCanPhotoManager();

    /**
     * In-memory cache for photos
     */
    protected Map<PhotoId, WateringCanPhoto> photoCache = new HashMap<PhotoId, WateringCanPhoto>();

    public WateringCanPhotoManager(){
        photoTagCollector = WateringCanPhotoFactory.getInstance().createPhotoTagCollector();
    }

    public static WateringCanPhoto getWateringCanPhoto(String id) {
        if (id == null) throw new IllegalArgumentException("id should not be null");
        return getWateringCanPhoto(PhotoId.getIdFromString(id));
    }

    public static WateringCanPhoto getWateringCanPhoto(PhotoId id) {
        if (id == null) throw new IllegalArgumentException("id should not be null");
        return instance.getPhotoFromId(id);
    }

    public WateringCanPhoto getPhotoFromId(PhotoId id) {
        if (id.isNullId()) {
            return null;
        }

        WateringCanPhoto result = doGetPhotoFromId(id);

        if (result == null) {
            try {
                PreparedStatement stmt = getReadingStatement("SELECT * FROM photos WHERE id = ?");
                result = (WateringCanPhoto) readObject(stmt, id.asInt());
            } catch (SQLException sex) {
                SysLog.logThrowable(sex);
            }
            if (result != null) {
                doAddPhoto(result);
            }
        }

        return result;
    }

    protected WateringCanPhoto doGetPhotoFromId(PhotoId id) {
        if (id == null) throw new IllegalArgumentException("id should not be null");
        return photoCache.get(id);
    }

    protected WateringCanPhoto createObject(ResultSet resultSet) throws SQLException{
        return WateringCanPhotoFactory.getInstance().createWateringCanPhoto(resultSet);
    }

    /**
     * @methodtype command
     *
     * Load all persisted photos. Executed when Wahlzeit is restarted.
     */
    public void addPhoto(WateringCanPhoto photo) {
        if (photo == null) throw new IllegalArgumentException("photo should not be null");
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
    protected void doAddPhoto(WateringCanPhoto myPhoto) {
        if (myPhoto == null) throw new IllegalArgumentException("myPhoto should not be null");
        photoCache.put(myPhoto.getId(), myPhoto);
    }

    /**
     *
     */
    public void savePhoto(WateringCanPhoto photo) {
        if (photo == null) throw new IllegalArgumentException("photo should not be null");
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
    public WateringCanPhoto getVisiblePhoto(PhotoFilter filter) {
        if (filter == null) throw new IllegalArgumentException("filter should not be null");
        WateringCanPhoto result = getPhotoFromFilter(filter);

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
    protected WateringCanPhoto getPhotoFromFilter(PhotoFilter filter) {
        if (filter == null) throw new IllegalArgumentException("filter should not be null");
        PhotoId id = filter.getRandomDisplayablePhotoId();
        WateringCanPhoto result = getPhotoFromId(id);
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
