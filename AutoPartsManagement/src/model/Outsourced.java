package model;

/**
 * Subclass of part.
 */
public class Outsourced extends Part{
    String companyName;

    /**
     * Constructor for Outsourced.
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     *
     * @param companyName the name to set.
     */
    public void setCompanyName(String companyName) {this.companyName = companyName;}

    /**
     *
     * @return the companyName
     */
    public String getCompanyName() {return companyName;}
}
