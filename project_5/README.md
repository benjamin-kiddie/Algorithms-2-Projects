# BigInteger Multiplication and XGCD

## Project Overview
Assigned as project 5 of course CS 1501 (Algorithms and Data Structures 2). Requires implementing multiplication and Euclidean GCD for a BigInteger object (HeftyInteger) of arbitrary length. Method details are as follows:

1. `multiply(HeftyInteger other)` - Compute the product of this and other.
2. `XGCD(HeftyInteger other)` - Run the extended Euclidean algorithm on this and other.

Multiplication is implemented using the grade school algorithm over bytes. XGCD is implemented recursively using a combination of division, multiplication, and a provided subtraction method.

## Contributors
Benjamin Kiddie - Multiplication and XGCD implementations, along with division, shifting, extension, truncation, and equating methods. 

Dr. Nicholas Farnan - Other methods and driver program. Project design and assignment.