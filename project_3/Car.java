/**
 * Car object for CS1501 Project 3
 * @author	Benjamin Kiddie
 */
package project_3;

public class Car implements Car_Inter {
    
    // Properties of this car
    private String vin;
    private String make;
    private String model;
    private int price;
    private int mileage;
	private String color;
	// Indices for all four heaps
    private int gp;
	private int gm;
	private int mmp;
	private int mmm;

    /**
     * Constructor for Car object
     *
     * @param v     Vin of the car
     * @param ma    Make of the car 
     * @param mo    Model of car
     * @param p     Price of car
     * @param mi    Mileage of car
     * @param c     Color of car 
     */
    public Car(String v, String ma, String mo, int p, int mi, String c) {
        this.vin = v;
        this.make = ma;
        this.model = mo;
        this.price = p;
        this.mileage = mi;
        this.color = c;
    }

    /**
	 * Getter for the VIN attribute
	 *
	 * @return 	String The VIN
	 */
	public String getVIN() {
        return vin;
    }

	/**
	 * Getter for the make attribute
	 *
	 * @return 	String The make
	 */
	public String getMake() {
        return make;
    }

	/**
	 * Getter for the model attribute
	 *
	 * @return 	String The model
	 */
	public String getModel() {
        return model;
    }

	/**
	 * Getter for the price attribute
	 *
	 * @return 	String The price
	 */
	public int getPrice() {
        return price;
    }

	/**
	 * Getter for the mileage attribute
	 *
	 * @return 	String The mileage
	 */
	public int getMileage() {
        return mileage;
    }

	/**
	 * Getter for the color attribute
	 *
	 * @return 	String The color
	 */
	public String getColor() {
        return color;
    }

	/**
	 * Getter for indices
	 * 
	 * @param type	Specific index to be retrieved
	 * 
	 * @return	Index requested
	 */
	public int getIndex(int type) {
		switch(type) {
			case 1:
				return gp;
			case 2:
				return gm;
			case 3:
				return mmp;
			case 4:
				return mmm;
		}
		return -1;
	}

	/**
	 * Setter for the price attribute
	 *
	 * @param 	newPrice The new Price
	 */
	public void setPrice(int newPrice) {
        price = newPrice;
    }

	/**
	 * Setter for the mileage attribute
	 *
	 * @param 	newMileage The new Mileage
	 */
	public void setMileage(int newMileage) {
        mileage = newMileage;
    }

	/**
	 * Setter for the color attribute
	 *
	 * @param 	newColor The new color
	 */
	public void setColor(String newColor) {
        color = newColor;
    }

	/**
	 * Setter for indices
	 *
	 * @param i 	The new index
	 * @param type	Specific index to be altered
	 */
	public void setIndex(int i, int type) {
		switch(type) {
			case 1:
				gp = i;
				return;
			case 2:
				gm = i;
				return;
			case 3:
				mmp = i;
				return;
			case 4:
				mmm = i;
				return;
		}
		return;
	}

}