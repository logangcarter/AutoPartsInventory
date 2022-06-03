package model;

/**
 * Subclass of Part.
 */
public class InHouse extends Part{
    private int machineId;

    /**
     * Constructor
     * @param id of the part
     * @param name of the part
     * @param price of the part
     * @param stock of the part
     * @param min of the part
     * @param max of the part
     * @param machineId of the part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * @param machineId the id to set
     */
    public void setMachineId(int machineId) {this.machineId = machineId;}

    /**
     * @return the machineId
     */
    public int getMachineId() {return machineId;}

}
