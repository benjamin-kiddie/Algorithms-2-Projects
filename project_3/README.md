# Car Priority Queue

## Project Overview
Assigned as project 3 of course CS 1501 (Algorithms and Data Structures 2). Requires the creation of a priority queues to sort cars based on price and mileage. Implements the following methods:

1. `add(Car c)` - Adds a car to the system.
2. `get(String vin)` - Retrieves a car with the specified vin from the system.
3. `updatePrice(String vin, int newPrice)` - Updates the price of the car with the given vin.
4. `updateMileage(String vin, int newMileage)` - Updates the mileage of the car with the given vin.
5. `updateColor(String vin, String newColor)` - Updates the color of the car with the given vin.
6. `remove(String vin)` - Removes a car with the gievn vin from the system.
7. `getLowPrice()` - Retrieves the car with the lowest price.
8. `getLowPrice(String make, String model)` - Retrieves the car of the given make and model with the lowest price.
9. `getLowMileage()` - Retrieves the car with the lowest mileage.
10. `getLowMileage(String make, String model)` - Retrieves the car of the given make and model with the lowest mileage.

To facilitate this, 2 priority queues based on MinHeaps are implemented, along with DLBs for the VINs and makes and models, nodes for those DLBs, and a car object.

## Contributors
Benjamin Kiddie - CarsPQ, Car, MinHeap, MMDLB, MMDLBNode, VinDLB, and VinDLBNode implementations..

Dr. Nicholas Farnan - CarsPQ_Inter, Car_Inter, and driver program. Project design and assignment.