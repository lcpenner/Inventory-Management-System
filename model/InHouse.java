package model;

/**This class is the model for an In-house Part . */
public class InHouse extends Part{

    /**Machine ID for the part. */
    private int machineId;

    /**Constructor for an In-house part. */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId){
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**Getter for part's Machine ID*/
    public int getMachineId() {
        return machineId;
    }

    /**Setter for part's Machine ID*/
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

}
