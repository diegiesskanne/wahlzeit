package org.wahlzeit.model;

import org.wahlzeit.annotations.PatternInstance;

import java.awt.*;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

@PatternInstance(
        patternName = "Mediator",
        participants = { "Managed Object" }
)

@PatternInstance(
        patternName = "Abstract Factory",
        participants = { "Concrete Product" }
)
public class WateringCanPhoto extends Photo {

    public WateringCan watering_can;

    public WateringCanPhotoManager manager;

    /**
     * @methodtype constructor
     */

    public WateringCanPhoto(){
        super();
    }

    /**
     * @methodtype constructor
     */

    public WateringCanPhoto(PhotoId id){
        super(id);
    }

    /**
     * @methodtype constructor
     */

    public WateringCanPhoto(Color color){
        super();
    }

    /**
     *
     * @methodtype constructor
     */

    public WateringCanPhoto(ResultSet rset) throws SQLException {
        super(rset);
    }


    public WateringCanPhotoManager getManager(){
        return manager;
    }

    @Override
    public void writeOn(ResultSet resultSet) throws SQLException {
        super.writeOn(resultSet);
    }

    @Override
    public void readFrom(ResultSet resultSet) throws SQLException {
        super.readFrom(resultSet);
    }
}
