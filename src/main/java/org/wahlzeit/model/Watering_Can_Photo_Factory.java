package org.wahlzeit.model;

import org.wahlzeit.services.SysLog;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Watering_Can_Photo_Factory extends PhotoFactory {

    private static Watering_Can_Photo_Factory instance = null;

    public static synchronized Watering_Can_Photo_Factory getInstance() {
        if (instance == null){
            SysLog.logSysInfo("setting generic Watering_Can_Photo_Factory");
            setInstance(new Watering_Can_Photo_Factory());
        }

        return instance;
    }

    protected static synchronized void setInstance(Watering_Can_Photo_Factory watering_can_photo_factory) {
        if (instance != null) {
            throw new IllegalStateException("There is already an instance");
        }
        instance = watering_can_photo_factory;
    }

    public static void initialize() { getInstance(); }

    protected Watering_Can_Photo_Factory() {
        // nothing to do
    }

    public Watering_Can_Photo createWateringCanPhoto() {
        return new Watering_Can_Photo();
    }

    public Watering_Can_Photo createWateringCanPhoto(PhotoId id){
        if (id == null) throw new IllegalArgumentException("id should not be null");
        return new Watering_Can_Photo(id);
    }

    public Watering_Can_Photo createWateringCanPhoto(ResultSet resultSet) throws SQLException {
        return new Watering_Can_Photo(resultSet);
    }
}
