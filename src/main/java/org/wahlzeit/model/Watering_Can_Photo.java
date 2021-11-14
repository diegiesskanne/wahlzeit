package org.wahlzeit.model;

import org.wahlzeit.services.SysLog;

import java.awt.*;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Watering_Can_Photo extends Photo {

    private Color color;

    public Watering_Can_Photo_Manager manager;

    /**
     * @methodtype constructor
     */

    public Watering_Can_Photo(){
        super();
    }

    /**
     * @methodtype constructor
     */

    public Watering_Can_Photo(PhotoId id){
        super(id);
    }

    /**
     * @methodtype constructor
     */

    public Watering_Can_Photo(Color color){
        super();
        this.color = color;
    }

    /**
     *
     * @methodtype constructor
     */

    public Watering_Can_Photo(ResultSet rset) throws SQLException {
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
        }
        System.out.println("HELLO");
        System.out.println(color);
        this.color = color;

    }

    public Watering_Can_Photo_Manager getManager(){
        return manager;
    }

    public void writeOn(ResultSet resultSet) throws SQLException {
        super.writeOn(resultSet);
        resultSet.updateInt("color", Integer.parseInt(this.getColor().toString()));
    }

    public void readFrom(ResultSet resultSet) throws SQLException {
        this.setColor(resultSet.getString("color"));
    }
}
