/**
 * A driver for CS1501 Project 5
 * @author	Dr. Farnan, Benjamin Kiddie
 */
package project_5;

import java.math.BigInteger;
import java.io.*;

public class App {

    public static void main(String[] args) {
        byte[] aVal = {-73, 100, 118, 4, -28, -24, 63, 125, 38, 127, -85, -91, -15, 96, 65, 41, -74, -6, -49, -96, -5, 83, 69, -34, -66, -111, -92, -98, -17, -106, 98, -26, -30, -60, 84, -81, 115, -21, 120, -46, 126, 111, 55, -114, 27, 92, 28, 33, 79, -58, 62, 74, -55, 45, 124, 51, -115, 26, 21, -32, 106, 123, 6, 43, -120, 94, -8, -39, -119, 85, -122, 117, 72, -53, 81, 114, 29, -62, -65, 66, 109, -70, 18, 1};
        byte[] bVal = {65, 70, 42, 27, 71, 110, -84, -83, 117, -90, 127, -1, -110, -77, 10, -56, -35, -113, -15, 100, 95, -37, 101, 120, 28, -74, 16, -27, -58, -24, 111, 126, 38, 91, -122, 47, 54, 51, 32, 82, -5, -124, 50, -51, -14, 62, 6, -30, -64, -8, 74, 12, -49, -85, -40, -16, -87, 36, 93, -44, -120, -127, 99, 23, -28, -118, -72, 119, 102, -68, -94, -96, -76, -93, 59, -19, -6, -38, 97, -114, 13, -31, -25, -32};
        HeftyInteger hiA = new HeftyInteger(aVal);
        HeftyInteger hiB = new HeftyInteger(bVal);
        BigInteger biA = new BigInteger(aVal);
        BigInteger biB = new BigInteger(bVal);

        HeftyInteger hiP = hiA.multiply(hiB);
        BigInteger biP = biA.multiply(biB);
        System.out.print("Product: ");
        for (byte b : hiP.getVal())
            System.out.print(b + " ");
        System.out.println();
        if (biP.compareTo(new BigInteger(hiP.getVal())) == 0)
            System.out.println("Product value is correct.\n");

        HeftyInteger[] hiGCD = hiA.XGCD(hiB);
        System.out.print("GCD: ");
        for (byte b : hiGCD[0].getVal())
            System.out.print(b + " ");
        System.out.println();
        System.out.print("S: ");
        for (byte b : hiGCD[1].getVal())
            System.out.print(b + " ");
        System.out.println();
        System.out.print("T: ");
        for (byte b : hiGCD[2].getVal())
            System.out.print(b + " ");
        System.out.println();
        BigInteger biGCD = biA.gcd(biB);
        if (biGCD.compareTo(new BigInteger(hiGCD[0].getVal())) == 0)
            System.out.println("GCD value is correct.");
        HeftyInteger estGCD = hiA.multiply(hiGCD[1]).add(hiB.multiply(hiGCD[2]));
        if (biGCD.compareTo(new BigInteger(estGCD.getVal())) == 0)
            System.out.println("s and t values are correct.");
    }
}
