/**
 * A driver for CS1501 Project 3
 * @author	Dr. Farnan
 */
package project_3;

public class App {
	public static void main(String[] args) {

		CarsPQ cpq = new CarsPQ("build/resources/main/cars.txt");

		Car test = cpq.getLowPrice("GMC", "Acadia");
		if (test == null)
			System.out.println("No Acadia's present.\n");

		// test general low price and delete
		Car a = cpq.getLowPrice();
		System.out.println("Cheapest car: " + a.getVIN());
		cpq.remove(a.getVIN());
		Car b = cpq.getLowPrice();
		System.out.println("Second cheapest car: " + b.getVIN() + "\n");

		// test general low mileage and delete
		Car c = cpq.getLowMileage();
		System.out.println("Least-driven car: " + c.getVIN());
		cpq.remove(c.getVIN());
		Car d = cpq.getLowMileage();
		System.out.println("Second least-driven car: " + d.getVIN() + "\n");

		// test mm specific price and delete
		Car e = cpq.getLowPrice("Ford", "Fiesta");
		System.out.println("Cheapest Ford Fiesta: " + e.getVIN());
		cpq.remove(e.getVIN());
		Car f = cpq.getLowPrice("Ford", "Fiesta");
		System.out.println("Second cheapest Ford Fiesta: " + f.getVIN() +"\n");

		Car g = cpq.getLowMileage("Ford", "Fiesta");
		System.out.println("Least-driven Ford Fiesta: " + g.getVIN());
		cpq.remove(g.getVIN());
		Car h = cpq.getLowMileage("Ford", "Fiesta");
		System.out.println("Second least-driven Ford Fiesta: " + h.getVIN() + "\n");

		// test updates
		System.out.println("Before updates -");
		Car i = cpq.getLowPrice();
		Car j = cpq.getLowMileage();
		Car k = cpq.getLowPrice("Honda", "Civic");
		System.out.println("Cheapest car: " + i.getVIN());
		System.out.println("Least-driven car: " + j.getVIN());
		System.out.println(k.getVIN() + "'s color: " + k.getColor());
		cpq.updatePrice(i.getVIN(), 100000);
		cpq.updateMileage(j.getVIN(), 200000);
		cpq.updateColor(k.getVIN(), "Rainbow");
		System.out.println("After updates -");
		i = cpq.getLowPrice();
		j = cpq.getLowMileage();
		System.out.println("Cheapest car: " + i.getVIN());
		System.out.println("Least-driven car: " + j.getVIN());
		System.out.println(k.getVIN() + "'s color: " + k.getColor());
	}
}
