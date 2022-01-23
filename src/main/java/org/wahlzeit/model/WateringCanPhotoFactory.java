package org.wahlzeit.model;

import org.wahlzeit.annotations.PatternInstance;
import org.wahlzeit.services.SysLog;

import java.sql.ResultSet;
import java.sql.SQLException;

@PatternInstance(
        patternName = "Abstract Factory",
        participants = {
                "Concrete Factory"
        }
)
public class WateringCanPhotoFactory extends PhotoFactory {

    private static WateringCanPhotoFactory instance = null;

    public static synchronized WateringCanPhotoFactory getInstance() {
        if (instance == null){
            SysLog.logSysInfo("setting generic Watering_Can_Photo_Factory");
            setInstance(new WateringCanPhotoFactory());
        }

        return instance;
    }

    protected static synchronized void setInstance(WateringCanPhotoFactory watering_can_photo_factory) {
        if (instance != null) {
            throw new IllegalStateException("There is already an instance");
        }
        instance = watering_can_photo_factory;
    }

    public static void initialize() { getInstance(); }

    protected WateringCanPhotoFactory() {
        // nothing to do
    }

    public WateringCanPhoto createWateringCanPhoto() {
        return new WateringCanPhoto();
    }

    public WateringCanPhoto createWateringCanPhoto(PhotoId id){
        if (id == null) throw new IllegalArgumentException("id should not be null");
        return new WateringCanPhoto(id);
    }

    public WateringCanPhoto createWateringCanPhoto(ResultSet resultSet) throws SQLException {
        return new WateringCanPhoto(resultSet);
    }
}
