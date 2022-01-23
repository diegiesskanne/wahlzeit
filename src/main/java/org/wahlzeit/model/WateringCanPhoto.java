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

    private Color color;

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
        this.color = color;
    }

    /**
     *
     * @methodtype constructor
     */

    public WateringCanPhoto(ResultSet rset) throws SQLException {
        super(rset);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(String scolor){

        Color color;
        try {
            Field field = Color.class.getField(scolor);
            color = (Color)field.get(null);
        } catch (Exception e) {
            color = null; // Not defined
            throw new NullPointerException("color is null");
        }
        this.color = color;

    }

    public WateringCanPhotoManager getManager(){
        return manager;
    }

    @Override
    public void writeOn(ResultSet resultSet) throws SQLException {
        super.writeOn(resultSet);
        resultSet.updateInt("color", Integer.parseInt(this.getColor().toString()));
    }

    @Override
    public void readFrom(ResultSet resultSet) throws SQLException {
        super.readFrom(resultSet);
        this.setColor(resultSet.getString("color"));
    }
}
