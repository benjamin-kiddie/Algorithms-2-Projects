/**
 * CarsPQ object for CS1501 Project 3
 * @author	Benjamin Kiddie
 */
package project_3;

// For file IO purposes
import java.io.*;
// For string methods
import java.lang.String;
// To indicate an absent VIN
import java.util.NoSuchElementException;

public class CarsPQ implements CarsPQ_Inter {
    
    // General heaps for price and mileage
    private MinHeap genMileageHeap = new MinHeap();
    private MinHeap genPriceHeap = new MinHeap();
	// Indirection structures based on VIN and M&M
	private VinDLB vinDLB = new VinDLB();
	private MMDLB mmDLB = new MMDLB();
    // Number of cars in system
    private int count = 0;

	/**
	 * Constructor for CarsPQ object
	 *
	 * @param fname	Name of file for attributes to be drawn from
	 */
	public CarsPQ(String fname) {
		// Call population method
		populatePQ(fname);
	} 

	/**
	 * Helper method for constructor. Takes lines from input file,
	 * turns them into Car objects, then puts them into CarsPQ.
	 *
	 * @param fname	Name of file for attributes to be drawn from
	 */
	private void populatePQ(String fname) {
		try {
			// read in from file
			BufferedReader read = new BufferedReader(new FileReader(fname));
			// skip the first line
			String line = read.readLine();
			// for each line, split the specs into individual strings and add them to the system
			// as a car
			while ((line = read.readLine()) != null) {
				String[] specs = line.split(":");
				Car c = new Car(specs[0], specs[1], specs[2], Integer.parseInt(specs[3]),
					Integer.parseInt(specs[4]), specs[5]);
				this.add(c);
			}
			// close the reader
			read.close();
		// handle exceptions
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** 
	 * Helper method for various methods. Indicates whether or not PQ is empty.
	 *
	 * @return	True if PQ is empty, false otherwise
	 */
	private boolean isEmpty() {
		return (count == 0);
	}

	/**
	 * Helper method for various methods. Determines if a VIN is valid.
	 *
	 * @param vin	VIN to be examined
	 *
	 * @return	True if vin is valid, false otherwise
	 */
	private boolean isValid(String vin) {
		if (vin.length() != 17)
			return false;
		for (char x : vin.toCharArray())
			if (x == 'I' || x == 'O' || x == 'Q')
				return false;
		return true;
	}

    /**
	 * Add a new Car to the data structure
	 * Should throw an `IllegalStateException` if there is already car with the
	 * same VIN in the datastructure.
	 *
	 * @param c	Car to be added to the data structure
	 */
	public void add(Car c) throws IllegalStateException {
		// verify the validity of this car's VIN
		String vin = c.getVIN().toUpperCase();
		if (!isValid(vin))
			return;
        // add to the vinDLB. if no addition was actually made, then this is a duplicate VIN
		vinDLB.add(c, vin);
		if (this.count == vinDLB.getCount()) {
			throw new IllegalStateException("A car with VIN " + vin + " already exists.");
		}
		// add car to the general heaps
		genPriceHeap.add(vin, 1, vinDLB);
		genMileageHeap.add(vin, 2, vinDLB);
		// add car to make/model DLB and its associated heaps
		mmDLB.add(c.getMake(), c.getModel(), vin, vinDLB);
		// finally, add to count
		count++;
    }

	/**
	 * Retrieve a new Car from the data structure
	 * Should throw a `NoSuchElementException` if there is no car with the 
	 * specified VIN in the datastructure.
	 *
	 * @param vin	VIN number of the car to be updated
	 */
	public Car get(String vin) throws NoSuchElementException {
		// check the validity of the VIN
		if (!isValid(vin))
			return null;
		// get car from vinDLB
		Car c = vinDLB.getCar(vin);
		// if we got someting, return it
		if (c != null)
			return c;
		// if there is no var with this vin, throw an exception
		throw new NoSuchElementException("Car with VIN " + vin + " does not exist.");
	}

	/**
	 * Update the price attribute of a given car
	 * Should throw a `NoSuchElementException` if there is no car with the 
	 * specified VIN in the datastructure.
	 *
	 * @param vin 		VIN number of the car to be updated
	 * @param newPrice 	The updated price value
	 */
	public void updatePrice(String vin, int newPrice) throws NoSuchElementException {
		// retrieve the car in question.
		vin = vin.toUpperCase();
		Car c = this.get(vin);
		// update the price
		int oldPrice = c.getPrice(); 
		c.setPrice(newPrice);
		// update the position of this car in both price heaps
		genPriceHeap.update(vinDLB.getIndex(vin, 1), 1, oldPrice, vinDLB);
		mmDLB.getHeap(c.getMake(), c.getModel(), 3).update(vinDLB.getIndex(vin, 3), 3, oldPrice, vinDLB);
	}

	/**
	 * Update the mileage attribute of a given car
	 * Should throw a `NoSuchElementException` if there is not car with the 
	 * specified VIN in the datastructure.
	 *
	 * @param vin 			VIN number of the car to be updated
	 * @param newMileage 	The updated mileage value
	 */
	public void updateMileage(String vin, int newMileage) throws NoSuchElementException {
		// retrieve the car in question.
		vin = vin.toUpperCase();
		Car c = this.get(vin);
		// update the mileage
		int oldMileage = c.getMileage();
		c.setMileage(newMileage);
		// update the position of this car in both mileage heaps
		genMileageHeap.update(vinDLB.getIndex(vin, 2), 2, oldMileage, vinDLB);
		mmDLB.getHeap(c.getMake(), c.getModel(), 4).update(vinDLB.getIndex(vin, 4), 4, oldMileage, vinDLB);
	}

	/**
	 * Update the color attribute of a given car
	 * Should throw a `NoSuchElementException` if there is not car with the 
	 * specified VIN in the datastructure.
	 *
	 * @param vin		VIN number of the car to be updated
	 * @param newColor	The updated color value
	 */
	public void updateColor(String vin, String newColor) throws NoSuchElementException {
		// retrieve the car in question
		vin = vin.toUpperCase();
		Car c = this.get(vin);
		// update the color
		c.setColor(newColor);
	}

	/**
	 * Remove a car from the data structure
	 * Should throw a `NoSuchElementException` if there is not car with the 
	 * specified VIN in the datastructure.
	 *
	 * @param vin	VIN number of the car to be removed
	 */
	public void remove(String vin) throws NoSuchElementException {
		// retrieve the car in question
		vin = vin.toUpperCase();
		Car c = this.get(vin);
		// remove this element from our heaps
		genPriceHeap.remove(vin, 1, vinDLB);
		genMileageHeap.remove(vin, 2, vinDLB);
		mmDLB.remove(vin, c.getMake(), c.getModel(), vinDLB);
		// finish by removing it from our reference table
		vinDLB.remove(c.getVIN());
	}

	/**
	 * Get the lowest priced car (across all makes and models)
	 * Should return `null` if the data structure is empty
	 *
	 * @return	Car object representing the lowest priced car
	 */
	public Car getLowPrice() {
		// if we're empty, return null
		if (this.isEmpty())
			return null;
		// otherwise, find the car with the general minimum price
		return vinDLB.getCar(genPriceHeap.getMin());
	}

	/**
	 * Get the lowest priced car of a given make and model
	 * Should return `null` if the data structure is empty
	 *
	 * @param make	The specified make
	 * @param model The specified model
	 * 
	 * @return	Car object representing the lowest priced car
	 */
	public Car getLowPrice(String make, String model) {
		// if we're empty, return null
		if (this.isEmpty())
			return null;
		// otherwise, try to find the car of this make and model with the minimum price
		String vin = mmDLB.getMin(make, model, 3);
		// if we got nothing, return null
		if (vin == null)
			return null;
		// otherwise, return it
		return vinDLB.getCar(vin);
	}

	/**
	 * Get the car with the lowest mileage (across all makes and models)
	 * Should return `null` if the data structure is empty
	 *
	 * @return	Car object representing the lowest mileage car
	 */
	public Car getLowMileage() {
		// if we're empty, return null
		if (this.isEmpty())
			return null;
		// otherwise, find the car with the general minimum mileage
		return vinDLB.getCar(genMileageHeap.getMin());
	}

	/**
	 * Get the car with the lowest mileage of a given make and model
	 * Should return `null` if the data structure is empty
	 *
	 * @param make	The specified make
	 * @param model The specified model
	 *
	 * @return	Car object representing the lowest mileage car
	 */
	public Car getLowMileage(String make, String model) {
		// if we're empty, return null
		if (this.isEmpty())
			return null;
		// otherwise, try to find the car of this make and model with the minimum mileage
		String vin = mmDLB.getMin(make, model, 4);
		// if we got nothing, return null
		if (vin == null)
			return null;
		// otherwise, return it
		return vinDLB.getCar(vin);
	}
}