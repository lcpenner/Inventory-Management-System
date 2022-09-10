package model;

/**This class is the model for an Outsourced Part. */
public class Outsourced extends Part{

    /**Company name for the outsourced part. */
    private String companyName;

    /**Constructor for an outsourced part. */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
            super(id, name, price, stock, min, max);
            this.companyName = companyName;
    }

    /**Getter for the outsourced part's company name. */
    public String getCompanyName() {
        return companyName;
    }

    /**Setter for the outsourced part's company name. */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
