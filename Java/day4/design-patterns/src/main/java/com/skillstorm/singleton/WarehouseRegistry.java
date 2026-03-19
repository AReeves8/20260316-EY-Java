package com.skillstorm.singleton;

import java.util.LinkedList;
import java.util.List;

/**
 * Warehouse Registry is a class that creates new Warehouses and validates them. 
 */
public class WarehouseRegistry {


    List<Warehouse> warehouses;

    /**
     * Singleton
     *      - ensure there will only ever be one instance
     *      - private constructor
     *      - creating a getInstance method the returns the existing instance if it exists
     *      - create a static instance
     */

    private static WarehouseRegistry instance = null;       // lazy loading


    private WarehouseRegistry() {
        // need to define constructor to ensure it is private
        // default constructor will be public
        warehouses = new LinkedList<>();
    }

    public static WarehouseRegistry getInstance() {

        // if the instance doesn't exist yet, create it
        // lazy loading - only going to create an instance, once it is asked for
        if(instance == null) {
            instance = new WarehouseRegistry();
        }
        return instance;
    }

    public void registerWarehouse(String name, String location) {
    
        // checks to ensure no duplicate locations
        for(Warehouse wh : warehouses) {
            if(wh.location().equals(location)) {
                return;
            }
        }

        warehouses.add(new Warehouse(name, location));
    }

    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

}
