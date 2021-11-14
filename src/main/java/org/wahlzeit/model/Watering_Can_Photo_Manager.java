package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Watering_Can_Photo_Manager extends PhotoManager{

    protected static final Watering_Can_Photo_Manager instance = new Watering_Can_Photo_Manager();

    public Watering_Can_Photo_Manager(){
        photoTagCollector = Watering_Can_Photo_Factory.getInstance().createPhotoTagCollector();
    }

    protected Watering_Can_Photo createObject(ResultSet resultSet) throws SQLException{
        return Watering_Can_Photo_Factory.getInstance().createWateringCanPhoto(resultSet);
    }

}
