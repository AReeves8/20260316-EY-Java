/**
 * This is equivalent to the Warehouse record 
 *      (with the exception that nothing is final in this class)
 * 
 */


public class WarehouseClass implements Comparable<WarehouseClass> {
    
    private String name; 
    private String location; 
    private int area; 
    private int numStorageLocations;

    public WarehouseClass() {
    }

    public WarehouseClass(String name, String location, int area, int numStorageLocations) {
        this.name = name;
        this.location = location;
        this.area = area;
        this.numStorageLocations = numStorageLocations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getNumStorageLocations() {
        return numStorageLocations;
    }

    public void setNumStorageLocations(int numStorageLocations) {
        this.numStorageLocations = numStorageLocations;
    }

   

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((location == null) ? 0 : location.hashCode());
        result = prime * result + area;
        result = prime * result + numStorageLocations;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        WarehouseClass other = (WarehouseClass) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (location == null) {
            if (other.location != null)
                return false;
        } else if (!location.equals(other.location))
            return false;
        if (area != other.area)
            return false;
        if (numStorageLocations != other.numStorageLocations)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "WarehouseClass [name=" + name + ", location=" + location + ", area=" + area + ", numStorageLocations="
                + numStorageLocations + "]";
    }

    /**
     * Comparable and compareTo are used to give your class a way to be sorted
     *      used by TreeMaps, Collection.sort(), TreeSet, PriorityQueue
     */

    @Override
    public int compareTo(WarehouseClass o) {

        // creates a sorting of Warehouses by the number of storage locations. 
        return o.getNumStorageLocations() - this.getNumStorageLocations();
    }

}
