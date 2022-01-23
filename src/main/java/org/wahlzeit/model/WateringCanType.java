package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class WateringCanType extends DataObject {

    protected WateringCanType superType = null;
    protected Set<WateringCanType> subTypes = new HashSet<WateringCanType>();
    protected String typeName;
    protected int capacity = 0;

    public WateringCanType(String name) {
        this.typeName = name;
    }

    public WateringCanType getSuperType() {
        return superType;
    }

    public void setSuperType(WateringCanType watering_can_type) {
        superType = watering_can_type;
    }

    public String getType() {
        return typeName;
    }

    public Iterator<WateringCanType> getSubTypeIterator() {
        return subTypes.iterator();
    }

    public WateringCan createInstance() {
        return new WateringCan(this);
    }

    public boolean isSubType(WateringCanType wateringCanType) {
        if (subTypes.contains(wateringCanType)){
            return true;
        }
        else{
            for(WateringCanType c : subTypes){
                if(c.isSubType(wateringCanType)){
                    return true;
                }
            }
        }
        return false;
    }

    public int getCapacity(){
        return this.capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void addSubType(WateringCanType watering_can_type) {
        if (watering_can_type == null) {
            throw new IllegalArgumentException("my watering can type is null");
        }
        watering_can_type.setSuperType(this);
        subTypes.add(watering_can_type);
    }

    public boolean hasInstance(WateringCan watering_can) {
        if (watering_can == null) {
            throw new IllegalArgumentException("my watering can is null");
        }
        if (watering_can.getWatering_Can_Type() == this){
            return true;
        }

        for (WateringCanType type : subTypes) {
            if (type.hasInstance(watering_can)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getIdAsString() {
        return null;
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {

    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {

    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) throws SQLException {

    }
}
