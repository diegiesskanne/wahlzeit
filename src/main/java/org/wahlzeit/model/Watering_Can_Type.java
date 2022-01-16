package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Watering_Can_Type extends DataObject {

    protected Watering_Can_Type superType = null;
    protected Set<Watering_Can_Type> subTypes = new HashSet<Watering_Can_Type>();
    protected String typeName;
    protected int capacity = 0;

    public Watering_Can_Type(String name) {
        this.typeName = name;
    }

    public Watering_Can_Type getSuperType() {
        return superType;
    }

    public void setSuperType(Watering_Can_Type watering_can_type) {
        superType = watering_can_type;
    }

    public String getType() {
        return typeName;
    }

    public Iterator<Watering_Can_Type> getSubTypeIterator() {
        return subTypes.iterator();
    }

    public Watering_Can createInstance() {
        return new Watering_Can(this);
    }

    public boolean isSubType() {
        return superType != null;
    }

    public int getCapacity(){
        return this.capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void addSubType(Watering_Can_Type watering_can_type) {
        assert (watering_can_type != null) : "my watering can type is null";
        watering_can_type.setSuperType(this);
        subTypes.add(watering_can_type);
    }

    public boolean hasInstance(Watering_Can watering_can) {
        assert (watering_can != null) : "my watering can is null";

        if (watering_can.getWatering_Can_Type() == this){
            return true;
        }

        for (Watering_Can_Type type : subTypes) {
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
